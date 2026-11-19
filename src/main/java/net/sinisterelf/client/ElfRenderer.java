package net.sinisterelf.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sinisterelf.SinisterElfMod;
import net.sinisterelf.entity.SinisterElf;

public class ElfRenderer extends MobRenderer<SinisterElf, ElfRenderState, ElfModel> {
    private static final Identifier TEXTURE = SinisterElfMod.id("textures/entity/sinister_elf.png");

    public ElfRenderer(EntityRendererProvider.Context context) {
        // Модель запекается напрямую, без регистрации слоя: так меньше зависимостей от API Fabric.
        super(context, new ElfModel(ElfModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ElfRenderState createRenderState() {
        return new ElfRenderState();
    }

    @Override
    public void extractRenderState(SinisterElf entity, ElfRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.aggressive = entity.isAggressive();
    }

    @Override
    public Identifier getTextureLocation(ElfRenderState state) {
        return TEXTURE;
    }
}
