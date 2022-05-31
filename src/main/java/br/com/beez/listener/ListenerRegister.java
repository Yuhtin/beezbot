package br.com.beez.listener;

import br.com.beez.util.Logger;
import com.google.common.reflect.ClassPath;
import lombok.Data;
import lombok.val;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

@Data(staticConstructor = "of")
public class ListenerRegister {

    private final JDABuilder jdaBuilder;

    public void register() {
        val logger = Logger.getLogger();

        ClassPath classPath;

        try {
            classPath = ClassPath.from(getClass().getClassLoader());
        } catch (IOException exception) {
            logger.severe("ClassPath could not be instantiated");
            return;
        }

        int i = 0;
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive("br.com.beez.listener.impl")) {
            try {

                Class event = Class.forName(classInfo.getName());
                Object object = event.newInstance();

                if (object instanceof ListenerAdapter) {
                    jdaBuilder.addEventListeners(object);
                    ++i;
                } else throw new InstantiationException();

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
                logger.severe("The " + classInfo.getName() + " class could not be instantiated");
            }
        }

        logger.info("Registered " + i + " events successfully");
    }

}
