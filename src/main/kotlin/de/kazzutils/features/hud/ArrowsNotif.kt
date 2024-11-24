package de.kazzutils.features.hud

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.ContainerUtils
import de.kazzutils.utils.graphics.ScreenRenderer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting

object ArrowsNotif {

    init {
        ArrowsNotifElement()
    }

    class ArrowsNotifElement : GuiElement("Arrow Notif Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.arrowSoulflowNotif.arrow!!
        var message : String? = ""
        var amount = 0

        override fun render() {
            val quiver: ItemStack? = ContainerUtils.checkInventoryForName("Quiver", mc.thePlayer.inventory)
            val quiverLore: List<String> = ContainerUtils.getLore(quiver)
            var arrowType = ""


            for (tag in quiverLore) {
                if (tag.contains("Active Arrow:")) {
                    arrowType = tag.substring(tag.indexOf("Active Arrow: ") + "Active Arrow: ".length, tag.indexOf('('))
                    amount = tag.substring(tag.indexOf('(') + 3, tag.indexOf(')') - 2).toInt()
                }
            }
            //max 2880

            if((amount<=config.minArrow) && config.ArrowNotif) de.kazzutils.utils.RenderUtils.drawTitle("Arrows",""+amount,EnumChatFormatting.RED)

            message = arrowType + " / " + amount + "x"
            if(arrowType == "") return
            if (toggled) {
                mc.fontRendererObj.drawStringWithShadow(message, x, y, config.ArrowDisplayColor.toChromaColorInt())
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Arrows", x, y, config.ArrowDisplayColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Arrows") + 50

        override val toggled: Boolean
            get() = config.ArrowDisplay

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }

}