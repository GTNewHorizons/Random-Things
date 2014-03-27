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

public class TileEntityLiquidRouter extends TileFluidHandler
{
	HashSet<IFluidHandler> connectedFluidHandler;
	InventoryBasic inventory;
	
	MODE mode;

	public enum MODE
	{
		FILL,DRAIN;
	}

	public TileEntityLiquidRouter()
	{
		mode = MODE.FILL;
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);
		connectedFluidHandler = new HashSet<IFluidHandler>();

		inventory = new InventoryBasic("LiquidRouter", false, 1)
		{
			@Override
			public boolean isItemValidForSlot(int slot, ItemStack par2ItemStack)
			{
				return par2ItemStack.getItem() instanceof ItemFilter && par2ItemStack.getItemDamage() == ItemFilter.FilterType.BLOCK.ordinal();
			}
		};
	}
	
	public MODE getMode()
	{
		return mode;
	}

	public InventoryBasic getInventory()
	{
		return inventory;
	}

	public void updateEntity()
	{
		if (mode == MODE.FILL)
		{
			if (tank.getFluid() == null)
			{
				return;
			}
		}
		else if (mode == MODE.DRAIN)
		{
			if (tank.getCapacity() == tank.getFluidAmount())
			{
				return;
			}
		}

		updateConnectedFluidHandler();

		for (IFluidHandler f : connectedFluidHandler)
		{
			if (mode == MODE.FILL)
			{
				if (tank.getFluid() == null)
				{
					break;
				}
				int fluidBefore = tank.getFluidAmount();
				int fluidRemoved = f.fill(ForgeDirection.UP, tank.getFluid(), true);
				if (fluidRemoved != 0)
				{
					tank.setFluid(new FluidStack(tank.getFluid().fluidID, fluidBefore - fluidRemoved));
				}
			}
			else if (mode == MODE.DRAIN)
			{
				if (tank.getCapacity() == tank.getFluidAmount())
				{
					break;
				}

				FluidStack myFluid = tank.getFluid();
				
				FluidTankInfo[] tankInfo = f.getTankInfo(ForgeDirection.UP);
				if (tankInfo==null || tankInfo.length<1 || tankInfo[0]==null)
				{
					continue;
				}
				
				FluidStack hisFluid = f.getTankInfo(ForgeDirection.UP)[0].fluid;
				if (hisFluid==null)
				{
					continue;
				}

				if (myFluid == null || hisFluid.fluidID == myFluid.fluidID)
				{
					FluidStack drained = f.drain(ForgeDirection.UP, tank.getCapacity() - tank.getFluidAmount(), true);
					this.fill(ForgeDirection.UP, drained, true);
				}
			}
		}

	}

	private void updateConnectedFluidHandler()
	{
		connectedFluidHandler = new HashSet<IFluidHandler>();

		updateConnectedFluidHandler(xCoord + 1, yCoord, zCoord);
		updateConnectedFluidHandler(xCoord - 1, yCoord, zCoord);

		updateConnectedFluidHandler(xCoord, yCoord + 1, zCoord);
		updateConnectedFluidHandler(xCoord, yCoord - 1, zCoord);

		updateConnectedFluidHandler(xCoord, yCoord, zCoord + 1);
		updateConnectedFluidHandler(xCoord, yCoord, zCoord - 1);
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

	private void updateConnectedFluidHandler(int posX, int posY, int posZ)
	{
		if (!isValid(posX, posY, posZ))
		{
			return;
		}
		TileEntity te = worldObj.getTileEntity(posX, posY, posZ);

		if (!connectedFluidHandler.contains(te))
		{
			connectedFluidHandler.add((IFluidHandler) te);

			updateConnectedFluidHandler(posX + 1, posY, posZ);
			updateConnectedFluidHandler(posX - 1, posY, posZ);

			updateConnectedFluidHandler(posX, posY + 1, posZ);
			updateConnectedFluidHandler(posX, posY - 1, posZ);

			updateConnectedFluidHandler(posX, posY, posZ + 1);
			updateConnectedFluidHandler(posX, posY, posZ - 1);
		}
		else
		{
			return;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("mode", mode.ordinal());

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

		this.mode = MODE.values()[nbt.getInteger("mode")];

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

	public void switchMode()
	{
		if (mode==MODE.DRAIN)
		{
			mode=MODE.FILL;
		}
		else if (mode==MODE.FILL)
		{
			mode=MODE.DRAIN;
		}
		
		this.markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
