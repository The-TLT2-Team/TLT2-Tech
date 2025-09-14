package com.fg.tlt_tech.content.entityTicker;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class FieryTicker extends EntityTicker {
    public static final String FIERY_UUID = "22bd75ce-18c6-c8cd-7314-8ae28a14f1b2";
    @Override
    public boolean tick(int duration, int level, Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            Level world = entity.level();
            Entity attackerLegacy = null;
            CompoundTag nbt = entity.getPersistentData();
            if (nbt.contains(CommonUtil.KEY_ATTACKER, Tag.TAG_INT)) attackerLegacy = world.getEntity(nbt.getInt(CommonUtil.KEY_ATTACKER));
            if (world.getGameTime()%10==0) {
                if (attackerLegacy instanceof LivingEntity living){
                    entity.hurt(LegacyDamageSource.mobAttack(living).setBypassArmor().setBypassInvul().setBypassInvulnerableTime().setExplosion().setMsgId("fiery_damage"), level*5);
                    entity.hurt(LegacyDamageSource.mobAttack(living).setBypassArmor().setBypassInvul().setBypassInvulnerableTime().setFire().setMsgId("fiery_damage"), level*5);
                }
                if (entity instanceof LivingEntity living) {
                    for (var attributes : List.of(Attributes.ARMOR_TOUGHNESS, Attributes.ARMOR)) {
                        AttributeInstance instance = living.getAttribute(attributes);
                        double existing = 0;
                        if (instance!=null) {
                            if (instance.getModifier(UUID.fromString(FIERY_UUID))!=null) {
                                existing+=instance.getModifier(UUID.fromString(FIERY_UUID)).getAmount();
                                instance.removeModifier(UUID.fromString(FIERY_UUID));
                            }
                            instance.addTransientModifier(new AttributeModifier(UUID.fromString(FIERY_UUID),"tlt_tech.desc.attribute.fiery",existing-5*level, AttributeModifier.Operation.ADDITION));
                        }
                    }
                }
            }
            serverLevel.sendParticles(ParticleTypes.FLAME, entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),2,entity.getBbWidth()*0.5,entity.getBbHeight()*0.5,entity.getBbWidth()*0.5,0.05);
        }
        entity.invulnerableTime =0;
        return true;
    }

}
