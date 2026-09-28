package mod.emt.hyxcate.command;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.api.celestialevent.HyxcateLunarEvent;
import mod.emt.hyxcate.api.celestialevent.HyxcateSolarEvent;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HyxcateCommandForce extends CommandBase {
    @Override
    public String getName() {
        return "hyxcateforce";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("hyxforce");
        return aliases;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.hyxcate.force.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) throw new WrongUsageException(this.getUsage(sender));
        HyxcateWorld world = HyxcateWorld.get(sender.getEntityWorld());
        if (world == null) return;
        if ("clear".equals(args[0])) {
            world.forcedLunarEvent = null;
            world.forcedSolarEvent = null;
            notifyCommandListener(sender, this, "command.hyxcate.force.clear");
        } else {
            Optional<HyxcateLunarEvent> eventLunar = world.lunarEvents.stream().filter(e -> e.name.equals(args[0])).findFirst();
            Optional<HyxcateSolarEvent> eventSolar = world.solarEvents.stream().filter(e -> e.name.equals(args[0])).findFirst();
            if (eventLunar.isPresent()) world.forcedLunarEvent = eventLunar.get();
            else if (eventSolar.isPresent()) world.forcedSolarEvent = eventSolar.get();
            else throw new SyntaxErrorException("command.hyxcate.force.invalid", args[0]);

            notifyCommandListener(sender, this, "command.hyxcate.force.success", args[0]);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length != 1) return Collections.emptyList();
        HyxcateWorld world = HyxcateWorld.get(sender.getEntityWorld());
        if (world == null) return Collections.emptyList();
        List<String> ret = world.lunarEvents.stream().map(e -> e.name).collect(Collectors.toList());
        ret.addAll(world.solarEvents.stream().map(e -> e.name).collect(Collectors.toList()));
        ret.add("clear");
        return getListOfStringsMatchingLastWord(args, ret);
    }
}
