package de.kazzutils.features.dungeon.M7

import de.kazzutils.KazzUtils
import de.kazzutils.event.WorldChangeEvent
import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.TabUtils
import net.minecraft.network.play.server.S2APacketParticles
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*
import kotlin.concurrent.schedule

class DragPrio {


    // Stuff
    var search = false
    var drags = arrayOfNulls<DragInfo.DragData>(2)
    var showText = false
    var bersTeam = false
    var purpleSpawn = false
    var healer = false
    var tank = false
    var conf = KazzUtils.config.dungeon.dragPrio

    // Function to check if the split drags are soul/flame/apex (easy split)
    private fun isEasySplit(): Boolean {
        return drags[0]?.easy == true && drags[1]?.easy == true
    }

    // Function to check if block pos is in a correct location
    private fun checkBlockPos(x: Int, y: Int, z: Int) {
        // Check if correct height
        if (y !in 14..19) return

        when {
            // Check if red/green
            x in 27..32 -> {
                // Check if red
                if (z == 59) {
                    if (!DragInfo.POWER.spawned) {
                        DragInfo.POWER.spawned = true
                        assignDrag(DragInfo.POWER)
                        Timer().schedule(8000) { DragInfo.POWER.spawned = false }
                    }
                    // Check if green
                } else if (z == 94) {
                    if (!DragInfo.APEX.spawned) {
                        DragInfo.APEX.spawned = true
                        assignDrag(DragInfo.APEX)
                        Timer().schedule(8000) { DragInfo.APEX.spawned = false }
                    }
                }
            }
            // Check if blue/orange
            x in 79..85 -> {
                // Check if blue
                if (z == 94) {
                    if (!DragInfo.ICE.spawned) {
                        DragInfo.ICE.spawned = true
                        assignDrag(DragInfo.ICE)
                        Timer().schedule(8000) { DragInfo.ICE.spawned = false }
                    }
                    // Check if orange
                } else if (z == 56) {
                    if (!DragInfo.FLAME.spawned) {
                        DragInfo.FLAME.spawned = true
                        assignDrag(DragInfo.FLAME)
                        Timer().schedule(8000) { DragInfo.FLAME.spawned = false }
                    }
                }
            }
            // Check if purple
            x == 56 -> {
                if (!DragInfo.SOUL.spawned) {
                    DragInfo.SOUL.spawned = true
                    purpleSpawn = true
                    assignDrag(DragInfo.SOUL)
                    Timer().schedule(8000) { DragInfo.SOUL.spawned = false }
                }
            }
        }
    }

    // Getting drag objects & calls determinePrio once both drags have been grabbed
    // If drags is full (first two already spawned) it just displays the spawning drag if enabled
    private fun assignDrag(drag: DragInfo.DragData) {
        if (drags[0] == null) {
            drags[0] = drag
        } else if (drags[1] == null) {
            drags[1] = drag
            determinePrio()
        } else if (conf.singleDrag) {

            de.kazzutils.utils.RenderUtils.drawTitle("${drag.dragColor} IS SPAWNING!",EnumChatFormatting.RED) // TODO: MAKE COLOR BE SAME AS DRAGON
            showText = true
            Timer().schedule(2000) { showText = false }
        }
    }

    // Determines drag priority, split/no split, & calls displayDragon once complete
    private fun determinePrio() {
        val normalDrag = if (drags[0]!!.prio[0] < drags[1]!!.prio[0]) drags[0]!! else drags[1]!!
        val truePower = 18  //TODO: MAKE GET TRUE POWER
        var split = 0
        val bersDrag : DragInfo.DragData?
        val archDrag : DragInfo.DragData?


        if (truePower >= conf.splitPower) {
            split = 1
        } else if (isEasySplit() && truePower >= conf.easyPower) {
            split = 1
        }

        if (drags[0]!!.prio[split] < drags[1]!!.prio[split]) {
            bersDrag = drags[0]
            archDrag = drags[1]
        } else {
            bersDrag = drags[1]
            archDrag = drags[0]
        }

        displayDragon(bersDrag!!, archDrag!!, normalDrag, split)
    }

    // Display title on screen and say party msg if enabled
    private fun displayDragon(
        bersDrag: DragInfo.DragData,
        archDrag: DragInfo.DragData,
        normalDrag: DragInfo.DragData,
        split: Int
    ) {
        if (conf.teamChat && split == 1) {
            ChatUtils.userMessage("/pc BERS TEAM --> ${bersDrag.dragColor}\n" + "\n" + "ARCH TEAM --> ${archDrag.dragColor}")
        }

       if (split == 1) {
            if (bersTeam || (purpleSpawn && ((healer && conf.healerPurp.toString() == "Arch_Team") || (tank && conf.tankPurp.toString() == "Arch_Team")))) {
                de.kazzutils.utils.RenderUtils.drawTitle("${bersDrag.dragColor} IS SPAWNING!", EnumChatFormatting.RED) //TODO: MAKE COLOR BE SAME AS DRAGON
            } else {
                de.kazzutils.utils.RenderUtils.drawTitle("${archDrag.dragColor} IS SPAWNING!", EnumChatFormatting.RED) //TODO: MAKE COLOR BE SAME AS DRAGON
            }
        } else {
            de.kazzutils.utils.RenderUtils.drawTitle("${normalDrag.dragColor} IS SPAWNING!", EnumChatFormatting.RED) //TODO: MAKE COLOR BE SAME AS DRAGON
        }

        showText = true
        Timer().schedule(2000) { showText = false }
    }

    // Checks and updates some stuff on p5 start
    fun registerChatHandler() {
        drags = arrayOfNulls(2)
        purpleSpawn = false
        bersTeam = false
        healer = false
        tank = false
        DragInfo.POWER.spawned = false
        DragInfo.FLAME.spawned = false
        DragInfo.ICE.spawned = false
        DragInfo.SOUL.spawned = false
        DragInfo.APEX.spawned = false

        val selectedClass = TabUtils.playerClass

        when (selectedClass[0]) {
            'B', 'M' -> bersTeam = true
            'H' -> {
                healer = true
                if (conf.healerTeam.toString() == "Bers_Team") bersTeam = true
            }
            'T' -> {
                tank = true
                if (conf.tankTeam.toString() == "Bers_Team") bersTeam = true
            }
            else -> bersTeam = false
        }

        // Say split message if enabled
        Timer().schedule(2000) {
            if (conf.saySplit) {
                val power = 18 //TODO: MAKE GET TRUE POWER
                val message = when {
                    power >= conf.splitPower -> "Power: $power || Split on all drags!"
                    power >= conf.easyPower -> "Power: $power || Split on easy drags!"
                    else -> "Power: $power || No split!"
                }
                ChatUtils.userMessage("/pc $message")
            }
        }

        // Start searching for particles
        search = true
    }

    @SubscribeEvent
    fun onPacketRecievedEvent(packet: S2APacketParticles) {
        if (search && packet.particleType.name == "ENCHANTMENT_TABLE") {
            checkBlockPos(
                packet.xCoordinate.toInt(),
                packet.yCoordinate.toInt(),
                packet.zCoordinate.toInt()
            )
        }
    }

    // Resetting search on world load
    @SubscribeEvent
    fun onWorldChange(event: WorldChangeEvent) {
        search = false
    }

}
object DragInfo {

    var POWER = DragData("RED", Renderer.RED, listOf(1, 3), false, false)
    var FLAME = DragData("ORANGE", Renderer.GOLD, listOf(2, 1), false, true)
    var ICE = DragData("BLUE", Renderer.BLUE, listOf(3, 4), false, false)
    var SOUL = DragData("PURPLE", Renderer.LIGHT_PURPLE, listOf(4, 5), false, true)
    var APEX = DragData("GREEN", Renderer.GREEN, listOf(5, 2), false, true)

    data class DragData(
        var dragColor: String,
        var renderColor: Renderer,
        var prio: List<Int>,
        var spawned: Boolean,
        var easy: Boolean
    )
}

enum class Renderer(val color: String) {
    RED("RED"),
    GOLD("GOLD"),
    BLUE("BLUE"),
    LIGHT_PURPLE("LIGHT_PURPLE"),
    GREEN("GREEN");
}