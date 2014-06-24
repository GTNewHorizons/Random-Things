package lumien.randomthings.Items;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import lumien.randomthings.RandomThings;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

public class ItemBiomeCapsule extends Item
{
	static public HashMap<Integer, Integer> biomeColors;
	static Random rng = new Random();
	
	final static float modColor = 1F/255F;

	public ItemBiomeCapsule()
	{
		this.setUnlocalizedName("biomeCapsule");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setMaxStackSize(1);

		GameRegistry.registerItem(this, "biomeCapsule");

		biomeColors = new HashMap<Integer, Integer>();
		{
			biomeColors.put(0, 1452177); // Ozean
			biomeColors.put(7, 4303848); // Fluss
			biomeColors.put(8, 6029312); // Nether
			biomeColors.put(9, 8223332); // The End
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (stack.getItemDamage() != 0)
		{
			NBTTagCompound nbt = stack.stackTagCompound;
			if (nbt == null)
			{
				nbt = stack.stackTagCompound = new NBTTagCompound();
				nbt.setInteger("charges", 0);
			}

			par3List.add("Charges: " + nbt.getInteger("charges") + " / " + getMaxDamage(stack));
		}
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return 256;
	}

	@Override
	public int getDisplayDamage(ItemStack stack)
	{
		if (stack.getItemDamage() != 0)
		{
			NBTTagCompound nbt = stack.stackTagCompound;
			if (nbt == null)
			{
				nbt = stack.stackTagCompound = new NBTTagCompound();
				nbt.setInteger("charges", 0);
			}

			return 256 - nbt.getInteger("charges");
		}
		return 0;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("RandomThings:biomeCapsule");
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if (entityItem.worldObj.isRemote)
		{
			ItemStack is = entityItem.getEntityItem();
			BiomeGenBase biome = entityItem.worldObj.getBiomeGenForCoords((int) Math.floor(entityItem.posX), (int) Math.floor(entityItem.posZ));

			NBTTagCompound nbt = is.stackTagCompound;
			if (nbt == null)
			{
				nbt = is.stackTagCompound = new NBTTagCompound();
				nbt.setInteger("charges", 0);
			}
			
			System.out.println(nbt.getInteger("selectingTimer"));
			
			int charges = is.stackTagCompound.getInteger("charges");
			int biomeID = is.getItemDamage()-1;
			if (charges < 256 && biomeID!=-1 && biome.biomeID == biomeID)
			{
				int intColor = getColorForBiome(BiomeGenBase.getBiome(biomeID));
				Color c = new Color(intColor);
				RandomThings.proxy.spawnColoredDust(entityItem.posX, entityItem.posY+0.1, entityItem.posZ, 0, 0, 0,modColor*c.getRed(),modColor*c.getGreen(),modColor*c.getBlue());
			}
		}
		else
		{
			if (entityItem.getEntityItem().getItemDamage() == 0)
			{
				NBTTagCompound nbt = entityItem.getEntityData();
				if (nbt.getInteger("selectingTimer") == 0)
				{
					nbt.setInteger("selectingTimer", 200);
				}
				else
				{
					int currentTime = nbt.getInteger("selectingTimer");
					if (currentTime == 1)
					{
						BiomeGenBase biome = entityItem.worldObj.getBiomeGenForCoords((int) Math.floor(entityItem.posX), (int) Math.floor(entityItem.posZ));
						ItemStack is = entityItem.getEntityItem();
						is.setItemDamage(biome.biomeID + 1);
						entityItem.setEntityItemStack(is);
					}
					else
					{
						currentTime--;
						nbt.setInteger("selectingTimer", currentTime);
					}
				}
			}
			else
			{
				ItemStack is = entityItem.getEntityItem();
				NBTTagCompound nbt = is.stackTagCompound;
				if (nbt == null)
				{
					nbt = is.stackTagCompound = new NBTTagCompound();
					nbt.setInteger("charges", 0);
				}
				int charges = is.stackTagCompound.getInteger("charges");
				int biomeID = is.getItemDamage() - 1;
				if (charges < 256)
				{
					int itemPosX = (int) Math.floor(entityItem.posX);
					int itemPosY = (int) Math.floor(entityItem.posY);
					int itemPosZ = (int) Math.floor(entityItem.posZ);

					int foundBiomeID = entityItem.worldObj.getBiomeGenForCoords(itemPosX, itemPosZ).biomeID;
					if (foundBiomeID == biomeID)
					{
						charges++;
						is.stackTagCompound.setInteger("charges", charges);
					}

				}
			}
		}
		return false;
	}

	public static int getColorForBiome(BiomeGenBase b)
	{
		if (biomeColors.containsKey(b.biomeID))
		{
			return biomeColors.get(b.biomeID);
		}
		else
		{
			return b.getBiomeFoliageColor(0, 0, 0);
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par1ItemStack.getItemDamage() == 0)
		{
			return 16777215;
		}
		else
		{
			int biomeID = par1ItemStack.getItemDamage() - 1;
			BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
			return getColorForBiome(biome);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		String biomeName = "";
		if (par1ItemStack.getItemDamage() == 0)
		{
			return ("" + StatCollector.translateToLocal("item.biomeCapsule" + ".name")).trim();
		}
		else
		{
			int biomeID = par1ItemStack.getItemDamage() - 1;
			BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
			biomeName = biome.biomeName;
		}
		return biomeName + " Capsule";
	}
}
