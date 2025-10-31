package de.ellpeck.nyx.commands;

import de.ellpeck.nyx.entities.NyxEntityFallingMeteor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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
        if (args.length != 0 && args.length != 2 && args.length != 3 && args.length != 4 && args.length != 5)
            throw new WrongUsageException(this.getUsage(sender));

        double x = sender.getPosition().getX();
        double z = sender.getPosition().getZ();
        Integer size = null;
        Integer type = null;
        boolean homing = false;
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

        BlockPos pos = new BlockPos(x, 0, z);
        NyxEntityFallingMeteor meteor = NyxEntityFallingMeteor.spawn(sender.getEntityWorld(), pos);
        if (size != null) meteor.getDataManager().set(NyxEntityFallingMeteor.SIZE, size);
        if (type != null) meteor.getDataManager().set(NyxEntityFallingMeteor.TYPE, type);
        meteor.homing = homing;
        pos = meteor.getPosition();
        notifyCommandListener(sender, this, "command.nyx.meteor.success", pos.getX(), pos.getY(), pos.getZ());
    }
}
