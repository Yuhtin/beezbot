package br.com.beez.util;

import com.yuhtin.commission.builderscommunity.Core;
import com.yuhtin.commission.builderscommunity.api.config.Config;
import com.yuhtin.commission.builderscommunity.models.enums.PunishType;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.time.Instant;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class DiscordLogUtils {

    private static final Config CONFIG = Core.getConfig();

    public static void sendRequest(EmbedBuilder embedBuilder, long area) {
        val channel = Core.getClient().getTextChannelById(CONFIG.getRequestChannel());
        if (channel == null) return;

        embedBuilder.setTitle("<:BC_Bell:898725366816968768> **Hey! Temos um novo pedido!**");

        val messageBuilder = new MessageBuilder(embedBuilder.build());
        messageBuilder.setContent("<@&" + area + ">");

        channel.sendMessage(messageBuilder.build())
                .setActionRows(ActionRow.of(
                        Button.primary("quote", "🛎️ Send a quote"),
                        Button.primary("question", "❓ Send a question")
                ), ActionRow.of(Button.secondary("questions", "📃 View Q&A")))
                .queue();
    }

    public static void sendRecommendRequest(User user,
                                            User profissional,
                                            String product,
                                            String feedback,
                                            String proof,
                                            int stars) {

        val channel = Core.getClient().getTextChannelById(CONFIG.getRecommendRequestChannel());
        if (channel == null) return;

        val embedBuilder = new EmbedBuilder();
        embedBuilder.setFooter(user.getId() + "|" + profissional.getId() + "|" + stars, channel.getGuild().getIconUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setAuthor("🚀 | Avaliação de profissional");
        embedBuilder.setThumbnail(profissional.getAvatarUrl());
        embedBuilder.setDescription("🟢 - Para aceitar a avaliação\n🔴 - Para negar a avaliação");

        embedBuilder.addField("🧡 Jogador avaliado", "`" + user.getAsTag() + "`", true);
        embedBuilder.addField("💘 Profissional avaliado", "`" + profissional.getAsTag() + "`", true);
        embedBuilder.addField("💫 Produto adquirido", product, false);
        embedBuilder.addField("💬 Feedback", feedback, false);
        embedBuilder.addField("💧 Provas da entrega", proof, false);
        embedBuilder.addField(":star: Estrelas", NumberUtils.format(stars), false);

        channel.sendMessageEmbeds(embedBuilder.build()).queue(
                message -> RestAction.allOf(
                        message.addReaction("🟢"),
                        message.addReaction("🔴")
                ).queue()
        );
    }

    public static void sendPunishLog(User user, User punished, PunishType type, String reason, long time) {
        val channel = Core.getClient().getTextChannelById(type == PunishType.BLACKLIST ? CONFIG.getBlacklistLogChannel() : CONFIG.getPunishLogChannel());
        val embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(255, 113, 0));
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setThumbnail(channel.getGuild().getIconUrl());

        embedBuilder.setTitle("<:BC__2ateno:904094079384711209> Nova Punição");

        embedBuilder.setDescription("⠀\n" +
                "<:BC_moderacao:898725366032650281> **Aplicador da Punição**\n" +
                user.getAsTag() + "\n" +
                "\n" +
                "<:BC_membros:898725367278354452> **Usuário Punido**\n" +
                punished.getAsMention() + " (" + punished.getIdLong() + ")\n" +
                "\n" +
                "<:BC_info:898725365999095859> **Motivo da Punição**\n" +
                reason + "\n" +
                "\n" +
                "<:BC_escudo:898725366519177326> **Tipo de punição**\n" +
                type.getFancy() + (time != 0 ? " (Até " + DateUtil.format(time) + ")" : "")
        );

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void sendUnpunishLog(User user, User unpunished, PunishType type) {
        val channel = Core.getClient().getTextChannelById(CONFIG.getPunishLogChannel());
        val embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(255, 215, 0));
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setThumbnail(channel.getGuild().getIconUrl());
        embedBuilder.setTitle("<:BC__2ateno:904094079384711209> Remoção de Punição");

        embedBuilder.setDescription("⠀\n" +
                "<:BC_moderacao:898725366032650281> **Autor**\n" +
                user.getAsTag() + "\n" +
                "\n" +
                "<:BC_membros:898725367278354452> **Usuário despunido**\n" +
                unpunished.getAsTag() + " (" + unpunished.getIdLong() + ")\n" +
                "\n" +
                "<:BC_escudo:898725366519177326> **Tipo de punição**\n" +
                type.getFancy()
        );

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }


}
