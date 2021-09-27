package barrenisles.common.block;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

public class DesertDiscItem extends MusicDiscItem {
    public DesertDiscItem(int comparatorOutput, SoundEvent sound, Item.Properties settings) {
        super(comparatorOutput, () -> sound, settings);
    }
}