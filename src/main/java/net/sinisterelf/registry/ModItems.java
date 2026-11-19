package net.sinisterelf.registry;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.sinisterelf.SinisterElfMod;

public final class ModItems {
    private ModItems() {}

    public static final Item SINISTER_ELF_SPAWN_EGG = register(
            "sinister_elf_spawn_egg", SpawnEggItem::new,
            new Item.Properties().spawnEgg(ModEntities.SINISTER_ELF));

    private static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, SinisterElfMod.id(name));
        return Registry.register(BuiltInRegistries.ITEM, key, factory.apply(properties.setId(key)));
    }

    public static void init() {}
}
