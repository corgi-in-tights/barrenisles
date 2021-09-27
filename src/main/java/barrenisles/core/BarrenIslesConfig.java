package barrenisles.core;

import net.minecraftforge.common.ForgeConfigSpec;

public final class BarrenIslesConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> spawn_duneraptors;
	public static final ForgeConfigSpec.ConfigValue<Boolean> spawn_coyotes;
	public static final ForgeConfigSpec.ConfigValue<Boolean> spawn_tumbleweeds;
	
	public static final ForgeConfigSpec.ConfigValue<Float> duneraptor_health;
	public static final ForgeConfigSpec.ConfigValue<Float> duneraptor_speed;
	public static final ForgeConfigSpec.ConfigValue<Float> tumbleweed_health;
	public static final ForgeConfigSpec.ConfigValue<Float> tumbleweed_speed;
	public static final ForgeConfigSpec.ConfigValue<Float> coyote_health;
	public static final ForgeConfigSpec.ConfigValue<Float> coyote_speed;
	public static final ForgeConfigSpec.ConfigValue<Float> coyote_attack_damage;
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> generate_oasis;
	public static final ForgeConfigSpec.ConfigValue<Boolean> generate_ore_rock;
	public static final ForgeConfigSpec.ConfigValue<Boolean> generate_tall_ore_rock;
	public static final ForgeConfigSpec.ConfigValue<Boolean> generate_badlands_temple;
	
	static {
		BUILDER.push("Config for BarrenIsles");
		
		spawn_duneraptors = BUILDER.comment("Will the duneraptors spawn? Default value is true").define("Spawn duneraptors", true);
		spawn_coyotes = BUILDER.comment("Will the coyotes spawn? Default value is true").define("Spawn coyotes", true);
		spawn_tumbleweeds = BUILDER.comment("Will the tumbleweeds spawn? Default value is true").define("Spawn tumbleweeds", true);
		
		duneraptor_health = BUILDER.comment("Default value is 25").define("Duneraptor health", 25.0F);
		duneraptor_speed = BUILDER.comment("Default value is 0.18").define("Duneraptor health", 0.18F);
		tumbleweed_health = BUILDER.comment("Default value is 3").define("Tumbleweed health", 3.0F);
		tumbleweed_speed = BUILDER.comment("Default value is 0.21").define("Tumbleweed speed", 0.21F);
		coyote_health = BUILDER.comment("Default value is 25").define("Coyote health", 25.0F);
		coyote_speed = BUILDER.comment("Default value is 0.33").define("Coyote speed", 0.33F);
		coyote_attack_damage = BUILDER.comment("Default value is 3.5").define("Coyote attack damage", 3.5F);
		
		generate_oasis = BUILDER.comment("Will generate oasis? Default value is true").define("Generate oasis", true);
		generate_ore_rock = BUILDER.comment("Will generate rock? Default value is true").define("Generate rock", true);
		generate_tall_ore_rock = BUILDER.comment("Will generate ore rock? Default value is true").define("Generate ore rock", true);
		generate_badlands_temple = BUILDER.comment("Will generate badlands temple? Default value is true").define("Generate badlands tample", true);
		
		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}