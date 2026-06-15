package space.plague.chattimestamps.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.PlatformDependent;

import java.io.*;

public class ModConfigManager {

    private static File configFile;
    private static ModConfig config;
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .create();

    private static void prepareConfigFile() {
        if (ModConfigManager.configFile == null) {
            ModConfigManager.configFile = new File(PlatformDependent.getConfigDirectory().toString(), "plagueschattimestamps.json");
        }
    }

    public static ModConfig initializeConfig() {
        if (ModConfigManager.config != null) {
            return ModConfigManager.config;
        }
        ModConfigManager.config = new ModConfig();
        load();
        return ModConfigManager.config;
    }

    public static void save() {
        prepareConfigFile();
        final String jsonString = gson.toJson(ModConfigManager.config);
        try {
            final FileWriter fileWriter = new FileWriter(ModConfigManager.configFile);
            try {
                fileWriter.write(jsonString);
                fileWriter.close();
            }
            catch (Throwable t) {
                try {
                    fileWriter.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        }
        catch (IOException e) {
            Main.LOGGER.warn("[" + Main.MOD_NAME + "] Couldn't save config file!", e);
        }
    }

    private static void load() {
        prepareConfigFile();
        try {
            if (!ModConfigManager.configFile.exists()) {
                save();
            }
            else{
                final BufferedReader br = new BufferedReader(new FileReader(ModConfigManager.configFile));
                final ModConfig parsed = gson.fromJson(br, ModConfig.class);
                if (parsed != null) {
                    ModConfigManager.config = parsed;
                }
            }
        }
        catch (FileNotFoundException e) {
            Main.LOGGER.warn("[" + Main.MOD_NAME + "] Couldn't load config file!", e);
        }
    }

    public static ModConfig getConfig() {
        if (ModConfigManager.config == null) {
            ModConfigManager.config = new ModConfig();
        }
        return ModConfigManager.config;
    }

}
