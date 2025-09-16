package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.fg.tlt_tech.TltTech;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.EnchantmentModule;
import slimeknights.tconstruct.library.modifiers.modules.combat.LootingModule;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.tools.stat.INumericToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

@Mod.EventBusSubscriber(modid = TltTech.MODID)
public class IndustrialBorn extends EtSTBaseModifier implements VolatileDataModifierHook , ToolStatsModifierHook, RepairFactorModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.VOLATILE_DATA,ModifierHooks.TOOL_STATS,ModifierHooks.REPAIR_FACTOR);
        hookBuilder.addModule(new ArmorLevelModule(KEY_INDUSTRIAL,false, TinkerTags.Items.MODIFIABLE));
        hookBuilder.addModule(LootingModule.builder().level(2).weapon());
        hookBuilder.addModule(new EnchantmentModule.Constant(Enchantments.BLOCK_FORTUNE, 2));
    }

    public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_INDUSTRIAL = TinkerDataCapability.TinkerDataKey.of(TltTech.getResource("industrial"));

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        List.of(SlotType.DEFENSE,SlotType.UPGRADE,SlotType.ABILITY).forEach(slotType -> volatileData.addSlots(slotType,modifier.getLevel()));
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.getAllStats().forEach(iToolStat -> {
            if (iToolStat instanceof INumericToolStat<?> toolStat &&(iToolStat.getName().getNamespace().equals(TConstruct.MOD_ID)||iToolStat.getName().getNamespace().equals(TinkersAdvanced.MODID))){
                toolStat.percent(builder,0.25*modifier.getLevel());
            }
        });
    }

    @Override
    public float getRepairFactor(IToolStackView tool, ModifierEntry entry, float factor) {
        return factor*(entry.getLevel()+1);
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        event.getEntity().getCapability(TinkerDataCapability.CAPABILITY).ifPresent(cap->{
            int lvl = cap.get(KEY_INDUSTRIAL,0);
            if (lvl>0){
                float percentage = Math.min(0.8f,0.05f*lvl*(event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)?0.6f:1));
                event.setAmount(event.getAmount()-percentage*event.getAmount());
            }
        });
    }
}
