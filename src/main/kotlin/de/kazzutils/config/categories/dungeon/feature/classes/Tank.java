package de.kazzutils.config.categories.dungeon.feature.classes;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Tank {

    @Expose
    @ConfigOption(name = "Tank Range", desc = "Displays a Range around the Tank")
    @ConfigEditorBoolean
    public boolean tankRange = false;

    @Expose
    @ConfigOption(name = "Bone Necklace Range", desc = "Expands the Tank Range by 15")
    @ConfigEditorBoolean
    public boolean boneNecklace = false;

}
