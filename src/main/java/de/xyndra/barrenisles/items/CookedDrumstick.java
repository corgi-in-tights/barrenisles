package de.xyndra.barrenisles.items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class CookedDrumstick extends Item {

    public static final Food FOOD_PROPERTIE = (new Food.Builder()).nutrition(5).saturationMod(6.9f).meat().build();
    public CookedDrumstick(Properties p_i48487_1_) {
        super(p_i48487_1_.food(FOOD_PROPERTIE));
    }

}
