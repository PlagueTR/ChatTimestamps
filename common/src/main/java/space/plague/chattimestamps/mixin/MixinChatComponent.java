package space.plague.chattimestamps.mixin;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.ModConfig;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent {

    @ModifyVariable(method = "addMessageToDisplayQueue(Lnet/minecraft/client/GuiMessage;)V", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
    private GuiMessage modifyMessage(GuiMessage message) {

        ModConfig config = Main.getConfig();

        if (!config.isEnableMod()) {
            return message;
        }
        if (config.isEnableHover()) {
            MutableComponent ts = Component.literal(Main.getFormattedTimestamp());

            Style original = message.content().getStyle();
            HoverEvent existing = original.getHoverEvent();
            HoverEvent newHover;
            if (existing != null && existing.getAction() == HoverEvent.Action.SHOW_TEXT) {
                Component combined = ts.append("§r\n").append(existing.getValue(HoverEvent.Action.SHOW_TEXT));
                newHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, combined);
            }
            else if (existing != null) {
                newHover = existing;
            }
            else {
                newHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, ts);
            }
            Style newStyle = original.withHoverEvent(newHover);
            message = new GuiMessage(
                    message.addedTime(),
                    message.content().copy().withStyle(newStyle),
                    message.signature(),
                    message.tag()
            );
        }
        else{
            message = new GuiMessage(
                    message.addedTime(),
                    Component.literal(Main.getFormattedTimestamp() + "§r").append(message.content()),
                    message.signature(),
                    message.tag()
            );
        }
        return message;
    }

}
