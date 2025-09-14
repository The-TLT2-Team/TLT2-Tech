package com.fg.tlt_tech.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ClientUtils {
    public static void doLeftClickAttack(){
        if (Minecraft.getInstance().options.keyAttack.isDown()) startAttack(Minecraft.getInstance());
    }

    public static void startAttack(Minecraft minecraft){
        if (minecraft.hitResult != null&&!minecraft.player.isHandsBusy()) {
                var inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, minecraft.options.keyAttack, InteractionHand.MAIN_HAND);
                if (!inputEvent.isCanceled())
                    switch (minecraft.hitResult.getType()) {
                        case ENTITY:
                            minecraft.gameMode.attack(minecraft.player, ((EntityHitResult)minecraft.hitResult).getEntity());
                            break;
                        case BLOCK:
                            BlockHitResult blockhitresult = (BlockHitResult)minecraft.hitResult;
                            BlockPos blockpos = blockhitresult.getBlockPos();
                            if (!minecraft.level.isEmptyBlock(blockpos)) {
                                minecraft.gameMode.startDestroyBlock(blockpos, blockhitresult.getDirection());
                                break;
                            }
                        case MISS:
                            minecraft.player.resetAttackStrengthTicker();
                            net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(minecraft.player);
                    }
                if (inputEvent.shouldSwingHand())
                    minecraft.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
