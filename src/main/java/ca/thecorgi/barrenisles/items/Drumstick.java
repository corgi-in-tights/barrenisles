package ca.thecorgi.barrenisles.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Drumstick extends Item {

    public static final Food FOOD_PROPERTIE = (new Food.Builder()).nutrition(2).saturationMod(0.9f).effect(new EffectInstance(Effects.HUNGER, 500, 0), 0.3F).meat().build();
    public Drumstick(Properties p_i48487_1_) {
        super(p_i48487_1_.food(FOOD_PROPERTIE));
    }

}
