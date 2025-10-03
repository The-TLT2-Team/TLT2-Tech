package com.fg.tlt_tech.content.event;

import com.fg.tlt_tech.TltTech;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import vazkii.botania.api.mana.ManaDiscountEvent;

import static com.fg.tlt_tech.content.tool.modifier.AlfBless.KEY_ALF;

@Mod.EventBusSubscriber(modid = TltTech.MODID)
public class ToolEventHandler {
    @SubscribeEvent
    public static void onManaRequired(ManaDiscountEvent event){
        event.getEntityPlayer().getCapability(TinkerDataCapability.CAPABILITY).ifPresent(cap->{
            if (cap.get(KEY_ALF,0)>=4){
                event.setDiscount(event.getDiscount()+0.2f);
            }
        });
    }
}
