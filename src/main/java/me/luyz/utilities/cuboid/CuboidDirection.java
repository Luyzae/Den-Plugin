package me.luyz.utilities.cuboid;

public enum CuboidDirection
{
    NORTH,
    EASY,
    SOUTH,
    WEST,
    UP,
    DOWN,
    HORIZONTAL,
    VERTICAL,
    BOTH,
    UNKNOWN;

    public CuboidDirection opposite() {
        switch (this) {
            case NORTH: {
                return CuboidDirection.SOUTH;
            }
            case EASY: {
                return CuboidDirection.WEST;
            }
            case SOUTH: {
                return CuboidDirection.NORTH;
            }
            case WEST: {
                return CuboidDirection.EASY;
            }
            case HORIZONTAL: {
                return CuboidDirection.VERTICAL;
            }
            case VERTICAL: {
                return CuboidDirection.HORIZONTAL;
            }
            case UP: {
                return CuboidDirection.DOWN;
            }
            case DOWN: {
                return CuboidDirection.UP;
            }
            case BOTH: {
                return CuboidDirection.BOTH;
            }
            default: {
                return CuboidDirection.UNKNOWN;
            }
        }
    }

    private static /* synthetic */ CuboidDirection[] $values() {
        return new CuboidDirection[] { CuboidDirection.NORTH, CuboidDirection.EASY, CuboidDirection.SOUTH, CuboidDirection.WEST, CuboidDirection.UP, CuboidDirection.DOWN, CuboidDirection.HORIZONTAL, CuboidDirection.VERTICAL, CuboidDirection.BOTH, CuboidDirection.UNKNOWN };
    }

    static {
        CuboidDirection[] $VALUES = $values();
    }
}
