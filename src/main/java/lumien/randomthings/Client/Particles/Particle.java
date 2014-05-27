package lumien.randomthings.Client.Particles;

import org.lwjgl.util.vector.Vector3f;

public class Particle
{
	Vector3f position;
	Vector3f velocity;
	Vector3f acceleration;

	int energy;

	int size;

	float mass;

	float colorRed;
	float colorGreen;
	float colorBlue;

	public Particle(Vector3f position, Vector3f velocity, Vector3f acceleration, int energy, int size)
	{
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.energy = energy;
		this.size = size;

		this.colorRed = 1;
		this.colorGreen = 1;
		this.colorBlue = 1;

		mass = 0.01f;
	}

	public void update(Vector3f gravity)
	{
		velocity.translate(acceleration.x, acceleration.y, acceleration.z);
		position.translate(velocity.x, velocity.y, velocity.z);
		this.energy--;

		float distance = Vector3f.sub(position, ParticleSystem.nullVector, null).length();
		this.colorRed = this.colorBlue = this.colorGreen = (float) Math.sin(energy*0.02f);
	}

	public boolean isDestroyed()
	{
		return energy == 0;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setVelocity(Vector3f velocity)
	{
		this.velocity = velocity;
	}

	public int getEnergy()
	{
		return energy;
	}

	public void setEnergy(int energy)
	{
		this.energy = energy;
	}

	public float getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
}
