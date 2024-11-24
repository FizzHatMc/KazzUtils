package de.kazzutils.config.categories.combat.slayer;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.combat.slayer.voidgloom.VoidgloomFeatures;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Slayer {

    @Expose
    @ConfigOption(name = "Voidgloom Slayer", desc = "")
    @Accordion
    public VoidgloomFeatures voidgloom = new VoidgloomFeatures();

}
