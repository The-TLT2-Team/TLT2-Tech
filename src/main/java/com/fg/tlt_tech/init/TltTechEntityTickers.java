package com.fg.tlt_tech.init;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.etstlib.content.register.EtSTLibRegistries;
import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.content.entityTicker.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TltTechEntityTickers {
    public static final DeferredRegister<EntityTicker> ENTITY_TICKERS = DeferredRegister.create(EtSTLibRegistries.ENTITY_TICKER, TltTech.MODID);

    public static final RegistryObject<EntityTicker> CHILLING = ENTITY_TICKERS.register("chilling", ChillingTicker::new);
    public static final RegistryObject<EntityTicker> CHILLING_CD = ENTITY_TICKERS.register("chilling_cooldown", EmptyTicker::new);
    public static final RegistryObject<EntityTicker> FIERY = ENTITY_TICKERS.register("fiery", FieryTicker::new);

}
