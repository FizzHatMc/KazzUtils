package de.kazzutils.config.categories.dungeon.feature.waypoints.m7f7;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Crystal {

    @Expose
    @ConfigOption(name = "Crystal Waypoints", desc = "")
    @ConfigEditorBoolean
    public boolean crystalWaypoints = false;

    @Expose
    @ConfigOption(name = "Crystal Text", desc = "")
    @ConfigEditorBoolean
    public boolean crystalText = false;

    @Expose
    @ConfigOption(name = "Crystal Waypoint Text", desc = "")
    @ConfigEditorText
    public String crystalWaypointText = "Crystal";

    @Expose
    @ConfigOption(name = "Waypoint Color", desc = "")
    @ConfigEditorColour
    public String crystalWaypointColor = "0:80:0:0:0";
}
