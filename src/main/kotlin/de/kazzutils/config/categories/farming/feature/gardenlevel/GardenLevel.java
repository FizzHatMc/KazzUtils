package de.kazzutils.config.categories.farming.feature.gardenlevel;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;


public class GardenLevel {

    @Expose
    @ConfigOption(name = "Garden Level Display", desc = "")
    @ConfigEditorBoolean
    public boolean gardenLevelDisplay = false;

    @Expose
    @ConfigOption(name = "Display Garden Level Progress in %", desc = "")
    @ConfigEditorBoolean
    public boolean gardenLevelPercentage = false;

    @Expose
    @ConfigOption(name = "Garden Level Display Text Color", desc = "")
    @ConfigEditorColour
    public String gardenLevelColor = "0:80:0:0:0";

}
