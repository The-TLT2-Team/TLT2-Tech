package com.fg.tlt_tech.content.entity;

import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LifeFlyingSword extends BasicFlyingSwordEntity {
    public LifeFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.LIFE_SWORD.get());
    }
    public LifeFlyingSword(Level level){
        this(TltTechEntityTypes.LIFE_SWORD.get(), level);
    }


    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        if (this.getOwner() instanceof LivingEntity living) living.setHealth(Math.min(living.getMaxHealth(),living.getHealth()+damageDealt/10));
    }
}
