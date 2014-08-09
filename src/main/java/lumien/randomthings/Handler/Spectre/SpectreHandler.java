package lumien.randomthings.Handler.Spectre;

import java.util.HashMap;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Configuration.Settings;
import lumien.randomthings.Library.PotionEffects;
import lumien.randomthings.Library.WorldUtils;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

public class SpectreHandler extends WorldSavedData
{
	int nextCoord;
	int spectreDimensionID;

	HashMap<String, Integer> playerConnection;
	World worldObj;

	public SpectreHandler(String s)
	{
		super("SpectreHandler");
		nextCoord = 0;
		playerConnection = new HashMap<String, Integer>();
		spectreDimensionID = Settings.SPECTRE_DIMENSON_ID;
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
		WorldServer spectreWorld = MinecraftServer.getServer().worldServerForDimension(Settings.SPECTRE_DIMENSON_ID);
		if (playerConnection.containsKey(playerName))
		{
			coord = playerConnection.get(playerName);
		}
		else
		{
			coord = nextCoord;
			nextCoord++;
			WorldUtils.generateCube(spectreWorld, coord * 32, 40, 0, coord * 32 + 15, 52, 15, ModBlocks.spectreBlock, 12, 2);
			playerConnection.put(playerName, coord);
			this.markDirty();
		}

		if (player.dimension != Settings.SPECTRE_DIMENSON_ID)
		{
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, Settings.SPECTRE_DIMENSON_ID, new TeleporterSpectre(spectreWorld));
		}

		player.setPositionAndUpdate(coord * 32 + 9 - 1, 42, 2 - 0.5);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("nextCoord", nextCoord);
		nbt.setInteger("spectreDimensionID", spectreDimensionID);
		NBTTagList tagList = new NBTTagList();

		for (String playerName : playerConnection.keySet())
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("playerName", playerName);
			int position = playerConnection.get(playerName);
			compound.setInteger("position", position);
			tagList.appendTag(compound);
		}

		nbt.setTag("playerList", tagList);

		nbt.setBoolean("newVersion", true);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		nextCoord = nbt.getInteger("nextCoord");
		spectreDimensionID = nbt.getInteger("spectreDimensionID");

		NBTTagList tagList = nbt.getTagList("playerList", 10);

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound compound = tagList.getCompoundTagAt(i);

			String playerName = compound.getString("playerName");
			int position = compound.getInteger("position");
			playerConnection.put(playerName, position);
		}

		// Old Version PATCH
		boolean newVersion = nbt.getBoolean("newVersion");
		if (!newVersion)
		{
			reset();
			RandomThings.instance.logger.log(Level.WARN, "The Spectre World got an update in the recent version so i had to \"reset\" the spectre world.");
			RandomThings.instance.logger.log(Level.WARN, "I would recommend to also reset the spectre world itself because the \"old\" cubes are still where they were");
			RandomThings.instance.logger.log(Level.WARN, "Also if there's still a player in the old spectre world you should either move him out or change the dimensionID to 32 in the config file");
			RandomThings.instance.logger.log(Level.WARN, "If you don't this will crash in a second!! :(");
		}

		if (spectreDimensionID != Settings.SPECTRE_DIMENSON_ID)
		{
			RandomThings.instance.logger.log(Level.WARN, "Resetting Spectre World because dimensionID changed (" + spectreDimensionID + "->" + Settings.SPECTRE_DIMENSON_ID + ")");
			spectreDimensionID = Settings.SPECTRE_DIMENSON_ID;
			reset();
			this.markDirty();
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

			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, oldDimension, new TeleporterSpectre(MinecraftServer.getServer().worldServerForDimension(oldDimension)));
			player.setPositionAndUpdate(oldPosX, oldPosY, oldPosZ);
		}
		else
		{
			ChunkCoordinates cc = MinecraftServer.getServer().worldServerForDimension(0).provider.getRandomizedSpawnPoint();
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterSpectre(MinecraftServer.getServer().worldServerForDimension(0)));

			player.setPositionAndUpdate(cc.posX, cc.posY, cc.posZ);
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
						AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(coord * 32, 40, 0, coord * 32 + 15, 52, 15);

						if (!bb.isVecInside(Vec3.createVectorHelper(player.posX, player.posY, player.posZ)))
						{
							player.setPositionAndUpdate(coord * 32 + 9 - 1, 42, 2 - 0.5);
							player.addPotionEffect(new PotionEffect(PotionEffects.SLOWNESS, 200, 5, false));
						}
					}
					else
					{
						ChunkCoordinates cc = MinecraftServer.getServer().worldServerForDimension(0).provider.getRandomizedSpawnPoint();
						MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, 0, new TeleporterSpectre(MinecraftServer.getServer().worldServerForDimension(0)));

						player.setPositionAndUpdate(cc.posX, cc.posY, cc.posZ);
					}
				}
			}
		}
	}

	public void reset()
	{
		playerConnection = new HashMap<String, Integer>();
		nextCoord = 0;
		this.markDirty();
	}

}
