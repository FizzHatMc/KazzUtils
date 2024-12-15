package de.kazzutils

import de.kazzutils.features.mining.*
import de.kazzutils.utils.*
import de.kazzutils.data.enumClass.*
import de.kazzutils.utils.Utils
import de.kazzutils.core.PersistentSave
import de.kazzutils.commands.CommandManager
import de.kazzutils.config.ConfigManager
import de.kazzutils.config.KazzUtilsConfig
import de.kazzutils.core.GuiManager
import de.kazzutils.features.chatCommands.ChatCommands
import de.kazzutils.features.deployable.DeployableHud
import de.kazzutils.features.deployable.DeployableManager
import de.kazzutils.features.dungeon.F5.LividFinder
import de.kazzutils.features.dungeon.F7.CrystalWaypoints
import de.kazzutils.features.dungeon.F7.TerminalWaypoints
import de.kazzutils.features.dungeon.HighlightClass
import de.kazzutils.features.dungeon.M7.RelicWaypoints
import de.kazzutils.features.dungeon.MaskTimer
import de.kazzutils.features.dungeon.PlayerClass
import de.kazzutils.features.dungeon.TankRange
import de.kazzutils.features.events.mythological.MythoTracker
import de.kazzutils.features.events.mythological.MythoTrackerHud
import de.kazzutils.features.farming.contest.ContestHud
import de.kazzutils.features.farming.gardenlevel.GardenLevelHud
import de.kazzutils.features.hud.ArrowsNotif
import de.kazzutils.features.hud.PetOverlay
import de.kazzutils.features.hud.SoulflowNotif
import de.kazzutils.features.hud.uioverlay.*
import de.kazzutils.features.keyshortcut.KeyShortcuts
import de.kazzutils.features.mining.CommissionTracker
import de.kazzutils.features.misc.MiscFeatures
import de.kazzutils.features.misc.SkullHider
import de.kazzutils.features.misc.items.GyroRange
import de.kazzutils.features.misc.items.RagAxe
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.TitleUtils
import de.kazzutils.utils.colors.CustomColor
import de.kazzutils.utils.graphics.ScreenRenderer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.io.File
import java.util.*

@Mod(modid = KazzUtils.MOD_ID, version = "1.0.0", useMetadata = true)
class KazzUtils {

    @Mod.EventHandler
    fun init(@Suppress("UNUSED_PARAMETER") event: FMLInitializationEvent) {
        configManager = ConfigManager()
        MinecraftForge.EVENT_BUS.register(configManager)

        arrayOf(
            this,
            guiManager,
            MythoTrackerHud,
            KeyShortcuts,
            DefenseOverlay,
            ChatUtils,
            ScreenRenderer,
            EffectiveHPOverlay,
            SkillOverlay,
            HPOverlay,
            ManaOverlay,
            ArrowsNotif,
            SoulflowNotif,
            PetOverlay,
            CommissionTracker,
            GardenLevelHud,
            ContestHud,
            DeployableHud,
            LividFinder,


        ).forEach(MinecraftForge.EVENT_BUS::register)
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        CommandManager()
        guiManager = GuiManager

        /**FEATURES*/
        reg(PlayerClass())
        reg(MaskTimer())
        reg(CrystalWaypoints())
        reg(TerminalWaypoints())
        reg(RelicWaypoints())
        reg(HighlightClass())
        reg(TankRange())
        reg(GyroRange())
        reg(ChatCommands())
        reg(MythoTracker())
        reg(DeployableManager())
        reg(MiscFeatures())
        reg(SkullHider())
        reg(RagAxe())
        reg(TitleUtils())
        reg(SchedRender())





    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent){
        PersistentSave.loadData()
    }

    @SubscribeEvent
    fun onEntityEvent(event: LivingEvent.LivingUpdateEvent) {
        if(event.entity is EntityArmorStand) DeployableManager.instance.detectDeployables(event.entity as EntityArmorStand)
    }


    private var ticks = 0L

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START || mc.thePlayer == null) return

        ticks++

        if(ticks % 2 == 0L) {
            TabUtils.parseTabEntries()
            CatacombsUtils.checkCata()
            DunClass.setupName()
        }//each 1/10th second
        if(ticks % 20 == 0L) {
            if(config.mining.starCult) StarCultNotif.checkCult()
            Utils.checkSkyblock()




        }//each second

        if (displayScreen != null) {
            if (mc.thePlayer?.openContainer == mc.thePlayer?.inventoryContainer) {
                mc.displayGuiScreen(displayScreen)
                displayScreen = null
            }
        }
    }

    companion object {
        lateinit var configManager: ConfigManager
        const val MOD_ID = "kazzutils"
        val mc: Minecraft = Minecraft.getMinecraft()
        val modDir by lazy {
            File(File(mc.mcDataDir, "config"), "kazzutils").also {
                it.mkdirs()
                File(it, "trackers").mkdirs()
            }
        }

        val json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                include(serializersModule)
                contextual(CustomColor::class, CustomColor.Serializer)
                contextual(Regex::class, RegexAsString)
                contextual(UUID::class, UUIDAsString)
            }
        }

        @JvmField
        var displayScreen: GuiScreen? = null

        @JvmStatic
        lateinit var guiManager: GuiManager

        @JvmStatic
        val version: String
            get() = Loader.instance().indexedModList[MOD_ID]!!.version

        val config: KazzUtilsConfig
            get() = configManager.config ?: error("config is null")
    }




    private fun reg(obj: Any){
        MinecraftForge.EVENT_BUS.register(obj)
    }

    object RegexAsString : KSerializer<Regex> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Regex", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Regex = Regex(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: Regex) = encoder.encodeString(value.pattern)
    }

    object UUIDAsString : KSerializer<UUID> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
        override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())
    }

    private fun String.toDashedUUID(): String {
        if (this.length != 32) return this
        return buildString {
            append(this@toDashedUUID)
            insert(20, "-")
            insert(16, "-")
            insert(12, "-")
            insert(8, "-")
        }
    }



}
