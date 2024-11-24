package de.kazzutils.features.hud

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.graphics.ScreenRenderer

object PetOverlay {

    init {
        PetOverlayElement()
    }

    class PetOverlayElement : GuiElement("Pet Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.petOverlay
        var message : String? = ""
        var xp : String? = ""

        override fun render() {

            message = TabUtils.petName
            xp = TabUtils.petXp

            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, config.petOverlayColor.toChromaColorInt())
                mc.fontRendererObj.drawStringWithShadow(xp, x, y+height, config.petOverlayColor.toChromaColorInt())
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Pet Overlay", x, y, config.petOverlayColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Pet Overlay") + 50

        override val toggled: Boolean
            get() = config.petOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }


}