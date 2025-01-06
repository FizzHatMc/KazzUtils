package de.kazzutils.event

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.Cancelable
import net.minecraftforge.fml.common.eventhandler.Event

@Cancelable
data class ItemDropEvent(val player: EntityPlayer, val droppedItem: ItemStack) : KazzUtilsEvent()