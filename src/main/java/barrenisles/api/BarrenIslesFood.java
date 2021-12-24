package barrenisles.api;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class BarrenIslesFood {
    public static final Food DRUMSTICK_C = (new Food.Builder())
            .nutrition(2)
            .saturationMod(0.9F)
            .effect(() -> new EffectInstance(Effects.HUNGER, 500, 0), 0.3F).build();

    public static final Food COOKED_DRUMSTICK_C = (new Food.Builder())
            .nutrition(5)
            .saturationMod(6.9F).build();

    public static final Food SUSPICIOUS_BERRIES_C = (new Food.Builder())
            .nutrition(2)
            .saturationMod(1.5F)
            .effect(() -> new EffectInstance(Effects.BLINDNESS, 300, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.POISON, 300, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.GLOWING, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.DIG_SPEED, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.HUNGER, 300, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.LUCK, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 400, 0), 0.065F)
            .effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 400, 0), 0.065F).build();
}