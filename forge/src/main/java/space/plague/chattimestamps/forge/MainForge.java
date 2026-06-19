package space.plague.chattimestamps.forge;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;

@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge(FMLJavaModLoadingContext context) {

        if (ModList.get().isLoaded("cloth_config")) {
            MinecraftForge.registerConfigScreen(
                    (mc, parentScreen) -> GeneralOptionsScreen.getConfigBuilder().build()
            );
        }

        // Run our common setup.
        Main.init();
    }

}
