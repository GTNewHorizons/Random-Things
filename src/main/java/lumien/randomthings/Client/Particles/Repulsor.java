package lumien.randomthings.Client.Particles;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.util.vector.Vector3f;

public class Repulsor implements IInteractor
{
	Vector3f position;
	float minDistance;

	public Repulsor(Vector3f position)
	{
		this.position = position;
		this.minDistance = 0.5f;
	}

	public void interact(Particle p)
	{
		Vector3f direction = Vector3f.sub(position, p.position,null);
		float distance=direction.length();
		if (distance<minDistance)
		{
			direction.normalise();
			float pushStrength = 0.2f;
			p.velocity=Vector3f.sub(p.velocity, (Vector3f) direction.scale((distance-minDistance)*pushStrength).scale(-1), null);
		}
	}

	@Override
	public void draw()
	{
		glBegin(GL_POINTS);
		glColor4f(1, 0, 0, 1);
		glVertex3f(position.x, position.y, position.z);
		glEnd();
	}
}
