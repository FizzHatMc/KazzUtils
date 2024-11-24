package de.kazzutils.config.categories.mining.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class CommissionTracker {

    @Expose
    @ConfigOption(name = "Commission Tracker ", desc = "")
    @ConfigEditorBoolean
    public boolean commissionTrackerOverlay = false;

    @Expose
    @ConfigOption(name = "Commission Tracker Text Color", desc = "")
    @ConfigEditorColour
    public String commissionTrackerOverlayColor = "0:80:0:0:0";


}
