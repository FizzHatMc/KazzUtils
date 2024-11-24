package de.kazzutils.features.dungeon.M7

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.data.enumClass.DunClass
import de.kazzutils.data.enumClass.WitherKingDragons
import de.kazzutils.data.m7.coords.CauldronCoords
import de.kazzutils.data.m7.coords.RelicCoords
import de.kazzutils.utils.CatacombsUtils
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class RelicWaypoints {

    @SubscribeEvent
    fun onWorldRender(event: RenderWorldLastEvent) {

        val player: EntityPlayerSP = mc.thePlayer
        val viewer: Entity = mc.renderViewEntity
        var playersClassAsInt : Int = 1

        for(s in DunClass.entries) {
            if(s.classNameAsString == TabUtils.playerClass) playersClassAsInt = s.classNum
        }
        
        //Relic
        if (player.posY < 50 && (CatacombsUtils.floor.contains("F7") || CatacombsUtils.inM7)) {
            
            val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks
            val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks
            val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks
            
            val blockPos: BlockPos? = RelicCoords.getRelic(playersClassAsInt)
            val x = blockPos!!.x - viewerX
            val y = blockPos.y - viewerY
            val z = blockPos.z - viewerZ
            var drag: WitherKingDragons? = null

            

            when (playersClassAsInt) {
                0 -> drag = WitherKingDragons.APEX
                1 -> drag = WitherKingDragons.ICE
                2 -> drag = WitherKingDragons.SOUL
                3 -> drag = WitherKingDragons.FLAME
                4 -> drag = WitherKingDragons.POWER
            }

            val color = Color(KazzUtils.config.dungeon.waypoints.m7f7.relic.relicWaypointColor.toChromaColorInt())

            if (KazzUtils.config.dungeon.waypoints.m7f7.relic.relicWaypoints) {
                checkNotNull(drag)
                if (!drag.isDestroyed) {
                    ChatUtils.messageToChat(EnumChatFormatting.LIGHT_PURPLE.toString() + "Render next")
                    de.kazzutils.utils.RenderUtils.renderBeaconBeam(x, y, z, color.rgb, color.alpha.toFloat(), event.partialTicks)
                }
            }
            if (KazzUtils.config.dungeon.waypoints.m7f7.relic.relicText) {
                checkNotNull(drag)
                if (!drag.isDestroyed) {
                    de.kazzutils.utils.RenderUtils.renderWaypointText(KazzUtils.config.dungeon.waypoints.m7f7.relic.relicWaypointText, blockPos, event.partialTicks)
                }
            }
        }




        //Cauldron
        if (player.posY < 50 && (CatacombsUtils.floor.contains("F7") || CatacombsUtils.inM7)) {
            val viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks
            val viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks
            val viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks


            val blockPos: BlockPos? = RelicCoords.getRelic(playersClassAsInt)
            val x = blockPos!!.x - viewerX
            val y = blockPos.y - viewerY
            val z = blockPos.z - viewerZ
            var drag: WitherKingDragons? = null
            when (playersClassAsInt) {
                0 -> drag = WitherKingDragons.APEX
                1 -> drag = WitherKingDragons.ICE
                2 -> drag = WitherKingDragons.SOUL
                3 -> drag = WitherKingDragons.FLAME
                4 -> drag = WitherKingDragons.POWER
            }
            val pos: BlockPos? = CauldronCoords.getRelic(playersClassAsInt)

            if (KazzUtils.config.dungeon.waypoints.m7f7.relic.cauldronHighlight) {
                checkNotNull(drag)
                if (!drag.picked) {
                    de.kazzutils.utils.RenderUtils.drawCustomBox(pos!!.x.toDouble(), 1.0, pos.y + 1.toDouble(), 1.0, pos.z.toDouble(), 1.0, drag.color, 3f, true)
                }
            }
        }


        if (player.posY < 50 && (CatacombsUtils.floor.contains("F7") || CatacombsUtils.inM7) && KazzUtils.config.dungeon.waypoints.m7f7.relic.renderDragonText) {
            for (drag in WitherKingDragons.entries) {
                de.kazzutils.utils.RenderUtils.renderWaypointText(drag.textColor  ,
                    drag.dragonText,
                    event.partialTicks,
                    KazzUtils.config.dungeon.waypoints.m7f7.relic.dragonTextScale
                )
                //RenderUtils.renderBoxOutline(drag.getCauldron(),1,1,1,event.partialTicks,3.69F,drag.getColor(),drag.getColor().getAlpha());
            }
        }

    }
    
}