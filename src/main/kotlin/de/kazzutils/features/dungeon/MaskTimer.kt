package de.kazzutils.features.dungeon

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.ChatUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MaskTimer {

    private var world: World? = mc.theWorld
    private var player: EntityPlayer? = mc.thePlayer

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onChatReceive(event: ClientChatReceivedEvent) {
        if(KazzUtils.config.dungeon.items.maskNotif) return

        val unformatted = event.message.unformattedText
        if (unformatted.contains("Your Bonzo's Mask saved your life!") || unformatted.contains("Your ⚚ Bonzo's Mask saved your life!")) {
            ChatUtils.messageToChat(EnumChatFormatting.RED.toString() + "BONZO MASK POPPED")
            mc.ingameGUI.displayTitle(EnumChatFormatting.RED.toString()+"BONZO POPPED","", 1, 2 ,0)
            world!!.playSound(player!!.posX, player!!.posY, player!!.posZ, "random.orb", 1f, 1f, false)

            //RenderUtils.renderTitle("BONZO POPPED"," ",0,20,5);
        } else if (unformatted.contains("Second Wind Activated! Your Spirit Mask saved your life!") || unformatted.contains("Second Wind Activated! Your ⚚Spirit Mask saved your life!")) {
            ChatUtils.messageToChat(EnumChatFormatting.RED.toString() + "SPIRIT MASK POPPED")
            mc.ingameGUI.displayTitle(EnumChatFormatting.RED.toString()+"SPIRIT MASK POPPED","", 1, 2 ,0)
            world!!.playSound(player!!.posX, player!!.posY, player!!.posZ, "random.orb", 1f, 1f, false)
        }
    }
}