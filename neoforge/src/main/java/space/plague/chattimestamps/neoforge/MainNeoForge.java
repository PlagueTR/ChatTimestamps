package space.plague.chattimestamps.neoforge;

import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.client.ConfigScreenHandler;
import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;

@Mod(Main.MOD_ID)
public final class MainNeoForge {
    public MainNeoForge() {

        if (ModList.get().isLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (minecraftClient, screen) ->  GeneralOptionsScreen.getConfigBuilder().build()
                    )
            );
        }

        // Run our common setup.
        Main.init();
    }
}
