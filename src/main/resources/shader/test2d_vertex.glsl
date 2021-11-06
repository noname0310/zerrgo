#version 330 core

layout(location = 0) in vec3 vertexData;
layout(location = 1) in vec3 normalData;
layout(location = 2) in vec2 texData;

out vec2 texCoords;

uniform mat4 model;
uniform mat4 viewProj;

void main()
{
    texCoords = texData;
    gl_Position = viewProj * model * vec4(vertexData, 1.0);
}