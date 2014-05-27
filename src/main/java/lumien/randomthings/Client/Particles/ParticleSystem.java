package lumien.randomthings.Client.Particles;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class ParticleSystem
{
	private static Random rng = new Random();
	private final Collection<Particle> particles;
	private Vector3f location;
	private int spawningRate;
	private int particleLifeTime;
	private Vector3f particleAcceleration;
	private Vector3f initialVelocity;
	private float velocityModifier;
	private ArrayDeque<IInteractor> interactor;

	private Minecraft mc;
	private ResourceLocation particleTexture;

	public int nrParticle;

	int vboID;

	private static float t = 1f / 255f;

	public static Vector3f nullVector = new Vector3f(0, 0, 0);

	FloatBuffer fb;

	public ParticleSystem()
	{
		this(new Vector3f(0, 0, 0), new Vector3f(0, -0.0003f, 0), new Vector3f(-0.5F, 0, -0.5F), 3, 300, 1.0F);
	}

	public ParticleSystem(Vector3f location, Vector3f acceleration, Vector3f initialVelocity, int spawningRate, int particleLifeTime, float velocityModifier)
	{
		this.location = location;
		this.particleLifeTime = particleLifeTime;
		this.spawningRate = spawningRate;
		this.particleAcceleration = acceleration;
		this.initialVelocity = initialVelocity;
		this.velocityModifier = velocityModifier;

		this.particles = new ArrayDeque<Particle>(spawningRate * particleLifeTime);
		this.interactor = new ArrayDeque<IInteractor>();

		this.interactor.add(new Attractor(new Vector3f(0, -3, 0)));

		mc = Minecraft.getMinecraft();
		particleTexture = new ResourceLocation("RandomThings:textures/particle.png");

		nrParticle = 0;

		initializeVBO();
	}

	private Particle generateNewParticle(int dx, int dy, int dz)
	{
		Vector3f particleLocation = new Vector3f(location);
		Vector3f particleVelocity = new Vector3f();

		float randomX = (float) (rng.nextDouble() - 0.5F);
		float randomZ = (float) (rng.nextDouble() - 0.5F);
		float randomY = (float) (rng.nextDouble() - 0.5F);

		particleVelocity.x = (randomX + initialVelocity.x + dx / 10) / 120;
		particleVelocity.y = (randomY + initialVelocity.y + dy / 10) / 120;
		particleVelocity.z = (randomZ + initialVelocity.z + dz / 10) / 120;

		particleVelocity.scale(velocityModifier);

		return new Particle(particleLocation, particleVelocity, particleAcceleration, particleLifeTime, rng.nextInt(40) + 1);
	}

	public void addInteractor(IInteractor interactor)
	{
		this.interactor.add(interactor);
	}

	private void initializeVBO()
	{
		vboID = glGenBuffers();

		fb = BufferUtils.createFloatBuffer(1000000 * 3 * 3);
		GL15.glBindBuffer(GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL_ARRAY_BUFFER, fb, GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void update()
	{
		if (Mouse.isButtonDown(1))
		{
			for (int i=0;i<spawningRate;i++)
			{
				this.particles.add(generateNewParticle(0,0,0));
			}
		}

		Iterator<Particle> iterator = particles.iterator();

		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			for (IInteractor i : interactor)
			{
				i.interact(particle);
			}

			particle.update(particleAcceleration);

			if (particle.isDestroyed())
			{
				nrParticle--;
				iterator.remove();
			}
			else
			{
				fb.put(particle.colorRed);
				fb.put(particle.colorGreen);
				fb.put(particle.colorBlue);
				fb.put(particle.position.x);
				fb.put(particle.position.y);
				fb.put(particle.position.z);
			}

		}
		fb.rewind();
		GL15.glBindBuffer(GL_ARRAY_BUFFER, vboID);
		GL15.glBufferSubData(GL_ARRAY_BUFFER, 0, fb);
		GL15.glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void draw()
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		glPointSize(5);

		for (IInteractor i : interactor)
		{
			i.draw();
		}

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);

		glInterleavedArrays(GL_C3F_V3F, 24, 0);

		glDrawArrays(GL_POINTS, 0, particles.size());

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);

		glEnable(GL11.GL_TEXTURE_2D);
		fb.clear();
	}
}
