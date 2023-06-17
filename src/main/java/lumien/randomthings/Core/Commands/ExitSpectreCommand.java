package lumien.randomthings.Core.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.RandomThings;

public class ExitSpectreCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "exitspectre";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/exitspectre";
    }

    @Override
    public void processCommand(ICommandSender commandUser, String[] parameter) {
        if (commandUser instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandUser;
            if (player.dimension == Settings.SPECTRE_DIMENSON_ID) {
                RandomThings.instance.spectreHandler.teleportPlayerOutOfSpectreWorld((EntityPlayerMP) player);
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
