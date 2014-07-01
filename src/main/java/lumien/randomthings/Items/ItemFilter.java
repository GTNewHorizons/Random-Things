package lumien.randomthings.Items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import lumien.randomthings.RandomThings;
import lumien.randomthings.Library.GuiIds;
import lumien.randomthings.Library.ItemUtils;
import lumien.randomthings.Library.Texts;
import lumien.randomthings.Library.Inventorys.InventoryItemFilter;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFilter extends Item
{
	public enum FilterType
	{
		BLOCK, ITEM, ENTITY;
	}

	IIcon[] icons;

	public ItemFilter()
	{
		this.setUnlocalizedName("filter");
		this.setCreativeTab(RandomThings.creativeTab);
		this.setHasSubtypes(true);

		icons = new IIcon[3];

		GameRegistry.registerItem(this, "filter");
	}

	public static boolean matchesBlock(ItemStack filter, Block block, int metadata)
	{
		String blockName = Block.blockRegistry.getNameForObject(block);

		if (filter.stackTagCompound == null || !filter.stackTagCompound.hasKey("block"))
		{
			return true;
		}

		String filterName = filter.stackTagCompound.getString("block");
		int filterMetadata = filter.stackTagCompound.getInteger("metadata");

		return filterName.equals(blockName) && filterMetadata == metadata;
	}

	public static boolean matchesItem(ItemStack filter, ItemStack toCheck)
	{
		if (filter == null || toCheck == null || filter.stackTagCompound==null)
		{
			return false;
		}
		
		if (!(filter.getItem() instanceof ItemFilter) || filter.getItemDamage() != 1)
		{
			return false;
		}
		
		boolean oreDict = filter.stackTagCompound.getBoolean("oreDict");
		int listType = filter.stackTagCompound.getInteger("listType");

		IInventory filterInventory = getItemFilterInv(null, filter);
		filterInventory.openInventory();
		for (int slot = 0; slot < filterInventory.getSizeInventory(); slot++)
		{
			ItemStack is = filterInventory.getStackInSlot(slot);
			if (is != null)
			{
				if (oreDict && ItemUtils.areOreDictionaried(is, toCheck))
				{
					return listType == 0 ? true : false;
				}
				if (is.isItemEqual(toCheck))
				{
					return listType == 0 ? true : false;
				}
			}
		}

		return listType == 0 ? false : true;
	}

	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack.stackTagCompound != null)
		{
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				par3List.add(Texts.PSHIFT);
			}
			else
			{
				switch (par1ItemStack.getItemDamage())
				{
					case 0:
						String block = par1ItemStack.stackTagCompound.getString("block");
						if (!block.equals(""))
						{
							par3List.add(I18n.format("text.miscellaneous.block", null) + ": " + block);
							par3List.add(I18n.format("text.miscellaneous.metadata", null) + ": " + par1ItemStack.stackTagCompound.getInteger("metadata"));
						}
						break;
					case 1:
						if (!(ItemFilter.getItemFilterInv(par2EntityPlayer, par1ItemStack) == null))
						{
							par3List.add(I18n.format("text.miscellaneous.oreDictionary", null) + ": " + (par1ItemStack.stackTagCompound.getBoolean("oreDict") ? "Yes" : "No"));
							par3List.add(I18n.format("text.miscellaneous.listType", null) + ": " + (par1ItemStack.stackTagCompound.getInteger("listType") == 1 ? "Blacklist" : "Whitelist"));
							IInventory inventoryFilter = new InventoryItemFilter(par2EntityPlayer, par1ItemStack);
							inventoryFilter.openInventory();
							if (inventoryFilter != null)
							{
								for (int i = 0; i < 9; i++)
								{
									ItemStack isg = inventoryFilter.getStackInSlot(i);
									if (isg != null)
									{
										par3List.add("- " + isg.getDisplayName());
									}
								}
							}
						}
						break;
					case 2:
						int entityID = par1ItemStack.stackTagCompound.getInteger("entity");
						String entityName = par1ItemStack.stackTagCompound.getString("entityName");
						if (!(entityID == 0))
						{
							par3List.add(I18n.format("text.miscellaneous.entityid", null) + ": " + entityID);
							par3List.add(I18n.format("text.miscellaneous.entityName", null) + ": " + entityName);
						}
						break;
				}
			}
		}
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
		switch (par1ItemStack.getItemDamage())
		{
			case 0:
				Block b = par3World.getBlock(par4, par5, par6);
				par1ItemStack.stackTagCompound.setString("block", Block.blockRegistry.getNameForObject(b));
				par1ItemStack.stackTagCompound.setInteger("metadata", par3World.getBlockMetadata(par4, par5, par6));
				return true;
		}
		return false;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
	{
		if (itemstack.getItemDamage() == 2)
		{
			ItemStack realItemStack = player.getCurrentEquippedItem();
			if (realItemStack.stackTagCompound == null)
			{
				realItemStack.stackTagCompound = new NBTTagCompound();
			}

			realItemStack.stackTagCompound.setInteger("entity", entity.getEntityId());
			realItemStack.stackTagCompound.setString("entityName", entity.getCommandSenderName());
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setBoolean("oreDict", false);
		}

		if (par1ItemStack.getItemDamage() == 1)
		{
			par3EntityPlayer.openGui(RandomThings.instance, GuiIds.ITEM_FILTER, par2World, (int) par3EntityPlayer.posX, (int) par3EntityPlayer.posY, (int) par3EntityPlayer.posZ);
		}
		return par1ItemStack;
	}

	@Override
	
	public IIcon getIconFromDamage(int par1)
	{
		if (par1 > 0 && par1 < icons.length)
		{
			return icons[par1];
		}
		else
		{
			return icons[0];
		}
	}

	@Override
	
	public void registerIcons(IIconRegister par1IconRegister)
	{
		icons[0] = par1IconRegister.registerIcon("RandomThings:filter/blockFilter");
		icons[1] = par1IconRegister.registerIcon("RandomThings:filter/itemFilter");
		icons[2] = par1IconRegister.registerIcon("RandomThings:filter/entityFilter");
	}

	public static FilterType getFilterType(int damage)
	{
		switch (damage)
		{
			case 0:
				return FilterType.BLOCK;
			case 1:
				return FilterType.ITEM;
			case 2:
				return FilterType.ENTITY;
			default:
				return FilterType.BLOCK;
		}
	}

	public static IInventory getItemFilterInv(EntityPlayer player)
	{
		ItemStack filter;
		IInventory inventoryFilter = null;
		filter = player.getCurrentEquippedItem();

		if (filter != null && filter.getItem() instanceof ItemFilter && filter.getItemDamage() == 1)
		{
			inventoryFilter = new InventoryItemFilter(player, filter);
		}

		return inventoryFilter;
	}

	public static IInventory getItemFilterInv(EntityPlayer player, ItemStack filter)
	{
		IInventory inventoryFilter = null;

		if (filter != null && filter.getItem() instanceof ItemFilter && filter.getItemDamage() == 1)
		{
			inventoryFilter = new InventoryItemFilter(player, filter);
		}

		return inventoryFilter;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		String type = "";
		switch (par1ItemStack.getItemDamage())
		{
			case 0: // Block Filter
				type = "filterBlock";
				break;
			case 1: // Item Filter
				type = "filterItem";
				break;
			case 2: // Entity Filter
				type = "filterEntity";
				break;
		}
		return "item." + type;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List list)
	{
		ItemStack blockFilter = new ItemStack(item, 1, 0); // Block Filter
		blockFilter.stackTagCompound = new NBTTagCompound();
		list.add(blockFilter);

		ItemStack itemFilter = new ItemStack(item, 1, 1);// Item Filter
		itemFilter.stackTagCompound = new NBTTagCompound();
		list.add(itemFilter);

		ItemStack entityFilter = new ItemStack(item, 1, 2); // Entity Filter
		entityFilter.stackTagCompound = new NBTTagCompound();
		list.add(entityFilter);
	}
}
