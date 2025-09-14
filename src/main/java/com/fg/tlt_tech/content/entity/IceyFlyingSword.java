package com.fg.tlt_tech.content.entity;

import cofh.core.init.CoreParticles;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTickers;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IceyFlyingSword extends BasicFlyingSwordEntity {
    public IceyFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.ICEY_SWORD.get());
    }
    public IceyFlyingSword(Level pLevel){
        this(TltTechEntityTypes.ICEY_SWORD.get(), pLevel);
    }


    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        var instance = EntityTickerManager.getInstance(target);
        if (!instance.hasTicker(TltTechEntityTickers.CHILLING_CD.get())) instance.addTicker(new EntityTickerInstance(TltTechEntityTickers.CHILLING.get(),1,100),Integer::max,Integer::max);
    }

//    @Override
//    public @Nullable ParticleOptions getParticle() {
//        return CoreParticles.FROST.get();
//    }
}
