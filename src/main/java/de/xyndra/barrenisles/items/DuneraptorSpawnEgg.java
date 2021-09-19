package de.xyndra.barrenisles.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class DuneraptorSpawnEgg extends SpawnEggItem {

    static final List<SpawnEggItem> UNADDED_EGGS = new ArrayList<SpawnEggItem>();
    final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public DuneraptorSpawnEgg(final RegistryObject<? extends EntityType<?>> entityType, int color1, int color2, Properties properties) {
        super(null, color1, color2, properties);
        this.entityTypeSupplier = Lazy.of(entityType::get);
        UNADDED_EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(CompoundNBT nbt) {
        return this.entityTypeSupplier.get();
    }
}
