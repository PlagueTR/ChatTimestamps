package space.plague.plagueschattimestamps;

import space.plague.plagueschattimestamps.config.ModConfig;
import space.plague.plagueschattimestamps.config.ModConfigManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main {

    public static final String MOD_ID = "plagueschattimestamps";
    public static final String MOD_NAME = "Plague Chat Timestamps";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final String repl = "\u200B";

    public static final Pattern p = Pattern.compile("(§[0-9a-fk-or])");

    public static String []formats;

    public static SimpleDateFormat sdf;

    public static void init() {
        LOGGER.info("[" + MOD_NAME + "] Loading...");

        ModConfigManager.initializeConfig();
        SetFormat(ModConfigManager.getConfig().getTimestampFormat());

        LOGGER.info("[" + MOD_NAME + "] All done!");
    }

    public static ModConfig getConfig() {
        return ModConfigManager.getConfig();
    }

    public static void saveConfig() {
        ModConfigManager.save();
    }

    public static void SetFormat(String timestampFormat) {

        LOGGER.info("[" + MOD_NAME + "] Setting format: " + timestampFormat + "...");

        String format = timestampFormat;
        format = format.replaceAll("&([0-9a-fk-or])", "§$1");

        List<String> matches = new ArrayList<>();

        Matcher m = p.matcher(format);
        while (m.find()) { matches.add(m.group()); }

        formats = matches.toArray(new String[0]);

        sdf = new SimpleDateFormat(format.replaceAll(p.pattern(), repl));

        try {
            sdf.format(System.currentTimeMillis());
        }
        catch (NullPointerException ex) {
            LOGGER.warn("[" + MOD_NAME + "] Format is null, using default format.");
            SetFormat(new ModConfig().getTimestampFormat());
        }
        catch (IllegalArgumentException ex) {
            LOGGER.warn("[" + MOD_NAME + "] Format is not parseable, using default format.");
            SetFormat(new ModConfig().getTimestampFormat());
        }
    }

    public static String getFormattedTimestamp() {
        Timestamp tstamp = new Timestamp(System.currentTimeMillis());

        String tformat = sdf.format(tstamp);

        int i = 0;
        while(tformat.contains(repl) && i < formats.length) {
            tformat = tformat.replaceFirst(repl, formats[i++]);
        }

        return tformat;
    }

}
