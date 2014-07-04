package lumien.randomthings.Client.ParticleSystem;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Particle
{
	Vector3d pos;
	Vector3d oldPos;
	Vector3d velocity;
	Vector3d gravity;
	
	Vector3f color;
	int energy;
	int lifetime;

	private boolean isDead;

	public Particle(Vector3d pos, Vector3d velocity, Vector3d gravity, Vector3f color, int energy)
	{
		this.pos = this.oldPos = pos;
		this.velocity = velocity;
		this.gravity = gravity;
		this.color = color;
		this.energy = energy;
		this.lifetime = energy;
	}

	public void update()
	{
		this.pos.add(velocity);
		this.velocity.sub(gravity);
		this.energy--;
		if (energy == 0)
		{
			this.setDead();
		}
		
		this.color.x = this.color.y = this.color.z = 1-1F/((float)lifetime)*((float)energy);
	}

	public void setDead()
	{
		this.isDead = true;
	}

	public boolean isDead()
	{
		return isDead;
	}
}
