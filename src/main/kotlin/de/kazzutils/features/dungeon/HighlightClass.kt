package de.kazzutils.features.dungeon

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.TabUtils
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class HighlightClass {

    @SubscribeEvent
    fun onRender(event: RenderWorldLastEvent){
        if(mc.thePlayer == null || mc.theWorld == null) return
        if(KazzUtils.config.dungeon.dungeonClass.enumClass == null) return
        val classPlayerName = KazzUtils.config.dungeon.dungeonClass.enumClass.playerName ?: return

        //if (classPlayerName == null) return
        val highlight = classPlayerName?.let { TabUtils.getPlayerByName(it) } ?: return
        if (highlight === mc.thePlayer) return
        if(highlight == null) return
        val boundingBox = highlight.entityBoundingBox
        de.kazzutils.utils.RenderUtils.drawOutlinedBoundingBox(boundingBox, Color.GREEN, 3.69f, event.partialTicks)

    }



}