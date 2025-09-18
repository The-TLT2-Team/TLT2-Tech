package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.LeftClickModifierHook;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.fg.tlt_tech.client.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.DistExecutor;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.UUID;
import java.util.function.BiConsumer;

import static com.c2h6s.etstlib.tool.modifiers.capabilityProvider.PnCIntegration.AirStorageProvider.*;

public class PneumaticTool extends EtSTBaseModifier implements AttributesModifierHook, LeftClickModifierHook {
    public static final UUID PNEUMATIC_UUID = UUID.fromString("7521929f-f84b-b7df-a505-f54e19677807");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES, EtSTLibHooks.LEFT_CLICK);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (holder instanceof Player player&&isSelected){
            DistExecutor.unsafeRunForDist(()->()->{
                if (player.getAttackStrengthScale(player.getCurrentItemAttackStrengthDelay()<2?1:0)>=1&& ClientUtils.isLeftClickDown()&&getAir(tool)>50){
                    addAir(tool,-50);
                    ClientUtils.syncToolDataToServer(tool,EquipmentSlot.MAINHAND);
                    ClientUtils.doLeftClickAttack();
                }
                return null;
            },()->()-> null);
        }
        if (!world.isClientSide && holder.isUsingItem() && holder.getUseItem() == stack) {
            int drawtime = ModifierUtil.getPersistentInt(stack, GeneralInteractionModifierHook.KEY_DRAWTIME, -1);
            int duration = stack.getUseDuration();
            if (duration<2) duration+=1;
            if ((float)(duration - holder.getUseItemRemainingTicks()) / (float)drawtime >= 1.0F) {
                holder.releaseUsingItem();
                addAir(tool,-50);
            }
        }
    }


    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (getAir(tool)>50&&slot==EquipmentSlot.MAINHAND){
            consumer.accept(Attributes.ATTACK_SPEED,new AttributeModifier(PNEUMATIC_UUID,"desc.tlt_tech.attribute.pneumatic_tool",tool.getStats().get(ToolStats.ATTACK_SPEED)*(getPressure(tool)/40), AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide){
            if (getAir(tool)>50){
                addAir(tool,-50);
            }
        }
    }
}
