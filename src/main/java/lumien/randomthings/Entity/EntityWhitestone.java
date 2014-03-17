package lumien.randomthings.Entity;

import java.util.List;

import javax.vecmath.Vector3d;

import lumien.randomthings.Items.ModItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityWhitestone extends EntityItem
{
	int state;
	EntityItem diamond = null;
	EntityItem quartz = null;
	EntityItem redstone = null;

	public EntityWhitestone(World par1World)
	{
		super(par1World);

		state = 0;
	}

	public EntityWhitestone(World world, double posX, double posY, double posZ, ItemStack itemstack)
	{
		super(world, posX, posY, posZ, itemstack);

		state = 0;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (worldObj.getCurrentMoonPhaseFactor() == 1 && worldObj.getWorldTime() >= 18000 && worldObj.canBlockSeeTheSky((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)))
		{
			if (state == 0)
			{
				AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(posX - 2, posY - 1, posZ - 2, posX + 2, posY + 1, posZ + 2);
				List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, bb);

				for (EntityItem ei : items)
				{
					if (ei.getEntityItem().getItem() == Items.diamond)
					{
						if (diamond == null)
						{
							diamond = ei;
						}
					}

					if (ei.getEntityItem().getItem() == Items.quartz)
					{
						if (quartz == null)
						{
							quartz = ei;
						}
					}

					if (ei.getEntityItem().getItem() == Items.redstone)
					{
						if (redstone == null)
						{
							redstone = ei;
						}
					}
				}

				if (diamond != null && diamond.isDead)
				{
					diamond = null;
				}

				if (redstone != null && redstone.isDead)
				{
					redstone = null;
				}

				if (quartz != null && quartz.isDead)
				{
					quartz = null;
				}

				if (diamond != null && quartz != null && redstone != null)
				{
					state = 1;
				}
			}
			else if (state == 1)
			{
				this.setEntityItemStack(new ItemStack(ModItems.whitestone, 1, 1));
				diamond.getEntityItem().stackSize--;
				redstone.getEntityItem().stackSize--;
				quartz.getEntityItem().stackSize--;
				state = 0;
			}

			if (redstone != null && worldObj.isRemote)
			{
				Vector3d vRedstone = new Vector3d(redstone.posX - posX, redstone.posY - posY, redstone.posZ - posZ);
				Vector3d stutz = new Vector3d(posX, posY, posZ);

				float maxK = (float) ((redstone.posX - stutz.x) / vRedstone.x);

				for (float k = 0; k < maxK; k += 0.15)
				{
					worldObj.spawnParticle("reddust", stutz.x + k * vRedstone.x, stutz.y + k * vRedstone.y, stutz.z + k * vRedstone.z, 0, 0, 0);
				}
			}
			
			if (quartz != null && worldObj.isRemote)
			{
				Vector3d vQuartz = new Vector3d(quartz.posX - posX, quartz.posY - posY, quartz.posZ - posZ);
				Vector3d stutz = new Vector3d(posX, posY, posZ);

				float maxK = (float) ((quartz.posX - stutz.x) / vQuartz.x);

				for (float k = 0; k < maxK; k += 0.15)
				{
					worldObj.spawnParticle("iconcrack_"+Item.getIdFromItem(Items.redstone), stutz.x + k * vQuartz.x, stutz.y + k * vQuartz.y, stutz.z + k * vQuartz.z, 0, 0, 0);
				}
			}

		}
	}

}
