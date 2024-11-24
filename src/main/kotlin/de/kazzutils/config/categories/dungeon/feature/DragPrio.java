package de.kazzutils.config.categories.dungeon.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorDropdown;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class DragPrio {
/*
    @Expose
    @ConfigOption(name = "", desc = "")
    @ConfigEditorBoolean
    public boolean  = false;
*/
    @Expose
    @ConfigOption(name = "Send Split Message", desc = "")
    @ConfigEditorBoolean
    public boolean teamChat = false;

    @Expose
    @ConfigOption(name = "Say Split", desc = "")
    @ConfigEditorBoolean
    public boolean saySplit = false;

    @Expose
    @ConfigOption(name = "Also Display Single Dragons", desc = "")
    @ConfigEditorBoolean
    public boolean singleDrag = false;

    @Expose
    @ConfigOption(name = "Split Power", desc = "")
    @ConfigEditorSlider(minValue = 0, maxValue = 32, minStep = 1)
    public int splitPower = 18;

    @Expose
    @ConfigOption(name = "Easy Split", desc = "")
    @ConfigEditorSlider(minValue = 0, maxValue = 32, minStep = 1)
    public int easyPower = 22;

    @Expose
    @ConfigOption(name = "Healer", desc = "Set the team the healer will go with when purple")
    @ConfigEditorDropdown()
    public team healerPurp = team.Arch_Team;

    @Expose
    @ConfigOption(name = "Tank", desc = "Set the team the tank will go with when purple")
    @ConfigEditorDropdown()
    public team tankPurp = team.Bers_Team;

    @Expose
    @ConfigOption(name = "Healer", desc = "Set the team the healer will go with")
    @ConfigEditorDropdown()
    public team healerTeam = team.Arch_Team;

    @Expose
    @ConfigOption(name = "Tank", desc = "Set the team the tank will go with")
    @ConfigEditorDropdown()
    public team tankTeam = team.Bers_Team;
}

enum team {Arch_Team,Bers_Team}