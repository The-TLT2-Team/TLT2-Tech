package com.fg.tlt_tech.content.entity.base;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.util.EntityInRangeUtil;
import com.c2h6s.etstlib.util.ProjectileUtil;
import com.fg.tlt_tech.util.TltTechCommonUtils;
import com.fg.tltmod.util.mixin.ILivingEntityMixin;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ModifierLootingHandler;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

import static com.c2h6s.etstlib.util.AttackUtil.dealModifiedDamage;
import static slimeknights.tconstruct.library.tools.helper.ToolAttackUtil.getAttributeAttackDamage;
import static slimeknights.tconstruct.library.tools.helper.ToolAttackUtil.getLivingEntity;

public class BasicFlyingSwordEntity extends Projectile {
    public final ItemStack renderItem;
    public BasicFlyingSwordEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Item renderItem) {
        super(pEntityType, pLevel);
        this.renderItem = new ItemStack(renderItem);
        this.initialRotation = random.nextFloat()*180;
    }
    public float damagePercentage = 0.25f;
    public ToolStack tool = null;
    public LivingEntity homingTarget = null;
    public IntOpenHashSet piercedEntities = new IntOpenHashSet();
    public float initialRotation;
    public List<Vec3> positionCache = new ArrayList<>();
    public List<Vec3> rotationCache = new ArrayList<>();
    public int lifeTime = 200;
    public boolean isTrueHurt = false;

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount>this.lifeTime) this.discard();
        if (this.shouldFindHitEntity()&&!this.isRemoved()&&this.getOwner() instanceof LivingEntity living&&this.tool!=null){
            List<Entity> entities = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBox().inflate(1).expandTowards(this.getDeltaMovement()),this::canHitEntity);
            entities.forEach(entity -> {
                entity.invulnerableTime=0;
                this.attackEntity(this.tool,living,InteractionHand.MAIN_HAND,entity,EquipmentSlot.MAINHAND,false,0,this.isCritical());
                entity.invulnerableTime=0;
                this.piercedEntities.add(entity.getId());
                if (this.homingTarget==entity) this.homingTarget=null;
                if (this.shouldDiscardAfterHit(entity)) this.discard();
            });
        }
        if (!this.isRemoved()) {
            if (this.homingTarget != null) {
                if (!this.homingTarget.isAlive()) this.homingTarget = null;
            }
            if (this.tickCount > 5) {
                if (this.homingTarget == null)
                    this.homingTarget = EntityInRangeUtil.getNearestLivingEntity(this, 16, this.piercedEntities, entity -> this.canHitEntity(entity) && !(entity instanceof Player));
                if (this.homingTarget != null) ProjectileUtil.homingToward(this, this.homingTarget, 1, 8);
            }
            this.setPos(this.position().add(this.getDeltaMovement()));
            Vec3 vec3 = this.getDeltaMovement().normalize();
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            if (this.getParticle()!=null){
                var particle = this.getParticle();
                Vec3 movement = this.getDeltaMovement();
                Vec3 direction = movement.reverse().normalize();
                for (double d =0;d<movement.length();d+=0.2){
                    Vec3 pos = this.position().add(direction.scale(d));
                    this.level().addParticle(particle,pos.x+random.nextFloat()*0.02,pos.y+random.nextFloat()*0.02,pos.z+random.nextFloat()*0.02,random.nextFloat()*0.02,random.nextFloat()*0.02,random.nextFloat()*0.02);
                }
            }else if (this.level() instanceof ServerLevel serverLevel){
                this.spawnParticle(serverLevel);
            }
        }
    }

    public final boolean attackEntity(IToolStackView tool, LivingEntity attackerLiving, InteractionHand hand,
                                       Entity targetEntity, EquipmentSlot sourceSlot, boolean setDamage, float damageSet,boolean critical) {
        if (tool.isBroken()) {
            return false;
        }
        Level level = attackerLiving.level();
        if (level.isClientSide) {
            return true;
        }
        LivingEntity targetLiving = getLivingEntity(targetEntity);
        Player attackerPlayer = null;
        if (attackerLiving instanceof Player player) {
            attackerPlayer = player;
        }

        float damage = getAttributeAttackDamage(tool, attackerLiving, sourceSlot);
        if (setDamage) damage = damageSet;

        boolean isCritical = critical;

        boolean setCritical;
        for (ModifierEntry entry : tool.getModifierList()) {
            setCritical = entry.getHook(EtSTLibHooks.CRITICAL_ATTACK).setCritical(tool, entry, attackerLiving, hand, targetEntity, sourceSlot, true, true, isCritical);
            isCritical = setCritical;
            break;
        }

        ToolAttackContext context = new ToolAttackContext(attackerLiving, attackerPlayer, hand, sourceSlot, targetEntity, targetLiving, isCritical, 1, true);

        float baseDamage = damage;
        List<ModifierEntry> modifiers = tool.getModifierList();
        for (ModifierEntry entry : modifiers) {
            damage = entry.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(tool, entry, context, baseDamage, damage);
        }

        if (damage <= 0) {
            return false;
        }

        float knockback = (float) attackerLiving.getAttributeValue(Attributes.ATTACK_KNOCKBACK) / 2f;
        if (targetLiving != null) {
            knockback += 0.4f;
        }
        SoundEvent sound;
        if (attackerLiving.isSprinting()) {
            sound = SoundEvents.PLAYER_ATTACK_KNOCKBACK;
            knockback += 0.5f;
        } else {
            sound = SoundEvents.PLAYER_ATTACK_STRONG;
        }


        float criticalModifier = isCritical ? 1.5f : 1.0f;
        if (attackerPlayer != null) {
            CriticalHitEvent hitResult = ForgeHooks.getCriticalHit(attackerPlayer, targetEntity, isCritical, isCritical ? 1.5F : 1.0F);
            isCritical = hitResult != null;
            if (isCritical) {
                criticalModifier = hitResult.getDamageModifier();
            }
        }
        if (isCritical) {
            damage *= criticalModifier;
        }

        damage*=damagePercentage;
        damage = this.getDamage(damage);

        boolean isMagic = damage > baseDamage;

        float oldHealth = 0.0F;
        if (targetLiving != null) {
            oldHealth = targetLiving.getHealth();
        }

        float baseKnockback = knockback;
        for (ModifierEntry entry : modifiers) {
            knockback = entry.getHook(ModifierHooks.MELEE_HIT).beforeMeleeHit(tool, entry, context, damage, baseKnockback, knockback);
        }

        ModifierLootingHandler.setLootingSlot(attackerLiving, sourceSlot);
        LegacyDamageSource source =LegacyDamageSource.any(attackerLiving instanceof Player player?attackerLiving.damageSources().playerAttack(player):attackerLiving.damageSources().mobAttack(attackerLiving)).setBypassInvulnerableTime();

        source = this.processDamageSource(source);

        for (ModifierEntry entry:tool.getModifierList()){
            source=entry.getHook(EtSTLibHooks.MODIFY_DAMAGE_SOURCE).modifyDamageSource(tool,entry,attackerLiving,hand,targetEntity,sourceSlot,true,true,isCritical,source);
        }

        boolean didHit = isTrueHurt&&targetLiving!=null? TltTechCommonUtils.trueHurt(targetLiving,source,damage): dealModifiedDamage(tool, context, damage,source);

        this.doAfterHitEffect(targetEntity,damage);


        ModifierLootingHandler.setLootingSlot(attackerLiving, EquipmentSlot.MAINHAND);

        if (!didHit) {
            level.playSound(null, attackerLiving.getX(), attackerLiving.getY(), attackerLiving.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, attackerLiving.getSoundSource(), 1.0F, 1.0F);

            for (ModifierEntry entry : modifiers) {
                entry.getHook(ModifierHooks.MELEE_HIT).failedMeleeHit(tool, entry, context, damage);
            }

            return false;
        }

        float damageDealt = damage;
        if (targetLiving != null) {
            damageDealt = oldHealth - targetLiving.getHealth();
        }

        if (targetEntity.hurtMarked && targetEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(targetEntity));
            targetEntity.hurtMarked = false;
        }

        if (attackerPlayer != null) {
            if (isCritical) {
                sound = SoundEvents.PLAYER_ATTACK_CRIT;
                attackerPlayer.crit(targetEntity);
            }
            if (isMagic) {
                attackerPlayer.magicCrit(targetEntity);
            }
            level.playSound(null, attackerLiving.getX(), attackerLiving.getY(), attackerLiving.getZ(), sound, attackerLiving.getSoundSource(), 1.0F, 1.0F);
        }
        if (damageDealt > 2.0F && level instanceof ServerLevel server) {
            int particleCount = (int)(damageDealt * 0.5f);
            server.sendParticles(ParticleTypes.DAMAGE_INDICATOR, targetEntity.getX(), targetEntity.getY(0.5), targetEntity.getZ(), particleCount, 0.1, 0, 0.1, 0.2);
        }
        attackerLiving.setLastHurtMob(targetEntity);
        if (targetLiving != null) {
            EnchantmentHelper.doPostHurtEffects(targetLiving, attackerLiving);
        }
        for (ModifierEntry entry : modifiers) {
            entry.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(tool, entry, context, damageDealt);
        }

        if (attackerPlayer != null) {
            if (targetLiving != null) {
                attackerPlayer.awardStat(Stats.DAMAGE_DEALT, Math.round(damageDealt * 10.0F));
            }
        }

        return true;
    }

    public boolean isCritical(){return false;}
    public void doAfterHitEffect(@NotNull Entity target,float damageDealt){}
    public LegacyDamageSource processDamageSource(LegacyDamageSource source){return source.setMsgId("elemental_sword_"+this.getId());}
    public float getDamage(float damage){return damage;}
    public @Nullable ParticleOptions getParticle(){return null;}
    public boolean shouldDiscardAfterHit(Entity entity){return entity instanceof LivingEntity;}
    public void spawnParticle(ServerLevel serverLevel){}
    public boolean shouldFindHitEntity(){return true;}

    @Override
    protected boolean canHitEntity(@NotNull Entity pTarget) {
        if (!pTarget.isAlive()) return false;
        if (pTarget==this.getOwner()||pTarget instanceof Projectile||this.piercedEntities.contains(pTarget.getId())) return false;
        if (pTarget instanceof ItemEntity||pTarget instanceof ExperienceOrb) return false;
        return !(pTarget instanceof Player player) || !(this.getOwner() instanceof Player player1) || player1.canHarmPlayer(player);
    }
}
