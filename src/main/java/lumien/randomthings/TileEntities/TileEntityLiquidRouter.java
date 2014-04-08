package lumien.randomthings.TileEntities;

import java.util.HashSet;

import lumien.randomthings.Items.ItemFilter;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.TileFluidHandler;

public class TileEntityLiquidRouter extends TileEntity implements IFluidHandler
{
	InventoryBasic inventory;

	public TileEntityLiquidRouter()
	{
		inventory = new InventoryBasic("LiquidRouter", false, 1)
		{
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack par2ItemStack)
			{
				return par2ItemStack.getItem() instanceof ItemFilter && par2ItemStack.getItemDamage() == ItemFilter.FilterType.BLOCK.ordinal();
			}
		};
	}

	public InventoryBasic getInventory()
	{
		return inventory;
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
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		NBTTagList inventoryTag = new NBTTagList();

		for (int slot = 0; slot < inventory.getSizeInventory(); slot++)
		{
			NBTTagCompound slotTag = new NBTTagCompound();
			slotTag.setInteger("slot", slot);
			if (inventory.getStackInSlot(slot) != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				slotTag.setTag("item", inventory.getStackInSlot(slot).writeToNBT(itemTag));
			}
			inventoryTag.appendTag(slotTag);
		}
		nbt.setTag("inventory", inventoryTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		NBTTagList inventoryTag = (NBTTagList) nbt.getTag("inventory");

		for (int slot = 0; slot < inventoryTag.tagCount(); slot++)
		{
			NBTTagCompound slotTag = inventoryTag.getCompoundTagAt(slot);
			int slotIndex = slotTag.getInteger("slot");
			if (slotTag.hasKey("item"))
			{
				inventory.setInventorySlotContents(slotIndex, ItemStack.loadItemStackFromNBT((NBTTagCompound) slotTag.getTag("item")));
			}
			else
			{
				inventory.setInventorySlotContents(slotIndex, null);
			}
		}
	}
	
	public void getTankInfos(HashSet<FluidTankInfo> tankInfos)
	{
		findTankInfo(tankInfos,xCoord+1,yCoord,zCoord);
		findTankInfo(tankInfos,xCoord-1,yCoord,zCoord);
		
		findTankInfo(tankInfos,xCoord,yCoord+1,zCoord);
		findTankInfo(tankInfos,xCoord,yCoord-1,zCoord);
		
		findTankInfo(tankInfos,xCoord,yCoord,zCoord+1);
		findTankInfo(tankInfos,xCoord,yCoord,zCoord-1);
	}
	
	private boolean isValid(int posX, int posY, int posZ)
	{
		TileEntity te = worldObj.getTileEntity(posX, posY, posZ);
		if (te == null || !(te instanceof IFluidHandler))
		{
			return false;
		}
		if (te instanceof TileEntityLiquidRouter)
		{
			return false;
		}
		if (inventory.getStackInSlot(0) != null)
		{
			if (!ItemFilter.matchesBlock(inventory.getStackInSlot(0), worldObj.getBlock(posX, posY, posZ), worldObj.getBlockMetadata(posX, posY, posZ)))
			{
				return false;
			}
		}

		return true;
	}

	private void findTankInfo(HashSet<FluidTankInfo> tankInfos, int posX, int posY, int posZ)
	{
		if (!isValid(posX, posY, posZ))
		{
			return;
		}
		FluidTankInfo[] foundTankInfos = ((IFluidHandler) worldObj.getTileEntity(posX, posY, posZ)).getTankInfo(ForgeDirection.NORTH);

		boolean addedTank = false;
		for (FluidTankInfo f : foundTankInfos)
		{
			if (!tankInfos.contains(f))
			{
				addedTank = true;
				tankInfos.add(f);
			}
		}

		if (addedTank)
		{
			findTankInfo(tankInfos, posX + 1, posY, posZ);
			findTankInfo(tankInfos, posX - 1, posY, posZ);

			findTankInfo(tankInfos, posX, posY + 1, posZ);
			findTankInfo(tankInfos, posX, posY - 1, posZ);

			findTankInfo(tankInfos, posX, posY, posZ + 1);
			findTankInfo(tankInfos, posX, posY, posZ - 1);
		}
		else
		{
			return;
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		HashSet<FluidTankInfo> infos = new HashSet<FluidTankInfo>();
		getTankInfos(infos);
		return infos.toArray(new FluidTankInfo[1]);
	}
}
