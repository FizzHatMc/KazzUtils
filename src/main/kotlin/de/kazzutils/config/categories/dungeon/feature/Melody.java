package de.kazzutils.config.categories.dungeon.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Melody {

    @Expose
    @ConfigOption(name = "Send Melody Progress Message", desc = "")
    @ConfigEditorBoolean
    public boolean melodyChatMessage = false;

    @Expose
    @ConfigOption(name = "Change Melody Progress Message", desc = "\"Melody\" 1/4")
    @ConfigEditorText
    public String melodyChatMessageChat = "Melody";

}
