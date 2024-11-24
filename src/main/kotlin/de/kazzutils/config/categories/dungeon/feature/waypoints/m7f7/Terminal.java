package de.kazzutils.config.categories.dungeon.feature.waypoints.m7f7;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorText;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Terminal {

    @Expose
    @ConfigOption(name = "Terminal Waypoints", desc = "")
    @ConfigEditorBoolean
    public boolean terminalWaypoints = false;

    @Expose
    @ConfigOption(name = "Terminal Text", desc = "")
    @ConfigEditorBoolean
    public boolean terminalText = false;

    @Expose
    @ConfigOption(name = "Terminal Waypoint Text", desc = "")
    @ConfigEditorText
    public String terminalWaypointText = "Crystal";

    @Expose
    @ConfigOption(name = "Waypoint Color", desc = "")
    @ConfigEditorColour
    public String terminalWaypointColor = "0:80:0:0:0";

}
