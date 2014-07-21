package lumien.randomthings.Client.ParticleSystem;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Particle
{
	Vector3f pos;
	Vector3f velocity;
	Vector3f gravity;

	int energy;
	private boolean isDead;

	public Particle()
	{
		this.energy = 0;
		this.pos = new Vector3f(0,0,0);
		this.velocity = new Vector3f(0,0,0);
		this.gravity = new Vector3f(0,0,0);
	}
	
	public void setGravity(float gX,float gY,float gZ)
	{
		this.gravity.set(gX,gY,gZ);
	}

	public void setPosition(float x, float y, float z)
	{
		this.pos.set(x, y, z);
	}

	public void setVelocity(float vX, float vY, float vZ)
	{
		this.velocity.set(vX, vY, vZ);
	}

	public void setEnergy(int energy)
	{
		this.energy = energy;
	}

	public void update()
	{
		this.velocity.add(gravity);
		this.pos.add(velocity);
		this.energy--;
	}

	public boolean isDead()
	{
		return energy == 0;
	}
}
