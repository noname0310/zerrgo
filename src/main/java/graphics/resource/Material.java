package graphics.resource;

import java.util.Optional;

public record Material (
        String name,
        Optional<Texture> texture
) { }
