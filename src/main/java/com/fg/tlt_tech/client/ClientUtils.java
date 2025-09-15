package com.fg.tlt_tech.client;

import com.fg.tlt_tech.network.TltTechPacketHandler;
import com.fg.tlt_tech.network.packet.PSyncToolPersistentDataC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ClientUtils {
    public static void doLeftClickAttack(){
        if (isLeftClickDown()) startAttack(Minecraft.getInstance());
    }
    public static boolean isLeftClickDown(){
        return Minecraft.getInstance().options.keyAttack.isDown();
    }
    public static void syncToolDataToServer(IToolStackView tool, EquipmentSlot slot){
        TltTechPacketHandler.sendToServer(new PSyncToolPersistentDataC2S(tool.getPersistentData().getCopy(),slot));
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
