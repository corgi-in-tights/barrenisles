package de.xyndra.barrenisles.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SUS_BERRY extends BlockNamedItem {

    public static final Food FOOD_PROPERTIE = (new Food.Builder()).nutrition(5).saturationMod(6.9f).meat()
            .effect(new EffectInstance(Effects.BLINDNESS, 300, 0), 0.065F)
            .effect(new EffectInstance(Effects.POISON, 300, 0), 0.065F)
            .effect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.GLOWING, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.DIG_SPEED, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.HUNGER, 300, 0), 0.065F)
            .effect(new EffectInstance(Effects.LUCK, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.NIGHT_VISION, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.FIRE_RESISTANCE, 400, 0), 0.065F)
            .effect(new EffectInstance(Effects.MOVEMENT_SPEED, 400, 0), 0.065F).build();

    public SUS_BERRY(Block block, Properties p_i48487_1_) {
        super(block, p_i48487_1_.food(FOOD_PROPERTIE));
    }

}
