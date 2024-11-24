package de.kazzutils.config.categories.dungeon.feature;

import com.google.gson.annotations.Expose;
import de.kazzutils.data.enumClass.DunClass;
import de.kazzutils.config.categories.dungeon.feature.classes.Tank;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorDropdown;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class DungeonClass {

    @Expose
    @ConfigOption(name = "Highlight Class", desc = "")
    @ConfigEditorDropdown
    public DunClass enumClass = null;


    @Expose
    @ConfigOption(name = "Tank", desc = "")
    @Accordion
    public Tank tank = new Tank();

}

