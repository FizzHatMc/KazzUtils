package de.kazzutils.features.dungeon

import de.kazzutils.KazzUtils
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.TabUtils.getPlayerByName
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class TankRange {

    @SubscribeEvent
    fun onRenderWorldLastEvent(event: RenderWorldLastEvent?) {
        val tankName: String = TabUtils.tankName
        val tank: EntityPlayer? = getPlayerByName(tankName)
        val color: Color = Color.GREEN


        var radius = 30f
        if (KazzUtils.config.dungeon.dungeonClass.tank.boneNecklace) radius += 15f
        if (KazzUtils.config.dungeon.dungeonClass.tank.tankRange  && tank != null && TabUtils.area == "Catacombs") {
            val pos = BlockPos(tank.posX, tank.posY, tank.posZ)
            de.kazzutils.utils.RenderUtils.drawCylinder(
                Vec3(pos).addVector(0.5, 1.0, 0.5), radius, radius,
                60f, 40, 1, 0f, 0f, 0f,
                color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f,
                false, false
            )
        }
    }

}