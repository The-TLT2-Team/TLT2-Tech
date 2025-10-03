package com.fg.tlt_tech.content.tool.item;

import cofh.redstonearsenal.common.entity.FluxArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FluxArrowItem extends ArrowItem {
    public FluxArrowItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        return true;
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        FluxArrow arrow = new FluxArrow(pLevel,pShooter);
        arrow.setBaseDamage(3f);
        return arrow;
    }
}
