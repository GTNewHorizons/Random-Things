package lumien.randomthings.Client.Renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Entity.EntityReviveCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderReviveCircle extends Render
{
	ResourceLocation texture = new ResourceLocation("RandomThings:textures/entitys/reviveCircle.png");

	private void doRender(EntityReviveCircle circle, double posX, double posY, double posZ, float partialTickTime)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.enableDefaultBlending();
		Minecraft.getMinecraft().entityRenderer.disableLightmap(0);

		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 0.13f, (float) posZ + 0.5f);

		this.bindEntityTexture(circle);

		GL11.glTranslatef(-0.5f, 0, -0.5f);

		if (circle.age < 40)
		{
			GL11.glScalef(circle.age * 0.05f + partialTickTime * 0.05f, 1, circle.age * 0.05f + partialTickTime * 0.05f);
		}
		else
		{
			GL11.glScalef(2, 1, 2);
		}
		GL11.glRotatef(circle.age * 20 + partialTickTime * 20, 0f, 1, 0f);
		GL11.glTranslatef(0.5f, 0, 0.5f);
		GL11.glRotatef(90, 1, 0, 0);

		int i = 1;
		float f2 = 1;
		float f3 = 0;
		float f4 = 0;
		float f5 = 1;

		float f6 = 1.0F;
		float f7 = 1F;
		float f8 = 1F;

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
		tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
		tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
		tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);

		tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
		tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
		tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
		tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		doRender((EntityReviveCircle) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return texture;
	}

}
