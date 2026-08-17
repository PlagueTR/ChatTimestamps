package space.plague.plagueschattimestamps.config.gui;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import space.plague.plagueschattimestamps.Main;
import space.plague.plagueschattimestamps.config.ModConfig;

public class GeneralOptionsScreen {

    public static ConfigBuilder getConfigBuilder() {

        ModConfig defaults = new ModConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(Minecraft.getInstance().gui.screen())
                .setTitle(Component.literal(Main.MOD_NAME + " - General"));

        builder.setSavingRunnable(Main::saveConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Mod"), Main.getConfig().isEnableMod())
                .setDefaultValue(defaults.isEnableMod())
                .setTooltip(Component.literal("Enables the mod."))
                .setSaveConsumer(newValue -> { Main.getConfig().setEnableMod(newValue); })
                .build());

        general.addEntry(entryBuilder.startStrField(Component.literal("Timestamp Format"), Main.getConfig().getTimestampFormat())
                .setDefaultValue(defaults.getTimestampFormat())
                .setTooltip(Component.literal("Set the formatting for the timestamp."))
                .setSaveConsumer(newValue -> { Main.getConfig().setTimestampFormat(newValue); })
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Hover"), Main.getConfig().isEnableHover())
                .setDefaultValue(defaults.isEnableHover())
                .setTooltip(Component.literal("Display time when hovered instead of prefixing the message."))
                .setSaveConsumer(newValue -> { Main.getConfig().setEnableHover(newValue); })
                .build());

        String mc_formatting_info =
                "&0 §0BLACK.§7            &1 §1DARK_BLUE.§7      &2 §2DARK_GREEN§7     &3 §3DARK_AQUA§7\n" +
                "&4 §4DARK_RED§7        &5 §5DARK_PURPLE.§7   &6 §6GOLD§7              &7 §7GRAY§7\n" +
                "&8 §8DARK_GRAY.§7      &9 §9BLUE§7              &a §AGREEN.§7            &b §BAQUA§7\n" +
                "&c §CRED.§7               &d §DLIGHT_PURPLE.§7  &e §EYELLOW§7           &f §FWHITE§7\n" +
                "&k §8§KOBFUSCATED§r§8.§7     &l §8§LBOLD§r§.§7             &m §8§MSTRIKETHROUGH§r§7 &n §8§NUNDERLINED§r§7\n" +
                "&o §8§OITALIC§r§7            &r §8RESET§7\n\n";

        String ts_formatting_info =
                "yy §8Last 2 digits of the year§7             yyyy §8Year§7\n" +
                "MM §8Numeric value of the month§7          MMM §8Shortened name of the month§7\n" +
                "MMMM §8Full name of the month§7             dd §8Day of the month§7\n" +
                "EEE §8Shortened name of day of the week§7\n" +
                "EEEE §8Full name of day of the week§7\n" +
                "HH §8Hour of the day in 24-hour format§7\n" +
                "hh §8Hour of the day in 12-hour format§7\n" +
                "a §8AM/PM marker.§7                            mm §8Minute of the hour§7\n" +
                "ss §8Second of the minute§7                  SS §8Millisecond of the second§7\n" +
                "zzz §8Shortened name of the general time zone§7\n" +
                "zzzz §8Full name of the general time zone§7\n" +
                "'text' §8Any text not part of the formatting§7";

        general.addEntry(entryBuilder.startTextDescription(Component.literal("§7Timestamp Formatting Help\n\n" + mc_formatting_info + ts_formatting_info))
                .build());

        builder.transparentBackground();

        return builder;
    }

}
