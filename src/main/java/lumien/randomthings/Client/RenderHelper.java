package lumien.randomthings.Client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import static org.lwjgl.opengl.GL11.*;

public class RenderHelper
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
}
