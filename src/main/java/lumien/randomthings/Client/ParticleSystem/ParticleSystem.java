package lumien.randomthings.Client.ParticleSystem;

import java.util.ArrayDeque;
import java.util.Iterator;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class ParticleSystem
{
	Vector3d emissionPoint;
	
	ArrayDeque<Particle> particles;
	
	int maxParticles;
	int particleCount;
	int generationSpeed;
	
	public ParticleSystem(Vector3d emissionPoint,int maxParticles,int generationSpeed)
	{
		particles = new ArrayDeque<Particle>(maxParticles);
		this.maxParticles = maxParticles;
		this.generationSpeed = generationSpeed;
		this.emissionPoint = emissionPoint;
	}
	
	public void update()
	{
		for (int i=0;i<generationSpeed;i++)
		{
			particles.add(new Particle((Vector3d) emissionPoint.clone(),new Vector3d(Math.random()*0.1-0.05,Math.random()*0.1,Math.random()*0.1-0.05),new Vector3d(0,0.01,0),new Vector3f(1,1,1),(int) (Math.random()*100)));
			particleCount++;
		}
		
		Iterator<Particle> iterator = particles.iterator();
		
		while (iterator.hasNext())
		{
			Particle p = iterator.next();
			p.update();
			if (p.isDead())
			{
				iterator.remove();
				particleCount--;
			}
		}
	}
	
	public void render(float partialTickTime)
	{
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		glPointSize(10);
		glBegin(GL_POINTS);
		for (Particle p:particles)
		{
			glColor3f(p.color.x,p.color.y,p.color.z);
			glVertex3d(p.pos.x+p.velocity.x*partialTickTime,p.pos.y+p.velocity.y*partialTickTime,p.pos.z+p.velocity.z*partialTickTime);
		}
		glEnd();
		glEnable(GL_LIGHTING);
		glEnable(GL_TEXTURE_2D);
	}
}
