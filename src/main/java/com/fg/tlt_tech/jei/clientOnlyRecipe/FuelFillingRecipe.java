package com.fg.tlt_tech.jei.clientOnlyRecipe;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechItems;
import com.google.gson.JsonObject;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.chemical.ItemStackToChemicalRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismGases;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class FuelFillingRecipe extends ItemStackToChemicalRecipe<Gas, GasStack> {

    public FuelFillingRecipe( Item item, int outPut){
        super(TltTech.getResource("fuel_filling_"+outPut), IngredientCreatorAccess.item().from(item), MekanismGases.FISSILE_FUEL.getStack(outPut));
    }

    public static final FuelFillingRecipe EMPTY = new FuelFillingRecipe(TltTechItems.FU_DUST.get(),500);

    @Override
    public void write(FriendlyByteBuf buffer) {

    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.getInstance();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isIncomplete() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<FuelFillingRecipe>{

        public static Serializer getInstance(){
            return new Serializer();
        }

        @Override
        public FuelFillingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return EMPTY;
        }

        @Override
        public @Nullable FuelFillingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return null;
        }
        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FuelFillingRecipe pRecipe) {
        }
    }
    public static class Type implements RecipeType<FuelFillingRecipe>{
        public static final Type INSTANCE = new Type();

        public static final String ID = "dry_state_fuel_filling";
    }
}
