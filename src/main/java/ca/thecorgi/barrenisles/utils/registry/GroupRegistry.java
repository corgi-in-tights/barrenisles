package ca.thecorgi.barrenisles.utils.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class GroupRegistry {
    public static final ItemGroup BARREN_ISLES = FabricItemGroupBuilder.create(
            new Identifier("barrenisles", "main_group"))
            .icon(() -> new ItemStack(ItemRegistry.DUNE_FEATHER))
            .build();
}
