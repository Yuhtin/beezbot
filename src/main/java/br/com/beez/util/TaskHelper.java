package br.com.beez.util;

import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskHelper {
    @Getter private static final ExecutorService executor = Executors.newFixedThreadPool(128);

    public static void runTaskTimerAsync(TimerTask task, int delay, int period, TimeUnit timeFormat) {
        Timer timer = new Timer();
        runAsync(new Thread(() -> timer.scheduleAtFixedRate(task, timeFormat.toMillis(delay), timeFormat.toMillis(period))));
    }

    public static void runAsync(Runnable runnable) { executor.execute(runnable); }
}