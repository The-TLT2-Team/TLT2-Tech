package com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism;

import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.common.lib.multiblock.IMultiblockEjector;
import mekanism.common.util.EmitUtils;
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

public class TEDryStateWasteExtractor extends TileEntityFissionReactorCasing implements IMultiblockEjector {
    private Set<Direction> outputDirections = Collections.emptySet();

    public TEDryStateWasteExtractor(BlockPos pos, BlockState state) {
        super(TltTechBlocks.DRY_STATE_WASTE_EXTRACTOR,pos, state);
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {
        this.outputDirections = sides;
    }

    @Override
    protected boolean onUpdateServer(FissionReactorMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        if (multiblock.isFormed()) EmitUtils.forEachSide(this.level,this.worldPosition,outputDirections,((blockEntity, direction) -> {
            LazyOptional<IItemHandler> optional = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER,direction);
            if (optional.isPresent()){
                IItemHandler portHandler = this.itemHandler.orElse(null);
                IItemHandler handler = optional.orElse(null);
                ItemStack stack = portHandler.extractItem(1,1024,true);
                ItemStack leftover = stack.copy();
                for (int a = 0; a < handler.getSlots(); a++) {
                    if (handler.getStackInSlot(a).isEmpty() || handler.isItemValid(a, stack)) {
                        int count = leftover.getCount();
                        leftover = handler.insertItem(a, leftover, false);
                        count = count- leftover.getCount();
                        portHandler.extractItem(1,count,false);
                        if (leftover.getCount()<=0) break;
                    }
                }
            }
        }));
        return needsPacket;
    }
    public final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(()->new IItemHandler(){
        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            ItemStack stack = ItemStack.EMPTY;
            int itemPerFuel = 2000;
            FissionReactorMultiblockData data = getMultiblock();
            int maxCount = (int) Math.min(data.wasteTank.getStored()/itemPerFuel,Integer.MAX_VALUE);
            if (maxCount>0) return new ItemStack(TltTechItems.REACTIVE_NUCLEAR_WASTE.get(),maxCount);
            return stack;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack stack = ItemStack.EMPTY;
            int itemPerFuel = 2000;
            FissionReactorMultiblockData data = getMultiblock();
            int maxCount = (int) (data.wasteTank.getStored()/itemPerFuel);
            maxCount=Math.min(maxCount,amount);
            long extracted = data.wasteTank.extract((long) maxCount *itemPerFuel, Action.SIMULATE, AutomationType.INTERNAL).getAmount();
            maxCount = Math.min((int) (extracted/itemPerFuel),maxCount);
            if (maxCount>0) {
                stack = new ItemStack(TltTechItems.REACTIVE_NUCLEAR_WASTE.get(),maxCount);
                if (!simulate) {
                    data.wasteTank.extract(extracted,Action.EXECUTE,AutomationType.INTERNAL);
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
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability== ForgeCapabilities.ITEM_HANDLER) {
            return getStructure()!=null?itemHandler.cast():super.getCapability(capability);
        }
        return super.getCapability(capability, side);
    }
}
