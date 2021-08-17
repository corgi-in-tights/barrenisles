package ca.thecorgi.barrenisles.utils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static ca.thecorgi.barrenisles.BarrenIsles.ModID;

@Config(name = ModID)
public class BarrenIslesConfig implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject
        public Spawning spawn = new Spawning();

        @ConfigEntry.Gui.CollapsibleObject
        public Stats stats = new Stats();

        @ConfigEntry.Gui.RequiresRestart
        public Generation generation = new Generation();

        @ConfigEntry.Gui.CollapsibleObject
        public Client client = new Client();

        public static class Spawning {
            public boolean spawn_duneraptors = true;
            public boolean spawn_coyotes = false;
            public boolean spawn_tumbleweeds = false;
        }

        public static class Stats {
            public double duneraptor_health = 25;
            public double duneraptor_speed = 0.18;
            public double tumbleweed_health = 3;
            public double tumbleweed_speed = 0.21;
            public double coyote_health = 25;
            public double coyote_speed = 0.33;
            public double coyote_attack_damage = 3.5;
        }

        public static class Generation {
            public boolean generate_oasis = true;
            public boolean generate_ore_rock = true;
            public boolean generate_tall_ore_rock = true;
            public boolean generate_badlands_temple = true;
        }

        public static class Client {
            public boolean stop_experimental_advice = true;
        }
}
