package me.luyz.utilities.cuboid;

import org.bukkit.block.*;
import org.bukkit.*;
import java.util.*;

public class Cuboid implements Iterable<Location>
{
    private String worldName;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;

    public Cuboid(final Location l1, final Location l2) {
        this(l1.getWorld().getName(), l1.getBlockX(), l1.getBlockY(), l1.getBlockZ(), l2.getBlockX(), l2.getBlockY(), l2.getBlockZ());
    }

    public Cuboid(final World world, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this(world.getName(), x1, y1, z1, x2, y2, z2);
    }

    public Cuboid(final String worldName, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public Location getLowerCorner() {
        return new Location(this.getWorld(), (double)this.x1, (double)this.y1, (double)this.z1);
    }

    public Location getUpperCorner() {
        return new Location(this.getWorld(), (double)this.x2, (double)this.y2, (double)this.z2);
    }

    public Location getCenter() {
        return new Location(this.getWorld(), (double)(this.getLowerX() + (this.getUpperX() - this.getLowerX()) / 2), (double)(this.getLowerY() + (this.getUpperY() - this.getLowerY()) / 2), (double)(this.getLowerZ() + (this.getUpperZ() - this.getLowerZ()) / 2));
    }

    public World getWorld() {
        final World world = Bukkit.getWorld(this.worldName);
        if (world == null) {
            throw new IllegalStateException("world '" + this.worldName + "' is not loaded");
        }
        return world;
    }

    public int getSizeX() {
        return this.x2 - this.x1 + 1;
    }

    public int getSizeY() {
        return this.y2 - this.y1 + 1;
    }

    public int getSizeZ() {
        return this.z2 - this.z1 + 1;
    }

    public int getLowerX() {
        return this.x1;
    }

    public int getLowerY() {
        return this.y1;
    }

    public int getLowerZ() {
        return this.z1;
    }

    public int getUpperX() {
        return this.x2;
    }

    public int getUpperY() {
        return this.y2;
    }

    public int getUpperZ() {
        return this.z2;
    }

    public Location[] getCorners() {
        final Location[] res = new Location[4];
        final World w = this.getWorld();
        res[0] = new Location(w, (double)this.x1, 0.0, (double)this.z1);
        res[1] = new Location(w, (double)this.x2, 0.0, (double)this.z1);
        res[2] = new Location(w, (double)this.x2, 0.0, (double)this.z2);
        res[3] = new Location(w, (double)this.x1, 0.0, (double)this.z2);
        return res;
    }

    public Cuboid expand(final CuboidDirection dir, final int amount) {
        switch (dir) {
            case NORTH: {
                return new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case SOUTH: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
            }
            case EASY: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
            }
            case WEST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
            }
            case DOWN: {
                return new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
            }
            case UP: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
            }
            default: {
                throw new IllegalArgumentException("invalid direction " + dir);
            }
        }
    }

    public Cuboid shift(final CuboidDirection dir, final int amount) {
        return this.expand(dir, amount).expand(dir.opposite(), -amount);
    }

    public Cuboid outset(final CuboidDirection dir, final int amount) {
        Cuboid c = null;
        switch (dir) {
            case HORIZONTAL: {
                c = this.expand(CuboidDirection.NORTH, amount).expand(CuboidDirection.SOUTH, amount).expand(CuboidDirection.EASY, amount).expand(CuboidDirection.WEST, amount);
                break;
            }
            case VERTICAL: {
                c = this.expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount);
                break;
            }
            case BOTH: {
                c = this.outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount);
                break;
            }
            default: {
                throw new IllegalArgumentException("invalid direction " + dir);
            }
        }
        return c;
    }

    public Cuboid inset(final CuboidDirection dir, final int amount) {
        return this.outset(dir, -amount);
    }

    public boolean contains(final int x, final int y, final int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    public boolean contains(final int x, final int z) {
        return x >= this.x1 && x <= this.x2 && z >= this.z1 && z <= this.z2;
    }

    public boolean contains(final Location l) {
        return this.worldName.equals(l.getWorld().getName()) && this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    public boolean contains(final Block b) {
        return this.contains(b.getLocation());
    }

    public int volume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }

    public Cuboid getFace(final CuboidDirection dir) {
        switch (dir) {
            case DOWN: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
            }
            case UP: {
                return new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
            }
            case NORTH: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
            }
            case SOUTH: {
                return new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
            }
            case EASY: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
            }
            case WEST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + dir);
            }
        }
    }

    public Cuboid getBoundingCuboid(final Cuboid other) {
        if (other == null) {
            return this;
        }
        final int xMin = Math.min(this.getLowerX(), other.getLowerX());
        final int yMin = Math.min(this.getLowerY(), other.getLowerY());
        final int zMin = Math.min(this.getLowerZ(), other.getLowerZ());
        final int xMax = Math.max(this.getUpperX(), other.getUpperX());
        final int yMax = Math.max(this.getUpperY(), other.getUpperY());
        final int zMax = Math.max(this.getUpperZ(), other.getUpperZ());
        return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(final int x, final int y, final int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public Block getRelativeBlock(final World w, final int x, final int y, final int z) {
        return w.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    public List<Chunk> getChunks() {
        final List<Chunk> chunks = new ArrayList<Chunk>();
        final World w = this.getWorld();
        final int x1 = this.getLowerX() & 0xFFFFFFF0;
        final int x2 = this.getUpperX() & 0xFFFFFFF0;
        final int z1 = this.getLowerZ() & 0xFFFFFFF0;
        final int z2 = this.getUpperZ() & 0xFFFFFFF0;
        for (int x3 = x1; x3 <= x2; x3 += 16) {
            for (int z3 = z1; z3 <= z2; z3 += 16) {
                chunks.add(w.getChunkAt(x3 >> 4, z3 >> 4));
            }
        }
        return chunks;
    }

    public Cuboid[] getWalls() {
        return new Cuboid[] { this.getFace(CuboidDirection.NORTH), this.getFace(CuboidDirection.SOUTH), this.getFace(CuboidDirection.WEST), this.getFace(CuboidDirection.EASY) };
    }

    @Override
    public Iterator<Location> iterator() {
        return new LocationCuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    @Override
    public String toString() {
        return "Cuboid: " + this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    public int getZ1() {
        return this.z1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY2() {
        return this.y2;
    }

    public int getZ2() {
        return this.z2;
    }

    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }

    public void setX1(final int x1) {
        this.x1 = x1;
    }

    public void setY1(final int y1) {
        this.y1 = y1;
    }

    public void setZ1(final int z1) {
        this.z1 = z1;
    }

    public void setX2(final int x2) {
        this.x2 = x2;
    }

    public void setY2(final int y2) {
        this.y2 = y2;
    }

    public void setZ2(final int z2) {
        this.z2 = z2;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cuboid)) {
            return false;
        }
        final Cuboid other = (Cuboid)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getX1() != other.getX1()) {
            return false;
        }
        if (this.getY1() != other.getY1()) {
            return false;
        }
        if (this.getZ1() != other.getZ1()) {
            return false;
        }
        if (this.getX2() != other.getX2()) {
            return false;
        }
        if (this.getY2() != other.getY2()) {
            return false;
        }
        if (this.getZ2() != other.getZ2()) {
            return false;
        }
        final Object this$worldName = this.getWorldName();
        final Object other$worldName = other.getWorldName();
        if (this$worldName == null) {
            if (other$worldName == null) {
                return true;
            }
        }
        else if (this$worldName.equals(other$worldName)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Cuboid;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getX1();
        result = result * 59 + this.getY1();
        result = result * 59 + this.getZ1();
        result = result * 59 + this.getX2();
        result = result * 59 + this.getY2();
        result = result * 59 + this.getZ2();
        final Object $worldName = this.getWorldName();
        result = result * 59 + (($worldName == null) ? 43 : $worldName.hashCode());
        return result;
    }

    public static class LocationCuboidIterator implements Iterator<Location>
    {
        private final World w;
        private final int baseX;
        private final int baseY;
        private final int baseZ;
        private int x;
        private int y;
        private int z;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;

        public LocationCuboidIterator(final World w, final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            final int x3 = 0;
            this.z = x3;
            this.y = x3;
            this.x = x3;
        }

        @Override
        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        @Override
        public Location next() {
            final Location b = new Location(this.w, (double)(this.baseX + this.x), (double)(this.baseY + this.y), (double)(this.baseZ + this.z));
            if (++this.x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return b;
        }

        @Override
        public void remove() {
        }
    }
}