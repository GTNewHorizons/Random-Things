package lumien.randomthings.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.Particle.ParticleWhitestone;
import lumien.randomthings.Items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityWhitestone extends EntityItem
{
	EntityItem quartz = null;
	int chargingProgress;

	@SideOnly(Side.CLIENT)
	ArrayList<ParticleWhitestone> particles = new ArrayList<ParticleWhitestone>();

	public EntityWhitestone(World par1World)
	{
		super(par1World);

		chargingProgress = 0;
	}

	public EntityWhitestone(World world, double posX, double posY, double posZ, ItemStack itemstack)
	{
		super(world, posX, posY, posZ, itemstack);

		chargingProgress = 0;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (this.onGround)
		{
			if (this.getEntityItem().getItemDamage() == 0 && worldObj.getWorldTime() >= 18000 && worldObj.getWorldTime() <= 22000 && worldObj.canBlockSeeTheSky((int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ)))
			{
				if (this.worldObj.getCurrentMoonPhaseFactor() == 1.0 || Boolean.TRUE)
				{
					this.ticksExisted = 0;

					if (FMLCommonHandler.instance().getEffectiveSide().isClient())
					{
						if (chargingProgress == 40)
						{
							for (double t = 0; t < Math.PI * 2D; t += Math.PI * 2D / 360D * 4D)
							{
								Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleWhitestone(worldObj, this, 0, t));
							}
						}
					}
					
					chargingProgress++;
					if (chargingProgress >= 400)
					{
						worldObj.playSoundAtEntity(this, "random.orb", 1, 1);
						this.getEntityItem().setItemDamage(1);
						chargingProgress = 0;
					}
				}
			}
		}
	}

}
