package br.com.beez.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class CommandHandler extends ListenerAdapter {

    private final CommandMap commandMap = new CommandMap();

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        val commands = commandMap.getCommands();
        val command = commands.get(event.getName());
        try {
            command.execute(event);
        } catch (Exception exception) {
            exception.printStackTrace();
            event.reply("Something went wrong!").setEphemeral(true).queue();
        }
    }
}