package lumien.randomthings.Handler.Spectre;

import java.util.HashMap;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Library.PotionEffects;
import lumien.randomthings.Library.WorldUtils;

import org.apache.commons.lang3.tuple.ImmutablePair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

public class SpectreHandler extends WorldSavedData
{
	int nextCoord;

	HashMap<String, Integer> playerConnection;
	World worldObj;

	public SpectreHandler(String s)
	{
		super("SpectreHandler");
		nextCoord = 0;
		playerConnection = new HashMap<String, Integer>();
	}

	public SpectreHandler()
	{
		this("SpectreHandler");
	}

	public void setWorld(World w)
	{
		this.worldObj = w;
	}

	public void teleportPlayerToSpectreWorld(EntityPlayerMP player)
	{
		String playerName = player.getCommandSenderName();
		int coord = 0;
		WorldServer spectreWorld = MinecraftServer.getServer().worldServerForDimension(32);
		if (playerConnection.containsKey(playerName))
		{
			coord = playerConnection.get(playerName);
		}
		else
		{
			coord = nextCoord;
			nextCoord++;
			WorldUtils.generateCube(spectreWorld, coord * 32, 4, 0, coord * 32 + 15, 16, 15, ModBlocks.spectreBlock);
			playerConnection.put(playerName, coord);
			this.markDirty();
		}

		if (player.dimension != 32)
		{
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, 32, new TeleporterSpectre(spectreWorld));
		}

		player.setPositionAndUpdate(coord * 32 + 9 - 1, 6, 2 - 0.5);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("nextCoord", nextCoord);

		NBTTagList tagList = new NBTTagList();

		for (String playerName : playerConnection.keySet())
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("playerName", playerName);
			int coord = playerConnection.get(playerName);
			compound.setInteger("position", coord);
			tagList.appendTag(compound);
		}

		nbt.setTag("playerList", tagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		nextCoord = nbt.getInteger("nextCoord");

		NBTTagList tagList = nbt.getTagList("playerList", 10);

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound compound = tagList.getCompoundTagAt(i);

			String playerName = compound.getString("playerName");
			int coord = compound.getInteger("coord");
			playerConnection.put(playerName, coord);
		}
	}

	public void teleportPlayerOutOfSpectreWorld(EntityPlayerMP player)
	{
		if (player.getEntityData().hasKey("oldPosX"))
		{
			int oldDimension = player.getEntityData().getInteger("oldDimension");
			double oldPosX = player.getEntityData().getDouble("oldPosX");
			double oldPosY = player.getEntityData().getDouble("oldPosY");
			double oldPosZ = player.getEntityData().getDouble("oldPosZ");

			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, oldDimension, new TeleporterSpectre((WorldServer) player.worldObj));
			player.setPositionAndUpdate(oldPosX, oldPosY, oldPosZ);
		}
		else
		{
			MinecraftServer.getServer().getConfigurationManager().respawnPlayer((EntityPlayerMP) player, 0, false);
		}
	}

	public void update()
	{
		if (this.worldObj.getTotalWorldTime() % 40 == 0)
		{
			for (int i = 0; i < worldObj.playerEntities.size(); i++)
			{
				if (worldObj.playerEntities.get(i) instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) worldObj.playerEntities.get(i);

					String username = player.getCommandSenderName();
					if (playerConnection.containsKey(username))
					{
						int coord = playerConnection.get(username);
						AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(coord * 32, 4, 0, coord * 32 + 15, 256, 15);

						if (!bb.isVecInside(Vec3.createVectorHelper(player.posX, player.posY, player.posZ)))
						{
							player.setPositionAndUpdate(coord * 32 + 9 - 1, 6, 2 - 0.5);
							player.addPotionEffect(new PotionEffect(PotionEffects.SLOWNESS, 200, 5, false));
						}
					}
					else
					{
						MinecraftServer.getServer().getConfigurationManager().respawnPlayer((EntityPlayerMP) player, 0, false);
					}
				}
			}
		}
	}

}
