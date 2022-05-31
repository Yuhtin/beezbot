package br.com.beez.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    public static boolean isAdmin(CommandInteraction commandInteraction, boolean sendMessage) {
        val member = commandInteraction.getMember();

        val permission = member == null || member.hasPermission(Permission.ADMINISTRATOR);
        if (!permission && sendMessage) sendNoPermissionMessage(commandInteraction);

        return permission;
    }

    public static void sendNoPermissionMessage(CommandInteraction commandInteraction) {
        commandInteraction.getHook().sendMessage(":x: Você não tem permissão para isto").queue();
    }

    public static PrivateChannel getPrivateChannel(User user) {
        val channel = user.openPrivateChannel().complete();
        if (channel == null || !user.hasPrivateChannel()) return null;

        return channel;
    }

}
