package com.fg.tlt_tech.init;

import com.fg.tlt_tech.content.tool.modifier.AutomaticTool;
import com.fg.tlt_tech.content.tool.modifier.ElementalSword;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.fg.tlt_tech.TltTech.MODID;

public class TltTechModifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MODID);

    public static final StaticModifier<ElementalSword> ELEMENTAL_SWORD = MODIFIERS.register("elemental_sword",ElementalSword::new);
    public static final StaticModifier<AutomaticTool> AUTOMATIC_TOOL = MODIFIERS.register("automatic_tool",AutomaticTool::new);
}
