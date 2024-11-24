package de.kazzutils.features.combat

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.ItemUtils
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import scala.swing.event.MouseClicked

class DebuffAlert {
    //Last Breath
    var lastBreathInterval : Long = 1000
    var lastBreathTimeoutDuration = 2000
    //TODO: SETTING val lastBreathTitleText = "§cLAST BREATH HIT"
    //TODO: SETTING val lastBreathAlert = true
    var lastBreathDuration = 2000
    var lastbreath = false //TODO: SETTING for this
    var lastBreathChat = true //TODO: SETTING for this
    var lastBreathTimer : Long = 0
    var lastBreathCount = 0
    var lastBreathDuplex = true //TODO: ???
    var lastBreathTimeout = 0L

    // Ice Spray
    var iceSpray = false
    var iceSprayTimeoutDuration = 500
    //TODO: SETTING val iceSprayTitleText = "§bICE SPRAY HIT"
    //TODO: SETTING val iceSprayAlert = true
    var iceSprayDuration = 2000
    //TODO: Send in Chat

    @SubscribeEvent
    fun onSound(event: PlaySoundEvent){
        if(event.sound.toString() != "random.successful_hit") return
        if(lastbreath){
            if(lastBreathTimer > System.currentTimeMillis()){
                lastBreathCount++
            }else{
                lastBreathCount = 1
            }
            if(lastBreathChat) ChatUtils.messageToChat("Last Breath Hit: $lastBreathCount")


            lastBreathTimer = System.currentTimeMillis() + lastBreathInterval
            if(lastBreathDuplex) lastBreathDuplex = false else lastbreath = false
        }
    }

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent){
        if (event.phase != TickEvent.Phase.START || mc.thePlayer == null) return

        if(lastbreath && lastBreathTimeout < System.currentTimeMillis()){
            lastbreath = false
            lastBreathDuplex = false
        }
        //TODO: ICE SPRAY
    }

    @SubscribeEvent
    fun mouseClick(event: MouseClicked){
        val itemStack: ItemStack? = mc.thePlayer.heldItem

        if (itemStack != null) {
            var skyblockId = ItemUtils.itemId(itemStack)
            if ((skyblockId == "STARRED_LAST_BREATH" || skyblockId == "LAST_BREATH")) {
                // Check if the last breath has the duplex enchantment
                val item = itemStack.item
                val duplex = itemStack.tagCompound.getCompoundTag("tag").getCompoundTag("ExtraAttributes").getCompoundTag("enchantments").getCompoundTag("ultimate_reiterate")

                if (duplex != null) {
                    lastBreathDuplex = true
                }

                // Store that we shot a last breath
                lastbreath = true
                lastBreathTimeout = System.currentTimeMillis() + lastBreathTimeoutDuration
            } /*else if (skyblockId == "ICE_SPRAY_WAND" && iceSprayTimer < System.currentTimeMillis()) {
                iceSpray = true
                iceSprayTimer = System.currentTimeMillis() + 5000 // Ice Spray has a 5-second cooldown and duration
                iceSprayTimeout = System.currentTimeMillis() + settings.iceSprayTimeoutDuration
                debug("Set iceSpray to true")
            }*/ // TODO: ICE SPRAY
        }
    }

    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Post<*>){
        var entity = event.entity
        if (iceSpray && entity != null && entity.name == "Armor Stand") {
            // [DEBUFF ALERT] 0 1xtile.icePacked@0 (,264)
            val heldItem: ItemStack? = entity.getEquipmentInSlot(0) // Assuming slot 0 is main hand slot
            if (heldItem != null && heldItem.item.unlocalizedName == "tile:ice_packed") {
                // Check if the coordinates of the armor stand are the same as the player, if so, ignore it
                // Check with a small margin of error using entity.getDistance
                val distance: Double = entity.getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)

                if (distance > 0.5) {
                    // Ice spray hit


                    if (true) { //TODO SETTING ICE SPRAY IN CHAT
                        ChatUtils.messageToChat("Ice Spay Hit")
                    }
                    //guiSettings.iceSprayGUI.visibleTime = System.currentTimeMillis() + settings.iceSprayDuration

                    iceSpray = false

                }
            }
        }
    }

}