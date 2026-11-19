package net.sinisterelf.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.sinisterelf.SinisterElfMod;
import net.sinisterelf.entity.SinisterElf;

public final class ModEntities {
    private ModEntities() {}

    public static final EntityType<SinisterElf> SINISTER_ELF = register(
            "sinister_elf",
            EntityType.Builder.<SinisterElf>of(SinisterElf::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .clientTrackingRange(8)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, SinisterElfMod.id(name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    /** Вызов нужен, чтобы статические поля инициализировались в нужный момент. */
    public static void init() {}
}
