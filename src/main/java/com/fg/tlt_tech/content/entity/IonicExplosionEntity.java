package com.fg.tlt_tech.content.entity;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class IonicExplosionEntity extends VisualScaledProjectile {
    public IonicExplosionEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IonicExplosionEntity(Level level){
        this(TltTechEntityTypes.IONIC_EXPLOSION.get(), level);
    }

    protected AABB makeBoundingBox() {
        return super.makeBoundingBox().move(new Vec3(0,-this.getBbHeight()/2,0)).inflate(this.getScale()*2);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void tick() {
        this.setScaleOld(this.getScale());
        if (this.firstTick){
            this.tickCount=0;
        }
        if (this.tickCount>=14){
            this.discard();
        }
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().scale(0.75));
        if (this.tickCount<6&&this.tickCount>1) this.setScale(this.getScale()*1.2f);
        if (this.tickCount==6) this.setDeltaMovement(new Vec3(0,0,0));
        this.setPos(this.position().add(this.getDeltaMovement()));
        if (this.tickCount==7&&this.getOwner() instanceof Player player&&!this.level().isClientSide) {
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBoxForCulling(), this::canHitEntity);
            if (!list.isEmpty()) {
                for (Entity entity : list) {
                    entity.hurt(LegacyDamageSource.any(this.damageSources().playerAttack(player)).setMsgId("plasma")
                            .setBypassArmor().setBypassMagic(), baseDamage);
                }
            }
        }
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return pTarget!=this.getOwner()&&pTarget.isAttackable()&&!(pTarget instanceof Player);
    }
}
