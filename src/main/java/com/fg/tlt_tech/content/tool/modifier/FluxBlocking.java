package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.fg.tlt_tech.TltTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.*;

public class FluxBlocking extends Modifier implements InventoryTickModifierHook , VolatileDataModifierHook {
    public static final ResourceLocation KEY_MAX_CHARGE = TltTech.getResource("rsa_max_charge");
    public static final ResourceLocation KEY_CUR_CHARGE = TltTech.getResource("rsa_cur_charge");
    public static final int FE_PER_BLOCK = 240000;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK,ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!tool.isBroken()&&world.getGameTime()%5==0&&!world.isClientSide){
            ModDataNBT nbt = tool.getPersistentData();
            IModDataView volatileData = tool.getVolatileData();
            int max = volatileData.getInt(KEY_MAX_CHARGE);
            float cur = nbt.getFloat(KEY_CUR_CHARGE);
            if (max>0&&max> cur /FE_PER_BLOCK){
                int energy = ToolEnergyUtil.extractEnergy(tool,5000*modifier.getLevel(),false);
                cur+= energy;
                nbt.putFloat(KEY_CUR_CHARGE,cur);
            }
        }
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        volatileData.putInt(KEY_MAX_CHARGE,modifier.getLevel());
    }
}
