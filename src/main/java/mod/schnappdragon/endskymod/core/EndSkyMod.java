package mod.schnappdragon.endskymod.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(EndSkyMod.MODID)
public class EndSkyMod {
    public static final String MODID = "endskymod";

    public EndSkyMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}