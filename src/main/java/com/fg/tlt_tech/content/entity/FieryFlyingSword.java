package com.fg.tlt_tech.content.entity;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTickers;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FieryFlyingSword extends BasicFlyingSwordEntity {
    public FieryFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.FIERY_SWORD.get());
    }
    public FieryFlyingSword( Level pLevel) {
        this(TltTechEntityTypes.FIERY_SWORD.get(), pLevel);
    }

    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        if (this.getOwner() instanceof Player player&&!this.level().isClientSide) {
            target.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,player.getId());
            var instance = EntityTickerManager.getInstance(target);
            instance.addTicker(new EntityTickerInstance(TltTechEntityTickers.FIERY.get(),1,400),Integer::sum,Integer::max);
        }
    }
}
