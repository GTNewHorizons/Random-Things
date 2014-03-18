package lumien.randomthings.Handler;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Entity.EntityDyeSlime;
import lumien.randomthings.Items.ItemWhiteStone;
import lumien.randomthings.Proxy.ClientProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void preTextureStitch(TextureStitchEvent.Pre event)
	{
		if (event.map.getTextureType()==1)
		{
			ClientProxy.slimeParticleTexture = event.map.registerIcon("RandomThings:slimeParticle");
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
