package de.kazzutils.config.categories.dungeon.feature.waypoints.m7f7;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.*;

public class Relic {

    @Expose
    @ConfigOption(name = "Relic Waypoints", desc = "")
    @ConfigEditorBoolean
    public boolean relicWaypoints = false;

    @Expose
    @ConfigOption(name = "Relic Text", desc = "")
    @ConfigEditorBoolean
    public boolean relicText = false;

    @Expose
    @ConfigOption(name = "Relic Waypoint Text", desc = "")
    @ConfigEditorText
    public String relicWaypointText = "Crystal";

    @Expose
    @ConfigOption(name = "Waypoint Color", desc = "")
    @ConfigEditorColour
    public String relicWaypointColor = "0:80:0:0:0";

    @Expose
    @ConfigOption(name = "Highlight Cauldron", desc = "")
    @ConfigEditorBoolean
    public boolean cauldronHighlight = false;

    @Expose
    @ConfigOption(name = "Render Dragon Text", desc = "")
    @ConfigEditorBoolean
    public boolean renderDragonText = false;

    @Expose
    @ConfigOption(
            name = "Dragon Text Size",
            desc = ""
    )
    @ConfigEditorSlider(
            minValue = 1F,
            maxValue = 15F,
            minStep = 1
    )
    public float dragonTextScale = 1F;

}
