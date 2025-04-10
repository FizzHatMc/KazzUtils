package de.kazzutils.features.events.mythological

import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class MythoTracker {

    companion object{
        var inq: Int = 0
        var champ: Int = 0
        var mino: Int = 0
        var gaia: Int = 0
        var lynx: Int = 0
        var hunter: Int = 0

        var totalMobs: Int = 0
        var coins: Double = 0.0
        var burrows: Int = 0
        var feathers: Int = 0

        var sinceInq: Int = 0

        var champChance: Double = 0.0
        var minoChance: Double = 0.0
        var gaiaChance: Double = 0.0
        var lynxChance: Double = 0.0
        var hunterChance: Double = 0.0
        var inqChance : Double = 0.0


        fun getVarByName(name: String) : Any{
            when (name) {
                "inq" -> return inq
                "champ" -> return  champ
                "mino" -> return  mino
                "gaia" -> return gaia
                "lynx" -> return lynx
                "hunter" -> return hunter
                "totalMobs" -> return  totalMobs
                "coins" ->return   coins
                "burrows" -> return  burrows
                "feathers" -> return  feathers
                "sinceInq" -> return  sinceInq
                "champChance" -> return  champChance
                "minoChance" -> return  minoChance
                "gaiaChance" -> return  gaiaChance
                "lynxChance" -> return  lynxChance
                "hunterChance" -> return  hunterChance
                "inqChance" -> return inqChance
                else -> return "N/A"
            }
        }
    }
    @SubscribeEvent
    fun onChat(event : ClientChatReceivedEvent){
        if (event.type.toInt() == 2) return
        val message = event.message.unformattedTextForChat

        when(message){
            "You dug out a Minos Inquisitor!" -> inq++
            "You dug out a Champion!" -> champ++
            "You dug out a Minotaur!" -> mino++
            "You dug out a Gaia!" -> gaia++
            "You dug out a Lynx!" -> lynx++
            "You dug out a Hunter!" -> hunter++
        }

        if(message.contains("You dug out a Minos Inquisitor!")) sinceInq = 0
        if(message.contains("You dug out") && message.contains("coins!")){
            val coin = message.substring(message.indexOf("out") + 4, message.indexOf("coins!") - 1).replace(",", "")
            val coins2 = coin.toInt()
            var coins3 = coins2.toDouble() / 1000000
            coins3 *= 100
            val komma = coins3.toInt()
            coins3 = komma.toDouble() / 100

            coins += coins3
        }

        if(message.contains("You dug out a Griffin Feather!")) feathers++

        if(message.contains("You dug out a Griffin Burrow!")) burrows++

        totalMobs = inq + champ + mino + gaia + lynx + hunter

        if(totalMobs>0){
            inqChance = (Math.round(((inq / totalMobs) * 100).toDouble()) / 100).toDouble()
            champChance = (Math.round(((champ / totalMobs) * 100).toDouble()) / 100).toDouble()
            minoChance = (Math.round(((mino / totalMobs) * 100).toDouble()) / 100).toDouble()
            gaiaChance = (Math.round(((gaia / totalMobs) * 100).toDouble()) / 100).toDouble()
            lynxChance = (Math.round(((lynx / totalMobs) * 100).toDouble()) / 100).toDouble()
            hunterChance = (Math.round(((hunter / totalMobs) * 100).toDouble()) / 100).toDouble()
        }
    }

}