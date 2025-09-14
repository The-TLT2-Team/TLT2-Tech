package com.fg.tlt_tech.data;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.data.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TltTech.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class TltTechDataProviders {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();
        ExistingFileHelper helper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new TltTechBlockStateProvider(output,helper));
        generator.addProvider(event.includeClient(),new TltTechItemModelProvider(output,helper));
        generator.addProvider(event.includeClient(),new TltTechFluidTextureProvider(output));
        generator.addProvider(event.includeClient(),new TltTechFluidTagProvider(output,lookupProvider,helper));
        generator.addProvider(event.includeClient(),new FluidBucketModelProvider(output,TltTech.MODID));
        TltTechBlockTagProvider blockTags = new TltTechBlockTagProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeClient(),blockTags);
        generator.addProvider(event.includeServer(),new TltTechItemTagProvider(output,lookupProvider,blockTags.contentsGetter(),helper));
    }
}
