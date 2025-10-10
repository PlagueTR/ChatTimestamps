package space.plague.chattimestamps.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import space.plague.chattimestamps.Main;

import java.util.Objects;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {

    //modify message in addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh) method
    @ModifyArg(method = "addVisibleMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/ChatMessages;breakRenderedChatMessageLines(Lnet/minecraft/text/StringVisitable;ILnet/minecraft/client/font/TextRenderer;)Ljava/util/List;"), index = 0)
    private StringVisitable modifyMessage(StringVisitable message) {

        //if mod is disabled, don't change anything
        if (Main.getConfig().isEnableMod()) {

            TextCollector tc = new TextCollector();

            String formattedTimestamp = Main.getFormattedTimestamp();
            String hoverTextValue = Main.getHoverText().replace('&', '§');

            if (!hoverTextValue.isBlank()) {
                Text hoverText = Text.literal(hoverTextValue)
                        .setStyle(Style.EMPTY.withHoverEvent(
                                new HoverEvent.ShowText(Text.literal(formattedTimestamp.trim()))
                        ));

                tc.add(hoverText);
            } else {
                //get formatted timestamp and add 'reset formatting modifier' to the end of timestamp
                tc.add(StringVisitable.plain(formattedTimestamp + "§r"));
            }


            //add the rest of the message to the timestamp
            tc.add(message);

            //set argument to stamped text
            message = tc.getCombined();
        }
        return message;

    }

}