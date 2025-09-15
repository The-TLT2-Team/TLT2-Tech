package com.fg.tlt_tech;

import cofh.thermal.core.common.config.ThermalCoreConfig;
import com.fg.tlt_tech.init.*;
import com.fg.tlt_tech.network.TltTechPacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static cofh.lib.util.Constants.AUG_SCALE_MAX;
import static cofh.thermal.lib.util.ThermalAugmentRules.flagUniqueAugment;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TltTech.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = TltTech.MODID)
public class TltTech
{
    public static final String MODID = "tlt_tech";
    public TltTech() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = context.getModEventBus();
        TltTechModifiers.MODIFIERS.register(modEventBus);
        TltTechItems.ITEMS.register(modEventBus);
        TltTechItems.PROJECTILE_ITEMS.register(modEventBus);
        TltTechBlocks.BLOCKS.register(modEventBus);
        TltTechBlocks.MEK_BLOCKS.register(modEventBus);
        TltTechTabs.CREATIVE_MODE_TABS.register(modEventBus);
        TltTechBlocks.BlockEntities.TILE_ENTITY_TYPES.register(modEventBus);
        TltTechBlocks.BlockEntities.BLOCK_ENTITIES.register(modEventBus);
        TltTechEntityTickers.ENTITY_TICKERS.register(modEventBus);
        TltTechEntityTypes.ENTITY_TYPES.register(modEventBus);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            flagUniqueAugment(TltTechItems.UPGRADE_AUGMENT_4.get());
            flagUniqueAugment(TltTechItems.UPGRADE_AUGMENT_5.get());
            flagUniqueAugment(TltTechItems.UPGRADE_AUGMENT_6.get());
            AUG_SCALE_MAX = Integer.MAX_VALUE;
            ThermalCoreConfig.machineAugments = 8;
            ThermalCoreConfig.dynamoAugments = 6;
            TltTechPacketHandler.init();
        });
    }

    public static ResourceLocation getResource(String string) {
        return new ResourceLocation(MODID, string);
    }

    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }
    public static String makeDescriptionId(String type, String name) {
        return type + ".tlt_tech." + name;
    }
}
