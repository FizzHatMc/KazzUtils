package de.kazzutils.features.dungeon.F5

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.data.enumClass.ChatColor
import de.kazzutils.data.enumClass.ChatColor.Companion.toChatColor
import de.kazzutils.mixin.RenderLivingEntityHelper
import de.kazzutils.utils.ItemUtils
import de.kazzutils.utils.TabUtils
import de.kazzutils.utils.withAlpha
import net.minecraft.block.BlockStainedGlass
import net.minecraft.client.entity.EntityOtherPlayerMP
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.potion.Potion
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.awt.Color

object LividFinder {
    private val blockLoc = BlockPos(6,109,43)
    var lividEntity: EntityOtherPlayerMP? = null
    private var lividArmorStand: EntityArmorStand? = null
    private var gotBlinded = false
    private var color : ChatColor? = null

    private fun isCurrentlyBlind() = if (mc.thePlayer.isPotionActive(Potion.blindness)) {
        mc.thePlayer.getActivePotionEffect(Potion.blindness).duration > 10
    } else false

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent){
        if(mc.theWorld == null)return
        if((TabUtils.area != "Catacombs") || !KazzUtils.config.dungeon.lividFinder ) return
        if(TabUtils.area != "Catacombs") gotBlinded = false


        val isCurrentlyBlind = isCurrentlyBlind()
        if (!gotBlinded) {
            gotBlinded = isCurrentlyBlind
            return
        } else if (isCurrentlyBlind) return


        val blockColor = mc.theWorld.getBlockState(blockLoc).getValue(BlockStainedGlass.COLOR)
        color = blockColor.toChatColor()

        val color = color
        val chat = color?.toChatColor()

        lividArmorStand = getEntities<EntityArmorStand>()
            .firstOrNull { it.name.startsWith("${chat}﴾ ${chat}§lLivid") }
        val lividArmorStand = lividArmorStand ?: return


        val aabb = with(lividArmorStand) {
            AxisAlignedBB(
                posX - 0.5,
                posY - 2,
                posZ - 0.5,
                posX + 0.5,
                posY,
                posZ + 0.5
            )
        }

        val newLivid = mc.theWorld.getEntitiesWithinAABB(EntityOtherPlayerMP::class.java, aabb)
            .takeIf { it.size == 1 }?.firstOrNull() ?: return
        if (!newLivid.name.contains("Livid")) return

        lividEntity = newLivid
        RenderLivingEntityHelper.setEntityColorWithNoHurtTime(
            newLivid,
            color!!.toColor()!!.withAlpha(30)
        ) { shouldHighlight() }



    }


    @SubscribeEvent
    fun onRender(event: RenderWorldLastEvent){
        if(mc.theWorld == null)return
        if(getLividAlive() == null)return
        if(!KazzUtils.config.dungeon.lividFinder) return
        de.kazzutils.utils.RenderUtils.drawOutlinedBoundingBox(getLividAlive()?.entityBoundingBox, Color.RED, 4f,event.partialTicks) ?: return
    }


    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Post<*>){
        if(ItemUtils.mc.theWorld == null) return
        if(TabUtils.area != "Catacombs") return
        if(!KazzUtils.config.dungeon.lividHider) return

        val entity = event.entity
        if (entity is EntityPlayerSP) return
        val livid = getLividAlive() ?: return

        if (entity != livid && entity != lividArmorStand) {
            if (entity.name.contains("Livid")) {
                event.isCanceled = true
            }
        }


    }

    private fun shouldHighlight() = getLividAlive() != null

    private fun getLividAlive() = lividEntity?.let {
        if (!it.isDead && it.health > 0.5) it else null
    }


    private inline fun <reified R : Entity> getEntities(): Sequence<R> = getAllEntities().filterIsInstance<R>()

    private fun getAllEntities(): Sequence<Entity> = mc?.theWorld?.loadedEntityList?.let {
        if (mc.isCallingFromMinecraftThread) it else it.toMutableList()
    }?.asSequence()?.filterNotNull() ?: emptySequence()
}