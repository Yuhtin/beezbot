package br.com.beez.command.impl;

import br.com.beez.BeezBot;
import br.com.beez.command.Command;
import br.com.beez.command.CommandHandler;
import br.com.beez.util.DateUtil;
import br.com.beez.util.SimpleEmbed;
import br.com.beez.util.TimeUtils;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;

@CommandHandler(
        name = "info",
        description = "Veja as informações do bot",
        args = {}
)
public class BotInfoCommand implements Command {

    @Override
    public void execute(CommandInteraction command, InteractionHook hook) {
        try {
            String model = "I7 4790K";
            val selfUser = command.getJDA().getSelfUser();
            val embedBuilder = new EmbedBuilder()
                    .setTitle("<:BC_localizador:898725366418513980> Informações do bot")
                    .setDescription("Modelo da CPU: `" + model + "`\nSistema operacional: `" + System.getProperty("os.name") + "`")

                    .addField(":calendar: Criado em", DateUtil.format(selfUser.getTimeCreated().toInstant().toEpochMilli()), true)
                    .addField(":id: Versão atual", "`v2.0.0`", true)
                    .addField(":man: Dono", "`Yuhtin#9147`", true)

                    .addField("🎉 Uptime", "`" + TimeUtils.format(System.currentTimeMillis() - BeezBot.getStartupMillis()) + "`", true)
                    .addField("<:BC_conexo:898725366187827241> Ping API", "`" + command.getJDA().getGatewayPing() + "`", true)
                    .addField("\uD83D\uDD8A Prefixo", "`/`", true)

                    .addField(":mag: Versão Java", "`v" + System.getProperty("java.version").replace("-heroku", "") + "`", true)
                    .addField("<:Minecart:926886180832817214> Versão JDA", "`v5.0.0-alpha.4`", true)
                    .addField("💻 Database", "`MongoDB`", true)

                    .addField("<:BC_settings:898725366271705088> Threads", "Veja mais em `:d`", true)
                    .addField("<:BC_robozin:898725366447894530> Núcleos", "`" + Runtime.getRuntime().availableProcessors() + " cores`", true)
                    .setThumbnail(selfUser.getAvatarUrl())
                    .setColor(SimpleEmbed.getColor());

            hook.sendMessageEmbeds(embedBuilder.build()).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            hook.sendMessage("Sorry").queue();
        }
    }

}
