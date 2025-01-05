package de.kazzutils.config.categories.event;

import com.google.gson.annotations.Expose;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Event {

    @Expose
    @ConfigOption(name = "Mythological Event", desc = "")
    @Accordion
    public mythologicalEvent mythologicalEvent = new mythologicalEvent();

    @Expose
    @ConfigOption(name = "Carnival", desc = "")
    @Accordion
    public carnival carnival = new carnival();

}
