package de.kazzutils.features.deployable

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement

import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.IResource
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.ResourceLocation
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

object DeployableHud {

    init {
        DeployableElement()
    }

    class DeployableElement : GuiElement("Deployable Hud",1f,10,10) {
        val config = KazzUtils.config.misc.deployables
        override fun render() {
            if(toggled){
                val activeOrb = DeployableManager.instance.getActiveDeployable()?.deployable ?: return
                val rs : ResourceLocation = activeOrb.resourceLocation
                mc.textureManager.bindTexture(rs)
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
                Gui.drawModalRectWithCustomSizedTexture(x.toInt(), y.toInt(), 0f, 0f, width, height+5, width.toFloat(), height.toFloat())

                val renderList = mutableListOf<String>()
                if(config.deployableName) mc.fontRendererObj.drawStringWithShadow(activeOrb.display + ": " + DeployableManager.instance.getActiveDeployable()?.seconds + " s",x+width,y, Color.CYAN.rgb)


                if(config.deployableInfo) {
                    if (activeOrb.healthRegen != 0.0) renderList.add("Health Regen: ${activeOrb.healthRegen * 100} %")
                    if (activeOrb.vitality != 0) renderList.add("Vitality: ${activeOrb.vitality}")
                    if (activeOrb.trueDefense != 0) renderList.add("True Defense: ${activeOrb.trueDefense}")
                    if (activeOrb.ferocity != 0) renderList.add("Ferocity: ${activeOrb.ferocity}")
                    if (activeOrb.attackSpeed != 0.0) renderList.add("Attack Speed: ${activeOrb.attackSpeed * 100} %")
                    if (activeOrb.manaRegen != 0.0) renderList.add("Mana Regen: ${activeOrb.manaRegen * 100} %")
                    if (activeOrb.strength != 0) renderList.add("Strength: ${activeOrb.strength}")
                    if (activeOrb.mending != 0.0) renderList.add("Mending: ${activeOrb.mending}")

                    renderList.forEachIndexed { index, line ->
                        mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.GRAY.toString()+line, x+width, y+textHeight + (index * 10), Color.GRAY.rgb)
                    }
                }
            }
        }

        override fun demoRender() {
            if(toggled){
                mc.fontRendererObj.drawStringWithShadow("Deployable",x,y, Color.CYAN.rgb)
                val activeOrb = DeployableManager.instance.getActiveDeployable()?.deployable ?: return
                val rs : ResourceLocation = activeOrb.resourceLocation
                mc.textureManager.bindTexture(rs)
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
                Gui.drawModalRectWithCustomSizedTexture(x.toInt(), y.toInt(), 0f, 0f, width, height+5, width.toFloat(), height.toFloat())
            }
        }

        val textHeight = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val toggled: Boolean
            get() = config.deployable
        override val height: Int
            get() = DeployableManager.instance.getActiveDeployable()?.deployable?.let { getImageSize(it.resourceLocation)?.first } ?: 50
        override val width: Int
            get() = DeployableManager.instance.getActiveDeployable()?.deployable?.let { getImageSize(it.resourceLocation)?.second } ?: 50

        init {
            KazzUtils.guiManager.registerElement(this)
        }

        private fun getImageSize(resourceLocation: ResourceLocation): Pair<Int, Int>? {
            val resourceManager = mc.resourceManager
            val domainResource = ResourceLocation(resourceLocation.resourceDomain, "powerorbs/${resourceLocation.resourcePath}")
            try {
                val iResource: IResource = resourceManager.getResource(domainResource)
                val bufferedImage: BufferedImage = ImageIO.read(iResource.inputStream)
                val width = bufferedImage.width
                val height = bufferedImage.height
                return Pair(width, height)
            } catch (e: IOException) {
                //e.printStackTrace()
            }
            return null
        }

    }

}