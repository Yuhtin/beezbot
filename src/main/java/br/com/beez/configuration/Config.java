package br.com.beez.configuration;

import br.com.beez.util.Logger;
import br.com.beez.util.Serializer;
import lombok.Getter;

import java.io.*;

@Getter
public class Config {

    private long guildId;
    private long commandChannelId;

    private String mongoDatabase;
    private String mongoLogin;
    private String mongoUri;
    private String token;

    public static Config loadConfig(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {

                if (!file.createNewFile()) return null;

                Config config = new Config();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
                    writer.write(Serializer.CONFIG.serialize(config));
                    writer.newLine();
                    writer.flush();
                }

                Logger.getLogger().warning("Put a valid token in the bot's config");
                return null;
            }

            String line;
            StringBuilder responseContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                while ((line = reader.readLine()) != null) responseContent.append(line);
            }

            return Serializer.CONFIG.deserialize(responseContent.toString());
        } catch (Exception exception) {
            return null;
        }

    }

}
