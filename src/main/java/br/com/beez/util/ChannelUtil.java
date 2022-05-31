package br.com.beez.util;

import br.com.beez.BeezBot;
import net.dv8tion.jda.api.entities.MessageChannel;

public class ChannelUtil {

    public static boolean isCommandChannel(MessageChannel channel) {
        return BeezBot.getConfig().getCommandChannelId() == channel.getIdLong();
    }

}
