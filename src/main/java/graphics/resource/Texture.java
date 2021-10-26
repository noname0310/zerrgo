package graphics.resource;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class Texture {
    private final int id;
    private final int width;
    private final int height;

    public Texture(int width, int height, ByteBuffer data) {
        this.width = width;
        this.height = height;

        // create texture
        this.id = GL11.glGenTextures();

        // bind texture
        this.bind();
    }

    public void setParameter(int name, int value) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, name, value);
    }

    public void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
    }

    public void unbind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
