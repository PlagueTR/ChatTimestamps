package space.plague.chattimestamps.config.gui;

import space.plague.chattimestamps.config.GeneralOptions;
import space.plague.chattimestamps.config.GeneralOptionsDefault;
import space.plague.chattimestamps.config.ModConfigFile;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class GeneralOptionsScreen {

    public static ConfigBuilder getConfigBuilder() {
        
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(MinecraftClient.getInstance().currentScreen)
                .setTitle(Text.of("Plague's Chat Timestamps - General"));

        builder.setSavingRunnable(ModConfigFile.saveRunnable);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));

        general.addEntry(entryBuilder.startBooleanToggle(Text.of("Disable Mod"), GeneralOptions.disableMod)
                .setDefaultValue(GeneralOptionsDefault.disableMod)
                .setTooltip(Text.of("Disables the mod. This will stop adding timestamps to chat."))
                .setSaveConsumer(newValue -> GeneralOptions.disableMod = newValue)
                .build());

        general.addEntry(entryBuilder.startStrField(Text.of("Timestamp Format"), GeneralOptions.timestampFormat)
                .setDefaultValue(GeneralOptionsDefault.timestampFormat)
                .setTooltip(Text.of("Set the formatting for the timestamp."))
                .setSaveConsumer(newValue -> GeneralOptions.timestampFormat = newValue)
                .build());

        String mc_formatting_info = """
                            &0 §0BLACK.§7            &1 §1DARK_BLUE.§7      &2 §2DARK_GREEN§7     &3 §3DARK_AQUA§7
                            &4 §4DARK_RED§7        &5 §5DARK_PURPLE.§7   &6 §6GOLD§7              &7 §7GRAY§7
                            &8 §8DARK_GRAY.§7      &9 §9BLUE§7              &a §AGREEN.§7            &b §BAQUA§7
                            &c §CRED.§7               &d §DLIGHT_PURPLE.§7  &e §EYELLOW§7           &f §FWHITE§7
                            &k §8§KOBFUSCATED§r§8.§7     &l §8§LBOLD§r§.§7             &m §8§MSTRIKETHROUGH§r§7 &n §8§NUNDERLINED§r§7
                            &o §8§OITALIC§r§7            &r §8RESET§7
                """;

        String ts_formatting_info = """
                            yy §8Last 2 digits of the year§7             yyyy §8Year§7
                            MM §8Numeric value of the month§7          MMM §8Shortened name of the month§7
                            MMMM §8Full name of the month§7             dd §8Day of the month§7
                            EEE §8Shortened name of day of the week§7
                            EEEE §8Full name of day of the week§7
                            HH §8Hour of the day in 24-hour format§7
                            hh §8Hour of the day in 12-hour format§7
                            a §8AM/PM marker.§7                            mm §8Minute of the hour§7
                            ss §8Second of the minute§7                  SS §8Millisecond of the second§7
                            zzz §8Shortened name of the general time zone§7
                            zzzz §8Full name of the general time zone§7
                """;
        general.addEntry(entryBuilder.startTextDescription(
                Text.of("§7Timestamp Formatting Help\n" + mc_formatting_info + ts_formatting_info))
                .build());

        builder.transparentBackground();

        return builder;

    }

}
