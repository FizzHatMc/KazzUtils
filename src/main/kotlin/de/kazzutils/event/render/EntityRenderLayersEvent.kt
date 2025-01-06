package de.kazzutils.event.render

import de.kazzutils.event.KazzUtilsEvent
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.eventhandler.Cancelable

open class EntityRenderLayersEvent<T : Entity>(
    val entity: T,
) : KazzUtilsEvent() {

    @Cancelable
    class Pre<T : Entity>(
        entity: T,
    ) : EntityRenderLayersEvent<T>(entity)
}