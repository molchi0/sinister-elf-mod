package net.sinisterelf.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sinisterelf.registry.ModEntities;

public class SinisterElfClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SINISTER_ELF, ElfRenderer::new);
    }
}
