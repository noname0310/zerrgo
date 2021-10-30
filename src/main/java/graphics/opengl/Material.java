package graphics.opengl;

import org.joml.Vector4f;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(name, material.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() { return name; }
}
