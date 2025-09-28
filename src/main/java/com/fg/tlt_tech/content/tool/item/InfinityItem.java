package com.fg.tlt_tech.content.tool.item;

import mekanism.api.Action;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.chemical.infuse.IInfusionHandler;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.IPigmentHandler;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.ISlurryHandler;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.IModifiable;

import java.util.List;

public class InfinityItem extends Item implements IModifiable {
    public static final String KEY_MATTER_TYPE = "infinity_matter_type";
    public static final String KEY_MATTER_NAME = "infinity_matter_name";
    public InfinityItem() {
        super(new Properties());
    }
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapabilityProvider(stack);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public ItemStack getDefaultInstance() {
        CompoundTag tag = new CompoundTag();
        tag.putString(KEY_MATTER_TYPE,MatterType.ITEM.name());
        tag.putString(KEY_MATTER_NAME,"minecraft:cobblestone");
        ItemStack stack =new ItemStack(this);
        stack.setTag(tag);
        return stack;
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        CompoundTag nbt = pStack.getTag();
        if (nbt!=null){
            if (!nbt.contains(KEY_MATTER_NAME)||!nbt.contains(KEY_MATTER_TYPE)) {
                nbt.putString(KEY_MATTER_TYPE,MatterType.ITEM.name());
                nbt.putString(KEY_MATTER_NAME,"minecraft:cobblestone");
            }
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putString(KEY_MATTER_TYPE,MatterType.ITEM.name());
            tag.putString(KEY_MATTER_NAME,"minecraft:cobblestone");
            pStack.setTag(tag);
        }
    }

    @Override
    public ToolDefinition getToolDefinition() {
        return ToolDefinition.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = pStack.getTag();
        if (nbt!=null&&nbt.contains(KEY_MATTER_TYPE)&&nbt.contains(KEY_MATTER_NAME)){
            pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.info").withStyle(ChatFormatting.AQUA));
            pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.info2").withStyle(ChatFormatting.DARK_AQUA));
            pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider").withStyle(ChatFormatting.LIGHT_PURPLE));
            MatterType type = MatterType.valueOf(nbt.getString(KEY_MATTER_TYPE));
            ResourceLocation name = new ResourceLocation(nbt.getString(KEY_MATTER_NAME));
            switch (type){
                case GAS -> {
                    Gas gas = Gas.getFromRegistry(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.chemical").withStyle(Style.EMPTY.withColor(0x51FFC6)));
                    pTooltipComponents.add(Component.translatable(gas.getTranslationKey()));
                }
                case INFUSE -> {
                    InfuseType gas = InfuseType.getFromRegistry(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.chemical").withStyle(Style.EMPTY.withColor(0x51FFC6)));
                    pTooltipComponents.add(Component.translatable(gas.getTranslationKey()));
                }
                case SLURRY -> {
                    Slurry gas = Slurry.getFromRegistry(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.chemical").withStyle(Style.EMPTY.withColor(0x51FFC6)));
                    pTooltipComponents.add(Component.translatable(gas.getTranslationKey()));
                }
                case PIGMENT -> {
                    Pigment gas = Pigment.getFromRegistry(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.chemical").withStyle(Style.EMPTY.withColor(0x51FFC6)));
                    pTooltipComponents.add(Component.translatable(gas.getTranslationKey()));
                }
                case ITEM -> {
                    Item item = ForgeRegistries.ITEMS.getValue(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.item").withStyle(Style.EMPTY.withColor(0xFFDA77)));
                    if (item != null) {
                        pTooltipComponents.add(item.getName(new ItemStack(item)));
                    }
                }
                case FLUID -> {
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(name);
                    pTooltipComponents.add(Component.translatable("info.tlt_tech.inf_provider.fluid").withStyle(Style.EMPTY.withColor(0x77A8FF)));
                    if (fluid != null) {
                        pTooltipComponents.add(fluid.getFluidType().getDescription());
                    }
                }
            }
        }
        super.appendHoverText(pStack,pLevel,pTooltipComponents,pIsAdvanced);
    }

    public static class CapabilityProvider implements ICapabilityProvider{
        public LazyOptional<IItemHandler> itemHandler=LazyOptional.empty();
        public LazyOptional<IFluidHandler> fluidHandler=LazyOptional.empty();
        public LazyOptional<IGasHandler> gasHandler=LazyOptional.empty();
        public LazyOptional<IInfusionHandler> infuseHandler=LazyOptional.empty();
        public LazyOptional<ISlurryHandler> slurryHandler=LazyOptional.empty();
        public LazyOptional<IPigmentHandler> pigmentHandler=LazyOptional.empty();
        public CapabilityProvider(ItemStack stack){
            CompoundTag nbt = stack.getTag();
            if (nbt!=null&&nbt.contains(KEY_MATTER_TYPE)&&nbt.contains(KEY_MATTER_NAME)){
                MatterType type = MatterType.valueOf(nbt.getString(KEY_MATTER_TYPE));
                ResourceLocation name = new ResourceLocation(nbt.getString(KEY_MATTER_NAME));
                switch (type){
                    case GAS -> {
                        Gas gas = Gas.getFromRegistry(name);
                        this.gasHandler = LazyOptional.of(()->createHandler(gas));
                    }
                    case INFUSE -> {
                        InfuseType gas = InfuseType.getFromRegistry(name);
                        this.infuseHandler = LazyOptional.of(()->createHandler(gas));
                    }
                    case PIGMENT -> {
                        Pigment gas = Pigment.getFromRegistry(name);
                        this.pigmentHandler = LazyOptional.of(()->createHandler(gas));
                    }
                    case SLURRY -> {
                        Slurry gas = Slurry.getFromRegistry(name);
                        this.slurryHandler = LazyOptional.of(()->createHandler(gas));
                    }
                    case ITEM -> {
                        Item item = ForgeRegistries.ITEMS.getValue(name);
                        this.itemHandler = LazyOptional.of(()->createHandler(item));
                    }
                    case FLUID -> {
                        Fluid fluid = ForgeRegistries.FLUIDS.getValue(name);
                        this.fluidHandler = LazyOptional.of(()->createHandler(stack,fluid));
                    }
                }
            }
        }
        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap== ForgeCapabilities.ITEM_HANDLER&&itemHandler.isPresent()) return itemHandler.cast();
            if ((cap== ForgeCapabilities.FLUID_HANDLER||cap==ForgeCapabilities.FLUID_HANDLER_ITEM)&&fluidHandler.isPresent()) return fluidHandler.cast();
            if (cap== Capabilities.GAS_HANDLER &&gasHandler.isPresent()) return gasHandler.cast();
            if (cap== Capabilities.INFUSION_HANDLER &&infuseHandler.isPresent()) return infuseHandler.cast();
            if (cap== Capabilities.PIGMENT_HANDLER &&pigmentHandler.isPresent()) return pigmentHandler.cast();
            if (cap== Capabilities.SLURRY_HANDLER &&slurryHandler.isPresent()) return slurryHandler.cast();
            return LazyOptional.empty();
        }
        public static IItemHandler createHandler(Item item){
            return new IItemHandler() {
                @Override
                public int getSlots() {
                    return 1;
                }

                @Override
                public @NotNull ItemStack getStackInSlot(int slot) {
                    return new ItemStack(item,Integer.MAX_VALUE);
                }

                @Override
                public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                    if (isItemValid(slot,stack)) return ItemStack.EMPTY;
                    return stack;
                }

                @Override
                public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                    return new ItemStack(item,amount);
                }

                @Override
                public int getSlotLimit(int slot) {
                    return Integer.MAX_VALUE;
                }

                @Override
                public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                    return stack.is(item);
                }
            };
        }
        public static IFluidHandlerItem createHandler(ItemStack stack,Fluid fluid){
            return new IFluidHandlerItem() {
                @Override
                public @NotNull ItemStack getContainer() {
                    return stack;
                }

                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public @NotNull FluidStack getFluidInTank(int tank) {
                    return new FluidStack(fluid,Integer.MAX_VALUE);
                }

                @Override
                public int getTankCapacity(int tank) {
                    return Integer.MAX_VALUE;
                }

                @Override
                public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                    return stack.getFluid()==fluid;
                }

                @Override
                public int fill(FluidStack resource, FluidAction action) {
                    return isFluidValid(0,resource) ? resource.getAmount():0;
                }

                @Override
                public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
                    if (this.isFluidValid(0,resource)) return new FluidStack(fluid,resource.getAmount());
                    return FluidStack.EMPTY;
                }

                @Override
                public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
                    return new FluidStack(fluid,maxDrain);
                }
            };
        }
        public static IGasHandler createHandler(Gas gas){
            return new IGasHandler() {
                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public GasStack getChemicalInTank(int tank) {
                    return new GasStack(gas,Long.MAX_VALUE);
                }

                @Override
                public void setChemicalInTank(int tank, GasStack stack) {
                }

                @Override
                public long getTankCapacity(int tank) {
                    return Long.MAX_VALUE;
                }

                @Override
                public boolean isValid(int tank, GasStack stack) {
                    return stack.isTypeEqual(gas);
                }

                @Override
                public GasStack insertChemical(int tank, GasStack stack, Action action) {
                    return GasStack.EMPTY;
                }

                @Override
                public GasStack extractChemical(int tank, long amount, Action action) {
                    return new GasStack(gas,amount);
                }
            };
        }
        public static IInfusionHandler createHandler(InfuseType infuse){
            return new IInfusionHandler() {
                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public InfusionStack getChemicalInTank(int tank) {
                    return new InfusionStack(infuse,Long.MAX_VALUE);
                }

                @Override
                public void setChemicalInTank(int tank, InfusionStack stack) {
                }

                @Override
                public long getTankCapacity(int tank) {
                    return Long.MAX_VALUE;
                }

                @Override
                public boolean isValid(int tank, InfusionStack stack) {
                    return stack.isTypeEqual(infuse);
                }

                @Override
                public InfusionStack insertChemical(int tank, InfusionStack stack, Action action) {
                    return InfusionStack.EMPTY;
                }

                @Override
                public InfusionStack extractChemical(int tank, long amount, Action action) {
                    return new InfusionStack(infuse,amount);
                }
            };
        }
        public static ISlurryHandler createHandler(Slurry slurry){
            return new ISlurryHandler() {
                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public SlurryStack getChemicalInTank(int tank) {
                    return new SlurryStack(slurry,Long.MAX_VALUE);
                }

                @Override
                public void setChemicalInTank(int tank, SlurryStack stack) {
                }

                @Override
                public long getTankCapacity(int tank) {
                    return Long.MAX_VALUE;
                }

                @Override
                public boolean isValid(int tank, SlurryStack stack) {
                    return stack.isTypeEqual(slurry);
                }

                @Override
                public SlurryStack insertChemical(int tank, SlurryStack stack, Action action) {
                    return SlurryStack.EMPTY;
                }

                @Override
                public SlurryStack extractChemical(int tank, long amount, Action action) {
                    return new SlurryStack(slurry,amount);
                }
            };
        }
        public static IPigmentHandler createHandler(Pigment pigment){
            return new IPigmentHandler() {
                @Override
                public int getTanks() {
                    return 1;
                }

                @Override
                public PigmentStack getChemicalInTank(int tank) {
                    return new PigmentStack(pigment,Long.MAX_VALUE);
                }

                @Override
                public void setChemicalInTank(int tank, PigmentStack stack) {
                }

                @Override
                public long getTankCapacity(int tank) {
                    return Long.MAX_VALUE;
                }

                @Override
                public boolean isValid(int tank, PigmentStack stack) {
                    return stack.isTypeEqual(pigment);
                }

                @Override
                public PigmentStack insertChemical(int tank, PigmentStack stack, Action action) {
                    return PigmentStack.EMPTY;
                }

                @Override
                public PigmentStack extractChemical(int tank, long amount, Action action) {
                    return new PigmentStack(pigment,amount);
                }
            };
        }
    }
    public enum MatterType{
        ITEM,
        FLUID,
        GAS,
        INFUSE,
        SLURRY,
        PIGMENT
    }
}
