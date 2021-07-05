package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class GroupRegistry {
    public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
            new Identifier("tutorial", "other"))
            .icon(() -> new ItemStack(Items.BOWL))
            .build();
}
