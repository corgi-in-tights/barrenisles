package ca.thecorgi.barrenisles.food;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class BarrenIslesFoodComponents {
    public static final FoodComponent DRUMSTICK_C = (new FoodComponent.Builder())
            .hunger(2)
            .saturationModifier(0.9F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 500, 0), 0.3F).build();

    public static final FoodComponent COOKED_DRUMSTICK_C = (new FoodComponent.Builder())
            .hunger(5)
            .saturationModifier(6.9F).build();
}