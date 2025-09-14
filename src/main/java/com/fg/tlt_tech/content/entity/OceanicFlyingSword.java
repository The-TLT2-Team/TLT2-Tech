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

public class OceanicFlyingSword extends BasicFlyingSwordEntity {
    public OceanicFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.OCEANIC_SWORD.get());
    }
    public boolean isExtra = false;
    public OceanicFlyingSword(Level level){
        this(TltTechEntityTypes.OCEANIC_SWORD.get(), level);
    }

    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        if (!isExtra){
            for (int i=0;i<360;i+=60){
                Vec3 vec3 = new Vec3(Math.cos(Math.toRadians(i)),0,Math.sin(Math.toRadians(i)));
                OceanicFlyingSword entity = new OceanicFlyingSword(this.level());
                entity.setPos(target.position().add(0,0.5*target.getBbHeight(),0).add(vec3));
                entity.shoot(vec3.x, vec3.y, vec3.z, 2f, 0);
                entity.setOwner(this.getOwner());
                entity.tool = this.tool;
                entity.damagePercentage = this.damagePercentage/2;
                entity.isExtra = true;
                entity.lifeTime=200;
                this.level().addFreshEntity(entity);
            }
        }
    }

    @Override
    public boolean shouldFindHitEntity() {
        return !this.isExtra||this.tickCount>3;
    }
}
