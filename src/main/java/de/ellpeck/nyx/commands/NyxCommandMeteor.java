package de.ellpeck.nyx.commands;

import de.ellpeck.nyx.entities.NyxEntityFallingMeteor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class NyxCommandMeteor extends CommandBase {
    @Override
    public String getName() {
        return "nyxmeteor";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.nyx.meteor.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // set up defaults
        double x = sender.getPosition().getX();
        double z = sender.getPosition().getZ();
        int size = 2;
        int type = 1;
        boolean homing = true;
        // parse arguments
        if (args.length >= 2) {
            x = parseDouble(x, args[0], false);
            z = parseDouble(z, args[1], false);
            if (args.length >= 3) {
                size = parseInt(args[2], 1);
                if (args.length >= 4) {
                    type = parseInt(args[3], 1);
                    if (args.length == 5) {
                        homing = parseBoolean(args[4]);
                    }
                }
            }
        }
        // perform spawning
        BlockPos pos = new BlockPos(x, 0, z);
        NyxEntityFallingMeteor meteor = NyxEntityFallingMeteor.spawn(sender.getEntityWorld(), pos);
        meteor.getDataManager().set(NyxEntityFallingMeteor.SIZE, size);
        meteor.getDataManager().set(NyxEntityFallingMeteor.TYPE, type);
        meteor.homing = homing;
        pos = meteor.getPosition();
        notifyCommandListener(sender, this, "command.nyx.meteor.success", pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) return getTabCompletionCoordinateXZ(args, 0, targetPos);
        if (args.length == 2) return getTabCompletionCoordinateXZ(args, 1, targetPos);
        if (args.length == 3) return getListOfStringsMatchingLastWord(args, "1", "2", "3");
        if (args.length == 4) return getListOfStringsMatchingLastWord(args, "1", "2", "3", "4");
        if (args.length == 5) return getListOfStringsMatchingLastWord(args, "true", "false");
        return Collections.emptyList();
    }
}
