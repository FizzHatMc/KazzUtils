package de.kazzutils.utils

import de.kazzutils.KazzUtils
import de.kazzutils.KazzUtils.Companion.mc
import de.kazzutils.utils.Utils.removeMinecraftColorCodes
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern

object ChatUtils {

    private val hpRegex = Regex("""(?<health>[0-9,.]+)/(?<maxHealth>[0-9,.]+)❤(?<wand>\\+(?<wandHeal>[0-9,.]+)[▆▅▄▃▂▁])?""")
    private val defenseRegex = Regex("""(\d+)❈ Defense""")
    private val manaUseRegex = Regex("""-(\d+) Mana""")
    private val manaRegex = Regex("""(?<num>[0-9,.]+)/(?<den>[0-9,.]+)✎(| Mana| (?<overflow>-?[0-9,.]+)ʬ)""")
    private val skillRegex = Regex("\\+(?<gained>[0-9,.]+) (?<skillName>[A-Za-z]+) (?<progress>\\((((?<current>[0-9.,kM]+)/(?<total>[0-9.,kM]+))|((?<percent>[0-9.,]+)%))\\))")

    var health: String? = null
    var manaUse: String? = null
    var mana: String = ""
    var skill: String? = null
    var defense: String? = null

    fun getUserMessageFromUnformatedText(message: String): String {
        val regex = "(?<=: ).*"
        var returnMessage = ""
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(noColorCodes(message))

        if (matcher.find()) {
            returnMessage = matcher.group()
        }

        return returnMessage
    }

    fun messageToChat(message: String) {
        mc.thePlayer.addChatMessage(ChatComponentText(message))
    }

    fun userMessage(message: String){
        mc.thePlayer.sendChatMessage(message)
    }

    fun error(message: String){
        mc.thePlayer.addChatMessage(ChatComponentText(EnumChatFormatting.RED.toString()+message))
    }

    fun noColorCodes(message: String): String {
        // Definiere den regulären Ausdruck für Minecraft-Farbcode-Tags


        val regex = "§[0-9a-fklmnor]"

        // Erstelle ein Pattern-Objekt
        val pattern = Pattern.compile(regex)

        // Erstelle einen Matcher für den Eingabetext
        val matcher = pattern.matcher(message)

        // Entferne alle Minecraft-Farbcode-Tags
        val output = matcher.replaceAll("")
        return output
    }

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent){
        if (event.type.toInt() != 2) return
        event.isCanceled = true
        val text = event.message.unformattedTextForChat.removeMinecraftColorCodes()

        health = hpRegex.find(text)?.groupValues?.let { "${it[1]} ${it[2]}" }.toString()
        defense = defenseRegex.find(text)?.groupValues?.get(1)
        manaUse = manaUseRegex.find(text)?.groupValues?.get(1)
        mana = manaRegex.find(text)?.groupValues?.let { Pair(it[1], it[2]) }.toString()
        skill =  skillRegex.find(text)?.groupValues?.let { "+${it[1]} ${it[2]} ${it[3]}" }




    }

    @SubscribeEvent
    fun onRenderGameOverlayEvent(event: RenderGameOverlayEvent.Pre){
        val conf = KazzUtils.config.misc.hud
        if(conf.hideArmor && event.type ==  RenderGameOverlayEvent.ElementType.ARMOR) event.isCanceled = true
        if(conf.hideHP && event.type ==  RenderGameOverlayEvent.ElementType.HEALTH) event.isCanceled = true
        if(conf.hideFood && event.type ==  RenderGameOverlayEvent.ElementType.FOOD) event.isCanceled = true


    }


}