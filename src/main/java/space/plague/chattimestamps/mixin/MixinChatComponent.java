package space.plague.chattimestamps.mixin;

import space.plague.chattimestamps.Main;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.swing.*;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent {

    //modify message in addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh) method
    @ModifyVariable(method = "Lnet/minecraft/client/gui/components/ChatComponent;addMessageToDisplayQueue(Lnet/minecraft/client/multiplayer/chat/GuiMessage;)V", at = @At(value = "HEAD"), argsOnly = true)
    private GuiMessage modifyMessage(GuiMessage message) {

        //if mod is disabled, don't change anything
        if (!Main.getConfig().isEnableMod()) {
            return message;
        }

        if (Main.getConfig().isEnableHover()) {
            MutableComponent ts = Component.literal(Main.getFormattedTimestamp());
            HoverEvent existing = message.content().getStyle().getHoverEvent();
            HoverEvent newHover;

            if (existing instanceof HoverEvent.ShowText(Component value)){
                Component combined = ts.append("§r\n").append(value);
                newHover = new HoverEvent.ShowText(combined);
            }
            else if (existing != null) {
                newHover = existing;
            }
            else {
                newHover = new HoverEvent.ShowText(ts);
            }

            return new GuiMessage(message.addedTime(),
                    message.content().copy().withStyle(message.content().getStyle().withHoverEvent(newHover)),
                    message.signature(),
                    message.source(),
                    message.tag()
            );
        }
        else {
            return new GuiMessage(message.addedTime(),
                    Component.literal(Main.getFormattedTimestamp() + "§r").append(message.content()),
                    message.signature(),
                    message.source(),
                    message.tag()
            );
        }

    }

}