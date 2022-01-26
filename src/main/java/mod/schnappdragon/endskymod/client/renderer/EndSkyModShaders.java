package mod.schnappdragon.endskymod.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.schnappdragon.endskymod.core.EndSkyMod;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = EndSkyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EndSkyModShaders {
    private static ShaderInstance rendertypeEndSky;
    private static final RenderStateShard.ShaderStateShard RENDERTYPE_END_SKY_SHADER = new RenderStateShard.ShaderStateShard(() -> rendertypeEndSky);

    @SubscribeEvent
    public static void registerShader(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceManager(), new ResourceLocation(EndSkyMod.MODID, "rendertype_end_sky"), DefaultVertexFormat.POSITION), shaderInstance -> {
            EndSkyModShaders.rendertypeEndSky = shaderInstance;
        });
    }

    public static ShaderInstance getRendertypeEndSkyShader() {
        return rendertypeEndSky;
    }
}