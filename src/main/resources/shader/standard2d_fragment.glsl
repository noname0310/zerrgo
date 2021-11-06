#version 330 core
in vec2 texCoord;
out vec4 fragColor;

uniform vec3 spriteColor;

void main()
{
    fragColor = vec4(spriteColor, 1.0);
}