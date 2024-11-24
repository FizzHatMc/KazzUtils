package de.kazzutils.features.farming.gardenlevel

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.data.farming.GardenXP
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.graphics.ScreenRenderer

object GardenLevelHud {

    init {
        GardenLevelHudElement()
    }

    class GardenLevelHudElement : GuiElement("Garden Level Display", 1f, 10,10) {
        val config = KazzUtils.config.farming.gardenLevel
        var message : String? = "N/A"


        override fun render() {
            if(TabUtils.area != "Garden") return


            if (TabUtils.gardenLevel == 15) {
                message = "15"
            } else if (KazzUtils.config.farming.gardenLevel.gardenLevelPercentage) {
                message = ""+TabUtils.gardenLevel + " §e" + TabUtils.gardenPercent + "§7%"
            } else {
                val xp: Int = GardenXP.getGardenXp(TabUtils.gardenLevel, TabUtils.gardenPercent)
                message = ""+TabUtils.gardenLevel + " §7(§6" + xp + "§7/§6" + GardenXP.getMaxXp(TabUtils.gardenLevel) + "§7)§r"

            }

            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, config.gardenLevelColor.toChromaColorInt())
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Garden Level", x, y, config.gardenLevelColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Garden Level") + 50

        override val toggled: Boolean
            get() = config.gardenLevelDisplay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }


}