package lumien.randomthings.TileEntities;

import java.util.List;

import lumien.randomthings.Client.Renderer.RenderItemCollector;

import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;

public class TileEntityItemCollector extends TileEntity
{
	private final int range = 2;

	private int tickRate = 20;
	private int tickCounter = 0;

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			tickCounter++;
			if (tickCounter >= tickRate)
			{
				tickCounter=0;
				int targetX, targetY, targetZ;

				EnumFacing facing = BlockDispenser.func_149937_b(Facing.oppositeSide[worldObj.getBlockMetadata(xCoord, yCoord, zCoord)]);

				targetX = xCoord + facing.getFrontOffsetX();
				targetY = yCoord + facing.getFrontOffsetY();
				targetZ = zCoord + facing.getFrontOffsetZ();

				Block block = worldObj.getBlock(targetX, targetY, targetZ);

				if (block != null)
				{
					TileEntity te = worldObj.getTileEntity(targetX, targetY, targetZ);
					if (te != null && (te instanceof IInventory || te instanceof ISidedInventory))
					{
						AxisAlignedBB bounding = AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range + 1, yCoord + range + 1, zCoord + range + 1);

						List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bounding);
						
						if (tickRate < 20)
						{
							tickRate++;
						}

						for (EntityItem ei : items)
						{
							ItemStack rest = TileEntityHopper.func_145889_a((IInventory) te, ei.getEntityItem(), Facing.oppositeSide[facing.ordinal()]);

							if (rest == null || !rest.equals(ei.getEntityItem()))
							{
								if (tickRate > 2)
								{
									tickRate--;
								}
							}

							if (rest == null)
							{
								ei.setDead();
							}
							else if (!rest.equals(ei.getEntityItem()))
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
}
