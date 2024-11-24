package de.kazzutils.features.deployable

import de.kazzutils.data.enumClass.Deployable
import de.kazzutils.utils.Utils.removeMinecraftColorCodes
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.WorldSettings
import net.minecraft.world.WorldType
import net.minecraftforge.common.util.Constants
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class DeployableManager {
    private val deployablePattern = Pattern.compile("[A-Za-z ]* (?<seconds>[0-9]*)s")
    private val TEXTURE_URL_PATTERN = Pattern.compile("\"url\"\\s?:\\s?\".+/(?<textureId>\\w+)\"")

    companion object{
        val DUMMY_POWER_ORB_ENTRY: DeployableEntry = DeployableEntry(Deployable.RADIANT, 20, null)
        val instance = DeployableManager()
    }

    private val powerOrbEntryMap: MutableMap<Deployable, DeployableEntry> = HashMap()

    /**
     * Put any detected orb into the list of active orbs.
     *
     * @param powerOrb Detected PowerOrb type
     * @param seconds Seconds the orb has left before running out
     * @param uuid UUID of the orb
     */
    private fun put(powerOrb: Deployable, seconds: Int, uuid: UUID) {
        powerOrbEntryMap[powerOrb] = DeployableEntry(powerOrb, seconds, uuid)
    }



    fun getActiveDeployable(): DeployableEntry? {
        val max = powerOrbEntryMap.entries.stream()
            .filter { entry -> entry.value.timestamp + 100 > System.currentTimeMillis() }
            .max(compareBy<Map.Entry<Deployable, DeployableEntry>> { it.key })

        return max.map { it.value }.orElse(null)
    }

    fun detectDeployables(entityArmorStand: EntityArmorStand) {
        if (entityArmorStand.hasCustomName()) {
            val customNameTag = entityArmorStand.customNameTag
            val orb = Deployable.getByDisplayname(customNameTag)

            if (orb != null && orb.isInRadius(entityArmorStand.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer))) {
                val matcher = deployablePattern.matcher(customNameTag.removeMinecraftColorCodes())

                if (matcher.matches()) {
                    val seconds: Int
                    try {
                        seconds = matcher.group("seconds")?.toInt() ?: return
                    } catch (ex: NumberFormatException) {
                        return
                    }

                    val surroundingArmorStands = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(
                        EntityArmorStand::class.java,
                        AxisAlignedBB(
                            entityArmorStand.posX - 0.1,
                            entityArmorStand.posY - 3.0,
                            entityArmorStand.posZ - 0.1,
                            entityArmorStand.posX + 0.1,
                            entityArmorStand.posY,
                            entityArmorStand.posZ + 0.1
                        )
                    )
                    if (surroundingArmorStands.isNotEmpty()) {
                        var orbArmorStand: EntityArmorStand? = null

                        for (surroundingArmorStand in surroundingArmorStands) {
                            val helmet = surroundingArmorStand.getCurrentArmor(3)
                            if (helmet != null) {
                                orbArmorStand = surroundingArmorStand
                            }
                        }

                        orbArmorStand?.let { put(orb, seconds, it.uniqueID) }
                    }
                }
            }
        } else {
            if (entityArmorStand.isInvisible) {
                if (entityArmorStand.getCurrentArmor(3) == null) return

                val skullTexture = getSkullTexture(entityArmorStand.getCurrentArmor(3))
                val decodedTextureUrl = decodeSkinTexture(skullTexture, true) ?: return

                val flare = Deployable.getByTextureId(decodedTextureUrl)
                if (flare != null && flare.isInRadius(entityArmorStand.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer))) {
                    var seconds = 180
                    seconds -= entityArmorStand.ticksExisted * 50 / 1000

                    put(flare, seconds, entityArmorStand.uniqueID)
                }
            }
        }
    }
    fun getSkullTexture(skull: ItemStack?): String? {
        if (skull == null || !skull.hasTagCompound()) {
            return null
        }

        val nbt = skull.tagCompound
        if (nbt?.hasKey("SkullOwner", 10) == true) {
            return nbt.getCompoundTag("SkullOwner").getCompoundTag("Properties")
                .getTagList("textures", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0).getString("Value")
        }
        return null
    }

    fun decodeSkinTexture(base64: String?, justTextureUrl: Boolean): String? {
        if (base64 == null) return null

        val decodedString = String(
            Base64.getDecoder().decode(
                // Getting before '=' to avoid IllegalArgumentException. No padding needed
                if (base64.contains("=")) base64.substring(0, base64.indexOf('=')) else base64
            ),
            StandardCharsets.UTF_8
        )

        return if (justTextureUrl) {
            val matcher: Matcher = TEXTURE_URL_PATTERN.matcher(decodedString)
            if (matcher.find()) {
                matcher.group("textureId")
            } else {
                null
            }
        } else {
            decodedString
        }
    }

    fun createVirtualArmorStand(armorStandToClone: EntityArmorStand): EntityArmorStand {
        val virtualArmorStand = EntityArmorStand(getDummyWorld())

        virtualArmorStand.setCurrentItemOrArmor(4, armorStandToClone.getEquipmentInSlot(4))

        return virtualArmorStand
    }


    val DUMMY_WORLD: WorldClient = WorldClient(
        null, WorldSettings(
            0L, WorldSettings.GameType.SURVIVAL,
            false, false, WorldType.DEFAULT
        ), 0, null, null
    )

    fun getDummyWorld(): WorldClient {
        return DUMMY_WORLD
    }

}


class DeployableEntry(
    deployable: Deployable,
    seconds: Int,
    uuid: UUID?
) {
    /** The PowerOrb type.  */
    val deployable: Deployable = deployable

    /** Seconds the orb has left before running out  */
    val seconds = seconds

    val timestamp = System.currentTimeMillis()

    val uuid: UUID? = uuid
}