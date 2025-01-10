package de.kazzutils.features.museum


import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.data.protect.ItemProtectStrategy
import de.kazzutils.event.GuiContainerEvent
import de.kazzutils.event.ItemTossEvent
import de.kazzutils.utils.skyblockfeatures.ItemUtil
import gg.essential.universal.UChat
import net.minecraft.init.Blocks
import net.minecraft.inventory.ContainerChest
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MuseumBlockDrop {

    @SubscribeEvent
    fun onCloseWindow(event: GuiContainerEvent.CloseWindowEvent) {
//        if (!Utils.inSkyblock) return
        if (mc.thePlayer.inventory.itemStack != null) {
            val item = mc.thePlayer.inventory.itemStack
            val extraAttr = ItemUtil.getExtraAttributes(item)
            val strategy = ItemProtectStrategy.findValidStrategy(item, extraAttr, ItemProtectStrategy.ProtectType.USERCLOSEWINDOW) ?: return
            for (slot in event.container.inventorySlots) {
                if (slot.inventory !== mc.thePlayer.inventory || slot.hasStack || !slot.isItemValid(item)) continue
                mc.playerController.windowClick(event.container.windowId, slot.slotNumber, 0, 0, mc.thePlayer)
                notifyStopped(null, "dropping", strategy)
                return
            }
            notifyStopped(null, "closing the window on", strategy)
            event.isCanceled = true
        }
    }


    @SubscribeEvent
    fun onDropItem(event: ItemTossEvent) {
//        if (!Utils.inSkyblock) return
        val strategy = ItemProtectStrategy.findValidStrategy(event.item, ItemUtil.getExtraAttributes(event.item), ItemProtectStrategy.ProtectType.HOTBARDROPKEY) ?: return
        notifyStopped(event, "dropping", strategy)
    }


    @SubscribeEvent
    fun onSlotClick(event: GuiContainerEvent.SlotClickEvent) {
//        if (!Utils.inSkyblock) return
        if (event.container is ContainerChest && ItemProtectStrategy.isAnyToggled()) {
            val inv = event.container.lowerChestInventory
            val chestName = event.chestName
            if (event.slot != null && event.slot.hasStack) {
                var item: ItemStack = event.slot.stack ?: return
                var extraAttr = ItemUtil.getExtraAttributes(item)
                if (chestName.startsWith("Salvage")) {
                    var inSalvageGui = false
                    if (item.displayName.contains("Salvage") || item.displayName.contains("Essence")) {
                        val salvageItem = inv.getStackInSlot(13) ?: return
                        item = salvageItem
                        extraAttr = ItemUtil.getExtraAttributes(item) ?: return
                        inSalvageGui = true
                    }
                    if (inSalvageGui || event.slot.inventory === mc.thePlayer.inventory) {
                        val strategy = ItemProtectStrategy.findValidStrategy(
                            item,
                            extraAttr,
                            ItemProtectStrategy.ProtectType.SALVAGE
                        )
                        if (strategy != null) {
                            notifyStopped(event, "salvaging", strategy)
                            return
                        }
                    }
                }
                if (chestName != "Large Chest" && inv.sizeInventory == 54) {
                    if (!chestName.contains("Auction")) {
                        val sellItem = inv.getStackInSlot(49)
                        if (sellItem != null) {
                            if (sellItem.item === Item.getItemFromBlock(Blocks.hopper) && sellItem.displayName.contains(
                                    "Sell Item"
                                ) || ItemUtil.getItemLore(sellItem).any { s: String -> s.contains("buyback") }
                            ) {
                                if (event.slotId != 49 && event.slot.inventory === mc.thePlayer.inventory) {
                                    val strategy = ItemProtectStrategy.findValidStrategy(
                                        item,
                                        extraAttr,
                                        ItemProtectStrategy.ProtectType.SELLTONPC
                                    )
                                    if (strategy != null) {
                                        notifyStopped(event, "selling", strategy)
                                        return
                                    }
                                }
                            }
                        }
                    } else if (event.slotId == 29 && chestName.startsWith("Create ") && chestName.endsWith(" Auction")) {
                        val itemForSale = inv.getStackInSlot(13)
                        if (itemForSale != null) {
                            val strategy = ItemProtectStrategy.findValidStrategy(
                                itemForSale,
                                ItemUtil.getExtraAttributes(itemForSale),
                                ItemProtectStrategy.ProtectType.SELLTOAUCTION
                            )
                            if (strategy != null) {
                                notifyStopped(event, "auctioning", strategy)
                                return
                            }
                        }
                    }
                }
            }
        }
        if (event.slotId == -999 && mc.thePlayer.inventory.itemStack != null && event.clickType != 5) {
            val item = mc.thePlayer.inventory.itemStack
            val extraAttr = ItemUtil.getExtraAttributes(item)
            val strategy = ItemProtectStrategy.findValidStrategy(item, extraAttr, ItemProtectStrategy.ProtectType.CLICKOUTOFWINDOW)
            if (strategy != null) {
                notifyStopped(event, "dropping", strategy)
                return
            }
        }
        if (event.clickType == 4 && event.slotId != -999 && event.slot != null && event.slot.hasStack) {
            val item = event.slot.stack
            val extraAttr = ItemUtil.getExtraAttributes(item)
            val strategy = ItemProtectStrategy.findValidStrategy(item, extraAttr, ItemProtectStrategy.ProtectType.DROPKEYININVENTORY)
            if (strategy != null) {
                notifyStopped(event, "dropping", strategy)
                return
            }
        }
    }

    private fun notifyStopped(event: Event?, action: String, strategy: ItemProtectStrategy? = null) {
        UChat.chat("§cStopped you from $action that item!${"§7 (§e${strategy?.name}§7)".toStringIfTrue(strategy != null)}")
        event?.isCanceled = true
    }

    fun Any?.toStringIfTrue(bool: Boolean?): String = if (bool == true) toString() else ""
}