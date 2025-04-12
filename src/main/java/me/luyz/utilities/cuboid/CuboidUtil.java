package me.luyz.utilities.cuboid;

import org.bukkit.*;

public final class CuboidUtil
{
    public static String serializeCuboid(final Cuboid cuboid) {
        if (cuboid == null) {
            return null;
        }
        return cuboid.getWorld().getName() + ":" + cuboid.getX1() + ":" + cuboid.getY1() + ":" + cuboid.getZ1() + ":" + cuboid.getX2() + ":" + cuboid.getY2() + ":" + cuboid.getZ2();
    }

    public static Cuboid deserializeCuboid(final String data) {
        if (data == null) {
            return null;
        }
        final String[] splittedData = data.split(":");
        if (splittedData.length < 7) {
            return null;
        }
        final World world = Bukkit.getWorld(splittedData[0]);
        final int x1 = Integer.parseInt(splittedData[1]);
        final int y1 = Integer.parseInt(splittedData[2]);
        final int z1 = Integer.parseInt(splittedData[3]);
        final int x2 = Integer.parseInt(splittedData[4]);
        final int y2 = Integer.parseInt(splittedData[5]);
        final int z2 = Integer.parseInt(splittedData[6]);
        return new Cuboid(world, x1, y1, z1, x2, y2, z2);
    }

    private CuboidUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}