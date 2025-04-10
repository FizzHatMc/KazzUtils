package de.kazzutils.config.categories.event;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class carnival {

    @Expose
    @ConfigOption(name = "Fruit Digging Helper ", desc = "Marks Possible Bomb locations when mined in Bomb mode")
    @ConfigEditorBoolean
    public boolean carnivalDig = false;

}
