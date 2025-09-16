package com.fg.tlt_tech.content.tool.modifier;

import cofh.core.client.particle.options.BiColorParticleOptions;
import cofh.core.common.network.packet.client.OverlayMessagePacket;
import cofh.core.init.CoreMobEffects;
import cofh.core.init.CoreParticles;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.util.AttackUtil;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.FluxInfused;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataKeys;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class SharpEdgeDischarge extends FluxInfused implements VolatileDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (context.isFullyCharged()&&context.getTarget() instanceof LivingEntity living&&getMode(tool)>=1&& ToolEnergyUtil.extractEnergy(tool,500,true)>=500){
            ToolEnergyUtil.extractEnergy(tool,500,false);
            int amount = RANDOM.nextInt(modifier.getLevel()+1)+1;
            List<Entity> list = living.level().getEntitiesOfClass(Entity.class,new AABB(living.blockPosition().above()).inflate(5+modifier.getLevel()));
            list.remove(living);
            list.remove(context.getAttacker());
            if (list.isEmpty()) return;
            LegacyDamageSource source = LegacyDamageSource.any(new DamageSource(living.damageSources().lightningBolt().typeHolder(),context.getAttacker())).setBypassMagic().setBypassArmor().setBypassInvulnerableTime().setMsgId("tlt_tech.plasma_flux");
            int i=0;
            while (i<amount&&!list.isEmpty()){
                Entity entity = list.get(RANDOM.nextInt(list.size()));
                list.remove(entity);
                if (entity instanceof ItemEntity ||entity instanceof ExperienceOrb) continue;
                if (entity.hurt(source,damage*0.25f*modifier.getLevel())&&entity.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.3F, 4.0F, 0.0F, -1, 0xFF0000FF),living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),0,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),1);
                    i++;
                }
            }
        }
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (world instanceof ServerLevel serverLevel&&world.getGameTime()%Math.max(20/(modifier.getLevel()+1),4)==0&&getMode(tool)==2&&isSelected){
            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class,new AABB(
                    holder.getX()-7,holder.getY()-7,holder.getZ()-7,holder.getX()+7,holder.getY()+7,holder.getZ()+7
            ),entity->entity!=holder&&!(entity instanceof Player)&&entity.isAlive()&&entity.distanceTo(holder)<6.5);
            int count = modifier.getLevel();
            while (!list.isEmpty()&&count>0&&ToolEnergyUtil.extractEnergy(tool,250,true)>=250){
                LivingEntity living = list.get(RANDOM.nextInt(list.size()));
                list.remove(living);
                living.invulnerableTime = 0;
                if (AttackUtil.attackEntity(tool,holder, InteractionHand.MAIN_HAND,living,()->1,false, EquipmentSlot.MAINHAND,false,0,true)) {
                    ToolEnergyUtil.extractEnergy(tool,250,false);
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.3F, 4.0F, 0.0F, -1, 0xFF0000FF),holder.getX(),holder.getY()+0.5*holder.getBbHeight(),holder.getZ(),0,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),1);
                    count--;
                }
            }
        }
    }

    @Override
    public void onModeSwitch(IToolStackView tool, ServerPlayer player, ModifierEntry entry) {
        OverlayMessagePacket.sendToClient(Component.translatable("msg.tinkers_advanced.flux_mode.thermal_slash." + getMode(tool)), player);
        ((ToolStack)tool).rebuildStats();
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        if (context.getPersistentData().getInt(MODE_LOCATION) % 3==2){
            volatileData.putBoolean(IModifiable.SHINY,true);
        }
    }
}
