package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.data.enums.EnumMaterial;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class TiAcMaterialProvider extends AbstractMaterialDataProvider {
    public TiAcMaterialProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addMaterials() {
        Logger logger = LogUtils.getLogger();
        for (EnumMaterial material:EnumMaterial.values()){
            //logger.info("Now generating: {}", material.id);
            addMaterial(material.id,material.tier,8, material.craftable, material.hidden, material.condition);
        }
    }

    @Override
    public String getName() {
        return "TLT Tech Material Data Provider";
    }
}
