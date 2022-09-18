package lumien.randomthings.Proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.List;
import lumien.randomthings.Client.ClientTickHandler;
import lumien.randomthings.Client.Renderer.*;
import lumien.randomthings.Entity.*;
import lumien.randomthings.Handler.Bloodmoon.ClientBloodmoonHandler;
import lumien.randomthings.Items.ItemGinto;
import lumien.randomthings.Items.ModItems;
import lumien.randomthings.Library.Interfaces.IContainerWithProperties;
import lumien.randomthings.Library.RenderIds;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    RenderItemCollector renderer;
    private static final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void registerTickHandler() {
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
    }

    @Override
    public boolean isBloodmoon() {
        return ClientBloodmoonHandler.INSTANCE.isBloodmoonActive();
    }

    @Override
    public void setContainerProperty(int index, int value) {
        Container openContainer = Minecraft.getMinecraft().thePlayer.openContainer;
        if (openContainer instanceof IContainerWithProperties) {
            IContainerWithProperties prop = (IContainerWithProperties) openContainer;
            prop.setValue(index, value);
        }
    }

    @Override
    public boolean canBeCollidedWith(EntitySoul soul) {
        ItemStack equipped = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
        return equipped != null && (equipped.getItem() instanceof ItemGinto) && equipped.getItemDamage() == 1;
    }

    @Override
    public void registerRenderers() {
        RenderIds.WIRELESS_LEVER = RenderingRegistry.getNextAvailableRenderId();

        renderer = new RenderItemCollector();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemCollector.class, renderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvancedItemCollector.class, renderer);

        RenderingRegistry.registerEntityRenderingHandler(
                EntitySpirit.class, new RenderSpirit(new ModelSlime(16), new ModelSlime(0), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityHealingOrb.class, new RenderHealingOrb());
        RenderingRegistry.registerEntityRenderingHandler(EntitySoul.class, new RenderSoul());
        RenderingRegistry.registerEntityRenderingHandler(EntityReviveCircle.class, new RenderReviveCircle());
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodmoonCircle.class, new RenderBloodmoonCircle());

        RenderingRegistry.registerBlockHandler(new RenderWirelessLever());

        RenderRTItem rtRenderer = new RenderRTItem();
        MinecraftForgeClient.registerItemRenderer(ModItems.whitestone, rtRenderer);
        MinecraftForgeClient.registerItemRenderer(ModItems.bloodStone, rtRenderer);
    }

    @Override
    public void spawnPfeilParticle(double x, double y, double z, double motionX, double motionY, double motionZ) {
        World worldObj = Minecraft.getMinecraft().theWorld;

        EntityCritFX particle = new EntityCritFX(worldObj, x, y, z, motionX, motionY, motionZ);
        particle.setRBGColorF(1F / 255F * 198F, 1F / 255F * 246F, 1F / 255F * 252F);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    @Override
    public void spawnColoredDust(
            double x,
            double y,
            double z,
            double motionX,
            double motionY,
            double motionZ,
            float red,
            float green,
            float blue) {
        EntityReddustFX particle = new EntityReddustFX(Minecraft.getMinecraft().theWorld, x, y, z, 0, 0, 0);
        particle.setRBGColorF(red, green, blue);
        particle.motionY = motionY;
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    @Override
    public ArrayList<String> getUsernameList() {
        NetHandlerPlayClient nethandlerplayclient = ClientProxy.mc.thePlayer.sendQueue;
        List<GuiPlayerInfo> list = nethandlerplayclient.playerInfoList;
        ArrayList<String> players = new ArrayList<>();
        for (GuiPlayerInfo info : list) {
            players.add(info.name);
        }
        return players;
    }
}
