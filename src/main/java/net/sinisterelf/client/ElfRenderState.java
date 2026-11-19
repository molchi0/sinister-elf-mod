package net.sinisterelf.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/** Состояние для отрисовки: целится ли эльф (поднимает арбалет к плечам). */
public class ElfRenderState extends LivingEntityRenderState {
    public boolean aggressive;
}
