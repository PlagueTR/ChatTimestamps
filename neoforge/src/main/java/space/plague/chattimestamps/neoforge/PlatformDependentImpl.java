package space.plague.chattimestamps.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformDependentImpl {

    public static Path getConfigDirectory() { return FMLPaths.CONFIGDIR.get(); }

}
