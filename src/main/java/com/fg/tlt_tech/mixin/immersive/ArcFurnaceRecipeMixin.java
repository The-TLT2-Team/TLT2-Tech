package com.fg.tlt_tech.mixin.immersive;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArcFurnaceRecipe.class,remap = false)
public abstract class ArcFurnaceRecipeMixin extends MultiblockRecipe {
    protected <T extends Recipe<?>> ArcFurnaceRecipeMixin(Lazy<ItemStack> outputDummy, IERecipeTypes.TypeWithClass<T> type, ResourceLocation id) {
        super(outputDummy, type, id);
    }

    @Inject(at = @At("RETURN"),method = "findRecipe",cancellable = true)
    private static void modifyRecipe(Level level, ItemStack input, NonNullList<ItemStack> additives, CallbackInfoReturnable<ArcFurnaceRecipe> cir){
        IMultiblockRecipeAccessor recipe =(IMultiblockRecipeAccessor) cir.getReturnValue();
        recipe.setTotalProcessTime(()->2);
        recipe.setTotalProcessEnergy(()->2000);
        cir.setReturnValue((ArcFurnaceRecipe) recipe);
    }
}
