package me.luyz.utilities;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import org.bukkit.util.io.*;
import java.io.*;

public final class BukkitUtil {

    public static String getLocation(final Location location) {

        if(location == null){
            return null;
        }
        return  location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + " ("+ location.getWorld().getName() + ")";
    }

    public static String serializeLocation(final Location location) {
        if(location == null){
            return null;
        }
        return location.getWorld().getName()+ ", " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getYaw()+ ", " + location.getPitch();
    }

    public static Location deserializeLocation(final String data) {

        if(data == null){
            return null;
        }
        final String[] splittedData = data.split(", ");
        if(splittedData.length < 6){
            return null;
        }
        final World world = Bukkit.getWorld(splittedData[0]);
        final double x = Double.parseDouble(splittedData[1]);
        final double y = Double.parseDouble(splittedData[2]);
        final double z = Double.parseDouble(splittedData[3]);
        final float yaw = Float.parseFloat(splittedData[4]);
        final float pitch = Float.parseFloat(splittedData[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String serializeItemStackArray(final ItemStack[] items) {

        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(items.length);
            for (final ItemStack item : items) {
                dataOutput.writeObject((Object)item);
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to serialize ItemStackArray.", e);
        }
    }

    public static ItemStack[] deserializeItemStackArray(final String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; ++i) {
                items[i] = (ItemStack)dataInput.readObject();
            }
            dataInput.close();
            return items;
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize ItemStackArray.", e);
        }
    }

    public static String serializeItemStack(final ItemStack itemStack) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeObject((Object)itemStack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to serialize ItemStack.", e);
        }
    }

    public static ItemStack deserializeItemStack(final String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final ItemStack itemStack = (ItemStack)dataInput.readObject();
            dataInput.close();
            return itemStack;
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize ItemStack.", e);
        }
    }

    private BukkitUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


}
