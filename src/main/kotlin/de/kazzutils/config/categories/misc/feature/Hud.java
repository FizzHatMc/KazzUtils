package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Hud {

    @Expose
    @ConfigOption(name = "Defense Overlay", desc = "")
    @ConfigEditorBoolean
    public boolean defenseOverlay = false;

    @Expose
    @ConfigOption(name = "Mana Overlay", desc = "")
    @ConfigEditorBoolean
    public boolean manaOverlay = false;

    @Expose
    @ConfigOption(name = "Skill Overlay", desc = "")
    @ConfigEditorBoolean
    public boolean skillOverlay = false;

    @Expose
    @ConfigOption(name = "HP Overlay", desc = "")
    @ConfigEditorBoolean
    public boolean hpOverlay = false;

    @Expose
    @ConfigOption(name = "Hide Vanilla Health", desc = "")
    @ConfigEditorBoolean
    public boolean hideHP = false;

    @Expose
    @ConfigOption(name = "Hide Vanilla Food", desc = "")
    @ConfigEditorBoolean
    public boolean hideFood = false;

    @Expose
    @ConfigOption(name = "Hide Vanilla Armor", desc = "")
    @ConfigEditorBoolean
    public boolean hideArmor = false;

    @Expose
    @ConfigOption(name = "EffectiveHP Overlay", desc = "")
    @ConfigEditorBoolean
    public boolean effectiveHPOverlay = false;



}
