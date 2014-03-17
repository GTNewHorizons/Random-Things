package lumien.randomthings.Handler;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Entity.EntityDyeSlime;
import lumien.randomthings.Items.ItemWhiteStone;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;

public class RTEventHandler
{
	@SubscribeEvent
	public void useHoe(UseHoeEvent event)
	{
		if (event.world.getBlock(event.x, event.y, event.z) == ModBlocks.fertilizedDirt)
		{
			event.setResult(Result.ALLOW);
			event.world.setBlock(event.x, event.y, event.z, ModBlocks.fertilizedDirtTilled);
			event.world.playSoundEffect(event.x + 0.5F, event.y + 0.5F, event.z + 0.5F, ModBlocks.fertilizedDirtTilled.stepSound.getStepResourcePath(), (ModBlocks.fertilizedDirtTilled.stepSound.getVolume() + 1.0F) / 2.0F, ModBlocks.fertilizedDirtTilled.stepSound.getPitch() * 0.8F);
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void chunkWatch(ChunkWatchEvent.Watch event)
	{
		System.out.println(event.chunk);
	}
	
	public void onEntitySpawn(LivingSpawnEvent event)
	{
		if (event.entity instanceof EntitySlime && !event.world.isRemote)
		{
			EntitySlime slime = (EntitySlime) event.entity;
			event.setCanceled(true);
			EntityDyeSlime dyeSlime = new EntityDyeSlime(event.world);
			dyeSlime.setPosition(event.x,event.y,event.z);
			event.world.spawnEntityInWorld(dyeSlime);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void entityPreRender(RenderLivingEvent.Pre event)
	{
		if (event.entity instanceof EntityDyeSlime)
		{
			EntityDyeSlime slime = (EntityDyeSlime) event.entity;
			Color c = new Color(ItemDye.field_150922_c[slime.getDye()]);
			GL11.glColor3f(1F/255F*c.getRed(), 1F/255F*c.getGreen(), 1F/255F*c.getBlue());
		}
	}

	@SubscribeEvent
	public void entityDeath(LivingDeathEvent event)
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			if (event.entityLiving instanceof EntityPlayer && !event.source.canHarmInCreative())
			{
				EntityPlayer player = (EntityPlayer) event.entityLiving;

				for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++)
				{
					ItemStack is = player.inventory.getStackInSlot(slot);
					if (is != null && is.getItem() instanceof ItemWhiteStone)
					{
						event.setCanceled(true);

						player.setHealth(1F);

						player.addPotionEffect(new PotionEffect(10, 200, 10, false));
						player.addPotionEffect(new PotionEffect(11, 200, 5, false));
						player.addPotionEffect(new PotionEffect(12, 200, 1, false));

						player.inventory.setInventorySlotContents(slot, null);
						player.inventory.markDirty();
						player.inventoryContainer.detectAndSendChanges();
						return;
					}
				}
			}
		}
	}
}
