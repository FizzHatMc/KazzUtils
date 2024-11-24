package de.kazzutils.utils

import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import de.kazzutils.KazzUtils.Companion.mc
import net.minecraft.client.Minecraft
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.WorldSettings.GameType
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.regex.Pattern
import java.util.stream.Collectors

object TabUtils { //TODO: REWORK CLASS = CRASHES ALOT

    private val areaPattern: Pattern = Pattern.compile("Area: (.+)")
    private var trim: String = ""
    private var p: PlayerInfoOrdering = PlayerInfoOrdering()
    var playerClass = ""
    var area: String = ""
    var archerName: String = ""
    var tankName: String = ""
    var healerName: String = ""
    var mageName: String = ""
    var berserkerName: String = ""
    var gardenLevel: Int = 0
    var gardenPercent: Double = 0.0
    var contest : ArrayList<String?> = ArrayList()
    var time: String = ""
    var petName: String = ""
    var petXp: String? = ""
    var comms: ArrayList<String?> = ArrayList()
    var soulflow : Int = 0

    fun getPlayerByName(playerName: String): EntityPlayer? {
        val players: List<EntityPlayer> = mc.theWorld.playerEntities

        for (player in players) {
            if (player.name == playerName) {
                return player
            }
        }

        return null // Player not found
    }

    fun getPlayerNameByClass(playerClass: String): String {
        return when (playerClass) {
            "Archer" -> archerName
            "Tank" -> tankName
            "Healer" -> healerName
            "Mage" -> mageName
            "Berserker" -> berserkerName
            else -> ""
        }
    }

    @SideOnly(Side.CLIENT)
    class PlayerInfoOrdering : Ordering<NetworkPlayerInfo?>() {
        override fun compare(info1: NetworkPlayerInfo?, info2: NetworkPlayerInfo?): Int {
            if (info1 == null) return -1
            if (info2 == null) return 0
            return ComparisonChain.start()
                .compareTrueFirst(!isSpectator(info1.gameType), !isSpectator(info2.gameType))
                .compare(
                    if (info1.playerTeam != null) info1.playerTeam.registeredName else "",
                    if (info2.playerTeam != null) info2.playerTeam.registeredName else ""
                )
                .compare(info1.gameProfile.name, info2.gameProfile.name)
                .result()
        }
    }


    private fun fetchTabEntries(): List<NetworkPlayerInfo> {
        var entries = emptyList<NetworkPlayerInfo>()
        if (mc.thePlayer != null) {
            entries = p.sortedCopy(mc.thePlayer.sendQueue.playerInfoMap)
        }
        return entries
    }


    fun parseTabEntries() {
        comms.clear()
        contest.clear()
        if (mc.thePlayer == null) return
        if (mc.theWorld == null) return

        val scoreboardList = mapNotNull(fetchTabEntries())

        for (line in scoreboardList) {
            trim = line!!.trim { it <= ' ' }
            val matcher = areaPattern.matcher(trim)
            if (matcher.find()) {
                area = matcher.group(1)
            }
            /*
            if (petFlag && petFlagCount <= 1) {
                if (petFlagCount == 0) petName = line.substring(trim.indexOf(']') + 2)
                if (petFlagCount == 1) petXp = line
            petFlagCount++
            {} else {
                petFlagCount = 0
                petFlag = false
            }
            */

            when {
                trim.contains("Pet:", ignoreCase = true) -> {
                    val index = scoreboardList.indexOf(line) + 1
                    petName = scoreboardList.getOrNull(index)?.substring(trim.indexOf(']') + 2) ?: return
                    petXp = scoreboardList.getOrNull(index + 1) ?: return
                }
                trim.contains("Contest:") -> {
                    val index = scoreboardList.indexOf(line) + 1
                    time = scoreboardList.getOrNull(index)
                        ?.substring(scoreboardList[index]?.indexOf(": ")?.plus(1) ?: return).toString()
                    for (i in 1..3) {
                        contest.add(scoreboardList.getOrNull(index + i) ?: continue)
                    }
                }
                trim.contains("Garden Level:") -> {
                    val split = trim.substring(trim.indexOf(":") + 1)
                    var lvl = split.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    lvl = lvl.replace(" ", "")
                    val test: Int? = NumberUtils.getNumber(lvl)
                    if (test != null) {
                        gardenLevel = test
                        if (gardenLevel != 15) {
                            gardenPercent = trim.substring(trim.indexOf("(") + 1, trim.indexOf(")") - 7).toDouble()
                        }
                    } else {
                        if (lvl != "XV") {
                            gardenLevel = NumberUtils.toInteger(lvl)
                            gardenPercent = trim.substring(trim.indexOf("(") + 1, trim.indexOf(")") - 1).toDouble()
                        } else {
                            gardenLevel = NumberUtils.toInteger("XV")
                        }
                    }
                }
                trim.contains("Soulflow: ") -> {
                    soulflow = trim.substring(trim.indexOf('S') + "Soulflow: ".length).replace(",", "").toInt()
                }
                trim.contains("Dungeon: Catacombs") -> {
                    area = "Catacombs"
                }
                trim.contains("Commissions:") -> {
                    val index = scoreboardList.indexOf(line)
                    for (i in 1..5) {
                        if (scoreboardList.getOrNull(index + i).isNullOrBlank()) return
                        comms.add(scoreboardList[index + i])
                    }
                }
                line.contains("(Archer") -> {
                    val archerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    if (archerName.contains(mc.thePlayer.name)) playerClass = "Archer"
                }
                line.contains("(Tank") -> {
                    val tankName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    if (tankName.contains(mc.thePlayer.name)) playerClass = "Tank"
                }
                line.contains("(Mage") -> {
                    val mageName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    if (mageName.contains(mc.thePlayer.name)) playerClass = "Mage"
                }
                line.contains("(Healer") -> {
                    val healerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    if (healerName.contains(mc.thePlayer.name)) playerClass = "Healer"
                }
                line.contains("(Berserk") -> {
                    val berserkerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    if (berserkerName.contains(mc.thePlayer.name)) playerClass = "Berserk"
                }
            }
            /*
            if(trim.contains("Pet:",true)){
                var index = scoreboardList.indexOf(line) + 1
                petName = scoreboardList[index]?.substring(trim.indexOf(']') + 2) ?: return
                petXp = scoreboardList[index+1] ?: return
            }
            if (trim.contains("Contest:")) {
                val index = scoreboardList.indexOf(line) + 1
                time = scoreboardList[index]?.substring((scoreboardList[index]?.indexOf(": ") )?.plus(1) ?: return).toString()
                for(i in 1..3){
                    contest.add(scoreboardList[index+i])
                }
            } else if (trim.contains("Garden Level:")) {
                val split = trim.substring(trim.indexOf(":") + 1)
                var lvl = split.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                lvl = lvl.replace(" ", "")
                val test: Int? = NumberUtils.getNumber(lvl)

                if (test != null) {
                    //gardenLevel = Integer.parseInt(trim.substring(trim.indexOf(":") + 2 /*trim.indexOf(":") + 4*/).replace(" ", ""));
                    gardenLevel = test
                    if (gardenLevel != 15) {
                        gardenPercent = trim.substring(trim.indexOf("(") + 1, trim.indexOf(")") - 7).toDouble()
                    }
                } else {
                    if (lvl != "XV") {
                        //String percent = split.split(" ")[1];
                        gardenLevel = NumberUtils.toInteger(lvl)
                        gardenPercent = trim.substring(trim.indexOf("(") + 1, trim.indexOf(")") - 1).toDouble()
                    } else gardenLevel = NumberUtils.toInteger("XV")
                }
            } else if (trim.contains("Pet:")) {
                petFlag = true
            } else if (trim.contains("Dungeon: Catacombs")) {
                //area = "Catacombs"
            } else if (trim.contains("Commissions:")) {
                //area = "Dwarven"
                var index = scoreboardList.indexOf(line)
                for(i in 1..5){
                    if(scoreboardList[index+i] == "") return
                    comms.add(scoreboardList[index+i])
                }
            }



            if(trim.contains("Soulflow: ")){
                soulflow = trim.substring( trim.indexOf('S')+"Soulflow: ".length).replace(",","").toInt()
            }

            if (line.contains("(Archer")) {
                archerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                if(archerName.contains(mc.thePlayer.name)) playerClass = "Archer"
                //ChatUtils.messageToChat("Player: $archerName class: $playerClass Name: "+mc.thePlayer.name)
            } else if (line.contains("(Tank")) {
                tankName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                if(tankName.contains(mc.thePlayer.name)) playerClass = "Tank"
                //ChatUtils.messageToChat("Player: $tankName class: $playerClass"+mc.thePlayer.name)
            } else if (line.contains("(Mage")) {
                mageName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                if(mageName .contains(mc.thePlayer.name)) playerClass = "Mage"
                //ChatUtils.messageToChat("Player: $mageName class: $playerClass"+mc.thePlayer.name)
            } else if (line.contains("(Healer")) {
                healerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                if(healerName.contains(mc.thePlayer.name)) playerClass = "Healer"
                //ChatUtils.messageToChat("Player: $healerName class: $playerClass"+mc.thePlayer.name)
            } else if (line.contains("(Berserk")) {
                berserkerName = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                if(berserkerName.contains(mc.thePlayer.name)) playerClass = "Berserk"
                //ChatUtils.messageToChat("Player: $berserkerName class: $playerClass"+mc.thePlayer.name)
            }
        }
        */
        }
    }

    private fun isSpectator(gameType: GameType): Boolean {
        return gameType == GameType.SPECTATOR
    }

    private fun mapNotNull(tabEntries: List<NetworkPlayerInfo>): List<String?> {
        return tabEntries.stream()
            .map { info: NetworkPlayerInfo ->
                if (info.displayName != null) info.displayName.unformattedText else null
            }
            .filter { displayName: String? -> displayName != null }
            .collect(Collectors.toList())
    }

    private var playerOrdering: Collection<NetworkPlayerInfo>? = null

    fun readTabList(): List<String> {
        val thePlayer = Minecraft.getMinecraft().thePlayer
        playerOrdering = thePlayer.sendQueue.playerInfoMap
        val result: MutableList<String> = ArrayList()
        for (info in (playerOrdering as MutableCollection<NetworkPlayerInfo>?)!!) {
            val name: String = mc.ingameGUI.tabList.getPlayerName(info)
            result.add(stripVanillaMessage(name))
        }
        if (result.isNotEmpty()) {
            result.removeAt(result.size - 1)
        }
        return result
    }

    private fun stripVanillaMessage(orignalMessage: String): String {
        var message = orignalMessage
        while (message.startsWith("§r")) {
            message = message.substring(2)
        }
        while (message.endsWith("§r")) {
            message = message.substring(0, message.length - 2)
        }
        return message
    }




}