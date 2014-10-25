package lumien.randomthings.Proxy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import lumien.randomthings.RandomThings;
import lumien.randomthings.Client.ClientTickHandler;
import lumien.randomthings.Client.Renderer.RenderHealingOrb;
import lumien.randomthings.Client.Renderer.RenderItemCollector;
import lumien.randomthings.Client.Renderer.RenderReviveCircle;
import lumien.randomthings.Client.Renderer.RenderSoul;
import lumien.randomthings.Client.Renderer.RenderSpirit;
import lumien.randomthings.Client.Renderer.RenderWhitestone;
import lumien.randomthings.Client.Renderer.RenderWirelessLever;
import lumien.randomthings.Configuration.VanillaChanges;
import lumien.randomthings.Entity.EntityHealingOrb;
import lumien.randomthings.Entity.EntityReviveCircle;
import lumien.randomthings.Entity.EntitySoul;
import lumien.randomthings.Entity.EntitySpirit;
import lumien.randomthings.Items.ItemGinto;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.OverrideUtils;
import lumien.randomthings.Library.RenderIds;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import lumien.randomthings.Transformer.MCPNames;

public class ClientProxy extends CommonProxy
{
	RenderItemCollector renderer;
	private static final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
	}
	
	@Override
	public boolean canBeCollidedWith(EntitySoul soul)
	{
		ItemStack equipped = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
		if (equipped != null && (equipped.getItem() instanceof ItemGinto) && equipped.getItemDamage() == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void registerRenderers()
	{
		RenderIds.WIRELESS_LEVER = RenderingRegistry.getNextAvailableRenderId();

		renderer = new RenderItemCollector();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemCollector.class, renderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvancedItemCollector.class, renderer);

		RenderingRegistry.registerEntityRenderingHandler(EntitySpirit.class, new RenderSpirit(new ModelSlime(16), new ModelSlime(0), 0.25f));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealingOrb.class, new RenderHealingOrb());
		RenderingRegistry.registerEntityRenderingHandler(EntitySoul.class, new RenderSoul());
		RenderingRegistry.registerEntityRenderingHandler(EntityReviveCircle.class, new RenderReviveCircle());
		
		RenderingRegistry.registerBlockHandler(new RenderWirelessLever());

		MinecraftForgeClient.registerItemRenderer(ModItems.whitestone, new RenderWhitestone());
	}

	@Override
	public void spawnPfeilParticle(double x, double y, double z, double motionX, double motionY, double motionZ)
	{
		World worldObj = Minecraft.getMinecraft().theWorld;

		EntityCritFX particle = new EntityCritFX(worldObj, x, y, z, motionX, motionY, motionZ);
		particle.setRBGColorF(1F / 255F * 198F, 1F / 255F * 246F, 1F / 255F * 252F);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	@Override
	public void spawnColoredDust(double x, double y, double z, double motionX, double motionY, double motionZ, float red, float green, float blue)
	{
		EntityReddustFX particle = new EntityReddustFX(Minecraft.getMinecraft().theWorld, x, y, z, 0, 0, 0);
		particle.setRBGColorF(red, green, blue);
		particle.motionY = motionY;
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	@Override
	public ArrayList<String> getUsernameList()
	{
		NetHandlerPlayClient nethandlerplayclient = ClientProxy.mc.thePlayer.sendQueue;
		List<GuiPlayerInfo> list = nethandlerplayclient.playerInfoList;
		ArrayList<String> players = new ArrayList<String>();
		for (GuiPlayerInfo info : list)
		{
			players.add(info.name);
		}

		return players;
	}

	@Override
	public void postInit()
	{
		if (VanillaChanges.LOCKED_GAMMA)
		{
			GameSettings.Options[] videoOptions = ReflectionHelper.getPrivateValue(GuiVideoSettings.class, null, MCPNames.field("field_146502_i"));
			ArrayList<GameSettings.Options> options = new ArrayList<GameSettings.Options>(Arrays.asList(videoOptions));

			Iterator<GameSettings.Options> iterator = options.iterator();
			while (iterator.hasNext())
			{
				GameSettings.Options option = iterator.next();
				if (option == GameSettings.Options.GAMMA)
				{
					iterator.remove();
				}
			}

			RandomThings.instance.logger.log(Level.INFO, "Removing Gamma from settings... (GammaLock is on)");
			try
			{
				OverrideUtils.setFinalStatic(GuiVideoSettings.class.getDeclaredField(MCPNames.field("field_146502_i")), options.toArray(videoOptions));
			}
			catch (NoSuchFieldException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				// Still works
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
