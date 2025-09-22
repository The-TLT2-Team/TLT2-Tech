package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.tinkers_advanced.content.modifier.combat.AutoShot;
import com.fg.tlt_tech.client.ClientUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.DistExecutor;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AutomaticTool extends AutoShot {
    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        super.modifierOnInventoryTick(tool,modifier,world,holder,itemSlot,isSelected,isCorrectSlot,stack);
        if (holder instanceof Player player&&isSelected){
            DistExecutor.unsafeRunForDist(()->()->{
                if (player.getAttackStrengthScale(0)>=1){
                    ClientUtils.doLeftClickAttack();
                }
                return null;
            },()->()-> null);
        }
    }
}
