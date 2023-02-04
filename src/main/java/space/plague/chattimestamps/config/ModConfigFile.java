package space.plague.chattimestamps.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;

import space.plague.chattimestamps.Main;

import java.io.File;
import java.nio.file.Paths;

public class ModConfigFile {

    static final String configpath = "config/plagueschattimestamps.json";

    public static Runnable saveRunnable = () -> {

        FileConfig config = FileConfig.builder(Paths.get(configpath)).concurrent().autosave().build();

        Config general = Config.inMemory();

        general.set("disable_mod", GeneralOptions.disableMod);
        general.set("timestamp_format", GeneralOptions.timestampFormat);

        config.set("general", general);

        config.close();

        //update the format
        Main.SetFormat(GeneralOptions.timestampFormat);

    };

    public static void load() {

        File file = new File(configpath);

        if (!file.exists()){
            return;
        }

        FileConfig config = FileConfig.builder(file).concurrent().autosave().build();

        config.load();

        Config general = config.getOrElse("general", () -> null);

        if (general != null){

            GeneralOptions.disableMod = general.getOrElse("disable_mod", GeneralOptionsDefault.disableMod);
            GeneralOptions.timestampFormat = general.getOrElse("timestamp_format", GeneralOptionsDefault.timestampFormat);

        }

        config.close();

    }

}
