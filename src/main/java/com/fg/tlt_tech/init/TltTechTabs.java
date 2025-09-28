package com.fg.tlt_tech.init;

import com.fg.tlt_tech.TltTech;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TltTechTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TltTech.MODID);

    public static final RegistryObject<CreativeModeTab> MISC_TAB = CREATIVE_MODE_TABS.register("tlt_tech_misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tlt_tech.misc"))
            .icon(() -> TltTechItems.REACTIVE_NUCLEAR_WASTE.get().getDefaultInstance())
            .displayItems((parameters, output) ->{
                TltTechItems.ITEMS.getEntries().forEach(object-> {
                    if (object.isPresent()) output.accept(object.get().getDefaultInstance());
                });
                TltTechBlocks.MEK_BLOCKS.getAllBlocks().forEach(iBlockProvider -> {
                    try {
                        output.accept(iBlockProvider.asItem());
                    } catch (Exception ignored){}
                });
            }).build());
}
