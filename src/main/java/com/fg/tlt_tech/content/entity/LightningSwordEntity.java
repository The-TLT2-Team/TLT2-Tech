package com.fg.tlt_tech.content.entity;

import cofh.core.client.particle.options.BiColorParticleOptions;
import cofh.core.init.CoreParticles;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LightningSwordEntity extends BasicFlyingSwordEntity {
    public LightningSwordEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.LIGHTNING_SWORD.get());
        this.lifeTime = 80;
    }
    public int tickSinceLastHit=0;
    public LightningSwordEntity(Level level){
        this(TltTechEntityTypes.LIGHTNING_SWORD.get(), level);
    }

    @Override
    public boolean shouldDiscardAfterHit(Entity entity) {
        return false;
    }

    @Override
    public void doAfterHitEffect(@NotNull Entity target, float damageDealt) {
        this.tickSinceLastHit=0;
    }

    @Override
    public void tick() {
        if (this.tickSinceLastHit<5) this.tickSinceLastHit++;
        if (this.tickSinceLastHit==5) this.piercedEntities.clear();
        super.tick();
    }

    @Override
    public void spawnParticle(ServerLevel serverLevel) {
        Vec3 offSet = this.getDeltaMovement().cross(new Vec3(random.nextDouble()-0.5,random.nextDouble()-0.5,random.nextDouble()-0.5));
        if (offSet.length()<=0) offSet = new Vec3(1,1,1);
        offSet.normalize().scale(random.nextDouble()*this.getDeltaMovement().length()*0.2+0.1).add(this.getDeltaMovement());
        serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(),0.2f,4,0,-1, -240988),this.getX()-offSet.x,this.getY()-offSet.y,this.getZ()-offSet.z,0,this.getX()+offSet.x,this.getY()+offSet.y,this.getZ()+offSet.z,1);
    }
}
