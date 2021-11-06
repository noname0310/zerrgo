package core.graphics.record;

import core.graphics.resource.Shader;
import core.graphics.resource.Texture;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public class Material {
    private final String name;
    private final Texture texture;
    private final Vector4f color;
    private final Shader shader;

    public Material(String name, Texture texture, Vector4fc color, Shader shader) {
        this.name = name;
        this.texture = texture;
        this.color = new Vector4f(color);
        this.shader = shader;
    }

    public String getName() { return name; }

    public Texture getTexture() { return texture; }

    public Vector4fc getColor() { return color; }

    public Shader getShader() { return shader; }
}
