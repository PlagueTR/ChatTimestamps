package space.plague.chattimestamps.mixin;

import net.minecraft.text.StringRenderable;
import space.plague.chattimestamps.Main;
import space.plague.chattimestamps.config.GeneralOptions;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.TextCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {

    //modify message in addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh) method
    @ModifyArg(method = "addMessage(Lnet/minecraft/text/StringRenderable;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/ChatMessages;breakRenderedChatMessageLines(Lnet/minecraft/text/StringRenderable;ILnet/minecraft/client/font/TextRenderer;)Ljava/util/List;"), index = 0)
    private StringRenderable modifyMessage(StringRenderable message) {

        //if mod is disabled, don't change anything
        if (!GeneralOptions.disableMod) {

            TextCollector tc = new TextCollector();

            //get formatted timestamp and add 'reset formatting modifier' to the end of timestamp
            tc.add(StringRenderable.plain(Main.getFormattedTimestamp() + "§r"));

            //add the rest of the message to the timestamp
            tc.add(message);

            //set argument to stamped text
            message = tc.getCombined();

        }
        return message;

    }

}