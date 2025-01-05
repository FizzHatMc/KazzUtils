package de.kazzutils.features.events.carnival


import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11
import java.util.HashMap
import java.util.HashSet


class Minesweeper {

    // There is X bomb hidden nearby.   // 0-X
    //Here's yer shovel, then.


    // -112 72 -11 | -106 72 -11
    // -112 72 -5  | -105 72 -5

    private val bombCounts = HashMap<BlockPos, Int>()
    private val possibleBombs = HashSet<BlockPos>()
    private var lastClickedBlock: BlockPos? = null

    @SubscribeEvent
    fun onMouseEvent(event: MouseEvent) {
        if (event.button == 0 && event.buttonstate) { // Linksklick
            val mc = Minecraft.getMinecraft()
            val mop = mc.objectMouseOver
            if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                val clickedPos = mop.blockPos
                val world = mc.theWorld
                if (world != null && world.getBlockState(clickedPos).block === Blocks.sand) {
                    lastClickedBlock = clickedPos
                    // ***SEHR WICHTIG: Ersetze dies mit der korrekten Hypixel Interaktion!***
                    // Platzhalter (wahrscheinlich falsch):
                    // Minecraft.getMinecraft().netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(clickedPos, 255, null, 0, 0, 0))
                }
            }
        }
    }

    @SubscribeEvent
    fun onClientChatReceived(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if (message.startsWith("MINES! There ")) {
            try {
                val number = message.replace(Regex("[^0-9]"), "")
                val bombCount = number.toInt()
                lastClickedBlock?.let {
                    bombCounts[it] = bombCount
                    updateHighlights(Minecraft.getMinecraft().theWorld)
                    lastClickedBlock = null
                }
            } catch (e: NumberFormatException) {
                // Fehlerbehandlung (protokolliere es zum Debuggen)
                System.err.println("Fehler beim Parsen der Bombenanzahl: ${e.message}")
            }
        }
    }

    private fun updateHighlights(world: World?) {
        if (world == null) return

        possibleBombs.clear()

        for ((minedPos, bombCount) in bombCounts) {
            if (bombCount > 0) {
                for (x in -1..1) {
                    for (z in -1..1) {
                        if (x == 0 && z == 0) continue

                        val neighborPos = minedPos.add(x, 0, z)
                        if (world.getBlockState(neighborPos).block === Blocks.sand) {
                            possibleBombs.add(neighborPos)
                        }
                    }
                }
            }
        }
        for(possibleBomb in HashSet(possibleBombs)){
            var remove = true
            for ((minedPos, _) in bombCounts) {
                for (x in -1..1) {
                    for (z in -1..1) {
                        if (x == 0 && z == 0) continue

                        val neighborPos = minedPos.add(x, 0, z)
                        if(neighborPos == possibleBomb && bombCounts[minedPos] != 0){
                            remove = false
                        }
                    }
                }
            }
            if(remove){
                possibleBombs.remove(possibleBomb)
            }
        }
    }




    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (possibleBombs.isNotEmpty()) {
            GlStateManager.pushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.disableDepth()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.disableTexture2D()
            GlStateManager.disableLighting()
            GL11.glLineWidth(2.0f)

            val tessellator = Tessellator.getInstance()
            val worldRenderer = tessellator.worldRenderer

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR) // Correct way to begin

            for (pos in possibleBombs) {
                val x = pos.x - Minecraft.getMinecraft().renderManager.viewerPosX
                val y = pos.y - Minecraft.getMinecraft().renderManager.viewerPosY
                val z = pos.z - Minecraft.getMinecraft().renderManager.viewerPosZ

                // Draw cube outline (using worldRenderer.pos and worldRenderer.color)
                val red = 0f
                val green = 1f
                val blue = 0f
                val alpha = 0.5f

                // Bottom face
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()

                // Top face
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                // Sides
                worldRenderer.pos(x, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x + 1.0, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x + 1.0, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()

                worldRenderer.pos(x, y, z + 1.0).color(red, green, blue, alpha).endVertex()
                worldRenderer.pos(x, y + 1.0, z + 1.0).color(red, green, blue, alpha).endVertex()
            }

            tessellator.draw() // Call draw() after adding all vertices

            GlStateManager.enableTexture2D()
            GlStateManager.enableDepth()
            GlStateManager.disableBlend()
            GlStateManager.popMatrix()
        }
    }


}

