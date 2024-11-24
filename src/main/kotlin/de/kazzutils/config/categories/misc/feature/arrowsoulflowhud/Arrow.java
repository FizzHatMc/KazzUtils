package de.kazzutils.config.categories.misc.feature.arrowsoulflowhud;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Arrow {

    @Expose
    @ConfigOption(name = "Arrows Display", desc = "")
    @ConfigEditorBoolean
    public boolean ArrowDisplay = false;

    @Expose
    @ConfigOption(name = "Arrows Low Notification", desc = "")
    @ConfigEditorBoolean
    public boolean ArrowNotif = false;

    @Expose
    @ConfigOption(name = "When to send Notification", desc = "")
    @ConfigEditorSlider(minValue = 0, maxValue = 2880, minStep = 10)
    public int minArrow = 0;

    @Expose
    @ConfigOption(name = "Arrows Display Text Color", desc = "")
    @ConfigEditorColour
    public String ArrowDisplayColor = "0:80:0:0:0";

}
