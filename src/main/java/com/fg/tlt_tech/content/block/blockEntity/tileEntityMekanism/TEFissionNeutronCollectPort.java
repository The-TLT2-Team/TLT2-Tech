package com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism;

import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import committee.nova.mods.avaritia.init.registry.ModItems;
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
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

public class TEFissionNeutronCollectPort extends TileEntityFissionReactorCasing implements IMultiblockEjector {
    public TEFissionNeutronCollectPort(BlockPos pos, BlockState state) {
        super(TltTechBlocks.FISSION_NEUTRON_COLLECT_PORT,pos, state);
    }
    private Set<Direction> outputDirections = Collections.emptySet();
    @Override
    public void setEjectSides(Set<Direction> sides) {
        this.outputDirections = sides;
    }

    public double process = 0;

    @Override
    protected boolean onUpdateServer(FissionReactorMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()){
            this.process+=multiblock.lastBurnRate;
            coolDownReactor(multiblock);
            EmitUtils.forEachSide(this.level,this.worldPosition,outputDirections,((blockEntity, direction) -> {
                if (this.process>1000) {
                    LazyOptional<IItemHandler> optional = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction);
                    if (optional.isPresent()) {
                        IItemHandler handler = optional.orElse(null);
                        int maxCount = (int) (this.process/1000);
                        ItemStack stack = new ItemStack(ModItems.neutron_ingot.get(),maxCount);
                        ItemStack leftover = stack.copy();
                        for (int a = 0; a < handler.getSlots(); a++) {
                            if (handler.getStackInSlot(a).isEmpty() || handler.isItemValid(a, stack)) {
                                leftover = handler.insertItem(a, leftover, false);
                                if (leftover.getCount() <= 0) break;
                            }
                        }
                    }
                }
            }));
        }
        return needsPacket;
    }
    public final LazyOptional<IItemHandler> getHandler(TEFissionNeutronCollectPort port) {
        return LazyOptional.of(() -> new IItemHandler() {
            @Override
            public int getSlots() {
                return 1;
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                ItemStack stack = ItemStack.EMPTY;
                int itemPerFuel = 1000;
                int maxCount = (int) (port.process / itemPerFuel);
                if (maxCount > 0) return new ItemStack(ModItems.neutron_ingot.get(), maxCount);
                return stack;
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack stack = ItemStack.EMPTY;
                int itemPerFuel = 1000;
                int maxCount = (int) (port.process / itemPerFuel);
                maxCount = Math.min(maxCount, amount);
                if (maxCount > 0) {
                    stack = new ItemStack(TltTechItems.REACTIVE_NUCLEAR_WASTE.get(), maxCount);
                    if (!simulate) {
                        port.process-=1000*maxCount;
                    }
                }
                return stack;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1024;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.is(TltTechItems.REACTIVE_NUCLEAR_WASTE.get());
            }
        });
    }

    private static void coolDownReactor(FissionReactorMultiblockData data){
        double lastBurn = data.lastBurnRate;
        double heatProduce = lastBurn * MekanismGeneratorsConfig.generators.energyPerFissionFuel.get().doubleValue();
        double percentage = data.heatCapacitor.getTemperature()/950;
        data.handleHeat(-Math.min(percentage*heatProduce,data.heatCapacitor.getHeat()));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability== ForgeCapabilities.ITEM_HANDLER) {
            return getStructure()!=null?getHandler(this).cast():super.getCapability(capability);
        }
        return super.getCapability(capability, side);
    }
}
