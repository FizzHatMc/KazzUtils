package de.kazzutils.features.dungeon

import DelayedExecutor
import de.kazzutils.KazzUtils
import de.kazzutils.event.GuiContainerEvent
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.ContainerUtils
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MelodyProgress {
//  25, 34, 43
    var isInInventory = false
    var melodyMessage = "Melody"


    @SubscribeEvent
    fun tryEvent(event: GuiContainerEvent.SlotClickEvent){
        var data = "Test: "

        //data

        ChatUtils.messageToChat(data)
    }

    @SubscribeEvent
    fun onOpenGui(event: GuiOpenEvent) {
        if(!KazzUtils.config.dungeon.melody.melodyChatMessage) return
        if(KazzUtils.mc.thePlayer == null) return
        melodyMessage = KazzUtils.config.dungeon.melody.melodyChatMessageChat
        if(event.gui!=null) {
            DelayedExecutor.runDelayed(100) {
                melodyTerminal()
            }
        }else{//TODO: This will be Buggy 100%
            if(isInInventory) ChatUtils.messageToChat("$melodyMessage 4/4")
        }

    }

    fun melodyTerminal() {
        val currentScreen = ContainerUtils.openInventoryName()
        if(currentScreen.contains("Click the button on time!")){
            //ChatUtils.messageToChat("Found Correct Inventory")
            isInInventory = true
        }else{
            isInInventory = false
        }
    }

    var messageSent = false

    @SubscribeEvent
    fun onMouseClick(event: GuiScreenEvent.MouseInputEvent) {
        if(!KazzUtils.config.dungeon.melody.melodyChatMessage) return
        if(!isInInventory) return
        val slots = ContainerUtils.getItemsInOpenChest()
        val itemSlot24 = ContainerUtils.getItemStackFromSlot(slots, 25)
        val itemSlot34 = ContainerUtils.getItemStackFromSlot(slots, 34)
        val itemSlot43 = ContainerUtils.getItemStackFromSlot(slots, 43)
        if (itemSlot24 != null && itemSlot34 != null && itemSlot43 != null) {
            val slot25Color = getColorFromMetadata(itemSlot24.metadata)
            val slot34Color = getColorFromMetadata(itemSlot34.metadata)
            val slot43Color = getColorFromMetadata(itemSlot43.metadata)

            //ChatUtils.messageToChat("25: $slot25Color | 34: $slot34Color | 43: $slot43Color")

            if(slot25Color.equals("Lime")){
                sendMessage("$melodyMessage 1/4")
            }else if(slot34Color.equals("Lime")){
                ChatUtils.messageToChat("$melodyMessage 2/4")
            }else if(slot43Color.equals("Lime")){
                ChatUtils.messageToChat("$melodyMessage 3/4")
            }
        }
    }

    private fun sendMessage(message: String){
        if(!messageSent){
            ChatUtils.messageToChat(message)
        }
    }

    fun getColorFromMetadata(metadata: Int): String {
        return when (metadata) {
            0 -> "White"
            1 -> "Orange"
            2 -> "Magenta"
            3 -> "Light Blue"
            4 -> "Yellow"
            5 -> "Lime"
            6 -> "Pink"
            7 -> "Gray"
            8 -> "Light Gray"
            9 -> "Cyan"
            10 -> "Purple"
            11 -> "Blue"
            12 -> "Brown"
            13 -> "Green"
            14 -> "Red"
            15 -> "Black"
            else -> "Unknown"
        }
    }

}