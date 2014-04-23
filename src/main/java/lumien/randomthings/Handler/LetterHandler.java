package lumien.randomthings.Handler;

import java.util.ArrayList;
import java.util.Iterator;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.WorldUtils;
import lumien.randomthings.Network.Packets.PacketNotification;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

public class LetterHandler
{
	ArrayList<ItemStack> waitingLetters;

	int tickCounter;
	final int tickRate;

	public LetterHandler()
	{
		waitingLetters = new ArrayList<ItemStack>();

		tickCounter = 0;
		tickRate = 20;
	}

	public void addLetter(ItemStack letter)
	{
		waitingLetters.add(letter);
		writeToNBT();
		RandomThings.instance.saveNBT();
	}

	public void update()
	{
		tickCounter++;
		if (tickCounter >= tickRate)
		{
			this.tickCounter = 0;
			Iterator<ItemStack> iterator = waitingLetters.iterator();
			while (iterator.hasNext())
			{
				ItemStack toCheck = iterator.next();

				String receiver = toCheck.stackTagCompound.getString("receiver");
				if (WorldUtils.isPlayerOnline(receiver))
				{
					EntityPlayer receiverEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(receiver);

					EntityItem entityItem = new EntityItem(receiverEntity.worldObj, receiverEntity.posX, receiverEntity.posY, receiverEntity.posZ, toCheck);
					entityItem.delayBeforeCanPickup = 0;

					entityItem.worldObj.spawnEntityInWorld(entityItem);
					iterator.remove();
					writeToNBT();
					RandomThings.instance.saveNBT();

					RandomThings.packetPipeline.sendTo(new PacketNotification("Received Ender Letter", "Sender: " + toCheck.stackTagCompound.getString("sender"), new ItemStack(ModItems.enderLetter, 1, 1)), (EntityPlayerMP) receiverEntity);
				}
			}
		}
	}

	public void writeToNBT()
	{
		NBTTagList letters = new NBTTagList();

		for (ItemStack is : waitingLetters)
		{
			NBTTagCompound itemNBT = new NBTTagCompound();
			is.writeToNBT(itemNBT);
			letters.appendTag(itemNBT);
		}
		RandomThings.instance.modNBT.setTag("letters", letters);
	}

	public void readFromNBT()
	{
		NBTTagCompound nbt = RandomThings.instance.modNBT;
		NBTTagList letters = nbt.getTagList("letters", 10);

		for (int index = 0; index < letters.tagCount(); index++)
		{
			NBTTagCompound tag = letters.getCompoundTagAt(index);
			ItemStack letter = ItemStack.loadItemStackFromNBT(tag);
			waitingLetters.add(letter);
		}
	}
}
