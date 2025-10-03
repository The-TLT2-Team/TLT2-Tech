package com.fg.tlt_tech.content.tool.modifier;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.UUIDUtil;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import com.fg.tlt_tech.TltTech;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2hostility.init.registrate.LHEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.shared.TinkerEffects;
import vazkii.botania.api.mana.ManaDiscountEvent;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.entity.PixieEntity;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;


public class AlfBless extends EtSTBaseModifier implements ModifyDamageModifierHook , AttributesModifierHook {
    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_ALF = TinkerDataCapability.TinkerDataKey.of(TltTech.getResource("alf_bless"));
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFY_HURT,ModifierHooks.ATTRIBUTES);
        hookBuilder.addModule(new ArmorLevelModule(KEY_ALF,false, TinkerTags.Items.HELD));
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    public static final List<MobEffect> EFFECT_LIST = List.of(
            MobEffects.WEAKNESS,
            MobEffects.DIG_SLOWDOWN,
            MobEffects.MOVEMENT_SLOWDOWN,
            TiAcEffects.PLAGUE.get(),
            LHEffects.GRAVITY.get(),
            LCEffects.CURSE.get(),
            LCEffects.FLAME.get(),
            TinkerEffects.enderference.get(),
            TinkerEffects.bleeding.get(),
            CoreMobEffects.SUNDERED.get(),
            CoreMobEffects.CHILLED.get()
    );
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()&&context.getTarget() instanceof LivingEntity living){
            LivingEntity attacker = context.getAttacker();
            PixieEntity entity = new PixieEntity(context.getLevel());
            entity.setProps(living,attacker,0,damageDealt*0.2f);
            entity.setApplyPotionEffect(new MobEffectInstance(EFFECT_LIST.get(RANDOM.nextInt(EFFECT_LIST.size())),100,2));
            entity.setPos(living.position().add(RANDOM.nextDouble(),living.getBbHeight()+living.getBbHeight()*RANDOM.nextDouble(),RANDOM.nextDouble()));
            living.level().addFreshEntity(entity);
        }
    }


    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity owner = context.getEntity();
        LivingEntity living = source.getEntity() instanceof LivingEntity living1?living1:null;
        if (living!=null){
            PixieEntity entity = new PixieEntity(context.getLevel());
            entity.setProps(living,owner,0,amount*0.2f);
            entity.setApplyPotionEffect(new MobEffectInstance(EFFECT_LIST.get(RANDOM.nextInt(EFFECT_LIST.size())),100,2));
            entity.setPos(owner.position().add(living.getBbHeight()*RANDOM.nextDouble()*1.5,living.getBbHeight()*RANDOM.nextDouble()*1.5,living.getBbHeight()*RANDOM.nextDouble()*1.5));
            living.level().addFreshEntity(entity);
        }
        return amount*0.8f;
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        UUID uuid = UUIDUtil.UUIDFromSlot(slot,modifier.getId());
        switch (slot){
            case FEET -> consumer.accept(Attributes.MOVEMENT_SPEED,new AttributeModifier(uuid,Attributes.MOVEMENT_SPEED.getDescriptionId(), 0.05, AttributeModifier.Operation.ADDITION));
            case HEAD -> List.of(ForgeMod.BLOCK_REACH.get(),ForgeMod.ENTITY_REACH.get()).forEach(attribute ->
                    consumer.accept(attribute,new AttributeModifier(uuid,attribute.getDescriptionId(),2, AttributeModifier.Operation.ADDITION)));
            case LEGS -> consumer.accept(ForgeMod.SWIM_SPEED.get(),new AttributeModifier(uuid,ForgeMod.SWIM_SPEED.get().getDescriptionId(),0.05, AttributeModifier.Operation.ADDITION));
            default -> consumer.accept(Attributes.MAX_HEALTH,new AttributeModifier(uuid,Attributes.MAX_HEALTH.getDescriptionId(), 20, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder instanceof Player player&&isCorrectSlot) {
            player.getCapability(TinkerDataCapability.CAPABILITY).ifPresent(cap->{
                if (cap.get(KEY_ALF,0)>=4) {
                    int food = player.getFoodData().getFoodLevel();
                    if (food > 0 && player.isHurt() && player.tickCount % 80 == 0) {
                        player.heal(1F * modifier.getLevel());
                    }
                    if (player.tickCount % 10 == 0) {
                        ManaItemHandler.instance().dispatchManaExact(stack, player, 10* modifier.getLevel(), true);
                    }
                }
            });
        }
    }
}
