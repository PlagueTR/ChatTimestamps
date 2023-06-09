package space.plague.chattimestamps.config;

import space.plague.chattimestamps.Main;

public class ModConfig {

    private boolean enableMod;

    private String timestampFormat;

    public ModConfig() {
        this.enableMod = true;
        this.timestampFormat = "&7[HH:mm:ss] ";
    }

    public boolean isEnableMod() {
        return enableMod;
    }

    public void setEnableMod(boolean enableMod) {
        this.enableMod = enableMod;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
        Main.SetFormat(timestampFormat);
    }

}