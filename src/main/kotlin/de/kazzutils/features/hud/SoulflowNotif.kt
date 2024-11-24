package de.kazzutils.features.hud

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraft.util.EnumChatFormatting

object SoulflowNotif {

    init {
        SoulflowNotifElement()
    }

    class SoulflowNotifElement : GuiElement("Soulflow Notif Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.arrowSoulflowNotif.soulflow
        var message : String? = ""

        override fun render() {
            var amount = TabUtils.soulflow
            message = amount.toString()
            if(amount<= config.minSoulflow && config.soulflowNotif) de.kazzutils.utils.RenderUtils.drawTitle("Soulflow",""+amount, EnumChatFormatting.RED)
            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, config.soulflowDisplayColor.toChromaColorInt())
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Soulflow", x, y, config.soulflowDisplayColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Soulflow") + 50

        override val toggled: Boolean
            get() = config.soulflowDisplay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }



}