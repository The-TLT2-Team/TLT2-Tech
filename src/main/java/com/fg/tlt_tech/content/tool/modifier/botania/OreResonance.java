package com.fg.tlt_tech.content.tool.modifier.botania;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.IToolUuidGetter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import static com.fg.tlt_tech.content.tool.modifier.botania.MagicalOres.*;

public class OreResonance extends EtSTBaseModifier implements AttributesModifierHook, ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES,ModifierHooks.MODIFY_HURT);
    }
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (holder instanceof Player player&&isCorrectSlot&&world.getGameTime()%20==0){
            List<Item> list = new ArrayList<>();
            AtomicInteger integer = new AtomicInteger(0);
            player.getInventory().items.forEach(itemStack->{
                if (!itemStack.isEmpty()&&!list.contains(itemStack.getItem())){
                    if (itemStack.is(Tags.Items.ORES)) integer.addAndGet(2);
                    else if (itemStack.is(Tags.Items.RAW_MATERIALS)) integer.addAndGet(1);
                    list.add(itemStack.getItem());
                }
            });
            tool.getPersistentData().putInt(KEY_BONUS,integer.intValue());
        }
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (getArmorBonus(tool)>0) {
            IToolUuidGetter.getUuid(tool).ifPresent(uuid -> List.of(Attributes.ARMOR, Attributes.ARMOR_TOUGHNESS, Attributes.LUCK, Attributes.MAX_HEALTH)
                    .forEach(attribute -> consumer
                            .accept(attribute, new AttributeModifier(uuid, "desc.tlt_tech.attribute.ore_resonance", 2*getArmorBonusRaw(tool), AttributeModifier.Operation.ADDITION))));
        }
    }

    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (getArmorBonus(tool)>0) amount*=1-Math.max(0.35f,getArmorBonus(tool)*0.01f);
        return amount;
    }
}
