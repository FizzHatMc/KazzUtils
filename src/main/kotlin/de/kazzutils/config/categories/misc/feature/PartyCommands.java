package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import de.kazzutils.data.enumClass.partyPrefix;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorDropdown;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class PartyCommands {

    @Expose
    @ConfigOption(name = "Party Commands", desc = "Enable Party Commands (select Prefix below")
    @ConfigEditorBoolean
    public boolean partyCommands = false;

    @Expose
    @ConfigOption(name = "Party Commands Prefix", desc = "Select between \"!\" , \".\" and \"?\"")
    @ConfigEditorDropdown
    public partyPrefix partyPrefix = null;


}

