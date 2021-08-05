package de.xyndra.barrenisles.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Coconut_open extends Item {

    public static final Food FOOD_PROPERTIE = (new Food.Builder()).nutrition(4).saturationMod(10).effect(new EffectInstance(Effects.REGENERATION, 100, 0), 1F).alwaysEat().build();
    public Coconut_open(Properties p_i48487_1_) {
        super(p_i48487_1_.food(FOOD_PROPERTIE));
    }

}
