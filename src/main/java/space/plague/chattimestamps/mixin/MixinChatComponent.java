package space.plague.chattimestamps.mixin;

import space.plague.chattimestamps.Main;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent {

    //modify message in addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh) method
    @ModifyVariable(method = "Lnet/minecraft/client/gui/components/ChatComponent;addMessageToDisplayQueue(Lnet/minecraft/client/multiplayer/chat/GuiMessage;)V", at = @At(value = "HEAD"), argsOnly = true)
    private GuiMessage modifyMessage(GuiMessage message) {

        //if mod is disabled, don't change anything
        if (Main.getConfig().isEnableMod()) {

            message = new GuiMessage(message.addedTime(),
                    Component.literal(Main.getFormattedTimestamp() + "§r").append(message.content()),
                    message.signature(),
                    message.source(),
                    message.tag()
            );

        }

        return message;
    }

}