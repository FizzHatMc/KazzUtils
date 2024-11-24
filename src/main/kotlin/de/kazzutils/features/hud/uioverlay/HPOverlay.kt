package de.kazzutils.features.hud.uioverlay

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraft.util.EnumChatFormatting
import java.awt.Color

object HPOverlay {

    init {
        HPOverlayElement()
    }

    class HPOverlayElement : GuiElement("Health Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.hud
        var message : String? = ""
        var healthMax : Int = 0
        var healthTest : String = ""
        var healthMaxTest : String = ""
        var healthRN : Int = 0
        var color = EnumChatFormatting.RED.toString()

        override fun render() {
            if (toggled) {
            //if(!Utils.inSkyblock) return
            if(ChatUtils.health != "" && ChatUtils.health != null) {
                message = ChatUtils.health

                healthMaxTest = message?.substring(message!!.indexOf(" ")) ?: return
                healthMaxTest = healthMaxTest.replace(",", "")
                healthMaxTest = healthMaxTest.replace(" ", "")
                healthMaxTest = healthMaxTest.replace(")", "")
                healthMax = healthMaxTest.toInt()

                healthTest = message?.substring(0, message!!.indexOf(" ")) ?: return
                healthTest = healthTest.replace(",", "")
                healthTest = healthTest.replace(" ", "")
                healthTest = healthTest.replace("(", "")
                healthRN = healthTest.toInt()

                if (healthRN > healthMax) color = EnumChatFormatting.GOLD.toString() else color = EnumChatFormatting.RED.toString()
            }
            if(healthRN != 0 && healthMax != 0) message = color + healthRN + EnumChatFormatting.RED + "/" + healthMax

                mc.fontRendererObj.drawStringWithShadow(message, x, y, Color.RED.rgb)
            }

        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Health", x, y, Color.RED.rgb)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Health") + 50

        override val toggled: Boolean
            get() = config.hpOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}