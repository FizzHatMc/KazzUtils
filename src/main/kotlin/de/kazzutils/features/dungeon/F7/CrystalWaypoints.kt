package de.kazzutils.features.dungeon.F7

import de.kazzutils.KazzUtils
import de.kazzutils.data.enumClass.DunClass
import de.kazzutils.data.m7.coords.CrystalCoords
import de.kazzutils.utils.CatacombsUtils
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import net.minecraft.client.Minecraft
import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class CrystalWaypoints {

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {
        if(!KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalWaypoints || !KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalText) return
        val player = Minecraft.getMinecraft().thePlayer
        var isMageOrBers : Boolean = false
        var playersClassAsInt : Int = 6

        for(s in DunClass.entries) {
            if(s.classNameAsString == TabUtils.playerClass) playersClassAsInt = s.classNum
        }

        if (player.posY > 220 && (playersClassAsInt == 1 || playersClassAsInt == 3) && (CatacombsUtils.floor.contains("F7") || CatacombsUtils.inM7)) {
            val viewer = Minecraft.getMinecraft().renderViewEntity
            val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks
            val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks
            val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks



            val blockPos: BlockPos? = CrystalCoords.getCrystal(playersClassAsInt)
            val x = blockPos?.x?.minus(viewerX)
            val y = blockPos?.y?.minus(viewerY)
            val z = blockPos?.z?.minus(viewerZ)

            val color = Color(KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalWaypointColor.toChromaColorInt())

            if (KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalWaypoints) de.kazzutils.utils.RenderUtils.renderBeaconBeam(x!!, y!!, z!!, Color.RED.rgb, color.rgb.toFloat(), event.partialTicks)
            if (KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalText) de.kazzutils.utils.RenderUtils.renderWaypointText(KazzUtils.config.dungeon.waypoints.m7f7.crystal.crystalWaypointText, blockPos!!, event.partialTicks)
        }
    }
}

