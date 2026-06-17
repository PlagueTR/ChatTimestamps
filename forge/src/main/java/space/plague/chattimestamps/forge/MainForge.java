package space.plague.chattimestamps.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;


@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge() {

        if (ModList.get().isLoaded("cloth_config")) {
            MinecraftForge.registerConfigScreen(screen -> {
                    return GeneralOptionsScreen.getConfigBuilder().build();
                }
            );
        }

        // Run our common setup.
        Main.init();
    }

}
