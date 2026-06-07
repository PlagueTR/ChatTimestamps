package space.plague.chattimestamps.mixin;

import space.plague.chattimestamps.Main;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static net.minecraft.text.Text.*;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {

    //modify message in addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh) method
    @ModifyVariable(method = "Lnet/minecraft/client/gui/hud/ChatHud;addVisibleMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", at = @At(value = "HEAD"), argsOnly = true)
    private ChatHudLine modifyMessage(ChatHudLine message) {

        //if mod is disabled, don't change anything
        if (Main.getConfig().isEnableMod()) {

            message = new ChatHudLine(message.creationTick(),
                    Text.empty().append(Text.of(Main.getFormattedTimestamp() + "§r")).append(message.content()),
                    message.signature(),
                    message.indicator()
            );
            Main.LOGGER.info(Main.getFormattedTimestamp() + "§r" + message.content());
            Main.LOGGER.info(of(Main.getFormattedTimestamp() + "§r" + message.content()));

        }

        return message;
    }

}