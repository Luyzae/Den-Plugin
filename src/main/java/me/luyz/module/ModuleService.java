package me.luyz.module;

import me.luyz.module.impl.*;
import me.luyz.*;


public final class ModuleService {

    private static final FileModule fileModule;
    private static final ServiceModule serviceModule;
    private static final ManagerModule managerModule;
    private static final ListenerModule listenerModule;
    private static final CommandModule commandModule;

    public static void start(final Den plugin) {
        for (final Module module : Module.getOrderModules()) {
            module.onEnable(plugin);
        }
    }

    public static void stop() {

    }
    public static void reload() {
        ModuleService.fileModule.reload();
        ModuleService.serviceModule.reload();
        ModuleService.managerModule.reload();
    }

    private ModuleService() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static FileModule getFileModule() {
        return ModuleService.fileModule;
    }

    public static ServiceModule getServiceModule() {
        return ModuleService.serviceModule;
    }

    public static ManagerModule getManagerModule() {
        return ModuleService.managerModule;
    }

    public static ListenerModule getListenerModule() {
        return ModuleService.listenerModule;
    }

    public static CommandModule getCommandModule() {
        return ModuleService.commandModule;
    }

    static {
        fileModule = new FileModule();
        serviceModule = new ServiceModule();
        managerModule = new ManagerModule();
        listenerModule = new ListenerModule();
        commandModule = new CommandModule();
    }
}
