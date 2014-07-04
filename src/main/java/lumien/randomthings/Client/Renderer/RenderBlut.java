package lumien.randomthings.Client.Renderer;

import static org.lwjgl.opengl.GL11.*;
import lumien.randomthings.Client.RenderUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderBlut
{
	final static ResourceLocation blut_vene = new ResourceLocation("randomthings:textures/blut/blut_vene.png");
	final static ResourceLocation blut_arterie = new ResourceLocation("randomthings:textures/blut/blut_arterie.png");

	public static void renderBlut(RenderPlayerEvent.Post event)
	{
		if (Boolean.FALSE)
		{
			glDisable(GL_LIGHTING);
			glPushMatrix();
			{
				ModelBiped mainModel = event.renderer.modelBipedMain;
				ModelRenderer body = mainModel.bipedBody;

				float rotation = RenderUtils.interpolateRotation(event.entityPlayer.prevRenderYawOffset, event.entityPlayer.renderYawOffset, event.partialRenderTick);
				glRotatef(180.0F - rotation, 0.0F, 1.0F, 0.0F);

				glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

				ModelBox bodyBox = (ModelBox) body.cubeList.get(0);
				glTranslated(0, 0, (bodyBox.posZ2 - bodyBox.posZ1) / 32);

				glTranslated(0, -0.585, 0);

				double planeWidth = (bodyBox.posX2 - bodyBox.posX1) / 32;
				double planeHeight = (bodyBox.posY2 - bodyBox.posY1) / 32;

				Minecraft.getMinecraft().renderEngine.bindTexture(blut_vene);

				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

				glBegin(GL_QUADS);
				{
					glTexCoord2d(0.0, 0.0);
					glVertex3d(-planeWidth, -planeHeight, 0);

					glTexCoord2d(1.0, 0.0);
					glVertex3d(planeWidth, -planeHeight, 0);

					glTexCoord2d(1.0, 1.0);
					glVertex3d(planeWidth, planeHeight, 0);

					glTexCoord2d(0.0, 1.0);
					glVertex3d(-planeWidth, planeHeight, 0);
				}
				glEnd();
			}
			glPopMatrix();

			glDisable(GL_BLEND);
			glEnable(GL_LIGHTING);
		}
	}
}
