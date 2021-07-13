package com.bambam01.DestroyThePluginCache;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Collections;

public class DestroyerModContainer extends DummyModContainer {

    public DestroyerModContainer(){
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "plugincachedestroyer";
        meta.name = "Plugin Cache Destroyer";
        meta.description = "Remove the \"Still Generating Crop Plugin Cache\" message from ic2 crop plugin";
        meta.version = "1.7.10-1.0";
        meta.authorList = Collections.singletonList("bambam01");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}
