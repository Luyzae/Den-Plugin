package me.luyz.providers.scoreboard;

import me.luyz.servicies.impl.*;
import java.util.concurrent.atomic.*;
import me.luyz.utilities.*;
import java.util.*;

public class ScoreboardAnimated {

    public static String TITLE;
    public static String FOOTER;

    public static void init() {
        final List<String> titles = ScoreboardService.TITLE_ANIMATED_TITLE;
        final List<String> footers = ScoreboardService.FOOTER_ANIMATED_FOOTER;
        ScoreboardAnimated.TITLE = titles.get(0);
        ScoreboardAnimated.FOOTER = footers.get(0);
        if (ScoreboardService.TITLE_ANIMATED_ENABLED) {
            final AtomicInteger atomicInteger = new AtomicInteger();
            final AtomicInteger atomicInteger2 = new AtomicInteger();
            final List<String> list = new ArrayList<>(titles);

            TaskUtil.runTimerAsync(() -> {
                if (atomicInteger2.get() == list.size()) {
                    atomicInteger2.set(0);
                }
                ScoreboardAnimated.TITLE = list.get(atomicInteger2.getAndIncrement());
            }, 20L, (long)(ScoreboardService.TITLE_ANIMATED_INTERVAL * 20.0));
        }

        if (ScoreboardService.FOOTER_ANIMATED_ENABLED) {
            final AtomicInteger atomicInteger = new AtomicInteger();
            final AtomicInteger atomicInteger3 = new AtomicInteger();
            final List<String> list2 = new ArrayList<>(footers);
            TaskUtil.runTimerAsync(() -> {
                if (atomicInteger3.get() == list2.size()) {
                    atomicInteger3.set(0);
                }
                ScoreboardAnimated.FOOTER = list2.get(atomicInteger3.getAndIncrement());
            }, 20L, (long)(ScoreboardService.FOOTER_ANIMATED_INTERVAL * 20.0));
        }
    }
}
