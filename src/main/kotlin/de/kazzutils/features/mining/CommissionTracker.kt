package de.kazzutils.features.mining

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.graphics.ScreenRenderer

object CommissionTracker {

    init {
        CommissionTrackerElement()
    }

    class CommissionTrackerElement : GuiElement("Commission Tracker Display", 1f, 10,10) {
        val config = KazzUtils.config.mining.commissionTracker
        var message : String? = ""

        override fun render() {
            //if(!TabUtils.area.contains("Dwarven Mines") || !TabUtils.area.contains("Crystal Hollows")) return

            val text = TabUtils.comms

            if (toggled) {
                for((i,com) in text.withIndex()) mc.fontRendererObj.drawStringWithShadow(com, x, y+ (i * height), config.commissionTrackerOverlayColor.toChromaColorInt())

            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Commission Tracker", x, y,  config.commissionTrackerOverlayColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Commission Tracker") + 50

        override val toggled: Boolean
            get() = config.commissionTrackerOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }


}