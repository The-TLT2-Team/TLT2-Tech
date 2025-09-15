package com.fg.tlt_tech.init;

import com.fg.tlt_tech.content.tool.modifier.*;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.fg.tlt_tech.TltTech.MODID;

public class TltTechModifiers {
    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MODID);

    public static final StaticModifier<ElementalSword> ELEMENTAL_SWORD = MODIFIERS.register("elemental_sword",ElementalSword::new);
    public static final StaticModifier<AutomaticTool> AUTOMATIC_TOOL = MODIFIERS.register("automatic_tool",AutomaticTool::new);
    public static final StaticModifier<PneumaticTool> PNEUMATIC_TOOL = MODIFIERS.register("pneumatic_tool",PneumaticTool::new);
    public static final StaticModifier<SharpEdgeDischarge> EDGE_DISCHARGE = MODIFIERS.register("edge_discharge",SharpEdgeDischarge::new);
    public static final StaticModifier<Flying> FLYING = MODIFIERS.register("flying",Flying::new);
    public static final StaticModifier<OverPressure> OVER_PRESSURE = MODIFIERS.register("over_pressure",OverPressure::new);

}
