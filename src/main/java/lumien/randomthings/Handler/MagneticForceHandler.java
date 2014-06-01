package lumien.randomthings.Handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.Particle.ParticleMagneticForce;
import lumien.randomthings.Network.PacketHandler;
import lumien.randomthings.Network.Messages.MessageMagneticForceParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class MagneticForceHandler
{
	public static int TELEPORT_LENGTH = 200; // 10 Seconds
	public static MagneticForceHandler INSTANCE = new MagneticForceHandler();
	ArrayList<MagneticForceEvent> events;

	public MagneticForceHandler()
	{
		this.events = new ArrayList<MagneticForceEvent>();
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = nbt.getTagList("magneticForces", 9);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			MagneticForceEvent event = new MagneticForceEvent();
			event.readFromNBT(tag);
			events.add(event);
		}
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList tagList = new NBTTagList();
		for (MagneticForceEvent event : events)
		{
			NBTTagCompound tag = new NBTTagCompound();
			event.writeToNBT(tag);
			tagList.appendTag(tag);
		}
		nbt.setTag("magneticForces", tagList);
	}

	public void addEvent(String user, String target)
	{
		events.add(new MagneticForceEvent(user, target));
		writeToNBT(RandomThings.instance.modNBT);
		RandomThings.instance.saveNBT();
	}

	private class MagneticForceEvent
	{
		String user;
		String target;
		int progress;

		float f;

		public MagneticForceEvent(String user, String target)
		{
			this.user = user;
			this.target = target;
			this.progress = TELEPORT_LENGTH;
			f = 0;
		}

		public MagneticForceEvent()
		{

		}

		public void readFromNBT(NBTTagCompound nbt)
		{
			this.user = nbt.getString("user");
			this.target = nbt.getString("target");
			this.progress = nbt.getInteger("progress");
		}

		public void writeToNBT(NBTTagCompound nbt)
		{
			nbt.setString("user", user);
			nbt.setString("target", target);
			nbt.setInteger("progress", progress);
		}

		public boolean tick()
		{
			this.progress--;
			if (this.progress<=0)
			{
				teleport();
				return true;
			}
			else
			{
				f+=0.01f;
				EntityPlayerMP userEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(user);
				EntityPlayerMP targetEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(target);
				if (userEntity!=null)
				{
					Set<EntityPlayer> trackingUser = ((WorldServer)userEntity.worldObj).getEntityTracker().getTrackingPlayers(userEntity);
					PacketHandler.INSTANCE.sendTo(new MessageMagneticForceParticle(userEntity.getEntityId(),userEntity.worldObj.provider.dimensionId,f), (EntityPlayerMP) userEntity);
					for (EntityPlayer player:trackingUser)
					{
						PacketHandler.INSTANCE.sendTo(new MessageMagneticForceParticle(userEntity.getEntityId(),userEntity.worldObj.provider.dimensionId,f), (EntityPlayerMP) player);
					}
				}
				if (targetEntity!=null)
				{
					Set<EntityPlayer> trackingTarget = ((WorldServer)userEntity.worldObj).getEntityTracker().getTrackingPlayers(targetEntity);
					PacketHandler.INSTANCE.sendTo(new MessageMagneticForceParticle(targetEntity.getEntityId(),targetEntity.worldObj.provider.dimensionId,f), (EntityPlayerMP) targetEntity);
					
					for (EntityPlayer player:trackingTarget)
					{
						PacketHandler.INSTANCE.sendTo(new MessageMagneticForceParticle(targetEntity.getEntityId(),targetEntity.worldObj.provider.dimensionId,f), (EntityPlayerMP) player);
					}
				}
					
				
				return false;
			}
		}

		private void teleport()
		{
			EntityPlayerMP userEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(user);
			EntityPlayerMP targetEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(target);

			if (userEntity != null && targetEntity != null)
			{
				userEntity.mountEntity(null);
				if (userEntity.worldObj.provider.dimensionId != targetEntity.worldObj.provider.dimensionId)
				{
					userEntity.travelToDimension(targetEntity.worldObj.provider.dimensionId);
				}
				userEntity.playerNetServerHandler.setPlayerLocation(targetEntity.posX, targetEntity.posY, targetEntity.posZ, targetEntity.rotationYaw, targetEntity.rotationPitch);
			}
		}
	}

	public void update()
	{
		int size = events.size();
		Iterator<MagneticForceEvent> iterator = events.iterator();
		while (iterator.hasNext())
		{
			MagneticForceEvent event = iterator.next();
			if (event.tick())
			{
				iterator.remove();
			}
		}
		if (size != events.size())
		{
			writeToNBT(RandomThings.instance.modNBT);
			RandomThings.instance.saveNBT();
		}
	}

}
