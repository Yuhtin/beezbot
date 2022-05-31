package br.com.beez;

import br.com.beez.command.CommandCatcher;
import br.com.beez.command.CommandRegistry;
import br.com.beez.configuration.Config;
import br.com.beez.dto.MongoClientManager;
import br.com.beez.dto.impl.EconomyRepository;
import br.com.beez.dto.impl.UserRepository;
import br.com.beez.dto.impl.WorkerRepository;
import br.com.beez.listener.ListenerRegister;
import br.com.beez.util.Logger;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class BeezBot {

    private static final CommandCatcher COMMAND_CATCHER = new CommandCatcher();

    @Getter
    private static JDA client;
    @Getter
    private static long startupMillis;
    @Getter
    private static Config config;

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

            ListenerRegister.of(jdaBuilder).register();
            client = jdaBuilder.build();

            CommandRegistry.of(client).register();

            val mongoUri = config.getMongoUri().replace("$login$", config.getMongoLogin());
            val mongoDatabase = config.getMongoDatabase();

            val mongoClientManager = MongoClientManager.instance();
            mongoClientManager.load(mongoUri, mongoDatabase);

            mongoClientManager.injectTables(
                    UserRepository.instance(),
                    EconomyRepository.instance(),
                    WorkerRepository.instance()
            );

            Runtime.getRuntime().addShutdownHook(new Thread(BeezBot::shutdown));
        } catch (LoginException exception) {
            exception.printStackTrace();
            logger.severe("Could not login to discord");
            System.exit(0);
        }
    }

    public static void shutdown() {
        MongoClientManager.instance().close();
        Logger.getLogger().info("Shutdown complete");
    }

    public static CommandCatcher getCommandCatcher() {
        return COMMAND_CATCHER;
    }

}
