package lumien.randomthings.Client.Renderer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;

import lumien.randomthings.Client.RenderUtils;
import lumien.randomthings.Entity.EntityMagicCircle;
import lumien.randomthings.Items.ItemImbue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMagicCircle extends Render
{
	static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void doRender(Entity entity, double posX, double posY, double posZ, float p_76986_8_, float p_76986_9_)
	{
		doRender((EntityMagicCircle) entity, posX, posY, posZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityMagicCircle magicCircle, double posX, double posY, double posZ, float p_76986_8_, float partialTickTime)
	{
		if (magicCircle.attachedTo()!=null)
		{
			EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
			
			Entity attachedTo = magicCircle.attachedTo();
			posX = difference(thePlayer.posX,attachedTo.posX);
			posY = difference(thePlayer.posY,attachedTo.posY + magicCircle.getModY());
			posZ = difference(thePlayer.posZ,attachedTo.posZ);
			
			if (thePlayer.getEntityId()==thePlayer.getEntityId())
			{
				posY-=thePlayer.eyeHeight;
			}
		}
		glPushMatrix();
		{
			mc.entityRenderer.disableLightmap(0);

			glTranslated(posX, posY, posZ);

			double rotation = magicCircle.getRotation();
			glRotated(magicCircle.getRotation() + magicCircle.getRotationStep() * partialTickTime, 0, 1, 0);
			
			mc.renderEngine.bindTexture(magicCircle.getCircleTexture());
			
			double circleRadius = magicCircle.getRadius();

			switch (magicCircle.getRadiusStatus())
			{
				case 0:
					circleRadius = Math.min(circleRadius+partialTickTime * magicCircle.getRadiusStep(), magicCircle.getMaxRadius());
					break;
				case 2:
					circleRadius = Math.max(circleRadius-partialTickTime * magicCircle.getRadiusStep(), 0);
					break;
			}
			
			glEnable(GL_BLEND);

			glBlendFunc(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_ONE);
			//glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			//glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			glColor4f(1,1,1,1);

			glDisable(GL_LIGHTING);
			glBegin(GL_QUADS);
			{
				// First Render
				glTexCoord2d(0.0, 0.0);
				glVertex3d(circleRadius, 0, circleRadius);

				glTexCoord2d(1.0, 0.0);
				glVertex3d(circleRadius, 0, -circleRadius);

				glTexCoord2d(1.0, 1.0);
				glVertex3d(-circleRadius, 0, -circleRadius);

				glTexCoord2d(0.0, 1.0);
				glVertex3d(-circleRadius, 0, circleRadius);
				
				// Second Render so it's visible from below
				glTexCoord2d(0.0, 0.0);
				glVertex3d(circleRadius, 0, circleRadius);

				glTexCoord2d(1.0, 0.0);
				glVertex3d(-circleRadius, 0, circleRadius);

				glTexCoord2d(1.0, 1.0);
				glVertex3d(-circleRadius, 0, -circleRadius);

				glTexCoord2d(0.0, 1.0);
				glVertex3d(circleRadius, 0, -circleRadius);
			}
			glEnd();
			glEnable(GL_LIGHTING);

			glDisable(GL_BLEND);

			mc.entityRenderer.enableLightmap(0);
		}
		glPopMatrix();
	}
	
	private double difference(double d1,double d2)
	{
		return Math.abs(d1-d2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityMagicCircle circle = (EntityMagicCircle) entity;
		return circle.getCircleTexture();
	}

}
