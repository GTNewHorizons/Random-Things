package lumien.randomthings.Potions;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Library.PotionIds;
import lumien.randomthings.Transformer.MCPNames;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionImbue extends Potion
{
	static float cf = 1F / 255F;
	Color potionColor;
	ResourceLocation icon;
	static final ResourceLocation inventory = new ResourceLocation("randomthings:textures/customInventory.png");

	public PotionImbue(String name, int potionID, int color, ResourceLocation icon)
	{
		super(potionID, false, color);

		this.icon = icon;
		this.setPotionName(name);
		this.potionColor = new Color(this.getLiquidColor());
	}

	@Override
	public int getLiquidColor()
	{
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements.length > 3 && stackTraceElements[3].getMethodName().equals(MCPNames.method("func_70679_bo")))
		{
			return 0;
		}

		return super.getLiquidColor();
	}

	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		GL11.glColor3f(cf * potionColor.getRed(), cf * potionColor.getGreen(), cf * potionColor.getBlue());
		RenderUtils.drawTexturedModalRect(x, y, 0, 166, 140, 32, 0);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(icon);

		if (this.getId() == PotionIds.IMBUE_SPECTRE)
		{
			RenderUtils.enableDefaultBlending();
			GL11.glColor4f(1, 1, 1,0.8f);
			RenderUtils.drawTexturedQuad(x + 6, y + 7, 18, 18, 300);
			GL11.glDisable(GL11.GL_BLEND);
		}
		else
		{
			RenderUtils.drawTexturedQuad(x + 6, y + 7, 18, 18, 300);
		}
	}
}
