package com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism;

import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.common.lib.multiblock.IMultiblockEjector;
import mekanism.common.util.EmitUtils;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.fission.FissionReactorMultiblockData;
import mekanism.generators.common.tile.fission.TileEntityFissionReactorCasing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

public class TEFissionThermalElectricPort extends TileEntityFissionReactorCasing implements IMultiblockEjector {
    private Set<Direction> outputDirections = Collections.emptySet();

    public TEFissionThermalElectricPort(BlockPos pos, BlockState state) {
        super(TltTechBlocks.FISSION_THERMAL_ELECTRIC_PORT,pos, state);
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {
        this.outputDirections = sides;
    }

    @Override
    protected boolean onUpdateServer(FissionReactorMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) EmitUtils.forEachSide(this.level,this.worldPosition,outputDirections,((blockEntity, direction) -> {
            LazyOptional<IEnergyStorage> optional = blockEntity.getCapability(ForgeCapabilities.ENERGY,direction);
            if (optional.isPresent()){
                var storage = optional.orElse(null);
                var fissionStorage = this.energyStorage.orElse(null);
                int toInsert = storage.receiveEnergy(fissionStorage.extractEnergy(fissionStorage.getMaxEnergyStored(),true),true);
                if (toInsert>0){
                    fissionStorage.extractEnergy(toInsert,false);
                    storage.receiveEnergy(toInsert,false);
                }
            }
        }));
        return needsPacket;
    }

    public final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(()->new IEnergyStorage() {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            FissionReactorMultiblockData data = getMultiblock();
            maxExtract = Math.min(maxExtract,getEnergyStored());
            if (data.isFormed()&&maxExtract>0) {
                if (!simulate) data.handleHeat(-maxExtract*MekanismGeneratorsConfig.generators.energyPerFissionFuel.get().doubleValue()/512d);
                return maxExtract;
            }
            return 0;
        }

        @Override
        public int getEnergyStored() {
            FissionReactorMultiblockData data = getMultiblock();
            if (data.isFormed()){
                var heatCapacitor = data.heatCapacitor;
                double heat = heatCapacitor.getHeat();
                if (heat>0){
                    return (int) Math.min(heat*512/ MekanismGeneratorsConfig.generators.energyPerFissionFuel.get().doubleValue(),Integer.MAX_VALUE);
                }
            }
            return 0;
        }

        @Override
        public int getMaxEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    });



    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability== ForgeCapabilities.ENERGY) {
            return getStructure()!=null?energyStorage.cast():super.getCapability(capability);
        }
        return super.getCapability(capability, side);
    }
}