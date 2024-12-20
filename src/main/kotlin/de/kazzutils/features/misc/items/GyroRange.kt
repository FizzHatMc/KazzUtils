package de.kazzutils.features.misc.items

import de.kazzutils.KazzUtils
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.ItemUtils
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

class GyroRange {

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        val mc = Minecraft.getMinecraft()
        val heldItem: ItemStack? = ItemUtils.heldItem
        val itemId: String? = if (heldItem != null) ItemUtils.itemId(heldItem) else null
        val color: Color = Color(KazzUtils.config.misc.items.gyroColor.toChromaColorInt())

        if (!KazzUtils.config.misc.items.gyroRange) return
        if (itemId != "GYROKINETIC_WAND") return
        val pos = mc.thePlayer.rayTrace(25.0, event.partialTicks).blockPos
        val block = mc.theWorld.getBlockState(pos).block
        if (block.isAir(mc.theWorld, pos)) return

        de.kazzutils.utils.RenderUtils.drawCylinder(
            Vec3(pos).addVector(0.5, 1.0, 0.5),
            10f, 10f - KazzUtils.config.misc.items.gyroRangeScale, 0.2f, 40, 1, 0f, 0f, 0f,
            color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f,
            false, false
        )
    }

}