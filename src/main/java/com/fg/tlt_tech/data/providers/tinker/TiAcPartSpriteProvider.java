package com.fg.tlt_tech.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

public class TiAcPartSpriteProvider extends AbstractPartSpriteProvider {
    public TiAcPartSpriteProvider() {
        super(TinkersAdvanced.MODID);
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Part Sprite Provider";
    }

    @Override
    protected void addAllSpites() {
    }
}
