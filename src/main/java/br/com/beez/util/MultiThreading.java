package br.com.beez.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreading {

    private static final ExecutorService executor = Executors.newFixedThreadPool(128);
    private static final Timer timer = new Timer();

    public static void runAsync(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runTaskTimerAsync(TimerTask task, int delay, int period, TimeUnit timeFormat) {
        runAsync(new Thread(() ->
                timer.scheduleAtFixedRate(
                        task,
                        timeFormat.toMillis(delay),
                        timeFormat.toMillis(period)
                ))
        );
    }

}
