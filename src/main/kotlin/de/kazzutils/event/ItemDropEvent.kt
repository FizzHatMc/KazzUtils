package de.kazzutils.event

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.Event

class ItemDropEvent(val player: EntityPlayer, val droppedItem: ItemStack) : Event() {
    var canceled: Boolean = false // Um das Fallenlassen zu verhindern
}