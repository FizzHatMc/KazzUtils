package de.kazzutils.features.misc.items

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion
import de.kazzutils.features.mining.StarCultNotif
import de.kazzutils.utils.*
import de.kazzutils.utils.ItemUtils.mc
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent


class RagAxe {
    //val titleUtils = TitleUtils()
/*
    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        if(!KazzUtilsV2.config.misc.items.ragAxe) return
        if(event.type.toInt() != 2) return
        if(mc.thePlayer.heldItem === null) return
        val heldItem: ItemStack? = ItemUtils.heldItem
        val itemId: String? = if (heldItem != null) ItemUtils.itemId(heldItem) else null


        if (itemId != null) {
            if(itemId == "RAGNAROCK_AXE"){
                ChatUtils.messageToChat("Axe in Hand")
                if(event.message.unformattedText.contains("CASTING")){
                    //titleUtils.renderTitle("Casted","", Color.RED, 60,true)


                    //ChatUtils.messageToChat("DebugRagCast")
                }
            }
        }
    }





    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    fun onPlaySound(event: PlaySoundEvent) {


        if(mc.thePlayer == null && event.sound == null) return
        if(!KazzUtilsV2.config.misc.items.ragAxe) return
        val heldItem: ItemStack? = ItemUtils.heldItem
        val itemId: String? = if (heldItem != null) ItemUtils.itemId(heldItem) else null
        if (itemId != null) {
            if(itemId == "RAGNAROCK_AXE"){
                val soundName: String = event.sound.soundLocation.toString()
                val titleUtils = TitleUtils()
                if(soundName.contains("minecraft:mob.wolf.howl")){
                    ChatUtils.messageToChat("AWOOGA")
                    test = true
                    mc.addScheduledTask {
                        mc.ingameGUI.displayTitle("AWOOGA", "", 0, 2, 0)
                    }
                }else test = false
            }
        }

    }
 */

    var test = false
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    fun onPlaySound(event: PlaySoundEvent) {
        if (mc.thePlayer == null || event.sound == null) return
        if (!KazzUtils.config.misc.items.ragAxe) return
        val heldItem: ItemStack? = ItemUtils.heldItem
        val itemId: String? = heldItem?.let { ItemUtils.itemId(it) }

        if (itemId != null && itemId == "RAGNAROCK_AXE") {
            val soundName: String = event.sound.soundLocation.toString()
            if (soundName.contains("minecraft:mob.wolf.howl")) {
                ChatUtils.messageToChat("AWOOGA")
                StarCultNotif.test("AWOOGAAAAAAAAAAAAAAA")
                test = true
            }
        }
    }



    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || Companion.mc.thePlayer == null) return
        if(test) StarCultNotif.test("AWOOGAAAAAAAAAAAAAAA"); test = false
    }

    @SubscribeEvent()
    fun onChatRecieved(event: ClientChatReceivedEvent){
        if(event.type.toInt() == 2) return
        if(!KazzUtils.config.misc.items.ragAxe) return
        if(event.message.unformattedText.contains("Ragnarock was cancelled due to taking damage!")) {
            ChatUtils.messageToChat("Cancelled")
            event.isCanceled = true
        }
    }


}