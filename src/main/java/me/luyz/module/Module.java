package me.luyz.module;

import me.luyz.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class Module {

    private String name;
    private int priority;
    private static final List<Module> modules;

    public Module() {
        Module.modules.add(this);
    }

    public static List<Module> getOrderModules() {
        final List<Module> modules = getModules();
        modules.sort(Comparator.comparingInt(Module::getPriority));
        return modules;
    }

    public abstract void onEnable(final Den p0);

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }

    public static List<Module> getModules() {
        return Module.modules;
    }

    static {
        modules = Lists.newArrayList();
    }
}