package lumien.randomthings.Core.Commands;

import com.google.common.base.Preconditions;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Collections;
import java.util.List;
import lumien.randomthings.Handler.Bloodmoon.ServerBloodmoonHandler;
import lumien.randomthings.Items.ItemBiomeCapsule;
import lumien.randomthings.Library.Reference;
import lumien.randomthings.Network.Messages.MessageNotification;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.RandomThings;
import net.minecraft.block.Block;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.oredict.OreDictionary;

public class RTCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return Reference.MOD_NAME.toLowerCase();
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return getCommandName() + " <command>";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        return par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())
                || par1ICommandSender.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME);
    }

    @Override
    public List getCommandAliases() {
        return Collections.singletonList("rt");
    }

    @Override
    public void processCommand(ICommandSender commandUser, String[] args) {
        ChatComponentText invalidArguments =
                new ChatComponentText("/rt <notify|moon|setItemColor|setBiomeCapsule|analyze|bloodmoon>");
        invalidArguments.getChatStyle().setColor(EnumChatFormatting.RED);
        if (args.length == 0) {
            commandUser.addChatMessage(invalidArguments);
            return;
        }

        String subCommand = args[0];

        if (subCommand.equals("notify")) {
            invalidArguments = new ChatComponentText(
                    "/rt notify <username> <title> <description> <displayduration> <iconstring> [metadata]");
            if (args.length < 6) {
                throw new WrongUsageException(invalidArguments.getFormattedText());
            }

            String receiver = args[1];
            String title = args[2];
            String description = args[3];
            int duration = CommandBase.parseInt(commandUser, args[4]);
            String iconString = args[5];

            if (duration <= 0) {
                throw new CommandException("Display Duration has to be > 0");
            }

            EntityPlayerMP receiverEntity = getPlayer(commandUser, receiver);
            if (receiverEntity == null) {
                throw new PlayerNotFoundException();
            }

            Item i = GameData.getItemRegistry().getObject(iconString);
            Block b = GameData.getBlockRegistry().getObject(iconString);

            int metadata = 0;
            if (args.length > 6) {
                metadata = CommandBase.parseInt(commandUser, args[6]);
            }

            ItemStack is;

            if (i != null) {
                is = new ItemStack(i, 1, metadata);
            } else if (b != null) {
                is = new ItemStack(b, 1, metadata);
            } else {
                throw new WrongUsageException(invalidArguments.getFormattedText());
            }

            MessageNotification packet = new MessageNotification(title, description, duration, is);

            PacketHandler.INSTANCE.sendTo(packet, receiverEntity);
        } else if (subCommand.equals("moon")) {
            if (args.length < 2) {
                commandUser.addChatMessage(invalidArguments);
                return;
            }
            if (args[1].equals("get")) {
                commandUser.addChatMessage(new ChatComponentText(
                        "Moon Factor: " + commandUser.getEntityWorld().getCurrentMoonPhaseFactor()));
            } else if (args[1].equals("set") && args.length > 2) {
                int moonPhase = CommandBase.parseInt(commandUser, args[2]);

                if (moonPhase < 0 || moonPhase > 7) {
                    commandUser.addChatMessage(invalidArguments);
                    return;
                }

                commandUser
                        .getEntityWorld()
                        .setWorldTime(commandUser.getEntityWorld().provider.getWorldTime() % 24000 + 24000 * moonPhase);
            } else {
                commandUser.addChatMessage(invalidArguments);
            }
        } else if (subCommand.equals("setItemColor")) {
            if (commandUser instanceof EntityPlayer) {
                ItemStack is = ((EntityPlayer) commandUser).getCurrentEquippedItem();
                if (is != null && args.length > 1) {
                    if (is.stackTagCompound == null) {
                        is.stackTagCompound = new NBTTagCompound();
                    }
                    is.stackTagCompound.setInteger("customRTColor", Integer.parseInt(args[1]));
                }
            }
        } else if (subCommand.equals("setBiomeCapsule")) {
            if (commandUser instanceof EntityPlayer) {
                Preconditions.checkNotNull(
                        ((EntityPlayer) commandUser).getCurrentEquippedItem(),
                        "Currently Equiped Item is not a Biome Capsule");
                Preconditions.checkState(
                        ((EntityPlayer) commandUser).getCurrentEquippedItem().getItem() instanceof ItemBiomeCapsule,
                        "Currently Equiped Item is not a Biome Capsule");
                Preconditions.checkPositionIndex(1, args.length, "You have to specify the Biome ID");

                int biomeID = Integer.parseInt(args[1]);
                Preconditions.checkArgument(biomeID >= 0, "Invalid Biome ID (%s)", args[0]);
                ItemStack is = ((EntityPlayer) commandUser).getCurrentEquippedItem();
                if (BiomeGenBase.getBiome(biomeID) != null) {
                    is.setItemDamage(biomeID + 1);
                    if (is.stackTagCompound == null) {
                        is.stackTagCompound = new NBTTagCompound();
                    }
                } else {
                    commandUser.addChatMessage(new ChatComponentText("This Biome does not exist")
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                }
            }
        } else if (subCommand.equals("spectre")) {
            if (commandUser instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) commandUser;

                RandomThings.instance.spectreHandler.sendOperator(player, args[1]);
            }
        } else if (subCommand.equals("analyze")) {
            if (commandUser instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) commandUser;
                ItemStack item = player.getCurrentEquippedItem();
                if (item != null) {
                    player.addChatMessage(new ChatComponentText("Start"));
                    player.addChatMessage(new ChatComponentText("Display-Name: " + item.getDisplayName()));
                    player.addChatMessage(new ChatComponentText(
                            "Class-Name: " + item.getItem().getClass()));
                    player.addChatMessage(new ChatComponentText("Metadata: " + item.getItemDamage()));
                    player.addChatMessage(
                            new ChatComponentText("NBT: " + (item.stackTagCompound != null ? "Yes" : "No")));
                    player.addChatMessage(new ChatComponentText(
                            "Registered-Name: \"" + GameRegistry.findUniqueIdentifierFor(item.getItem()) + "\""));
                    player.addChatMessage(new ChatComponentText("Ore-Dictionary:"));
                    int[] ids = OreDictionary.getOreIDs(item);
                    for (int id : ids) {
                        player.addChatMessage(new ChatComponentText(" -" + OreDictionary.getOreName(id)));
                    }
                    player.addChatMessage(new ChatComponentText("End"));
                }
            }
        } else if (subCommand.equals("bloodmoon")) {
            ServerBloodmoonHandler.INSTANCE.force();
            commandUser.addChatMessage(new ChatComponentTranslation("text.bloodmoon.command", new Object[0]));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] stringList) {
        if (stringList.length == 1) {
            return getListOfStringsMatchingLastWord(
                    stringList,
                    "notify",
                    "moon",
                    "setItemColor",
                    "spectre",
                    "setBiomeCapsule",
                    "spectre",
                    "analyze",
                    "bloodmoon");
        } else if (stringList[0].equals("notify") && stringList.length == 2) {
            return getListOfStringsMatchingLastWord(stringList, getListOfPlayerUsernames());
        } else if (stringList[0].equals("moon")) {
            if (stringList.length == 3) {
                if (stringList[1].equals("set")) {
                    return getListOfStringsMatchingLastWord(stringList, "0", "1", "2", "3", "4", "5", "6", "7");
                }
            } else {
                return getListOfStringsMatchingLastWord(stringList, "get", "set");
            }
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        if (par1ArrayOfStr.length < 2) {
            return false;
        } else {
            if (par1ArrayOfStr[0].equals("notify")) {
                return par2 == 1;
            }
        }
        return false;
    }

    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
