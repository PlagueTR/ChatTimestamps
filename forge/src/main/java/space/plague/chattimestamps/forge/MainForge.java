package space.plague.chattimestamps.forge;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;

@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge(FMLJavaModLoadingContext context) {

        if (ModList.get().isLoaded("cloth_config")) {
            context.registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parentScreen) ->
                            GeneralOptionsScreen.getConfigBuilder().build()
                    )
            );
        }

        // Run our common setup.
        Main.init();
    }

}
