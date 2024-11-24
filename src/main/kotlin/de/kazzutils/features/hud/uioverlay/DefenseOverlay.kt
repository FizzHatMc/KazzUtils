package de.kazzutils.features.hud.uioverlay

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import java.awt.Color

object DefenseOverlay {

    init {
        DefenseOverlayElement()
    }

    class DefenseOverlayElement : GuiElement("Defense Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.hud
        var message : String? = ""

        override fun render() {
            if (toggled) {
            if(ChatUtils.defense != "" && ChatUtils.defense != null) message = ChatUtils.defense

                mc.fontRendererObj.drawStringWithShadow(message, x, y, Color.GREEN.darker().rgb)
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Defense", x, y, Color.GREEN.darker().rgb)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Defense") + 50

        override val toggled: Boolean
            get() = config.defenseOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}