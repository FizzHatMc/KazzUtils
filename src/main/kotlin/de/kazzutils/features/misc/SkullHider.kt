package de.kazzutils.features.misc

import de.kazzutils.KazzUtils
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.ItemUtils.getSkullTexture
import de.kazzutils.utils.ItemUtils.mc
import de.kazzutils.utils.TabUtils
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class SkullHider {

    private val soulWeaverHider = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYyNGVkNjg3NTMwNGZhNGExZjBjNzg1YjJjYjZhNmE3MjU2M2U5ZjNlMjRlYTU1ZTE4MTc4NDUyMTE5YWE2NiJ9fX0="
    private val blessingTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkzZTIwNjg2MTc4NzJjNTQyZWNkYTFkMjdkZjRlY2U5MWM2OTk5MDdiZjMyN2M0ZGRiODUzMDk0MTJkMzkzOSJ9fX0="
    private val reviveStoneTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZhNzZjYzIyZTdjMmFiOWM1NDBkMTI0NGVhZGJhNTgxZjVkZDllMThmOWFkYWNmMDUyODBhNWI0OGI4ZjYxOCJ9fX0K"
    private val premiumFleshTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE3NWU4YjA0NGM3MjAxYTRiMmU4NTZiZTRmYzMxNmE1YWFlYzY2NTc2MTY5YmFiNTg3MmE4ODUzNGI4MDI1NiJ9fX0K"
    private val abilityOrbTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTAxZTA0MGNiMDFjZjJjY2U0NDI4MzU4YWUzMWQyZTI2NjIwN2M0N2NiM2FkMTM5NzA5YzYyMDEzMGRjOGFkNCJ9fX0="
    private val supportOrbTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTMxYTRmYWIyZjg3ZGI1NDMzMDEzNjUxN2I0NTNhYWNiOWQ3YzBmZTc4NDMwMDcwOWU5YjEwOWNiYzUxNGYwMCJ9fX0="
    private val damageOrbTexture = "eyJ0aW1lc3RhbXAiOjE1NzQ5NTEzMTkwNDQsInByb2ZpbGVJZCI6IjE5MjUyMWI0ZWZkYjQyNWM4OTMxZjAyYTg0OTZlMTFiIiwicHJvZmlsZU5hbWUiOiJTZXJpYWxpemFibGUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FiODZkYTJlMjQzYzA1ZGMwODk4YjBjYzVkM2U2NDg3NzE3MzE3N2UwYTIzOTQ0MjVjZWMxMDAyNTljYjQ1MjYifX19"
    private val healerFairyTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjM2UzMWNmYzY2NzMzMjc1YzQyZmNmYjVkOWE0NDM0MmQ2NDNiNTVjZDE0YzljNzdkMjczYTIzNTIifX19"


    @SubscribeEvent
    fun onRender(event: RenderLivingEvent.Post<*>){
        if(mc.theWorld == null || true) return
        if(TabUtils.area == "") return
        val entity = event.entity ?: return
        var head: ItemStack?
        if(entity.inventory.size>4){
            head = entity.inventory[4]
            val skullTexture = head.getSkullTexture()
            if(KazzUtils.config.dungeon.hideSoulweaverGloves){
                if (skullTexture != null) {
                    ChatUtils.messageToChat(skullTexture)
                }
                if(skullTexture == soulWeaverHider) event.isCanceled = true
            }
        }


    }
}