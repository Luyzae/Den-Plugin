package me.luyz.commons.timer;



public class Timer
{
    private String name;
    private String prefix;
    private long startMillis;
    private long endMillis;
    private long getRemaining;
    private boolean running;

    public Timer(final String name, final long startMillis, final long endMillis, final String prefix) {
        this.name = name;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.prefix = prefix;
        this.running = true;
        final TimerTask timerTask = new TimerTask(this);
        timerTask.run();
    }

    public long getRemaining() {
        return this.endMillis - System.currentTimeMillis();
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public long getStartMillis() {
        return this.startMillis;
    }

    public long getEndMillis() {
        return this.endMillis;
    }

    public long getGetRemaining() {
        return this.getRemaining;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setStartMillis(final long startMillis) {
        this.startMillis = startMillis;
    }

    public void setEndMillis(final long endMillis) {
        this.endMillis = endMillis;
    }

    public void setGetRemaining(final long getRemaining) {
        this.getRemaining = getRemaining;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }
}