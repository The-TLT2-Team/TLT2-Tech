package com.fg.tlt_tech.client.event;

import com.c2h6s.tinkers_advanced.client.renderer.RenderThermalSlash;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.FluxInfused;
import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechModifiers;
import com.fg.tlt_tech.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.FluxInfused.getMode;

@Mod.EventBusSubscriber(modid = TltTech.MODID,value = Dist.CLIENT)
public class ModifierClientEvent {
    @SubscribeEvent
    public static void onRenderPlayer(RenderLevelStageEvent event){

        Player player = Minecraft.getInstance().player;
        if (player!=null&&event.getStage()== RenderLevelStageEvent.Stage.AFTER_ENTITIES&& player.getMainHandItem().getItem() instanceof IModifiable) {
            ToolStack tool = ToolStack.from(player.getMainHandItem());
            if (tool.getModifierLevel(TltTechModifiers.EDGE_DISCHARGE.get())>0&&getMode(tool)==2&& ToolEnergyCapability.getEnergy(tool)>250) {
                PoseStack poseStack = event.getPoseStack();
                poseStack.pushPose();
                Vec3 vec3 = new Vec3(
                        Mth.lerp(event.getPartialTick(),player.xOld,player.getX()),
                        Mth.lerp(event.getPartialTick(),player.yOld,player.getY()),
                        Mth.lerp(event.getPartialTick(),player.zOld,player.getZ())
                );
                vec3 = vec3.subtract(event.getCamera().getPosition());
                poseStack.translate(vec3.x, vec3.y, vec3.z);
                poseStack.translate(0,player.getBbHeight()*0.5,0);
                MultiBufferSource source = event.getLevelRenderer().renderBuffers.bufferSource();
                VertexConsumer consumer = source.getBuffer( com.c2h6s.tinkers_advanced.util.RenderUtil.brightProjectileRenderType(RenderThermalSlash.TEXTURE));
                PoseStack.Pose pose = poseStack.last();
                Matrix4f poseMatrix = pose.pose();
                Matrix3f normalMatrix = pose.normal();
                RenderUtil.renderRing(5,5.1f,32,0xFFFFFF,1,0xFF0000,0.75f,consumer,poseStack,poseMatrix,normalMatrix);
                RenderUtil.renderRing(5.1f,5.5f,32,0xFF0000,0.75f,0xFF0000,0f,consumer,poseStack,poseMatrix,normalMatrix);
                poseStack.popPose();
            }
        }
    }
}
