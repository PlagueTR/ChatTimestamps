package space.plague.chattimestamps.forge;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;

@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge() {

        if (ModList.get().isLoaded("cloth-config")) {
            ModLoadingContext.get().registerExtensionPoint(
                    ExtensionPoint.CONFIGGUIFACTORY,
                    () -> (minecraft, parent) -> GeneralOptionsScreen.getConfigBuilder().build());
        }

        // Run our common setup.
        Main.init();
    }

}
