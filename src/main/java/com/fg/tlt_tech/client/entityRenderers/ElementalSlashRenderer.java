package com.fg.tlt_tech.client.entityRenderers;

import com.fg.tlt_tech.init.TltTechItems;
import com.fg.tlt_tech.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ElementalSlashRenderer extends EntityRenderer<Projectile> {
    private final ItemRenderer itemRenderer;
    public ElementalSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(Projectile entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.tickCount >= 2) {
            matrixStackIn.pushPose();
            matrixStackIn.mulPose(Axis.XP.rotationDegrees(90));
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(+(entity.tickCount + partialTicks) * 100 % 360));
            matrixStackIn.scale(8,8,1);
            this.renderItemWithTransparent(new ItemStack( TltTechItems.ELEMENTAL_SLASH.get()),entity.level(),  matrixStackIn, bufferIn,1);
            matrixStackIn.popPose();
        }
    }

    public void renderItemWithTransparent(ItemStack stack, Level level, PoseStack poseStack, MultiBufferSource buffer, float alpha){
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

    public void renderItemQuadWithTransparent(PoseStack poseStack, VertexConsumer consumer, List<BakedQuad> quads, ItemStack stack, float alpha){
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

    protected int getSkyLightLevel(Projectile p_114509_, BlockPos p_114510_) {
        return 15;
    }
    protected int getBlockLightLevel(Projectile p_114496_, BlockPos p_114497_) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}