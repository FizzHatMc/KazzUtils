package de.kazzutils.config.categories.combat.slayer.voidgloom;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class VoidgloomFeatures {

    @Expose
    @ConfigOption(name = "Stop Enderman Fake Teleportation", desc = "")
    @ConfigEditorBoolean
    public boolean stopEndermanFakeTeleportation = false;

}
