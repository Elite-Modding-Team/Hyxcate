package de.ellpeck.nyx.command;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.lunar.NyxLunarEvent;
import de.ellpeck.nyx.event.solar.NyxSolarEvent;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NyxCommandForce extends CommandBase {
    @Override
    public String getName() {
        return "nyxforce";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.nyx.force.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) throw new WrongUsageException(this.getUsage(sender));
        NyxWorld world = NyxWorld.get(sender.getEntityWorld());
        if (world == null) return;
        if ("clear".equals(args[0])) {
            world.forcedLunarEvent = null;
            world.forcedSolarEvent = null;
            notifyCommandListener(sender, this, "command.nyx.force.clear");
        } else {
            Optional<NyxLunarEvent> eventLunar = world.lunarEvents.stream().filter(e -> e.name.equals(args[0])).findFirst();
            Optional<NyxSolarEvent> eventSolar = world.solarEvents.stream().filter(e -> e.name.equals(args[0])).findFirst();
            if (eventLunar.isPresent()) world.forcedLunarEvent = eventLunar.get();
            else if (eventSolar.isPresent()) world.forcedSolarEvent = eventSolar.get();
            else throw new SyntaxErrorException("command.nyx.force.invalid", args[0]);

            notifyCommandListener(sender, this, "command.nyx.force.success", args[0]);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length != 1) return Collections.emptyList();
        NyxWorld world = NyxWorld.get(sender.getEntityWorld());
        if (world == null) return Collections.emptyList();
        List<String> ret = world.lunarEvents.stream().map(e -> e.name).collect(Collectors.toList());
        ret.addAll(world.solarEvents.stream().map(e -> e.name).collect(Collectors.toList()));
        ret.add("clear");
        return getListOfStringsMatchingLastWord(args, ret);
    }
}
