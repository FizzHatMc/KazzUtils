package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class DeploayAble {

    @Expose
    @ConfigOption(name = "Deployable Display", desc = "")
    @ConfigEditorBoolean
    public boolean deployable = false;

    @Expose
    @ConfigOption(name = "Display Name next to Image", desc = "")
    @ConfigEditorBoolean
    public boolean deployableName = false;

    @Expose
    @ConfigOption(name = "Display info under Name", desc = "")
    @ConfigEditorBoolean
    public boolean deployableInfo = false;

}
