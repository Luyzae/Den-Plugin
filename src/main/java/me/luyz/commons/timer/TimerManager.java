package me.luyz.commons.timer;

import java.util.*;
import com.google.common.collect.*;

public class TimerManager
{
    private final Map<String, Timer> timers;

    public TimerManager() {
        this.timers = Maps.<String, Timer>newHashMap();
    }

    public Map<String, Timer> getTimers() {
        return this.timers;
    }

    public void createTimer(final Timer timer) {
        this.timers.put(timer.getName(), timer);
    }

    public void deleteTimer(final Timer timer) {
        this.timers.remove(timer.getName());
    }

    public boolean existsTimer(final String name) {
        return this.timers.containsKey(name);
    }

    public Timer getTimer(final String name) {
        return this.timers.get(name);
    }
}