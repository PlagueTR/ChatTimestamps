package space.plague.chattimestamps.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.ModConfig;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
    private Text modifyMessage(Text message) {

        ModConfig config = Main.getConfig();

        if (!config.isEnableMod()) {
            return message;
        }
        if (config.isEnableHover()) {
            Text ts = Text.of(Main.getFormattedTimestamp());

            Style original =  message.getStyle();
            HoverEvent existing = original.getHoverEvent();
            HoverEvent newHover;
            if (existing != null && existing.getAction() == HoverEvent.Action.SHOW_TEXT) {
                Text combined = ts.copy().append("§r\n").append(existing.getValue(HoverEvent.Action.SHOW_TEXT));
                newHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, combined);
            }
            else if (existing != null) {
                newHover = existing;
            }
            else {
                newHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, ts);
            }
            Style newStyle = original.withHoverEvent(newHover);
            message = message.copy().setStyle(newStyle);
        }
        else{
            message = Text.of(Main.getFormattedTimestamp() + "§r").copy().append(message);
        }
        return message;
    }

}
