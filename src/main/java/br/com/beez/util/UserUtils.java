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
        commandInteraction.getHook().sendMessage("<:fuckyou:855725185846870016> Você não tem permissão para isto").queue();
    }

    public static PrivateChannel getPrivateChannel(User user) {
        val channel = user.openPrivateChannel().complete();
        if (channel == null || !user.hasPrivateChannel()) return null;

        return channel;
    }

    public static PrivateChannel getPrivateChannel(InteractionHook hook, boolean message) {
        val interaction = hook.getInteraction();
        val privateChannel = interaction.getUser().openPrivateChannel().complete();
        if (privateChannel == null || !interaction.getUser().hasPrivateChannel()) {
            if (!message) return null;

            val embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(SimpleEmbed.getColor());
            embedBuilder.setTitle("<:BC_info:898725365999095859> **Ops!**");
            embedBuilder.setDescription("Parece que você está com o recebimento de mensagens diretas desativado. Por favor, ative-as para prosseguir.");
            hook.sendMessageEmbeds(embedBuilder.build()).queue();
            return null;
        }

        return privateChannel;
    }

    public static PrivateChannel getPrivateChannel(TextChannel channel, User user) {
        if (user == null) return null;

        val privateChannel = user.openPrivateChannel().complete();
        if (privateChannel == null || !user.hasPrivateChannel()) {
            val embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(SimpleEmbed.getColor());
            embedBuilder.setTitle("<:BC_info:898725365999095859> **Ops!**");
            embedBuilder.setDescription("Parece que você está com o recebimento de mensagens diretas desativado. Por favor, ative-as para prosseguir.");
            if (channel != null) channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return null;
        }

        return privateChannel;
    }
}
