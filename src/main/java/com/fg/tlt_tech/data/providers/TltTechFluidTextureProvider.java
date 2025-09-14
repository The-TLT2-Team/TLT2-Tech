package com.fg.tlt_tech.data.providers;

import com.fg.tlt_tech.TltTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;


public class TltTechFluidTextureProvider extends AbstractFluidTextureProvider {
    public TltTechFluidTextureProvider(PackOutput packOutput) {
        super(packOutput, TltTech.MODID);
    }
    @Override
    public void addTextures() {
    }

    public FluidTexture.Builder commonFluid(FluidType fluid) {
        return super.texture(fluid).textures(TltTech.getResource("block/fluid/" + ForgeRegistries.FLUID_TYPES.get().getKey(fluid).getPath() + "/"), false, false);
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Fluid Texture Provider";
    }
}
