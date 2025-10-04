package com.fg.tlt_tech.init;

import com.fg.tlt_tech.content.tool.modifier.*;
import com.fg.tlt_tech.content.tool.modifier.botania.MagicalOres;
import com.fg.tlt_tech.content.tool.modifier.botania.OreCapture;
import com.fg.tlt_tech.content.tool.modifier.botania.OreResonance;
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
    public static final StaticModifier<IndustrialBorn> INDUSTRIAL_BORN = MODIFIERS.register("industrial_born",IndustrialBorn::new);
    public static final StaticModifier<ElementalSlashModifier> ELEMENTAL_SLASH = MODIFIERS.register("elemental_slash",ElementalSlashModifier::new);
    public static final StaticModifier<HyperFluxLoaded> HYPER_FLUX_LOADED = MODIFIERS.register("hyper_flux_loaded",HyperFluxLoaded::new);
    public static final StaticModifier<HyperEnergyShielding> HYPPER_ENERGY_SHIELDING = MODIFIERS.register("hyper_energy_shielding", HyperEnergyShielding::new);
    public static final StaticModifier<EnergyShielding> ENERGY_SHIELDING = MODIFIERS.register("energy_shielding", EnergyShielding::new);
    public static final StaticModifier<SupremeFluxLoaded> SUPREME_FLUX_LOADED = MODIFIERS.register("supreme_flux_loaded", SupremeFluxLoaded::new);
    public static final StaticModifier<BypassEffect> REVERSE_POLARITY = MODIFIERS.register("reverse_polarity", BypassEffect::new);
    public static final StaticModifier<MagicalOres> MAGICAL_ORES = MODIFIERS.register("magical_ores", MagicalOres::new);
    public static final StaticModifier<OreCapture> ORE_CAPTURE = MODIFIERS.register("ore_capture", OreCapture::new);
    public static final StaticModifier<OreResonance> ORE_RESONANCE = MODIFIERS.register("ore_resonance", OreResonance::new);
    public static final StaticModifier<FluxBlocking> FLUX_BLOCKING = MODIFIERS.register("flux_blocking", FluxBlocking::new);
    public static final StaticModifier<FluxSlashModifier> FLUX_SLASH = MODIFIERS.register("flux_slash", FluxSlashModifier::new);
    public static final StaticModifier<FluxInfinity> FLUX_INFINITY = MODIFIERS.register("flux_infinity", FluxInfinity::new);
    public static final StaticModifier<AlfBless> ALF_BLESS = MODIFIERS.register("alf_bless", AlfBless::new);
    public static final StaticModifier<MoltenCore> MOLTEN_CORE = MODIFIERS.register("molten_core", MoltenCore::new);

}
