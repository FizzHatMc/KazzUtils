package de.kazzutils.features.events.mythological

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.core.PersistentSave
import de.kazzutils.core.structure.GuiElement
import de.kazzutils.features.keyshortcut.KeyShortcuts.KeybindShortcut
import de.kazzutils.features.keyshortcut.KeyShortcuts.shortcuts
import de.kazzutils.utils.ColorUtils.toChromaColorInt
import de.kazzutils.utils.graphics.ScreenRenderer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import java.io.File
import java.io.Reader
import java.io.Writer


object MythoTrackerHud {

    init {
         MythoTrackerHudElement()
    }

    class MythoTrackerHudElement : GuiElement("Mythological Event Display", 1f, 10,10) {
        val config = KazzUtils.config.misc.mythologicalEvent
        var message : String? = ""

        override fun render() {
            val mythos = arrayOf("inq", "champ", "mino", "gaia", "lynx", "hunter") // List of mythos names

            for (i in mythos.indices) {
                var trackerName = ""
                when (mythos[i]) {
                    "mino" -> trackerName = "Minotaur: "
                    "gaia" -> trackerName = "Gaia Constructor: "
                    "lynx" -> trackerName = "Siamese Lynxes: "
                    "hunter" -> trackerName = "Minos Hunter: "
                    "inq" -> trackerName = "Minos Inquisitor: "
                    "champ" -> trackerName = "Minos Champion: "
                }
                message = trackerName + MythoTracker.getVarByName(mythos[i])// Get tracker value using a method

                if (toggled) {
                    mc.fontRendererObj.drawStringWithShadow(message, x, y + (i * height), config.mythoColor.toChromaColorInt())
                }
            }
        }

        override fun demoRender() {
            mc.fontRendererObj.drawStringWithShadow("Mythological Event Tracker", x, y,  config.mythoColor.toChromaColorInt())
        }

        override val height: Int
            get() = ScreenRenderer.fontRenderer.FONT_HEIGHT
        override val width: Int
            get() = ScreenRenderer.fontRenderer.getStringWidth("Mythological Event Tracker") + 50

        override val toggled: Boolean
            get() = config.mytholocialTracker

        init {
            KazzUtils.guiManager.registerElement(this)
        }
    }


}