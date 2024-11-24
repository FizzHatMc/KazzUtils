package de.kazzutils.config.categories.misc.feature.arrowsoulflowhud;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Soulflow {

    @Expose
    @ConfigOption(name = "Soulflow Display", desc = "")
    @ConfigEditorBoolean
    public boolean soulflowDisplay = false;

    @Expose
    @ConfigOption(name = "Soulflow Notification", desc = "")
    @ConfigEditorBoolean
    public boolean soulflowNotif = false;

    @Expose
    @ConfigOption(name = "When to send Notification", desc = "")
    @ConfigEditorSlider(minValue = 0, maxValue = 20000, minStep = 10)
    public int minSoulflow = 0;


    @Expose
    @ConfigOption(name = "Soulflow Display Text Color", desc = "")
    @ConfigEditorColour
    public String soulflowDisplayColor = "0:80:0:0:0";

}
