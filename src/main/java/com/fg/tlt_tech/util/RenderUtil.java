package com.fg.tlt_tech.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class RenderUtil {

    public static RenderType brightProjectileRenderType(ResourceLocation texture){
        return RenderType.create("bright_projectile",
                DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true,
                RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .createCompositeState(true));
    }

    public static void renderRing(float radius0,float radius1,int nodes,int rgb0,float alpha0,int rgb1,float alpha1, VertexConsumer consumer, PoseStack stack, Matrix4f poseMatrix, Matrix3f normalMatrix){
        float deg = (float) (2*Math.PI/nodes);
        for (int i=0;i<nodes;i++){
            renderFanShape(radius0,radius1,deg,rgb0,alpha0,rgb1,alpha1,consumer,stack,poseMatrix,normalMatrix);
            stack.mulPose(Axis.YP.rotation(deg));
        }
    }

    public static void renderFanShape(float radius0, float radius1, float deg, int rgb0,float alpha0, int rgb1,float alpha1, VertexConsumer consumer, PoseStack stack, Matrix4f poseMatrix, Matrix3f normalMatrix){
        float r0 = (float)(rgb0 >> 16 & 255) / 255.0F;
        float g0 = (float)(rgb0 >> 8 & 255) / 255.0F;
        float b0 = (float)(rgb0 & 255) / 255.0F;
        float r1 = (float)(rgb1 >> 16 & 255) / 255.0F;
        float g1 = (float)(rgb1 >> 8 & 255) / 255.0F;
        float b1 = (float)(rgb1 & 255) / 255.0F;
        Vector3f vec31 = new Vector3f(1,0,0);
        Vector3f vec32 = new Vector3f((float) Math.cos(deg),0 ,(float) Math.sin(deg) );
        consumer.vertex(poseMatrix,vec31.x*radius0, vec31.y*radius0, vec31.z*radius0).color(r0,g0,b0,alpha0).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix,vec32.x*radius0, vec32.y*radius0, vec32.z*radius0).color(r0,g0,b0,alpha0).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix,vec32.x*radius1, vec32.y*radius1, vec32.z*radius1).color(r1,g1,b1,alpha1).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix,vec31.x*radius1, vec31.y*radius1, vec31.z*radius1).color(r1,g1,b1,alpha1).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
    }
}
