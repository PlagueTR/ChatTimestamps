package space.plague.chattimestamps.config;

import space.plague.chattimestamps.Main;

public class ModConfig {

    private String hoverText;
    private boolean enableMod;

    private String timestampFormat;

    public ModConfig() {
        this.enableMod = true;
        this.timestampFormat = "&7[HH:mm:ss] ";
        this.hoverText = "&7⏰ ";
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
    public String getHoverText() {
        return hoverText;
    }
    public void setHoverText(String hoverText) {
        this.hoverText = hoverText;
        Main.setHoverText(hoverText);
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
        Main.SetFormat(timestampFormat);
    }

}