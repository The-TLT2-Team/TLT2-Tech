package com.fg.tlt_tech.content.entity;

import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class IonicFlyingSword extends BasicFlyingSwordEntity {
    public IonicFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.IONIC_SWORD.get());
    }
    public IonicFlyingSword(Level pLevel){
        this(TltTechEntityTypes.IONIC_SWORD.get(), pLevel);
    }

    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        int maxExplosion = 4+random.nextInt(4);
        for (int i=0;i<maxExplosion;i++){
            IonicExplosionEntity entity = new IonicExplosionEntity(this.level());
            entity.setPos(target.position().add(0,target.getBbHeight()/2,0));
            entity.setDeltaMovement(new Vec3(random.nextDouble()-0.5,random.nextDouble()*0.5-0.25,random.nextDouble()-0.5).normalize().scale(random.nextDouble()*1.5+0.5));
            entity.setOwner(this.getOwner());
            entity.baseDamage = damageDealt/maxExplosion;
            this.level().addFreshEntity(entity);
        }
    }
}
