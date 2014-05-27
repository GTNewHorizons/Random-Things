package lumien.randomthings.Client;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import lumien.randomthings.Client.Renderer.ItemCollectorRenderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL41;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils
{
	static Gui gui = new Gui();

	public static void fillAreaWithIcon(IIcon icon, int x, int y, int width, int height)
	{
		int scale = computeGuiScale();

		glScissor(0, Minecraft.getMinecraft().displayHeight - (y + height) * scale, (width + x) * scale, height * scale);
		glEnable(GL_SCISSOR_TEST);

		int cols = MathHelper.ceiling_float_int(width / 16F);
		int rows = MathHelper.ceiling_float_int(height / 16F);
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				gui.drawTexturedModelRectFromIcon(x + col * 16, y + row * 16, icon, 16, 16);
			}
		}

		glDisable(GL_SCISSOR_TEST);
	}

	public static Color getAverageIconColor(IIcon icon)
	{
		TextureAtlasSprite sprite = (TextureAtlasSprite) icon;
		int x = sprite.getOriginX();
		int y = sprite.getOriginY();

		int width = sprite.getIconWidth();
		int height = sprite.getIconHeight();

		int textureMapID = Minecraft.getMinecraft().getTextureMapBlocks().getGlTextureId();
		GL11.glBindTexture(GL_TEXTURE_2D, textureMapID);

		int textureWidth = (int) GL11.glGetTexLevelParameterf(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
		int textureHeight = (int) GL11.glGetTexLevelParameterf(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

		IntBuffer ib = BufferUtils.createIntBuffer((int) (textureWidth * textureHeight * 4));
		GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_INT, (IntBuffer) ib);
		GL11.glBindTexture(GL_TEXTURE_2D, 0);

		int startPos = y * width + x;
		ib.rewind();
		for (int i = 0; i < ib.capacity(); i++)
		{
			int color = ib.get();
			Color c = new Color(color);
			if (c.getRed() == 105 && c.getGreen() == 105 && c.getBlue() == 106)
			{
				System.out.println("Found at " + i);
			}
		}

		return null;
	}

	public static int computeGuiScale()
	{
		Minecraft mc = Minecraft.getMinecraft();
		int scaleFactor = 1;

		int k = mc.gameSettings.guiScale;

		if (k == 0)
		{
			k = 1000;
		}

		while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240)
		{
			++scaleFactor;
		}
		return scaleFactor;
	}

	public static void drawFluidStack(FluidTankInfo tankInfo, int x, int y, int width, int fullHeight)
	{
		TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;

		FluidStack fluidStack = tankInfo.fluid;

		if (fluidStack != null)
		{
			Fluid fluid = fluidStack.getFluid();
			IIcon fluidIcon = fluid.getStillIcon();
			int fluidHeight = MathHelper.ceiling_float_int((fluidStack.amount / (float) tankInfo.capacity) * fullHeight);

			GL11.glDisable(GL_BLEND);
			glColor3f(1, 1, 1);
			renderEngine.bindTexture(renderEngine.getResourceLocation(fluid.getSpriteNumber()));
			fillAreaWithIcon(fluidIcon, x, y + fullHeight - fluidHeight, width, fluidHeight);
		}
	}

	public static void drawCube(float posX, float posY, float posZ)
	{
		Minecraft.getMinecraft().entityRenderer.disableLightmap(0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glTranslatef(posX, posY, posZ);

		glBegin(GL_QUADS);
		glColor3f(1, 0, 0);

		glVertex3f(0F, 0F, 0F); // P1
		glVertex3f(0F, 1F, 0F); // P2
		glVertex3f(1F, 1F, 0F); // P3
		glVertex3f(1F, 0F, 0F); // P4

		glVertex3f(1F, 1F, 0F); // P1
		glVertex3f(1F, 1F, 1F); // P2
		glVertex3f(1F, 0F, 1F); // P3
		glVertex3f(1F, 0F, 0F); // P4

		glVertex3f(1F, 1F, 1F); // P1
		glVertex3f(0F, 1F, 1F); // P1
		glVertex3f(0F, 0F, 1F); // P1
		glVertex3f(1F, 0F, 1F); // P1

		glVertex3f(0F, 1F, 1F); // P1
		glVertex3f(0F, 1F, 0F); // P1
		glVertex3f(0F, 0F, 0F); // P1
		glVertex3f(0F, 0F, 1F); // P1

		glVertex3f(0F, 0F, 0F); // P1
		glVertex3f(1F, 0F, 0F); // P1
		glVertex3f(1F, 0F, 1F); // P1
		glVertex3f(0F, 0F, 1F); // P1

		glVertex3f(0F, 1F, 0F); // P1
		glVertex3f(0F, 1F, 1F); // P2
		glVertex3f(1F, 1F, 1F); // P3
		glVertex3f(1F, 1F, 0F); // P4

		glEnd();

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
	}
}
