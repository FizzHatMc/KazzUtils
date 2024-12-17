package de.kazzutils.features.dungeon

import DelayedExecutor
import de.kazzutils.KazzUtils
import de.kazzutils.utils.ContainerUtils
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object MelodyProgress {


    @SubscribeEvent
    fun onOpenGui(event: GuiOpenEvent) {
        if(event.gui == null || KazzUtils.mc.thePlayer == null) return

        DelayedExecutor.runDelayed(100) {
            checkForGui()
        }
    }

    fun checkForGui() {
        val currentScreen = ContainerUtils.openInventoryName()
        if(!currentScreen.contains("Click the button on time!")) return




    }

}