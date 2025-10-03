package com.fg.tlt_tech.init;

import cofh.core.util.helpers.AugmentDataHelper;
import cofh.thermal.lib.common.item.AugmentItem;
import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.content.tool.item.FluxArrowItem;
import com.fg.tlt_tech.content.tool.item.InfinityItem;
import me.desht.pneumaticcraft.api.upgrade.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import me.desht.pneumaticcraft.common.upgrades.ApplicableUpgradesDB;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static cofh.lib.util.constants.NBTTags.*;
import static com.fg.tlt_tech.util.Constants.TAG_MACHINE_PARALLEL;

public class TltTechItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TltTech.MODID);
    public static final DeferredRegister<Item> PROJECTILE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TltTech.MODID);
    public static List<RegistryObject<Item>> LIST_SIMPLE_MODEL = new ArrayList<>();

    public static RegistryObject<Item> registerSimpleModel(String name, Supplier<Item> supplier){
        RegistryObject<Item> object = ITEMS.register(name,supplier);
        LIST_SIMPLE_MODEL.add(object);
        return object;
    }

    public static final RegistryObject<Item> REACTIVE_NUCLEAR_WASTE = registerSimpleModel("reactive_nuclear_waste",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> FU_DUST = registerSimpleModel("fu_dust",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> CK_INDUCED_FU_DUST = registerSimpleModel("ck_indused_fu_dust",()->new Item(new Item.Properties()));

    public static final RegistryObject<Item> UPGRADE_AUGMENT_4 = ITEMS.register("upgrade_augment_pneumatic",()->new AugmentItem(new Item.Properties(), AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_UPGRADE)
            .mod(TAG_AUGMENT_RF_XFER,6)
            .mod(TAG_AUGMENT_BASE_MOD,6)
            .mod(TAG_MACHINE_PARALLEL,1)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,1.2f).build()));
    public static final RegistryObject<Item> UPGRADE_AUGMENT_5 = ITEMS.register("upgrade_augment_densium",()->new AugmentItem(new Item.Properties(), AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_UPGRADE)
            .mod(TAG_AUGMENT_RF_XFER,8)
            .mod(TAG_AUGMENT_BASE_MOD,8)
            .mod(TAG_MACHINE_PARALLEL,7)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,2)
            .mod(TAG_AUGMENT_DYNAMO_ENERGY,2).build()));
    public static final RegistryObject<Item> UPGRADE_AUGMENT_6 = ITEMS.register("upgrade_augment_activated_chroma",()->new AugmentItem(new Item.Properties(), AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_UPGRADE)
            .mod(TAG_AUGMENT_BASE_MOD,16)
            .mod(TAG_AUGMENT_RF_XFER,16)
            .mod(TAG_MACHINE_PARALLEL,15)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,4)
            .mod(TAG_AUGMENT_MACHINE_CATALYST,0.1f)
            .mod(TAG_AUGMENT_MACHINE_PRIMARY,1f)
            .mod(TAG_AUGMENT_MACHINE_SECONDARY,1f)
            .mod(TAG_AUGMENT_DYNAMO_ENERGY,4)
            .mod(TAG_AUGMENT_DYNAMO_POWER,4f).build()));
    public static final RegistryObject<Item> DYNAMO_UPGRADE_1 = registerSimpleModel("dynamo_upgrade_1",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_DYNAMO)
            .mod(TAG_AUGMENT_DYNAMO_ENERGY,2F)
            .mod(TAG_AUGMENT_DYNAMO_POWER,8)
            .mod(TAG_AUGMENT_DYNAMO_THROTTLE,1)
            .build()));
    public static final RegistryObject<Item> DYNAMO_UPGRADE_2 = registerSimpleModel("dynamo_upgrade_2",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_DYNAMO)
            .mod(TAG_AUGMENT_DYNAMO_ENERGY,4)
            .mod(TAG_AUGMENT_DYNAMO_POWER,32)
            .mod(TAG_AUGMENT_DYNAMO_THROTTLE,1).build()));
    public static final RegistryObject<Item> MACHINE_CATALYST_UPGRADE_1 = registerSimpleModel("machine_catalyst_upgrade_1",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_MACHINE)
            .mod(TAG_AUGMENT_MACHINE_CATALYST,0.5f)
            .mod(TAG_AUGMENT_MACHINE_PRIMARY,0.5f)
            .mod(TAG_AUGMENT_MACHINE_SECONDARY,0.5f)
            .build()));
    public static final RegistryObject<Item> MACHINE_CATALYST_UPGRADE_2 = registerSimpleModel("machine_catalyst_upgrade_2",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_MACHINE)
            .mod(TAG_AUGMENT_MACHINE_CATALYST,0.25f)
            .mod(TAG_AUGMENT_MACHINE_PRIMARY,2f)
            .mod(TAG_AUGMENT_MACHINE_SECONDARY,2f)
            .build()));
    public static final RegistryObject<Item> MACHINE_SPEED_UPGRADE_1 = registerSimpleModel("machine_speed_upgrade_1",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_MACHINE)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,1.2f)
            .mod(TAG_AUGMENT_MACHINE_SPEED,2.5f)
            .mod(TAG_MACHINE_PARALLEL,1)
            .build()));
    public static final RegistryObject<Item> MACHINE_SPEED_UPGRADE_2 = registerSimpleModel("machine_speed_upgrade_2",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_MACHINE)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,1.1f)
            .mod(TAG_AUGMENT_MACHINE_SPEED,7.5f)
            .mod(TAG_MACHINE_PARALLEL,4)
            .build()));
    public static final RegistryObject<Item> RANGE_UPGRADE_1 = registerSimpleModel("range_upgrade_1",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_AREA_EFFECT)
            .mod(TAG_AUGMENT_REACH,2)
            .build()));
    public static final RegistryObject<Item> RANGE_UPGRADE_2 = registerSimpleModel("range_upgrade_2",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_AREA_EFFECT)
            .mod(TAG_AUGMENT_REACH,4)
            .build()));
    public static final RegistryObject<Item> MACHINE_SPEED_UPGRADE_INF = registerSimpleModel("machine_speed_upgrade_inf",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_MACHINE)
            .mod(TAG_AUGMENT_MACHINE_ENERGY,0.01f)
            .mod(TAG_AUGMENT_MACHINE_SPEED,100f)
            .mod(TAG_MACHINE_PARALLEL,64)
            .mod(TAG_AUGMENT_MACHINE_CATALYST,0f)
            .mod(TAG_AUGMENT_MACHINE_PRIMARY,100f)
            .mod(TAG_AUGMENT_MACHINE_SECONDARY,100f)
            .build()));
    public static final RegistryObject<Item> DYNAMO_UPGRADE_INF = registerSimpleModel("dynamo_upgrade_inf",()->new AugmentItem(new Item.Properties(),AugmentDataHelper.builder()
            .type(TAG_AUGMENT_TYPE_DYNAMO)
            .mod(TAG_AUGMENT_DYNAMO_ENERGY,Integer.MAX_VALUE)
            .mod(TAG_AUGMENT_DYNAMO_POWER,131072)
            .mod(TAG_AUGMENT_DYNAMO_THROTTLE,1)
            .build()));
    public static final RegistryObject<Item> ADVANCED_SAFETY_UPGRADE = ITEMS.register("advanced_safety_upgrade",()->new UpgradeItem(PnCUpgrades.ADVANCED_SAFETY_UPGRADE,1));

    public static final RegistryObject<Item> INF_PROVIDER = registerSimpleModel("infinity_provider", InfinityItem::new);
    public static final RegistryObject<Item> FLUX_ARROW = ITEMS.register("flux_arrow", FluxArrowItem::new);


    public static class PnCUpgrades{
        public static final PNCUpgrade ADVANCED_SAFETY_UPGRADE = ApplicableUpgradesDB.getInstance().registerUpgrade(TltTech.getResource("advanced_safety_upgrade"),1,TltTech.MODID);
    }
    public static final RegistryObject<Item> ICEY_SWORD = PROJECTILE_ITEMS.register("icey_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> EARTH_SWORD = PROJECTILE_ITEMS.register("earth_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIERY_SWORD = PROJECTILE_ITEMS.register("fiery_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> IONIC_SWORD = PROJECTILE_ITEMS.register("ionic_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> LIFE_SWORD = PROJECTILE_ITEMS.register("life_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> LIGHTNING_SWORD = PROJECTILE_ITEMS.register("lightning_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> OCEANIC_SWORD = PROJECTILE_ITEMS.register("oceanic_sword",()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> ELEMENTAL_SLASH = PROJECTILE_ITEMS.register("elemental_slash",()->new Item(new Item.Properties()));
}
