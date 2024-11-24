package de.kazzutils.config.categories.mining;

import com.google.gson.annotations.Expose;
import de.kazzutils.config.categories.mining.feature.CommissionTracker;
import io.github.notenoughupdates.moulconfig.annotations.Accordion;
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean;
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption;

public class Mining {

    @Expose
    @ConfigOption(name = "Star Cult Notification", desc =  "")
    @ConfigEditorBoolean
    public boolean starCult = false;

    @Expose
    @ConfigOption(name = "Commmision Trakcer", desc = "")
    @Accordion
    public CommissionTracker commissionTracker = new CommissionTracker();


}
