package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MiscFeatures {

    @SubscribeEvent
    fun onEndermanTeleport(event: EnderTeleportEvent){
        if(KazzUtils.config.combat.slayer.voidgloom.stopEndermanFakeTeleportation) event.isCanceled = true
    }

}