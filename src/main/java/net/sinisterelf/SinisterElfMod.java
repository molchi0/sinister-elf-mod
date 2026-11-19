package net.sinisterelf;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.Identifier;
import net.sinisterelf.entity.SinisterElf;
import net.sinisterelf.registry.ModEntities;
import net.sinisterelf.registry.ModItems;

public class SinisterElfMod implements ModInitializer {
    public static final String MOD_ID = "sinister_elf";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        // Порядок важен: предметы ссылаются на сущности.
        ModEntities.init();
        ModItems.init();

        FabricDefaultAttributeRegistry.register(ModEntities.SINISTER_ELF, SinisterElf.createAttributes());

        // Сам по себе зловещий эльф не появляется: нигде нет спавна по биомам.
        // Пока только по команде /summon или из яйца призыва.
    }
}
