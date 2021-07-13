package com.bambam01.DestroyThePluginCache;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.bambam01.DestroyThePluginCache"})
public class PluginCacheDestroyer implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{"com.bambam01.DestroyThePluginCache.DestroyerClassTransformer"};
    }

    @Override
    public String getModContainerClass()
    {

        return "com.bambam01.DestroyThePluginCache.DestroyerModContainer";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
