package de.kazzutils.features.hud.uioverlay

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraft.util.EnumChatFormatting
import java.awt.Color

object ManaOverlay {

    init {
        ManaOverlayElement()
    }

    class ManaOverlayElement : GuiElement("Mana Overlay Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.hud
        var message : String = ""
        var healthMax : Int = 0
        var healthTest : String = ""
        var healthMaxTest : String = ""
        var healthRN : Int = 0
        var color = EnumChatFormatting.BLUE.toString()

        override fun render() {
            if (false) {
            //if(!Utils.inSkyblock) return
            if(ChatUtils.mana != "" && ChatUtils.mana != null) {
                if(ChatUtils.mana.toString().isBlank() || ChatUtils.mana.toString().isEmpty())return
                message = ChatUtils.mana.toString()

                message = message.replace(",","")


                healthMaxTest = message.substring(message.indexOf(" "))
                healthMaxTest = healthMaxTest.replace(",", "")
                healthMaxTest = healthMaxTest.replace(" ", "")
                healthMaxTest = healthMaxTest.replace(")", "")
                healthMax = healthMaxTest.toInt()

                healthTest = message.substring(0, message.indexOf(" "))
                healthTest = healthTest.replace(",", "")
                healthTest = healthTest.replace(" ", "")
                healthTest = healthTest.replace("(", "")
                healthRN = healthTest.toInt()

            if(healthRN != 0 && healthMax != 0) message = "$color$healthRN/$healthMax"

            if(message.isNotEmpty() && message.isNotBlank() && message != null)    mc.fontRendererObj.drawStringWithShadow(message, x, y, Color.BLUE.rgb)
            }
            }

        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Mana", x, y, Color.BLUE.rgb)
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Mana") + 50

        override val toggled: Boolean
            get() = config.manaOverlay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}