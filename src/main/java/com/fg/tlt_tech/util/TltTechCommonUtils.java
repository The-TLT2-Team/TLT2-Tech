package com.fg.tlt_tech.util;

import com.fg.tltmod.util.mixin.ILivingEntityMixin;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;

public class TltTechCommonUtils {
    public static boolean trueHurt(LivingEntity living, DamageSource source, float amount){
        if (ModList.get().isLoaded("tltmod")){
            return  ((ILivingEntityMixin)living).tltmod$hurt(source,amount);
        }
        return false;
    }
}
