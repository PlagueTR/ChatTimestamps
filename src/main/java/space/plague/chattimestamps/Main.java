package space.plague.chattimestamps;

import space.plague.chattimestamps.config.ModConfig;
import space.plague.chattimestamps.config.ModConfigManager;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements ModInitializer {

    //mod logger
    public static final Logger LOGGER = LogManager.getLogger("plagueschattimestamps");

    //Character to replace each formatting code into (I chose zero width space character)
    public static final String repl = "\u200B";

    //regex pattern for Minecraft text formatting
    public static final Pattern p = Pattern.compile("(§[0-9a-fk-or])");

    //each occurrence of Minecraft text formatting
    public static String []formats;
    //timestamp formatting
    public static SimpleDateFormat sdf;

    @Override
    public void onInitialize() {

        //Log beginning of initialization
        LOGGER.info("[Plague's Chat Timestamps] Loading... ");

        //Load Config
        ModConfigManager.initializeConfig();
        SetFormat(ModConfigManager.getConfig().getTimestampFormat());

        //Log initialization success
        LOGGER.info("[Plague's Chat Timestamps] All done!");

    }

    public static ModConfig getConfig() {
        return ModConfigManager.getConfig();
    }

    public static void saveConfig() {
        ModConfigManager.save();
    }

    public static void SetFormat(String timestampFormat){

        //Log format change
        LOGGER.info("[Plague's Chat Timestamps] Setting format: " + timestampFormat + "...");

        //Turn &(modifier) into $minecraft_text_formatting_code$(modifier)
        String format = timestampFormat;
        format = format.replaceAll("&([0-9a-fk-or])", "§$1");

        //Store each occurrence of Minecraft text formatting
        List<String> matches = new ArrayList<>();

        Matcher m = p.matcher(format);
        while (m.find()) matches.add(m.group());

        formats = matches.toArray(new String[0]);

        //Construct given timestamp formatting
        sdf = new SimpleDateFormat(format.replaceAll(p.pattern(), repl));

        //Exception Handling - Check whether timestamp formatting is valid
        // if not, log appropriate warning and use default timestamp formatting
        try{
            sdf.format(System.currentTimeMillis());
        }catch(NullPointerException ex){
            LOGGER.warn("[Plague's Chat Timestamps] Format is null, using default format.");
            SetFormat(new ModConfig().getTimestampFormat());
        }
        catch(IllegalArgumentException ex){
            LOGGER.warn("[Plague's Chat Timestamps] Format is not parseable, using default format.");
            SetFormat(new ModConfig().getTimestampFormat());
        }

    }

    public static String getFormattedTimestamp(){

        //get current timestamp
        Timestamp tstamp = new Timestamp(System.currentTimeMillis());

        //get formatted timestamp string
        String tformat = sdf.format(tstamp);

        //add Minecraft text formatting
        // Had to do this here because SimpleDateFormat doesn't like some format modifiers (ex: &a, &d)
        int i = 0;
        while (tformat.contains(repl) && i < formats.length) {
            tformat = tformat.replaceFirst(repl, formats[i++]);
        }

        return tformat;

    }

}
