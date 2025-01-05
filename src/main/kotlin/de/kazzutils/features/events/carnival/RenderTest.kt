package de.kazzutils.features.events.carnival

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.max
import kotlin.math.min

class RenderTest () {


    @SubscribeEvent
    fun onRenderOverlay(event: RenderGameOverlayEvent.Text) {
        val mc = Minecraft.getMinecraft()
        val fontRenderer = mc.fontRendererObj

        val startX = 10 // Starting X position
        val startY = 10 // Starting Y position
        val rowHeight = 10 // Height of each row
        val blockNames = getBlockLocalizedNames(getBlocksInRectangle2D(KazzUtils.mc.theWorld, 72, -112, -11, -106, -5))

        blockNames.forEachIndexed { rowIndex, row ->
            val rowText = row.joinToString(" | ")
            fontRenderer.drawString(rowText, startX.toFloat(),
                (startY + rowIndex * rowHeight).toFloat(), 0xFFFFFF, false)
        }
    }

}

fun getBlockLocalizedNames(blocks: Array<Array<Block>>): Array<Array<String>> {
    return blocks.map { row ->
        row.map { block -> block.localizedName }.toTypedArray()
    }.toTypedArray()
}

fun getBlocksInRectangle2D(
    world: World,
    yLevel: Int,
    startX: Int, startZ: Int,
    endX: Int, endZ: Int
): Array<Array<Block>> {
    // Ensure the coordinates are correctly assigned to min and max values
    val minX = min(startX, endX)
    val maxX = max(startX, endX)
    val minZ = min(startZ, endZ)
    val maxZ = max(startZ, endZ)

    // Create a 2D array to store blocks
    val blocks = Array(maxZ - minZ + 1) { zIndex ->
        Array(maxX - minX + 1) { xIndex ->
            // Calculate BlockPos
            val blockPos = BlockPos(
                if (startX > endX) maxX - xIndex else minX + xIndex,  // Reverse X-axis if facing west
                yLevel,
                // Reverse the Z-index only to mirror top-to-bottom
                if (startZ > endZ) maxZ - zIndex else minZ + zIndex
            )
            world.getBlockState(blockPos).block
        }
    }


    return blocks
}

// Function to generate block names as a 2D string array
fun getBlockNamesInRectangle2D(
    world: World,
    yLevel: Int,
    startX: Int, startZ: Int,
    endX: Int, endZ: Int
): Array<Array<String>> {
    val minX = minOf(startX, endX)
    val maxX = maxOf(startX, endX)
    val minZ = minOf(startZ, endZ)
    val maxZ = maxOf(startZ, endZ)

    return Array(maxX - minX + 1) { xIndex ->
        Array(maxZ - minZ + 1) { zIndex ->
            val blockPos = BlockPos(minX + xIndex, yLevel, minZ + zIndex)
            val block = world.getBlockState(blockPos).block
            block.localizedName
        }
    }
}

// Example usage in a command or event
fun openBlockGridGui(world: World) {
    val blockNames = getBlockNamesInRectangle2D(world, 72, -112, -11, -105, -5)

}
