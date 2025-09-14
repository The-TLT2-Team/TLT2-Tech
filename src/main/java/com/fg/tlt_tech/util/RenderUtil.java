package com.fg.tlt_tech.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

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
}
