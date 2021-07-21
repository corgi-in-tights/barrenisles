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

        @ConfigEntry.Gui.CollapsibleObject
        public Generation generation = new Generation();

        public static class Spawning {
            public boolean spawn_duneraptors = true;
            public boolean spawn_tumbleweeds = false;
        }

        public static class Stats {
            public double duneraptor_health = 25;
            public double duneraptor_speed = 0.18;
            public double tumbleweed_health = 3;
            public double tumbleweed_speed = 0.21;
        }

        public static class Generation {
            public boolean generate_oasis = true;
            public boolean generate_ore_rock = true;
            public boolean generate_tall_ore_rock = true;
            public boolean generate_badlands_temple = true;
        }
}
