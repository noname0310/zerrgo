package graphics.opengl;

import org.joml.Vector4f;

@SuppressWarnings("ClassCanBeRecord")
public final class Material {
    private final String name;
    private final Texture texture;
    private final Vector4f color;
    private final Shader shader;

    Material (
            String name,
            Texture texture,
            Vector4f color,
            Shader shader
    ) {
        this.name = name;
        this.texture = texture;
        this.color = color;
        this.shader = shader;
    }

    void render(Mesh mesh) {
        if (texture != null) texture.bind();
        if (texture != null) texture.unbind();
    }

    public String getName() { return name; }
}
