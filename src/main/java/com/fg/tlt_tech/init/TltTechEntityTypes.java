package com.fg.tlt_tech.init;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.content.entity.EarthFlyingSword;
import com.fg.tlt_tech.content.entity.*;
import com.fg.tlt_tech.content.entity.base.BasicFlyingSwordEntity;
import mekanism.common.registration.impl.EntityTypeDeferredRegister;
import mekanism.common.registration.impl.EntityTypeRegistryObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TltTechEntityTypes {
    public static final EntityTypeDeferredRegister ENTITY_TYPES =new EntityTypeDeferredRegister(TltTech.MODID);
    public static List<EntityTypeRegistryObject<? extends BasicFlyingSwordEntity>> LIST_ELEMENTAL_SWORDS = new ArrayList<>();
    public static EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> registerElementalSword(String id, EntityType.Builder<? extends BasicFlyingSwordEntity> builder){
        var object = ENTITY_TYPES.register(id,builder);
        LIST_ELEMENTAL_SWORDS.add(object);
        return object;
    }

    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> ICEY_SWORD = registerElementalSword("icey_sword",
            EntityType.Builder.<IceyFlyingSword>of(IceyFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new IceyFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> LIFE_SWORD = registerElementalSword("life_sword",
            EntityType.Builder.<LifeFlyingSword>of(LifeFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new LifeFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> EARTH_SWORD = registerElementalSword("earth_sword",
            EntityType.Builder.<EarthFlyingSword>of(EarthFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new EarthFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> FIERY_SWORD = registerElementalSword("fiery_sword",
            EntityType.Builder.<FieryFlyingSword>of(FieryFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new FieryFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> IONIC_SWORD = registerElementalSword("ionic_sword",
            EntityType.Builder.<IonicFlyingSword>of(IonicFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new IonicFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> LIGHTNING_SWORD = registerElementalSword("lightning_sword",
            EntityType.Builder.<LightningSwordEntity>of(LightningSwordEntity::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new LightningSwordEntity(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<? extends BasicFlyingSwordEntity> OCEANIC_SWORD = registerElementalSword("oceanic_sword",
            EntityType.Builder.<OceanicFlyingSword>of(OceanicFlyingSword::new, MobCategory.MISC)
                    .sized(0.5f,0.5f)
                    .setCustomClientFactory(((spawnEntity, level) -> new OceanicFlyingSword(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final EntityTypeRegistryObject<IonicExplosionEntity> IONIC_EXPLOSION = ENTITY_TYPES.register("ionic_explosion",
            EntityType.Builder.<IonicExplosionEntity>of(IonicExplosionEntity::new, MobCategory.MISC)
                    .sized(1f,1f)
                    .setCustomClientFactory(((spawnEntity, level) -> new IonicExplosionEntity(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
}
