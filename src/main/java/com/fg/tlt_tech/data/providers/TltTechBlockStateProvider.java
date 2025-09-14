package com.fg.tlt_tech.data.providers;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TltTechBlockStateProvider extends BlockStateProvider {
    public TltTechBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TltTech.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        TltTechBlocks.MEK_BLOCKS.getAllBlocks().forEach(provider->addSimpleBlock(provider.getBlock()));
    }

    private void addSimpleBlock(Block block){
        ModelFile file = cubeAll(block);
        simpleBlock(block,file);
    }

}
