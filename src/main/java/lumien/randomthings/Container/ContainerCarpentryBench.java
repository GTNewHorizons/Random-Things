package lumien.randomthings.Container;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Container.Slots.SlotCarpentryPattern;
import lumien.randomthings.Handler.CarpentryBench.CarpentryManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCarpentryBench extends Container
{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	public IInventory pattern = new InventoryBasic("Pattern", false, 1);

	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private InventoryPlayer playerInventory;

	public ContainerCarpentryBench(InventoryPlayer playerInventory, World worldObj, int posX, int posY, int posZ)
	{
		this.playerInventory = playerInventory;
		this.worldObj = worldObj;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.addSlotToContainer(new SlotCarpentryPattern(this, pattern, 0, 16, 35));
		this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 142, 35));

		for (int l = 0; l < 3; ++l)
		{
			for (int i1 = 0; i1 < 3; ++i1)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, i1 + l * 3, 48 + i1 * 18, 17 + l * 18));
			}
		}

		bindPlayerInventory(playerInventory);
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		this.craftResult.setInventorySlotContents(0, CarpentryManager.instance().findMatchingRecipe(this.pattern.getStackInSlot(0), this.craftMatrix, this.worldObj));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= 11 && par2 < 37)
			{
				if (!this.mergeItemStack(itemstack1, 38, 46, false))
				{
					return null;
				}
			}
			else if (par2 >= 37 && par2 < 46)
			{
				if (!this.mergeItemStack(itemstack1, 11, 38, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 11, 47, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);

		if (!this.worldObj.isRemote)
		{
			ItemStack carpentryPattern = this.pattern.getStackInSlot(0);
			if (carpentryPattern != null)
			{
				par1EntityPlayer.dropPlayerItemWithRandomChoice(carpentryPattern, false);
			}
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

				if (itemstack != null)
				{
					par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	private void bindPlayerInventory(InventoryPlayer playerInventory)
	{
		int l;
		for (l = 0; l < 3; ++l)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(playerInventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getBlock(this.posX, this.posY, this.posZ) != ModBlocks.carpentryBench ? false : par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
	}

}
