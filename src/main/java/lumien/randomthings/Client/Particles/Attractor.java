package lumien.randomthings.Client.Particles;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayDeque;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Attractor implements IInteractor
{
	Vector3f position;
	float maxDistance;
	float pullStrength;

	float distance;
	Vector3f direction;

	public Attractor(Vector3f position)
	{
		this.position = position;
		this.maxDistance = 5;

		pullStrength = 0.001f;

		direction = new Vector3f(0, 0, 0);
	}

	public void interact(Particle p)
	{
//		Vector3f.sub(position, p.position, direction);
//		distance = direction.length();
//		if (distance < maxDistance && distance > 0)
//		{
//			direction.normalise();
//			Vector3f.sub(p.velocity, (Vector3f) direction.scale((distance - maxDistance) * pullStrength), p.velocity);
//		}
		
		Vector3f.sub(position, p.position, direction);
		distance = direction.length();
		direction.scale(0.02f/(distance));
		p.acceleration.set(direction);
	}

	@Override
	public void draw()
	{
		glBegin(GL_POINTS);
		GL11.glColor3f(0, 1, 0);
		glVertex3f(position.x, position.y, position.z);
		glEnd();
	}
}
