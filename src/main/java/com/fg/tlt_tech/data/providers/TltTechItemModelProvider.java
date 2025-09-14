package com.fg.tlt_tech.data.providers;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.object.FluidObject;

public class TltTechItemModelProvider extends ItemModelProvider {
    public static final String PARENT_SIMPLE_ITEM ="item/generated";
    public static final String PARENT_BUCKET_FLUID ="forge:item/bucket_drip";

    public TltTechItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TltTech.MODID, existingFileHelper);
    }

    public void generateItemModel(Item item,String typePath){
        withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(ForgeRegistries.ITEMS.getKey(item).getPath(),typePath));
    }
    public void generateBlockItemModel(BlockItem item){
        withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(), getBlockItemLocation(ForgeRegistries.ITEMS.getKey(item).getPath()));
    }
    public void generateBucketItemModel(FluidObject<ForgeFlowingFluid> object,boolean flip){
        withExistingParent(object.getId().getPath()+"_bucket",PARENT_BUCKET_FLUID).customLoader((itemModelBuilder,existingFileHelper)->DynamicFluidContainerModelBuilder
                .begin(itemModelBuilder,existingFileHelper)
                .fluid(object.get())
                .flipGas(flip));
    }

    public ResourceLocation getItemLocation(String path,String typePath){
        return new ResourceLocation(TltTech.MODID,"item/"+typePath+"/"+path);
    }
    public ResourceLocation getBlockItemLocation(String path){
        return new ResourceLocation(TltTech.MODID,"block/"+path);
    }

    @Override
    protected void registerModels() {
        TltTechBlocks.MEK_BLOCKS.getAllBlocks().forEach(provider->generateBlockItemModel((BlockItem) provider.asItem()));
        TltTechItems.LIST_SIMPLE_MODEL.forEach(object -> generateItemModel(object.get(),"misc"));
        for (var object: TltTechItems.PROJECTILE_ITEMS.getEntries()) generateItemModel(object.get(),"projectile");
    }
}
