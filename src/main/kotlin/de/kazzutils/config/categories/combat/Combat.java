package de.kazzutils.config.categories.combat;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.combat.slayer.Slayer;
import io.github.notenoughupdates.moulconfig.annotations.Category;

public class Combat {

    @Expose
    @Category(name = "Slayer", desc = "Slayer Features")
    public Slayer slayer = new Slayer();

}
