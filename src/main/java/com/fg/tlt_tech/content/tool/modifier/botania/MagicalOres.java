package com.fg.tlt_tech.content.tool.modifier.botania;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.fg.tlt_tech.TltTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MagicalOres extends EtSTBaseModifier {
    public static final ResourceLocation KEY_BONUS = TltTech.getResource("magical_ore");

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
    public static float getBonus(IToolStackView tool){
        return tool.getPersistentData().getInt(KEY_BONUS)*0.04f;
    }
    public static float getBonus(ModDataNBT persistentData){
        return persistentData.getInt(KEY_BONUS)*0.04f;
    }
    public static float getArmorBonus(IToolStackView tool){
        return tool.getPersistentData().getInt(KEY_BONUS)*0.01f;
    }
    public static float getArmorBonusRaw(IToolStackView tool){
        return tool.getPersistentData().getInt(KEY_BONUS);
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return damage+damage*getBonus(tool);
    }

    @Override
    public void modifierProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        persistentData.putInt(KEY_BONUS,tool.getPersistentData().getInt(KEY_BONUS));
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        return damage+damage*getBonus(persistentData);
    }
}
