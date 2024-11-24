package de.kazzutils.config.categories.farming;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.farming.feature.contest.Contest;
import de.kazzutils.config.categories.farming.feature.gardenlevel.GardenLevel;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Farming {

    @Expose
    @ConfigOption(name = "Garden level", desc = "")
    @Accordion
    public GardenLevel gardenLevel = new GardenLevel();

    @Expose
    @ConfigOption(name = "Farming Contest", desc = "")
    @Accordion
    public Contest contest = new Contest();

}
