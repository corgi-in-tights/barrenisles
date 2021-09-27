package barrenisles.init;

import barrenisles.api.items.BarrenIslesItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModGroups {
	public static final ItemGroup BARREN_ISLES = new ItemGroup("barren_isles_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BarrenIslesItems.dune_feather.get());
        }
    };
}
