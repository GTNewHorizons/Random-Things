package lumien.randomthings.Client.Renderer;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Client.Model.ModelItemCollector;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemCollectorRenderer extends TileEntitySpecialRenderer
{
	static final ModelItemCollector model= new ModelItemCollector();

	ResourceLocation itemCollector = (new ResourceLocation("RandomThings:textures/blocks/itemCollector/itemCollectorModel.png"));
	ResourceLocation advancedItemCollector = (new ResourceLocation("RandomThings:textures/blocks/itemCollector/advancedItemCollectorModel.png"));

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	{
		// The PushMatrix tells the renderer to "start" doing something.
		GL11.glPushMatrix();
		// This is setting the initial location.
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		// This is the texture of your block. It's pathed to be the same place
		// as your other blocks here.
		// Outdated
		// bindTextureByName("/mods/roads/textures/blocks/TrafficLightPoleRed.png");
		// Use in 1.6.2 this
		// the ':' is very important
		// binding the textures
		if (te instanceof TileEntityItemCollector)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(itemCollector);
		}
		else if (te instanceof TileEntityAdvancedItemCollector)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(advancedItemCollector);
		}

		// This rotation part is very important! Without it, your model will
		// render upside-down! And for some reason you DO need PushMatrix again!
		GL11.glPushMatrix();

		int metadata = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);

		switch (metadata)
		{
			case 0:
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0, -2, 0);
				break;
			case 1:
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				break;
			case 2:
				GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0, -1, 1);
				break;
			case 3:
				GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0, -1, -1);
				break;
			case 4:
				GL11.glRotatef(270F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(1, -1, 0);
				break;
			case 5:
				GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-1, -1, 0);
				break;
		}

		// A reference to your Model file. Again, very important.
		ItemCollectorRenderer.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		// Tell it to stop rendering for both the PushMatrix's
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private void adjustRotatePivotViaMeta(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		GL11.glPushMatrix();
		GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
		GL11.glPopMatrix();
	}

	// Set the lighting stuff, so it changes it's brightness properly.
	private void adjustLightFixture(World world, int i, int j, int k, Block block)
	{
		Tessellator tess = Tessellator.instance;
		float brightness = block.getMixedBrightnessForBlock(world, i, j, k);
		int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int modulousModifier = skyLight % 65536;
		int divModifier = skyLight / 65536;
		tess.setColorOpaque_F(brightness, brightness, brightness);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, modulousModifier, divModifier);
	}
}
