package de.kazzutils.features.hud.uioverlay

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import java.awt.Color

object EffectiveHPOverlay {

    init {
        EffectiveHPOverlayElement()
    }

    class EffectiveHPOverlayElement : GuiElement("Effective HP Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.hud
        var message : String = ""
        var defense : Int = 0

        override fun render() {
            if (false) {
            if(ChatUtils.health == "" || ChatUtils.health == null)return
            val health = ChatUtils.health ?: return
            val healthInt = health.substring(0, health.indexOf(',')).replace("(", "").replace(" ", "").toInt()
            if(ChatUtils.defense != "" && ChatUtils.defense != null) defense = ChatUtils.defense!!.toInt()
            message = (healthInt * (1+(defense/100))).toString()

                mc.fontRendererObj.drawStringWithShadow(message, x, y, Color.RED.darker().rgb)
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Effective HP", x, y, Color.RED.darker().rgb)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Effective HP") + 50

        override val toggled: Boolean
            get() = config.effectiveHPOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}