package lumien.randomthings.TileEntities;

import java.util.HashSet;

import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Blocks.ItemBlocks.ItemBlockWirelessLever;
import lumien.randomthings.Configuration.Settings;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWirelessLever extends TileEntity
{
	public static HashSet<TileEntityWirelessLever> loadedLevers = new HashSet<TileEntityWirelessLever>();

	int targetX, targetY, targetZ;

	@Override
	public void validate()
	{
		super.validate();

		loadedLevers.add(this);
		//this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateEntity()
	{
		if (worldObj.isRemote && worldObj.getTotalWorldTime() % 2 == 0)
		{
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBlockWirelessLever)
			{
				Vector3f vec = new Vector3f(targetX - xCoord, targetY - yCoord, targetZ - zCoord);
				for (double d = 0; d <= 1; d += 0.02d)
				{
					EntityReddustFX particle = new EntityReddustFX(this.worldObj, xCoord + 0.5 + vec.x * d, yCoord + 0.5 + vec.y * d, zCoord + 0.5 + vec.z * d, 0, 0, 0);
					if ((this.blockMetadata & 8) == 0)
					{
						particle.setRBGColorF(0.7f, 0.6f, 0.6f);
					}
					Minecraft.getMinecraft().effectRenderer.addEffect(particle);
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        this.targetX = nbt.getInteger("targetX");
        this.targetY = nbt.getInteger("targetY");
        this.targetZ = nbt.getInteger("targetZ");
    }
    
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        nbt.setInteger("targetX", targetX);
        nbt.setInteger("targetY", targetY);
        nbt.setInteger("targetZ", targetZ);
    }

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void invalidate()
	{
		super.invalidate();

		loadedLevers.remove(this);
		this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
	}

	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
	{
		return (oldBlock != newBlock);
	}

	public static boolean isPowered(World worldObj, int posX, int posY, int posZ)
	{
		for (TileEntityWirelessLever te : loadedLevers)
		{
			if (te.worldObj == worldObj && te.targetX == posX && te.targetY == posY && te.targetZ == posZ && (te.worldObj.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord) & 8) > 0)
			{
				double distance = Math.sqrt((te.xCoord - te.targetX) * (te.xCoord - te.targetX) + (te.yCoord - te.targetY) * (te.yCoord - te.targetY) + (te.zCoord - te.targetZ) * (te.zCoord - te.targetZ));
				if (distance <= Settings.WIRELESSLEVER_RANGE)
				{
					return true;
				}
			}
		}

		return false;
	}

	public void setTarget(int x, int y, int z)
	{
		targetX = x;
		targetY = y;
		targetZ = z;

		updateTarget();
	}

	public void updateTarget()
	{
		this.worldObj.notifyBlockOfNeighborChange(targetX, targetY, targetZ, ModBlocks.wirelessLever);
	}

	public int getTargetX()
	{
		return targetX;
	}

	public int getTargetY()
	{
		return targetY;
	}

	public int getTargetZ()
	{
		return targetZ;
	}
}
