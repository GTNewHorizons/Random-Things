package lumien.randomthings.Client.Model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelItemCollector extends ModelBase
{
	// fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;

	private float color = 0.2F;

	public ModelItemCollector()
	{
		textureWidth = 16;
		textureHeight = 16;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 4, 1, 4);
		Shape1.setRotationPoint(-2F, 23F, -2F);
		Shape1.setTextureSize(16, 16);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(0F, 0F, 0F, 2, 1, 2);
		Shape2.setRotationPoint(-1F, 22F, -1F);
		Shape2.setTextureSize(16, 16);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 0);
		Shape3.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape3.setRotationPoint(-0.5F, 21F, -0.5F);
		Shape3.setTextureSize(16, 16);
		Shape3.mirror = true;
		setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 0);
		Shape4.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape4.setRotationPoint(-0.5F, 20F, -0.5F);
		Shape4.setTextureSize(16, 16);
		Shape4.mirror = true;
		setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 14);
		Shape5.addBox(0F, 0F, 0F, 1, 1, 1);
		Shape5.setRotationPoint(-0.5F, 19F, -0.5F);
		Shape5.setTextureSize(16, 16);
		Shape5.mirror = true;
		setRotation(Shape5, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);

		color += 0.01F;

		float colorMod = (float) (0.25F * Math.sin(color) + 0.55F);

		setRotationAngles(f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		GL11.glColor3f(colorMod, colorMod, colorMod);
		Shape5.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}

}
