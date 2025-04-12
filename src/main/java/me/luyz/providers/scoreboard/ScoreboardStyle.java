package me.luyz.providers.scoreboard;

public enum ScoreboardStyle {

    KOHI(true, 15),
    VIPER(true, -1),
    MODERN(false, 1);

    private final boolean descending;
    private final int startNumber;

    private static final ScoreboardStyle[] $VALUES;


    private ScoreboardStyle(final boolean descending, final int startNumber) {
        this.descending = descending;
        this.startNumber = startNumber;
    }


    public boolean isDescending() {
        return this.descending;
    }

    public int getStartNumber() {
        return this.startNumber;
    }

    private static /* synthetic */ ScoreboardStyle[] $values() {
        return new ScoreboardStyle[] { ScoreboardStyle.KOHI, ScoreboardStyle.VIPER, ScoreboardStyle.MODERN };
    }


    static {
        $VALUES = $values();
    }


}
