package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.data.enums.EnumModifier;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;

import java.util.Arrays;

public class TiAcModifierProvider extends AbstractModifierProvider {
    public TiAcModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        Arrays.stream(EnumModifier.values()).toList().forEach((enumModifier -> {
            buildModifier(enumModifier.id,enumModifier.condition).addModules(enumModifier.modules).tooltipDisplay(enumModifier.tooltipDisplay);
        }));
    }

    @Override
    public String getName() {
        return "TLT Tech Modifier Provider";
    }
}
