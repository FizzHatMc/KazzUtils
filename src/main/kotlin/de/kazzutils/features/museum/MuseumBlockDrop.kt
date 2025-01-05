package de.kazzutils.features.museum

import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.event.ItemDropEvent
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.skyblockfeatures.MuseumUtils
import net.minecraftforge.event.entity.player.PlayerDropsEvent
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class MuseumBlockDrop {





    private fun notifyStopped(event: Event?, action: String) {
        ChatUtils.messageToChat("§cStopped you from $action that item!")
        event?.isCanceled = true
    }

    fun Any?.toStringIfTrue(bool: Boolean?): String = if (bool == true) toString() else ""
}