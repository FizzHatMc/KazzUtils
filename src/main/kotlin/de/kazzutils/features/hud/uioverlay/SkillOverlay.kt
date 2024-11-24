package de.kazzutils.features.hud.uioverlay

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import java.awt.Color

object SkillOverlay {

    init {
        SkillOverlayElement()
    }

    class SkillOverlayElement : GuiElement("Skill Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.hud!!
        var message : String? = ""

        override fun render() {
            if(ChatUtils.skill == null)return

            message = ChatUtils.skill

            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, Color.CYAN.darker().rgb)
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Skill", x, y, Color.CYAN.darker().rgb)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Skill") + 50

        override val toggled: Boolean
            get() = config.skillOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}