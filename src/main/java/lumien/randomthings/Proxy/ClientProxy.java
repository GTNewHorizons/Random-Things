package lumien.randomthings.Proxy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import lumien.randomthings.Client.ClientTickHandler;
import lumien.randomthings.Client.Renderer.RenderHealingOrb;
import lumien.randomthings.Client.Renderer.RenderItemCollector;
import lumien.randomthings.Client.Renderer.RenderPfeil;
import lumien.randomthings.Client.Renderer.RenderSpirit;
import lumien.randomthings.Client.Renderer.RenderWhitestone;
import lumien.randomthings.Entity.EntityHealingOrb;
import lumien.randomthings.Entity.EntityPfeil;
import lumien.randomthings.Entity.EntitySpirit;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;

public class ClientProxy extends CommonProxy
{
	RenderItemCollector renderer;
	public static IIcon slimeParticleTexture;
	private static final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerTickHandler()
	{
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
	}

	@Override
	public void registerRenderers()
	{
		renderer = new RenderItemCollector();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemCollector.class, renderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvancedItemCollector.class, renderer);

		RenderingRegistry.registerEntityRenderingHandler(EntityPfeil.class, new RenderPfeil());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpirit.class, new RenderSpirit(new ModelSlime(16),new ModelSlime(0), 0.25f));
		RenderingRegistry.registerEntityRenderingHandler(EntityHealingOrb.class, new RenderHealingOrb());

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
	public void spawnSlimeParticle(int dye, double x, double y, double z, double motionX, double motionY, double motionZ)
	{
		EntityBreakingFX particle = new EntityBreakingFX(Minecraft.getMinecraft().theWorld, x, y, z, Items.slime_ball);
		Color c = new Color(ItemDye.field_150922_c[dye]);
		particle.setRBGColorF(1F / 255F * c.getRed(), 1F / 255F * c.getGreen(), 1F / 255F * c.getBlue());
		particle.setParticleIcon(slimeParticleTexture);
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
		NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<GuiPlayerInfo> list = nethandlerplayclient.playerInfoList;
        ArrayList<String> players=new ArrayList<String>();
        for (GuiPlayerInfo info:list)
        {
        	players.add(info.name);
        }
        
        return players;
	}
}
