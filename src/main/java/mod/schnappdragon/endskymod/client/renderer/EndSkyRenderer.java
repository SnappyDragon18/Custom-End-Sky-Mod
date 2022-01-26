package mod.schnappdragon.endskymod.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import mod.schnappdragon.endskymod.core.EndSkyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EndSkyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EndSkyRenderer {
    private static final NewEndSky NEW_END_SKY = new NewEndSky();

    @SubscribeEvent
    public static void replaceEndSky(WorldEvent.Load event) {
        Level world = (Level) event.getWorld();
        if (world.isClientSide() && world.dimension() == Level.END)
            ((ClientLevel) world).effects().setSkyRenderHandler(NEW_END_SKY);
    }

    static class NewEndSky implements ISkyRenderHandler {
        @Override
        public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(EndSkyModShaders::getRendertypeEndSkyShader);
            RenderSystem.setShaderTexture(0, TheEndPortalRenderer.END_SKY_LOCATION);
            RenderSystem.setShaderTexture(1, TheEndPortalRenderer.END_PORTAL_LOCATION);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();

            for (int i = 0; i < 6; ++i) {
                matrixStack.pushPose();
                switch (i) {
                    case 1 -> matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                    case 2 -> matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
                    case 3 -> matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
                    case 4 -> matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                    case 5 -> matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
                }

                Matrix4f matrix4f = matrixStack.last().pose();
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
                bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
                tesselator.end();
                matrixStack.popPose();
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
        }
    }
}