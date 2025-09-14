package com.fg.tlt_tech.content.block.blockEntity.tileEntityMekanism;

import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.lib.multiblock.IMultiblockEjector;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.tile.base.SubstanceType;
import mekanism.generators.common.content.fission.FissionReactorMultiblockData;
import mekanism.generators.common.tile.fission.TileEntityFissionReactorCasing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Set;

public class TEDryStateFissionFiller extends TileEntityFissionReactorCasing implements IMultiblockEjector {
    public TEDryStateFissionFiller(BlockPos pos, BlockState state) {
        super(TltTechBlocks.DRY_STATE_FUEL_FILLER,pos, state);
    }

    public static final HashMap<Item,Integer> fuelTransferCount;
    static {
        HashMap<Item,Integer> map = new HashMap<>();
        map.put(MekanismItems.YELLOW_CAKE_URANIUM.asItem(), 250);
        map.put(TltTechItems.FU_DUST.get(),500);
        map.put(TltTechItems.CK_INDUCED_FU_DUST.get(),8000);
        fuelTransferCount = map;
    }

    public static GasStack createFuel(ItemStack stack){
        if (!fuelTransferCount.containsKey(stack.getItem())) return GasStack.EMPTY;
        return MekanismGases.FISSILE_FUEL.getStack((long) stack.getCount() *fuelTransferCount.get(stack.getItem()));
    }
    public static GasStack createFuel(int count,Item item){
        if (!fuelTransferCount.containsKey(item)) return GasStack.EMPTY;
        return MekanismGases.FISSILE_FUEL.getStack((long) count *fuelTransferCount.get(item));
    }

    @Override
    public void setEjectSides(Set<Direction> sides) {

    }

    @Override
    protected boolean onUpdateServer(FissionReactorMultiblockData multiblock) {
        return super.onUpdateServer(multiblock);
    }
    public final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(()->new IItemHandler(){
        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (fuelTransferCount.containsKey(stack.getItem())){
                int itemPerFuel = fuelTransferCount.get(stack.getItem());
                stack = stack.copy();
                FissionReactorMultiblockData data = getMultiblock();
                int maxCount = (int) (data.fuelTank.getNeeded()/itemPerFuel);
                maxCount = Math.min(maxCount,stack.getCount());
                GasStack gasStack = createFuel(maxCount,stack.getItem());
                GasStack after = data.fuelTank.insert(gasStack,Action.SIMULATE,AutomationType.INTERNAL);
                int inserted = (int) (gasStack.getAmount()-after.getAmount());
                maxCount = Math.min(inserted/itemPerFuel,maxCount);
                if (maxCount>0) {
                    stack.shrink(maxCount);
                    if (!simulate) {
                        data.fuelTank.insert(gasStack,Action.EXECUTE,AutomationType.INTERNAL);
                    }
                }
            }
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return fuelTransferCount.containsKey(stack.getItem());
        }
    });
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        if (capability== ForgeCapabilities.ITEM_HANDLER) {
            return getStructure()!=null?itemHandler.cast():super.getCapability(capability);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public boolean persists(SubstanceType type) {
        return super.persists(type);
    }

}
