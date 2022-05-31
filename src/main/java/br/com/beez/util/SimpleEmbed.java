package br.com.beez.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleEmbed {

    private static final Color DEFAULT_COLOR = new Color(241, 196, 15);

    public static MessageEmbed of(String description) {
        return create(description).build();
    }

    public static EmbedBuilder create(String description) {
        return new EmbedBuilder().setColor(getColor()).setDescription(description);
    }

    public static EmbedBuilder create() {
        return new EmbedBuilder().setColor(getColor());
    }

    public static Color getColor() {
        return DEFAULT_COLOR;
    }

}
