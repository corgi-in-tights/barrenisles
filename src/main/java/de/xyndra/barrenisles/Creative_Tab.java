package de.xyndra.barrenisles;

import de.xyndra.barrenisles.setup.ModItems;
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
