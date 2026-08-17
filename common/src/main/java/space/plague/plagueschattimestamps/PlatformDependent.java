package space.plague.plagueschattimestamps;

import java.nio.file.Path;

public class PlatformDependent {

    @dev.architectury.injectables.annotations.ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

}
