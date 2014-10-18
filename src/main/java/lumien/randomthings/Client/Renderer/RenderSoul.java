package lumien.randomthings.Client.Renderer;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Entity.EntitySoul;
import lumien.randomthings.Items.ItemGinto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSoul extends Render
{
	ResourceLocation soul1 = new ResourceLocation("RandomThings:textures/entitys/soul1.png");
	ResourceLocation soul2 = new ResourceLocation("RandomThings:textures/entitys/soul2.png");

	private void doRender(EntitySoul soul, double posX, double posY, double posZ, float p_76986_8_, float p_76986_9_)
	{
		if (soul.render)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			RenderUtils.enableDefaultBlending();
			Minecraft.getMinecraft().entityRenderer.disableLightmap(0);

			GL11.glTranslatef((float) posX + 0.15f, (float) posY + 0.3F, (float) posZ);
			GL11.glScalef(0.3f, 0.3f, 0.3f);
			this.bindEntityTexture(soul);

			GL11.glTranslatef(-0.5F, -0.5F, 0);
			GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0);

			int i = 1;
			float f2 = 1;
			float f3 = 0;
			float f4 = 0;
			float f5 = 1;

			float f6 = 1.0F;
			float f7 = 1F;
			float f8 = 1F;
			GL11.glColor3f(1,1,1);
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f2, f5);
			tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f3, f5);
			tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f3, f4);
			tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f2, f4);
			tessellator.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void doRender(Entity entity, double posX, double posY, double posZ, float p_76986_8_, float p_76986_9_)
	{
		ItemStack equipped = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
		if (equipped != null && (equipped.getItem() instanceof ItemGinto) && equipped.getItemDamage() == 1)
		{
			doRender((EntitySoul) entity, posX, posY, posZ, p_76986_8_, p_76986_9_);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		switch (((EntitySoul) entity).type)
		{
			case 0:
				return soul1;
			case 1:
				return soul2;
		}
		return soul1;
	}

}
