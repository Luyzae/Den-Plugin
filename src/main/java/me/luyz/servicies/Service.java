package me.luyz.servicies;

import java.util.*;

public abstract class Service {

    private static final List<Service> services;

    public Service() {
        Service.services.add(this);
    }

    public abstract void initialize();

    public static List<Service> getServices() {
        return Service.services;
    }

    static {
        services = new ArrayList<Service>();
    }

}
