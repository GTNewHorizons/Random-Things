package lumien.randomthings.Core.Commands;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import cpw.mods.fml.common.registry.GameData;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Configuration.ConfigItems;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Items.ItemBiomeCapsule;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageNotification;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class RTCommand extends CommandBase
{
	private List<String> aliases;

	public RTCommand()
	{
		aliases = new ArrayList<String>();
		aliases.add("rt");
	}

	@Override
	public String getCommandName()
	{
		return "rt";
	}

	@Override
	public String getCommandUsage(ICommandSender var1)
	{
		return "rt <command>";
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
	{
		return par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName()) || par1ICommandSender.getCommandSenderName().equals(RandomThings.AUTHOR_USERNAME);
	}

	@Override
	public List getCommandAliases()
	{
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender commandUser, String[] args)
	{
		ChatComponentTranslation invalidArguments = new ChatComponentTranslation("text.miscellaneous.invalidArguments");
		invalidArguments.getChatStyle().setColor(EnumChatFormatting.RED);
		if (args.length == 0)
		{
			commandUser.addChatMessage(invalidArguments);
			return;
		}

		String subCommand = args[0];

		if (subCommand.equals("notify"))
		{
			if (args.length < 5)
			{
				commandUser.addChatMessage(invalidArguments);
				return;
			}

			String receiver = args[1];
			String title = args[2];
			String description = args[3];
			String iconString = args[4];

			EntityPlayerMP receiverEntity = getPlayer(commandUser, receiver);
			if (receiverEntity == null)
			{
				throw new PlayerNotFoundException();
			}

			Item i = GameData.getItemRegistry().getObject(iconString);
			Block b = GameData.getBlockRegistry().getObject(iconString);

			int metadata = 0;
			if (args.length > 5)
			{
				metadata = CommandBase.parseInt(commandUser, args[5]);
			}

			ItemStack is = null;

			if (i != null)
			{
				is = new ItemStack(i, 1, metadata);
			}
			else if (b != null)
			{
				is = new ItemStack(b, 1, metadata);
			}
			else
			{
				commandUser.addChatMessage(invalidArguments);
				return;
			}

			MessageNotification packet = new MessageNotification(title, description, is);

			PacketHandler.INSTANCE.sendTo(packet, receiverEntity);
		}
		else if (subCommand.equals("moon"))
		{
			if (args.length < 2)
			{
				commandUser.addChatMessage(invalidArguments);
				return;
			}
			if (args[1].equals("get"))
			{
				commandUser.addChatMessage(new ChatComponentText("Moon Factor: " + commandUser.getEntityWorld().getCurrentMoonPhaseFactor()));
			}
			else if (args[1].equals("set") && args.length > 2)
			{
				int moonPhase = CommandBase.parseInt(commandUser, args[2]);

				if (moonPhase < 0 || moonPhase > 7)
				{
					commandUser.addChatMessage(invalidArguments);
					return;
				}

				commandUser.getEntityWorld().setWorldTime(commandUser.getEntityWorld().provider.getWorldTime() % 24000 + 24000 * moonPhase);
			}
			else
			{
				commandUser.addChatMessage(invalidArguments);
				return;
			}
		}
		else if (subCommand.equals("setItemColor"))
		{
			if (commandUser instanceof EntityPlayer)
			{
				ItemStack is = ((EntityPlayer) commandUser).getCurrentEquippedItem();
				if (is != null && args.length > 1)
				{
					if (is.stackTagCompound == null)
					{
						is.stackTagCompound = new NBTTagCompound();
					}
					is.stackTagCompound.setInteger("customRTColor", Integer.parseInt(args[1]));
				}
			}
		}
		else if (subCommand.equals("setBiomeCapsule"))
		{
			if (commandUser instanceof EntityPlayer)
			{
				Preconditions.checkNotNull(((EntityPlayer) commandUser).getCurrentEquippedItem(), "Currently Equiped Item is not a Biome Capsule");
				Preconditions.checkState(((EntityPlayer) commandUser).getCurrentEquippedItem().getItem() instanceof ItemBiomeCapsule, "Currently Equiped Item is not a Biome Capsule");
				Preconditions.checkPositionIndex(1, args.length, "You have to specify the Biome ID");

				int biomeID = Integer.parseInt(args[1]);
				Preconditions.checkArgument(biomeID >= 0, "Invalid Biome ID (%s)", args[0]);
				ItemStack is = ((EntityPlayer) commandUser).getCurrentEquippedItem();
				is.setItemDamage(biomeID + 1);
			}
		}
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender var1, String[] stringList)
	{
		if (stringList[0].equals(""))
		{
			return getListOfStringsMatchingLastWord(stringList, "notify", "moon", "setItemColor", "spectre" , "setBiomeCapsule");
		}
		else if (stringList[0].equals("notify") && stringList.length == 2)
		{
			return getListOfStringsMatchingLastWord(stringList, getListOfPlayerUsernames());
		}
		else if (stringList[0].equals("moon"))
		{
			if (stringList.length == 3)
			{
				if (stringList[1].equals("set"))
				{
					return getListOfStringsMatchingLastWord(stringList, "0", "1", "2", "3", "4", "5", "6", "7");
				}
			}
			else
			{
				return getListOfStringsMatchingLastWord(stringList, "get", "set");
			}
		}
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
	{
		if (par1ArrayOfStr.length < 2)
		{
			return false;
		}
		else
		{
			if (par1ArrayOfStr[0].equals("notify"))
			{
				return par2 == 1;
			}
		}
		return false;
	}

	protected String[] getListOfPlayerUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
}
