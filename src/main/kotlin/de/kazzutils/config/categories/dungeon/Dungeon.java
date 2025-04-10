package de.kazzutils.config.categories.dungeon;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.dungeon.feature.*;
import io.github.notenoughupdates.moulconfig.annotations.Category;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;


public class Dungeon {

    @Expose
    @ConfigOption(name = "Hide Soulweaver Gloves", desc = "")
    @ConfigEditorBoolean
    public boolean hideSoulweaverGloves = false;

    @Expose
    @ConfigOption(name = "Livid Finder", desc = "")
    @ConfigEditorBoolean
    public boolean lividFinder = false;

    @Expose
    @ConfigOption(name = "Hide false Livid", desc = "")
    @ConfigEditorBoolean
    public boolean lividHider = false;

    @Expose
    @Category(name = "Items", desc = "Settings for different Items")
    public ItemConf items = new ItemConf();

    @Expose
    @Category(name = "Class", desc = "Settings for the Classes")
    public DungeonClass dungeonClass = new DungeonClass();

    @Expose
    @Category(name = "Waypoints", desc = "Settings for Waypoints in Dungeon")
    public Waypoints waypoints = new Waypoints();

    @Expose
    @Category(name = "Dragon Priority", desc = "Settings for Dragon Prios")
    public DragPrio dragPrio = new DragPrio();

    @Expose
    @Category(name = "Melody", desc = "Settings for Melody")
    public Melody melody = new Melody();

}