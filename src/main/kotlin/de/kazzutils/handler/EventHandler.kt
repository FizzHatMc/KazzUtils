package de.kazzutils.handler

import de.kazzutils.event.ItemDropEvent
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.skyblockfeatures.MuseumUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action

object EventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onItemDrop(event: ItemDropEvent) {
        ChatUtils.messageToChat("Test")
        val protectedItems = MuseumUtils.getMissingItems()
        val droppedItemStack = event.droppedItem
        if (droppedItemStack != null) {
            val droppedItem = droppedItemStack.item
            if (protectedItems.containsKey(droppedItem.registryName.toString())) {
                event.canceled = true
            }
        }
    }

    @SubscribeEvent
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            val stack = event.entityPlayer.heldItem // Get the held item
            if(stack != null) {
                val dropEvent = ItemDropEvent(event.entityPlayer, stack)
                MinecraftForge.EVENT_BUS.post(dropEvent)
                println("Test Event Fired! Canceled: ${dropEvent.isCanceled}")
            }
        }
    }
}