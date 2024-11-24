package de.kazzutils.config.categories.dungeon.feature.waypoints.m7f7;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class m7f7 {

    @Expose
    @ConfigOption(name = "Crystal", desc = "")
    @Accordion
    public Crystal crystal = new Crystal();

    @Expose
    @ConfigOption(name = "Terminal", desc = "")
    @Accordion
    public Terminal terminal = new Terminal();

    @Expose
    @ConfigOption(name = "Relic", desc = "")
    @Accordion
    public Relic relic = new Relic();
}

