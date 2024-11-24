package de.kazzutils.config.categories.dungeon.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class ItemConf {
    @Expose
    @ConfigOption(name = "Bonzo/Spirit Mask Notification", desc = "")
    @ConfigEditorBoolean
    public boolean maskNotif = false;

}
