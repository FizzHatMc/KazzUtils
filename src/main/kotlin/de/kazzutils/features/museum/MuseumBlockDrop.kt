package de.kazzutils.features.museum

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.event.GuiContainerEvent
import de.kazzutils.event.ItemTossEvent
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.skyblockfeatures.MuseumUtils
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class MuseumBlockDrop {


    @SubscribeEvent
    fun onDrop(event: ItemTossEvent){
        ChatUtils.messageToChat("Event")
        val missingItems = MuseumUtils.getMissingItems()


        for(item in missingItems){
            ChatUtils.messageToChat(event.item.unlocalizedName)
            if(event.item.unlocalizedName == item.key){
                ChatUtils.messageToChat("Found Item in missingItems")
            }
        }

        notifyStopped(event, "dropping")
    }


    @SubscribeEvent
    fun onSlotClick(event: GuiContainerEvent.SlotClickEventSkytils) {
        ChatUtils.messageToChat("Event SlotClick")
        if (event.slotId == -999 && mc.thePlayer.inventory.itemStack != null && event.clickType != 5) {
            notifyStopped(event, "dropping")
            return
        }
        if (event.clickType == 4 && event.slotId != -999 && event.slot != null && event.slot.hasStack) {
            notifyStopped(event, "dropping")
            return
        }
    }


    private fun notifyStopped(event: Event?, action: String) {
        ChatUtils.messageToChat("§cStopped you from $action that item!")
        event?.isCanceled = true
    }

    fun Any?.toStringIfTrue(bool: Boolean?): String = if (bool == true) toString() else ""
}