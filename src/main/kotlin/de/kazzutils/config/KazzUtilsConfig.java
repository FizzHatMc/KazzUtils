package de.kazzutils.config;


import com.google.gson.annotations.Expose;
import de.kazzutils.KazzUtils;
import de.kazzutils.config.categories.combat.Combat;
import de.kazzutils.config.categories.dungeon.Dungeon;
import de.kazzutils.config.categories.farming.Farming;
import de.kazzutils.config.categories.mining.Mining;
import de.kazzutils.config.categories.misc.Misc;
import io.github.notenoughupdates.moulconfig.Config;
import io.github.notenoughupdates.moulconfig.annotations.Category;


public class KazzUtilsConfig extends Config {


    @Override
    public String getTitle(){
        return "KazzUtilsV2 "+ KazzUtils.getVersion()+" by §aRealKazz§r, preset by §channibal22§r ";
    }

    @Override
    public void saveNow() {
        KazzUtils.configManager.save();
    }

    @Expose
    @Category(name = "Combat", desc = "General Combat Features")
    public Combat combat = new Combat();

    @Expose
    @Category(name = "Dungeon", desc = "Dungeon shit")
    public Dungeon dungeon = new Dungeon();

    @Expose
    @Category(name = "Mining", desc = "Mining shit")
    public Mining mining = new Mining();

    @Expose
    @Category(name = "Farming", desc = "Farming")
    public Farming farming = new Farming();

    @Expose
    @Category(name = "Misc", desc = "Random Settings")
    public Misc misc = new Misc();




}
