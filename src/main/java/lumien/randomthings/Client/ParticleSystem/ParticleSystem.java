package lumien.randomthings.Client.ParticleSystem;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;

import javax.vecmath.Vector3f;

import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import lumien.randomthings.RandomThings;

public class ParticleSystem {

    int programID;
    int vertexShaderID;
    int fragmentShaderID;

    Vector3f emissionPoint;

    Particle[] particles;
    int lastUsedPosition;

    int maxParticles;
    int generationSpeed;

    int vertexBufferID;

    public ParticleSystem(Vector3f emissionPoint, int maxParticles, int generationSpeed) {
        particles = new Particle[maxParticles];
        this.maxParticles = 1;
        this.generationSpeed = generationSpeed;
        this.emissionPoint = emissionPoint;
        this.lastUsedPosition = 0;
        initiateParticles();
        inititateShaders();

        vertexBufferID = glGenBuffers();
    }

    private void initiateParticles() {
        for (int i = 0; i < particles.length; i++) {
            particles[i] = new Particle();
        }
    }

    private void inititateShaders() {
        try {
            vertexShaderID = createShader("D:/development/vertex.shader", GL_VERTEX_SHADER);
            fragmentShaderID = createShader("D:/development/fragment.shader", GL_FRAGMENT_SHADER);
        } catch (Exception e) {
            e.printStackTrace();
            RandomThings.instance.logger.log(Level.WARN, "Couldn't initialize Shaders");
        }

        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        glLinkProgram(programID);

        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
    }

    public void update() {
        for (int i = 0; i < generationSpeed; i++) {
            int freeParticle = findUnusedParticle();
            if (freeParticle != -1) {
                Particle p = particles[freeParticle];
                p.setEnergy(200);
                p.setPosition(0, 0, 0);
                p.setVelocity((float) Math.random() * 0.1F, 0.1F, (float) Math.random() * 0.1F);
                p.setGravity(0, -0.001F, 0);
            }
        }

        for (Particle p : particles) {
            if (!p.isDead()) {
                p.update();
            }
        }
    }

    private int findUnusedParticle() {
        for (int i = lastUsedPosition; i < particles.length; i++) {
            if (particles[i].isDead()) {
                lastUsedPosition = i;
                return i;
            }
        }

        for (int i = 0; i < particles.length; i++) {
            if (particles[i].isDead()) {
                return i;
            }
        }

        return -1;
    }

    private int getParticleCount() {
        int counter = 0;

        for (Particle p : particles) {
            if (!p.isDead()) {
                counter++;
            }
        }

        return counter;
    }

    public void render(float partialTickTime) {
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_LIGHTING);

        int particleCount = getParticleCount();

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(particleCount * 3);

        for (Particle p : particles) {
            if (!p.isDead()) {
                vertexBuffer.put(p.pos.x + partialTickTime * p.velocity.x);
                vertexBuffer.put(p.pos.y + partialTickTime * p.velocity.y);
                vertexBuffer.put(p.pos.z + partialTickTime * p.velocity.z);
            }
        }
        vertexBuffer.rewind();

        // glUseProgram(programID);

        glPointSize(5);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STREAM_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glDrawArrays(GL_POINTS, 0, particleCount);

        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glUseProgram(0);

        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
    }

    private void checkGLError(String p_71361_1_) {
        int i = GL11.glGetError();

        if (i != 0) {
            String s1 = GLU.gluErrorString(i);
            RandomThings.instance.logger.error("########## GL ERROR ##########");
            RandomThings.instance.logger.error("@ " + p_71361_1_);
            RandomThings.instance.logger.error(i + ": " + s1);
        }
    }

    private int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = glCreateShader(shaderType);
            if (shader == 0) return 0;
            glShaderSource(shader, readFileAsString(filename));
            glCompileShader(shader);
            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB)
                    == GL11.GL_FALSE)
                throw new RuntimeException("Error creating shader");
            return shader;
        } catch (Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
    }

    private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        FileInputStream in = new FileInputStream(filename);
        Exception exception = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Exception innerExc = null;

            try {
                String line;
                while ((line = reader.readLine()) != null) source.append(line).append('\n');
            } catch (Exception exc) {
                exception = exc;
            } finally {
                try {
                    reader.close();
                } catch (Exception exc) {
                    innerExc = exc;
                }
            }
            if (innerExc != null) throw innerExc;
        } catch (Exception exc) {
            exception = exc;
        } finally {
            try {
                in.close();
            } catch (Exception exc) {
                if (exception == null) exception = exc;
                else exc.printStackTrace();
            }
            if (exception != null) throw exception;
        }

        return source.toString();
    }
}
