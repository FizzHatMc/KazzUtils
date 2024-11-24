package de.kazzutils.config.categories.misc.feature;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.misc.feature.arrowsoulflowhud.Arrow;
import de.kazzutils.config.categories.misc.feature.arrowsoulflowhud.Soulflow;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class ArrowSoulflowNotif {

    @Expose
    @ConfigOption(name = "Soulflow Settings", desc = "")
    @Accordion
    public Soulflow soulflow = new Soulflow();

    @Expose
    @ConfigOption(name = "Arrow Settings", desc = "")
    @Accordion
    public Arrow arrow = new Arrow();

}
