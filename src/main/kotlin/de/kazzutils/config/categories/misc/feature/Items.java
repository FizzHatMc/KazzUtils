package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Items {

    @Expose
    @ConfigOption(name = "Gyro Range", desc = "")
    @ConfigEditorBoolean
    public boolean gyroRange = false;

    @Expose
    @ConfigOption(name = "Gyro Color", desc = "")
    @ConfigEditorColour
    public String gyroColor = "0:80:0:0:0";

    @Expose
    @ConfigOption(
            name = "Gyro Range Scale",
            desc = ""
    )
    @ConfigEditorSlider(
            minValue = 0F,
            maxValue = 10F,
            minStep = 0.5F
    )
    public float gyroRangeScale = 1F;

    @Expose
    @ConfigOption(name = "Ragnarok Axe Notification", desc = "")
    @ConfigEditorBoolean
    public boolean ragAxe = false;

}
