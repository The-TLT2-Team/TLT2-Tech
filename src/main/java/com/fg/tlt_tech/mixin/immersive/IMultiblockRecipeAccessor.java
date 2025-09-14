package com.fg.tlt_tech.mixin.immersive;

import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import net.minecraftforge.common.util.Lazy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MultiblockRecipe.class,remap = false)
public interface IMultiblockRecipeAccessor {
    @Accessor("totalProcessEnergy")
    void setTotalProcessEnergy(Lazy<Integer> lazy);
    @Accessor("totalProcessTime")
    void setTotalProcessTime(Lazy<Integer> lazy);
}
