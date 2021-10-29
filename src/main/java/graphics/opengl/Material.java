package graphics.opengl;

import org.joml.Vector4f;

public final class Material {
    private final String name;
    private final Texture texture;
    private final Vector4f color;

    private Material (
            String name,
            Texture texture,
            Vector4f color
    ) {
        this.name = name;
        this.texture = texture;
        this.color = color;
    }

    public static Material fromTexture(Texture texture) {
        return new Material(texture.getName(), texture, null);
    }

    void render(Mesh mesh) {
        if (texture != null) texture.bind();
        if (texture != null) texture.unbind();
    }

    public String getName() { return name; }
}
