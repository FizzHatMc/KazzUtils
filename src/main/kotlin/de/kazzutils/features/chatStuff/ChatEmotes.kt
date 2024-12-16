package de.kazzutils.features.chatStuff

import de.kazzutils.utils.ChatUtils
import de.kazzutils.utils.ChatUtils.getUserMessageFromUnformatedText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.regex.Pattern


class ChatEmotes {
    val replacements = mapOf(
        "<3" to "❤",
        "o/" to "( ﾟ◡ﾟ)/",
        ":star:" to "✮",
        ":yes:" to "✔",
        ":no:" to "✖",
        ":java:" to "☕",
        ":arrow:" to "➜",
        ":shrug:" to "¯\\_(ツ)_/¯",
        ":tableflip:" to "(╯°□°）╯︵ ┻━┻",
        ":totem:" to "☉_☉",
        ":typing:" to "✎...",
        ":maths:" to "√(π+x)=L",
        ":snail:" to "@'-'",
        ":thinking:" to "(0.o?)",
        ":gimme:" to "༼つ◕_◕༽つ",
        ":wizard:" to "('-')⊃━☆ﾟ.*･｡ﾟ",
        ":pvp:" to "⚔",
        ":peace:" to "✌",
        ":puffer:" to "<('O')>",
        "h/" to "ヽ(^◇^*)/",
        ":sloth:" to "(・⊝・)",
        ":dog:" to "(ᵔᴥᵔ)",
        ":dj:" to "ヽ(⌐■_■)ノ♬",
        ":yey:" to "ヽ (◕◡◕) ﾉ",
        ":snow:" to "☃",
        ":dab:" to "<o/",
        ":cat:" to "= ＾● ⋏ ●＾ =",
        ":cute:" to "(✿◠‿◠)",
        ":skull:" to "☠"
    )

    var replaced = false

    @SubscribeEvent
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type.toInt() == 2) return
        var message = event.message.unformattedText
        message = getUserMessageFromUnformatedText(message)

        if (message.startsWith("/") &&
            !message.startsWith("/pc") &&
            !message.startsWith("/ac") &&
            !message.startsWith("/gc") &&
            !message.startsWith("/msg") &&
            !message.startsWith("/w") &&
            !message.startsWith("/r")) return

        replaced = false
        val words = message.split(" ").toMutableList()

        for (i in words.indices) {
            if (replacements.containsKey(words[i])) {
                replaced = true
                words[i] = replacements[words[i]].orEmpty()
            }
        }

        if (!replaced) return
        val newMessage = words.joinToString(" ")
        event.isCanceled = true
        ChatUtils.userMessage(newMessage)
    }




}