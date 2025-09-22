package com.fg.tlt_tech.content.entity;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.util.AttackUtil;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import mekanism.common.registries.MekanismDamageTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.shared.TinkerEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fg.tlt_tech.content.entityTicker.FieryTicker.FIERY_UUID;

public class ElementalSlashEntity extends Projectile {
    public ElementalSlashEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public ElementalSlashEntity(Level level){
        this(TltTechEntityTypes.ELEMENTAL_SLASH.get(), level);
    }

    @Override
    protected void defineSynchedData() {

    }
    public List<Entity> hitent = new ArrayList<>();
    public ToolStack tool = null;

    @Override
    public void tick() {
        super.tick();
        Level world = this.level();
        Player player =this.getOwner() instanceof Player e ? e:null;
        if (tickCount%4==0){
            hitent.clear();
        }
        Vec3 movement =this.getDeltaMovement();
        this.setPos(movement.x+this.getX(),movement.y+this.getY(),movement.z+this.getZ());
        double angle =((this.tickCount * 100 % 360)*Math.PI)/180;
        Vec3 anglevec =new Vec3(Math.sin(angle),0,Math.cos(angle)).scale(2);
        if (movement.length()<=2){
            this.setDeltaMovement(movement.scale(1.15));
        }
        if (this.tool!=null) {
            float damage = tool.getStats().get(ToolStats.ATTACK_DAMAGE);
            AABB aabb = new AABB(this.getX() + anglevec.x, this.getY(), this.getZ() + anglevec.z, this.getX(), this.getY(), this.getZ()).inflate(2.25);
            List<LivingEntity> ls = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> entity != this.getOwner() && !hitent.contains(entity) && !(entity instanceof Player));
            if (!ls.isEmpty()) {
                for (LivingEntity targets : ls) {
                    if (player != null) {
                        targets.invulnerableTime = 0;
                        AttackUtil.attackEntity(tool,player, InteractionHand.MAIN_HAND,targets,()->1,true, EquipmentSlot.MAINHAND,true,damage*0.5f,true);
                        targets.invulnerableTime = 0;
                        targets.hurt(LegacyDamageSource.any(MekanismDamageTypes.RADIATION.source(level())), damage*0.25f);
                        targets.hurt(LegacyDamageSource.any(MekanismDamageTypes.LASER.source(level())), damage*0.25f);
                        targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(), 100, 4, false, false), this.getOwner());
                        targets.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(), 100, 4, false, false), this.getOwner());
                        for (var attributes : List.of(Attributes.ARMOR_TOUGHNESS, Attributes.ARMOR)) {
                            AttributeInstance instance = targets.getAttribute(attributes);
                            double existing = 0;
                            if (instance!=null) {
                                if (instance.getModifier(UUID.fromString(FIERY_UUID))!=null) {
                                    existing+=instance.getModifier(UUID.fromString(FIERY_UUID)).getAmount();
                                    instance.removeModifier(UUID.fromString(FIERY_UUID));
                                }
                                instance.addTransientModifier(new AttributeModifier(UUID.fromString(FIERY_UUID),"tlt_tech.desc.attribute.fiery",existing-2.5, AttributeModifier.Operation.ADDITION));
                            }
                        }
                        targets.forceAddEffect(new MobEffectInstance(TinkerEffects.enderference.get(), 100, 4, false, false), this.getOwner());
                        hitent.add(targets);
                    }
                }
            }
        }
        if (this.tickCount>200){
            this.discard();
        }
        super.tick();
    }
}
