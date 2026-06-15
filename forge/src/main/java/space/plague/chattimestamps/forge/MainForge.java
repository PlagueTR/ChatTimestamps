package space.plague.chattimestamps.forge;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmlclient.ConfigGuiHandler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.gui.GeneralOptionsScreen;

import java.util.function.BiFunction;

@Mod(Main.MOD_ID)
public final class MainForge {
    public MainForge() {

        if (ModList.get().isLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigGuiHandler.ConfigGuiFactory.class,
                    () -> new ConfigGuiHandler.ConfigGuiFactory(new BiFunction<MinecraftClient, Screen, Screen>() {
                        @Override
                        public Screen apply(MinecraftClient minecraft, Screen parent) {
                            return GeneralOptionsScreen.getConfigBuilder().build();
                        }
                    })
            );
        }

        // Run our common setup.
        Main.init();
    }

}
