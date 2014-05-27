package lumien.randomthings.Client.Renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

import lumien.randomthings.Blocks.ModBlocks;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Client.Model.ModelItemCollector;
import lumien.randomthings.TileEntities.TileEntityAdvancedItemCollector;
import lumien.randomthings.TileEntities.TileEntityItemCollector;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemCollectorRenderer extends TileEntitySpecialRenderer
{
	static final ModelItemCollector model = new ModelItemCollector();

	public static ResourceLocation itemCollector = (new ResourceLocation("RandomThings:textures/blocks/itemCollector/itemCollectorModel.png"));
	ResourceLocation advancedItemCollector = (new ResourceLocation("RandomThings:textures/blocks/itemCollector/advancedItemCollectorModel.png"));

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
	{
		GL11.glPushMatrix();

		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		if (te instanceof TileEntityItemCollector)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(itemCollector);
		}
		else if (te instanceof TileEntityAdvancedItemCollector)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(advancedItemCollector);
		}

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

		ItemCollectorRenderer.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		// RenderUtils.drawCube((float) x, (float) y + 1, (float) z);
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
