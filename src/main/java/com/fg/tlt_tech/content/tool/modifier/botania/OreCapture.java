package com.fg.tlt_tech.content.tool.modifier.botania;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.OrechidRecipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OreCapture extends EtSTBaseModifier implements ProcessLootModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Level level = context.getLevel();
        List<OrechidRecipe> recipes = level.getRecipeManager().getAllRecipesFor(BotaniaRecipeTypes.ORECHID_TYPE);
        recipes = new ArrayList<>(recipes);
        recipes.sort(Comparator.comparingInt(OrechidRecipe::getWeight));
        var user = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (! (user instanceof Player player)) return;
        for (int i = 0; i < generatedLoot.size(); i++) {
            if (RANDOM.nextInt(20)<modifier.getLevel()){
                if (ManaItemHandler.instance().requestManaExact(((ToolStack)tool).createStack(),player,100,true)) {
                    int i1 = (int) (RANDOM.nextFloat()* (recipes.size() - 1));
                    List<ItemStack> outputs = recipes.get(i1).getOutput().getDisplayedStacks();
                    if (!outputs.isEmpty()) {
                        ItemStack stack = outputs.get(0);
                        stack.setCount(modifier.getLevel());
                        generatedLoot.add(stack);
                    }
                }
            }
        }
    }
}
