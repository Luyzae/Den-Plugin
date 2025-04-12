package me.luyz.utilities.command;

import me.luyz.module.*;
import java.util.*;

public abstract class BaseCommand
{
    public abstract void onCommand(final CommandArgs p0);

    public BaseCommand() {
        ModuleService.getManagerModule().getCommandManager().registerCommands(this, null);
    }

    public BaseCommand(final String command) {
        ModuleService.getManagerModule().getCommandManager().registerCommands(this, null, command);
    }
}