package com.fg.tlt_tech.init;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism.TEDryStateFissionFiller;
import com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism.TEDryStateWasteExtractor;
import com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism.TEFissionNeutronCollectPort;
import com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism.TEFissionThermalElectricPort;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.registries.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TltTechBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TltTech.MODID);
    public static final BlockDeferredRegister MEK_BLOCKS = new BlockDeferredRegister(TltTech.MODID);


    public static final BlockRegistryObject<BlockBasicMultiblock<TEDryStateFissionFiller>, ItemBlockTooltip<BlockBasicMultiblock<TEDryStateFissionFiller>>> DRY_STATE_FUEL_FILLER =registerTooltipBlock("dry_state_fuel_filler", () -> new BlockBasicMultiblock<>(BlockTypes.DRY_STATE_FUEL_FILLER, properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TEDryStateWasteExtractor>, ItemBlockTooltip<BlockBasicMultiblock<TEDryStateWasteExtractor>>> DRY_STATE_WASTE_EXTRACTOR =registerTooltipBlock("dry_state_waste_extractor", () -> new BlockBasicMultiblock<>(BlockTypes.DRY_STATE_WASTE_EXTRACTOR, properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TEFissionNeutronCollectPort>, ItemBlockTooltip<BlockBasicMultiblock<TEFissionNeutronCollectPort>>> FISSION_NEUTRON_COLLECT_PORT =registerTooltipBlock("fission_neutron_collect_port", () -> new BlockBasicMultiblock<>(BlockTypes.FISSION_NEUTRON_COLLECT_PORT, properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TEFissionThermalElectricPort>, ItemBlockTooltip<BlockBasicMultiblock<TEFissionThermalElectricPort>>> FISSION_THERMAL_ELECTRIC_PORT =registerTooltipBlock("fission_thermo_electric_port", () -> new BlockBasicMultiblock<>(BlockTypes.FISSION_THERMAL_ELECTRIC_PORT, properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY)));

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return MEK_BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }

    public static class BlockTypes{
        public static final BlockTypeTile<TEDryStateFissionFiller> DRY_STATE_FUEL_FILLER = BlockTypeTile.BlockTileBuilder
                .createBlock(() -> BlockEntities.DRY_STATE_FUEL_FILLER, () -> "tlt.tooltip.dry_state_fuel_filler")
                .withGui(() -> GeneratorsContainerTypes.FISSION_REACTOR, GeneratorsLang.FISSION_REACTOR)
                .withSound(GeneratorsSounds.FISSION_REACTOR)
                .externalMultiblock()
                .build();
        public static final BlockTypeTile<TEDryStateWasteExtractor> DRY_STATE_WASTE_EXTRACTOR = BlockTypeTile.BlockTileBuilder
                .createBlock(() -> BlockEntities.DRY_STATE_WASTE_EXTRACTOR, () -> "tlt.tooltip.dry_state_waste_extractor")
                .withGui(() -> GeneratorsContainerTypes.FISSION_REACTOR, GeneratorsLang.FISSION_REACTOR)
                .withSound(GeneratorsSounds.FISSION_REACTOR)
                .externalMultiblock()
                .build();
        public static final BlockTypeTile<TEFissionNeutronCollectPort> FISSION_NEUTRON_COLLECT_PORT = BlockTypeTile.BlockTileBuilder
                .createBlock(() -> BlockEntities.FISSION_NEUTRON_COLLECT_PORT, () -> "tlt.tooltip.fission_neutron_collect_port")
                .withGui(() -> GeneratorsContainerTypes.FISSION_REACTOR, GeneratorsLang.FISSION_REACTOR)
                .withSound(GeneratorsSounds.FISSION_REACTOR)
                .externalMultiblock()
                .build();
        public static final BlockTypeTile<TEFissionThermalElectricPort> FISSION_THERMAL_ELECTRIC_PORT = BlockTypeTile.BlockTileBuilder
                .createBlock(() -> BlockEntities.FISSION_THERMAL_ELECTRIC_PORT, () -> "tlt.tooltip.fission_thermo_electric_port")
                .withGui(() -> GeneratorsContainerTypes.FISSION_REACTOR, GeneratorsLang.FISSION_REACTOR)
                .withSound(GeneratorsSounds.FISSION_REACTOR)
                .externalMultiblock()
                .build();
    }

    public static class BlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TltTech.MODID);
        public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(TltTech.MODID);

        public static final TileEntityTypeRegistryObject<TEDryStateFissionFiller> DRY_STATE_FUEL_FILLER = TILE_ENTITY_TYPES.register(TltTechBlocks.DRY_STATE_FUEL_FILLER, TEDryStateFissionFiller::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        public static final TileEntityTypeRegistryObject<TEDryStateWasteExtractor> DRY_STATE_WASTE_EXTRACTOR = TILE_ENTITY_TYPES.register(TltTechBlocks.DRY_STATE_WASTE_EXTRACTOR, TEDryStateWasteExtractor::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        public static final TileEntityTypeRegistryObject<TEFissionNeutronCollectPort> FISSION_NEUTRON_COLLECT_PORT = TILE_ENTITY_TYPES.register(TltTechBlocks.FISSION_NEUTRON_COLLECT_PORT, TEFissionNeutronCollectPort::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
        public static final TileEntityTypeRegistryObject<TEFissionThermalElectricPort> FISSION_THERMAL_ELECTRIC_PORT = TILE_ENTITY_TYPES.register(TltTechBlocks.FISSION_THERMAL_ELECTRIC_PORT, TEFissionThermalElectricPort::new, TileEntityMekanism::tickServer, TileEntityMekanism::tickClient);
    }
}
