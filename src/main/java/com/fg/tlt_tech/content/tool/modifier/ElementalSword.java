package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.LeftClickModifierHook;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import mekanism.common.registration.impl.EntityTypeRegistryObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ElementalSword extends EtSTBaseModifier implements LeftClickModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, EtSTLibHooks.LEFT_CLICK);
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide&&player.getAttackStrengthScale(0)>0.8){
            createSwords(player,level,tool);
        }
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (context.isFullyCharged()&&!context.isExtraAttack()&&context.getPlayerAttacker() instanceof ServerPlayer player&&!(player instanceof FakePlayer)){
            createSwords(player,player.level(),tool);
        }
        return knockback;
    }

    public static void createSwords(Player player, Level level, IToolStackView tool){
        int i = 0;
        int max = RANDOM.nextInt(4)+4;
        Vec3 playerPos = player.position();
        Vec3 direction = player.getLookAngle();
        while (i<max){
            EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> entityType = TltTechEntityTypes.LIST_ELEMENTAL_SWORDS.get(RANDOM.nextInt(TltTechEntityTypes.LIST_ELEMENTAL_SWORDS.size()));
            Vec3 offset = direction.reverse().scale(4+RANDOM.nextDouble()*4).add(direction.cross(new Vec3(RANDOM.nextDouble()-0.5d,RANDOM.nextDouble()-0.5d,RANDOM.nextDouble()-0.5d)).normalize().scale(RANDOM.nextDouble()*3));
            BasicFlyingSwordEntity entity = entityType.getEntityType().create(level);
            if (entity!=null) {
                entity.setPos(playerPos.add(offset));
                entity.shoot(direction.x, direction.y, direction.z, 2f, 0);
                entity.tool = (ToolStack) tool;
                entity.setOwner(player);
                level.addFreshEntity(entity);
            }
            i++;
        }
    }
}
