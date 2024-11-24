package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorColour;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class PetOverlay {

    @Expose
    @ConfigOption(name = "Pet Overlay ", desc = "")
    @ConfigEditorBoolean
    public boolean petOverlay = false;

    @Expose
    @ConfigOption(name = "Pet Overlay Text Color", desc = "")
    @ConfigEditorColour
    public String petOverlayColor = "0:80:0:0:0";

}
