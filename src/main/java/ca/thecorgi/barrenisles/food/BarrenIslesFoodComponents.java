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

    public static final FoodComponent SUSPICIOUS_BERRIES_C = (new FoodComponent.Builder())
            .hunger(2)
            .saturationModifier(1.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 300, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400, 0), 0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, 0), 0.1F).build();
}