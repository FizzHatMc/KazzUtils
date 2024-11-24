package de.kazzutils.config.categories.event;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class mythologicalEvent {

    @Expose
    @ConfigOption(name = "Mythological Tracker ", desc = "")
    @ConfigEditorBoolean
    public boolean mytholocialTracker = false;

    @Expose
    @ConfigOption(name = "Mythological Tracker Text Color", desc = "")
    @ConfigEditorColour
    public String mythoColor = "0:80:0:0:0";

}
