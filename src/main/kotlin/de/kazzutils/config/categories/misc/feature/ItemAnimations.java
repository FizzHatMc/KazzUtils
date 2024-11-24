package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class ItemAnimations {

    @Expose
    @ConfigOption(name = "Global Toggle", desc = "Change the look of your held item")
    @ConfigEditorBoolean
    public boolean customAnimations = false;

    @Expose
    @ConfigOption(name = "Size", desc = "Scales the size of your currently held item. Default: 0")
    @ConfigEditorSlider(minValue = -1.5f, maxValue = 1.5f, minStep = 0.1f)
    public float customSize = 0f;

    @Expose
    @ConfigOption(name = "Scale Swing", desc = "Also scale the size of the swing animation.")
    @ConfigEditorBoolean
    public boolean doesScaleSwing = true;

    @Expose
    @ConfigOption(name = "X", desc = "Moves the held item. Default: 0")
    @ConfigEditorSlider(minValue = -1.5f, maxValue = 1.5f, minStep = 0.1f)
    public float customX = 0f;

    @Expose
    @ConfigOption(name = "Y", desc = "Moves the held item. Default: 0")
    @ConfigEditorSlider(minValue = -1.5f, maxValue = 1.5f, minStep = 0.1f)
    public float customY = 0f;

    @Expose
    @ConfigOption(name = "Z", desc = "Moves the held item. Default: 0")
    @ConfigEditorSlider(minValue = -1.5f, maxValue = 1.5f, minStep = 0.1f)
    public float customZ = 0f;

    @Expose
    @ConfigOption(name = "Yaw", desc = "Rotates your held item. Default: 0")
    @ConfigEditorSlider(minValue = -180f, maxValue = 180f, minStep = 1)
    public float customYaw = 0f;

    @Expose
    @ConfigOption(name = "Pitch", desc = "Rotates your held item. Default: 0")
    @ConfigEditorSlider(minValue = -180f, maxValue = 180f, minStep = 1)
    public float customPitch = 0f;

    @Expose
    @ConfigOption(name = "Roll", desc = "Rotates your held item. Default: 0")
    @ConfigEditorSlider(minValue = -180f, maxValue = 180f, minStep = 1)
    public float customRoll = 0f;

    @Expose
    @ConfigOption(name = "Speed", desc = "Speed of the swing animation.")
    @ConfigEditorSlider(minValue = -2f, maxValue = 1f, minStep = 0.1f)
    public float customSpeed = 0f;

    @Expose
    @ConfigOption(name = "Ignore Haste", desc = "Makes the chosen speed override haste modifiers.")
    @ConfigEditorBoolean
    public boolean ignoreHaste = true;

    @Expose
    @ConfigOption(name = "Drinking Fix", desc = "Pick how to handle drinking animations.")
    @ConfigEditorSlider(minValue = 0f, maxValue = 2f, minStep = 1)
    public int drinkingSelector = 2;

}
