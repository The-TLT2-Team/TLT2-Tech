package com.fg.tlt_tech.content.entityTicker;

import cofh.core.init.CoreParticles;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.fg.tlt_tech.init.TltTechEntityTickers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ChillingTicker extends EntityTicker {
    @Override
    public boolean tick(int duration, int level, Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) serverLevel.sendParticles(CoreParticles.FROST.get(), entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),4,entity.getBbWidth()*0.75,entity.getBbHeight()*0.75,entity.getBbWidth()*0.75,0.05);
        return false;
    }

    @Override
    public void onTickerStart(int duration, int level, Entity entity) {
        var manager = EntityTickerManager.getInstance(entity);
        manager.addTicker(new EntityTickerInstance(TltTechEntityTickers.CHILLING_CD.get(),1,200),(i1,i2)->1,Integer::max);
    }
}
