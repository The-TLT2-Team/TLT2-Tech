package com.fg.tlt_tech.client.entityRenderers;

import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DirectionalRotatingFlyingSwordRenderer extends EntityRenderer<BasicFlyingSwordEntity> {
    public final ItemRenderer itemRenderer;
    public DirectionalRotatingFlyingSwordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(BasicFlyingSwordEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pEntity.initialRotation+=10*pPartialTick;
        pEntity.initialRotation%=360;
        Minecraft minecraft = Minecraft.getInstance();
        //int maxPhantom = Mth.clamp(minecraft.getFps()/20 ,1,10);
        int maxPhantom = 4;

        double xRot = Mth.rotLerp(pPartialTick,pEntity.xRotO,pEntity.getXRot());
        double yRot = Mth.rotLerp(pPartialTick,pEntity.yRotO,pEntity.getYRot());
        pEntity.rotationCache.add(new Vec3(xRot,yRot,pEntity.initialRotation));
        while (pEntity.rotationCache.size()>maxPhantom) pEntity.rotationCache.remove(0);

        double x = Mth.lerp(pPartialTick,pEntity.xOld,pEntity.getX());
        double y = Mth.lerp(pPartialTick,pEntity.yOld,pEntity.getY());
        double z = Mth.lerp(pPartialTick,pEntity.zOld,pEntity.getZ());
        Vec3 renderPos = new Vec3(x,y,z);
        pEntity.positionCache.add(new Vec3(x,y,z));
        while (pEntity.positionCache.size()>maxPhantom) pEntity.positionCache.remove(0);
        int length = Math.min(pEntity.positionCache.size(),pEntity.rotationCache.size());
        for (int i=0;i<length;i++) {
            Vec3 rotation = pEntity.rotationCache.get(i);
            Vec3 offSet = pEntity.positionCache.get(i).subtract(renderPos);
            pPoseStack.pushPose();
            pPoseStack.translate(offSet.x,offSet.y,offSet.z);
            pPoseStack.mulPose(Axis.YP.rotationDegrees((float) rotation.y-90));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees((float) rotation.x));
            pPoseStack.mulPose(Axis.XP.rotationDegrees((float) rotation.z));
            pPoseStack.translate(-0.03125, -0.09375,0);
            pPoseStack.scale(1.5F,1.5F,1.5F);
            float alpha = (float)i/length;

            this.renderItemWithTransparent(pEntity.renderItem, pEntity.level(),pPoseStack,pBuffer,alpha);
            pPoseStack.popPose();
        }
    }

    public void renderItemWithTransparent(ItemStack stack, Level level,PoseStack poseStack,MultiBufferSource buffer,float alpha){
        BakedModel model = this.itemRenderer.getModel(stack,level,null,0);
        poseStack.pushPose();
        model.applyTransform(ItemDisplayContext.NONE,poseStack,false);
        poseStack.translate(-0.5,-0.5,-0.5);
        RenderType renderType = RenderUtil.brightProjectileRenderType(TextureAtlas.LOCATION_BLOCKS);

        VertexConsumer consumer = buffer.getBuffer(renderType);
        RandomSource randomsource = RandomSource.create();
        long i = 42L;
        for(Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            this.renderItemQuadWithTransparent(poseStack, consumer, model.getQuads(null, direction, randomsource), stack,alpha);
        }

        randomsource.setSeed(42L);
        this.renderItemQuadWithTransparent(poseStack, consumer, model.getQuads(null, null, randomsource), stack,alpha);
        poseStack.popPose();
    }

    public void renderItemQuadWithTransparent( PoseStack poseStack, VertexConsumer consumer, List<BakedQuad> quads,ItemStack stack,float alpha){
        boolean flag = !stack.isEmpty();
        PoseStack.Pose posestack$pose = poseStack.last();

        for(BakedQuad bakedquad : quads) {
            int i = -1;
            if (flag && bakedquad.isTinted()) {
                i = this.itemRenderer.itemColors.getColor(stack, bakedquad.getTintIndex());
            }

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            consumer.putBulkData(posestack$pose, bakedquad, f, f1, f2, alpha, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, true);
        }
    }

    @Override
    protected int getBlockLightLevel(BasicFlyingSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(BasicFlyingSwordEntity pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(BasicFlyingSwordEntity pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
