package lumien.randomthings.Client.Renderer;

import java.awt.Color;

import lumien.randomthings.Entity.EntityDyeSlime;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemDye;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDyeSlime extends RenderLiving
{
	private static final ResourceLocation slimeTextures = new ResourceLocation("RandomThings:textures/entitys/dyeSlime.png");
	private ModelBase scaleAmount;
	private static final String __OBFID = "CL_00001024";

	public RenderDyeSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par3);
		this.scaleAmount = par2ModelBase;
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityDyeSlime par1EntityDyeSlime, int par2, float par3)
	{
		if (par1EntityDyeSlime.isInvisible())
		{
			return 0;
		}
		else if (par2 == 0)
		{
			this.setRenderPassModel(this.scaleAmount);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		}
		else
		{
			if (par2 == 1)
			{
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return -1;
		}
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityDyeSlime par1EntityDyeSlime, float par2)
	{
		float f1 = (float) par1EntityDyeSlime.getSlimeSize();
		float f2 = (par1EntityDyeSlime.prevSquishFactor + (par1EntityDyeSlime.squishFactor - par1EntityDyeSlime.prevSquishFactor) * par2) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		GL11.glScalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
		
		EntityDyeSlime slime = par1EntityDyeSlime;
		Color c = new Color(ItemDye.field_150922_c[slime.getDye()]);
		GL11.glColor3f(1F/255F*c.getRed(), 1F/255F*c.getGreen(), 1F/255F*c.getBlue());
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityDyeSlime par1EntityDyeSlime)
	{
		return slimeTextures;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		this.preRenderCallback((EntityDyeSlime) par1EntityLivingBase, par2);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.shouldRenderPass((EntityDyeSlime) par1EntityLivingBase, par2, par3);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityDyeSlime) par1Entity);
	}
}