package lumien.randomthings.TileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityItemCollector extends TileEntity
{
	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			Block block = worldObj.getBlock(xCoord, yCoord - 1, zCoord);

			if (block != null)
			{
				TileEntity te = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
				if (te != null && (te instanceof IInventory || te instanceof ISidedInventory))
				{
					AxisAlignedBB bounding = AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord - 2, zCoord - 5, xCoord + 5, yCoord + 2, zCoord + 5);

					List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bounding);

					for (EntityItem ei : items)
					{
						ItemStack rest = TileEntityHopper.func_145889_a((IInventory) te, ei.getEntityItem(), 1);
						if (rest == null)
						{
							ei.setDead();
						}
						else
						{
							ei.setEntityItemStack(rest);
						}
						te.markDirty();
					}
				}
			}
		}
	}
}
