package de.kazzutils.config.categories.farming.feature.contest;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Contest {

    @Expose
    @ConfigOption(name = "Contest Display", desc = "")
    @ConfigEditorBoolean
    public boolean contestDisplay = false;


    @Expose
    @ConfigOption(name = "Contest Display Text Color", desc = "")
    @ConfigEditorColour
    public String contestDisplayColor = "0:80:0:0:0";

}
