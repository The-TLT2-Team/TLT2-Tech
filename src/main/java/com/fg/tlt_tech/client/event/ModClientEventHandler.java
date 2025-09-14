package com.fg.tlt_tech.client.event;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.client.entityRenderers.DirectionalRotatingFlyingSwordRenderer;
import com.fg.tlt_tech.client.entityRenderers.IonicExplosionRenderer;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= TltTech.MODID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventHandler {
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        TltTechEntityTypes.LIST_ELEMENTAL_SWORDS.forEach(object->{
            event.registerEntityRenderer(object.get(), DirectionalRotatingFlyingSwordRenderer::new);
        });
        event.registerEntityRenderer(TltTechEntityTypes.IONIC_EXPLOSION.getEntityType(), IonicExplosionRenderer::new);
    }
}
