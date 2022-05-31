package br.com.beez.command.impl;

import br.com.beez.command.Command;
import br.com.beez.command.CommandHandler;
import br.com.beez.dto.impl.EconomyRepository;
import br.com.beez.dto.impl.UserRepository;
import br.com.beez.dto.impl.WorkerRepository;
import br.com.beez.util.ChannelUtil;
import br.com.beez.util.DateUtil;
import br.com.beez.util.NumberUtils;
import br.com.beez.util.SimpleEmbed;
import lombok.val;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;

import java.awt.*;
import java.time.Instant;


@CommandHandler(
        name = "perfil",
        description = "Visualize o perfil de alguém",
        args = {
                "[@user]-Usuário que deseja ver o perfil"
        }
)
public class ProfileCommand implements Command {

    @Override
    public void execute(CommandInteraction command, InteractionHook hook) {
        val option = command.getOption("user");
        val user = option != null ? option.getAsUser() : hook.getInteraction().getUser();
        val idLong = user.getIdLong();

        val profile = option == null ? UserRepository.instance().find(idLong) : UserRepository.instance().findOnlyExists(idLong);
        val worker = option == null ? WorkerRepository.instance().find(idLong) : WorkerRepository.instance().findOnlyExists(idLong);
        val polenData = option == null ? EconomyRepository.instance().find(idLong) : EconomyRepository.instance().findOnlyExists(idLong);

        val knowledge = new StringBuilder();
        for (val workArea : worker.workAreas()) {
            knowledge.append("  • ").append(workArea.getName()).append(" (**").append(workArea.getCurrentPoints()).append(" pontos**)").append("\n");
        }

        val verifiedDate = profile.verified() == 0 ? "" : "<:verificado:980807144834236466> Verificado em: " + DateUtil.format(profile.verified()) + "\n";
        val color = profile.verified() == 0 ? SimpleEmbed.getColor() : new Color(235, 48, 197);

        val embedBuilder = new EmbedBuilder()
                .setTitle("Perfil de " + user.getName())
                .setDescription(verifiedDate +
                        "<:bc_booster:978384876443168799> Nível: " + profile.level() + " (" + profile.xp() + " XP) \n\n" +
                        "<:bc_icon1:978384876896137236> Áreas de conhecimento:\n\n" +
                        knowledge + "\n\n" +
                        ":honey_pot: Pólens: `" + NumberUtils.format(polenData.polens()) + " pólens`"
                )
                .setThumbnail(user.getAvatarUrl())
                .setTimestamp(Instant.now())
                .setColor(color);

        hook.sendMessageEmbeds(embedBuilder.build())
                .setEphemeral(!ChannelUtil.isCommandChannel(command.getChannel()))
                .queue();
    }
}
