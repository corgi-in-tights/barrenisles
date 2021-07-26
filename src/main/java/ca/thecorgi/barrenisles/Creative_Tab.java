package ca.thecorgi.barrenisles;

import ca.thecorgi.barrenisles.items.DuneraptorFeather;
import ca.thecorgi.barrenisles.setup.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Creative_Tab extends ItemGroup {

    public Creative_Tab() {
        super("barrenisles");
    }

    @Override
    public ItemStack makeIcon() {
        return ModItems.DUNERAPTORFEATHER.get().getDefaultInstance();
    }
}
