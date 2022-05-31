package br.com.beez;

import br.com.beez.configuration.Config;
import br.com.beez.util.Logger;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class BezzBot {

    @Getter private static JDA client;
    @Getter private static long startupMillis;
    @Getter private static Config config;

    public static void main(String[] args) {
        startupMillis = System.currentTimeMillis();

        val logger = Logger.getLogger();
        logger.info("Starting systems");

        config = Config.loadConfig("config.json");
        if (config == null) {
            System.exit(0);
            return;
        }

        try {
            val intents = Arrays.asList(
                    GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGE_REACTIONS
            );

            val jdaBuilder = JDABuilder.createDefault(config.getToken())
                    .enableIntents(intents)
                    .addEventListeners(COMMAND_CATCHER);


        } catch (LoginException exception) {

        }


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Logger.getLogger().info("Shuting down...");
            shutdown();
        }));
    }

    public static void shutdown() {

        Logger.getLogger().info("Shutdown complete");
    }

}
