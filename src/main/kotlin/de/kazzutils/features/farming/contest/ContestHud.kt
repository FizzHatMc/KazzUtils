package de.kazzutils.features.farming.contest

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.graphics.ScreenRenderer

object ContestHud {

    init {
        ContestHudElement()
    }

    class ContestHudElement : GuiElement("Contest Display", 1f, 10,10) {
        val config = KazzUtils.config.farming.contest
        var message : String? = ""


        override fun render() {
            if(TabUtils.area != "Garden") return
            message = TabUtils.time
            for(i in TabUtils.contest){
                message += " $i"
            }


            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, config.contestDisplayColor.toChromaColorInt())
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Contest Overlay", x, y, config.contestDisplayColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Contest Overlay") + 50

        override val toggled: Boolean
            get() = config.contestDisplay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }




}