package de.kazzutils.config.categories.misc;

import com.google.gson.annotations.Expose;
import de.kazzutils.KazzUtils;
import de.kazzutils.config.categories.event.mythologicalEvent;
import de.kazzutils.config.categories.misc.feature.*;
import de.kazzutils.gui.KeyShortcutsGui;
import de.kazzutils.gui.editing.ElementaEditingGui;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorButton;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Misc {


    @ConfigOption(name = "Edit Gui", desc = "Opens GUI Editor")
    @ConfigEditorButton(buttonText = "OPEN")
    public Runnable openGui = () -> KazzUtils.displayScreen = new ElementaEditingGui();

    @ConfigOption(name = "Hotkeys", desc = "Opens Hotkeys Editor")
    @ConfigEditorButton(buttonText = "OPEN")
    public Runnable hotkeys = () -> KazzUtils.displayScreen = new KeyShortcutsGui();


    @Expose
    @ConfigOption(name = "Deployables", desc = "")
    @Accordion
    public DeploayAble deployables = new DeploayAble();

    @Expose
    @ConfigOption(name = "Items", desc = "")
    @Accordion
    public Items items = new Items();

    @Expose
    @ConfigOption(name = "PartyCommands", desc = "")
    @Accordion
    public PartyCommands partyCommands = new PartyCommands();

    @Expose
    @ConfigOption(name = "Mythological Event", desc = "")
    @Accordion
    public mythologicalEvent mythologicalEvent = new mythologicalEvent();

    @Expose
    @ConfigOption(name = "Pet Overlay", desc = "")
    @Accordion
    public PetOverlay petOverlay = new PetOverlay();

    @Expose
    @ConfigOption(name = "Arrows and Soulflow stuff", desc = "")
    @Accordion
    public ArrowSoulflowNotif arrowSoulflowNotif = new ArrowSoulflowNotif();

    @Expose
    @ConfigOption(name = "Item Animations", desc = "")
    @Accordion
    public ItemAnimations itemAnimations = new ItemAnimations();

    @Expose
    @ConfigOption(name = "Hud", desc = "")
    @Accordion
    public Hud hud = new Hud();



}
