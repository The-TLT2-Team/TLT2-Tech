package com.fg.tlt_tech.content.entity;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import com.fg.tlt_tech.init.TltTechEntityTypes;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EarthFlyingSword extends BasicFlyingSwordEntity {
    public EarthFlyingSword(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, TltTechItems.EARTH_SWORD.get());
    }
    public EarthFlyingSword(Level pLevel) {
        this(TltTechEntityTypes.EARTH_SWORD.get(), pLevel);
    }

    @Override
    public LegacyDamageSource processDamageSource(LegacyDamageSource source) {
        return super.processDamageSource(source).setBypassArmor().setBypassEnchantment().setBypassInvul().setBypassMagic().setBypassShield();
    }
}
