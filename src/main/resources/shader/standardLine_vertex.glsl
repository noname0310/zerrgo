#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aColor;

out vec3 lineColor;

uniform mat4 viewProj;

void main()
{
    lineColor = aColor;
    gl_Position = viewProj * vec4(aPos, 1.0);
}
