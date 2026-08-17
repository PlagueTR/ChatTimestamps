package space.plague.plagueschattimestamps.config;

import space.plague.plagueschattimestamps.Main;

public class ModConfig {

    private boolean enableMod;

    private String timestampFormat;

    private boolean enableHover;

    public ModConfig() {
        this.enableMod = true;
        this.timestampFormat = "&7[HH:mm:ss] ";
        this.enableHover = false;
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

    public boolean isEnableHover() {
        return enableHover;
    }

    public void setEnableHover(boolean enableHover) {
        this.enableHover = enableHover;
    }
}
