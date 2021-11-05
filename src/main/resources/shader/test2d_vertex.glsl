#version 330 core

layout(location = 0) in vec2 vertexData;
layout(location = 1) in vec2 texData;

out vec2 texCoords;

uniform mat4 model;
uniform mat4 projection;

void main()
{
    texCoords = texData;
    gl_Position = projection * model * vec4(vertexData.xy, 0, 1.0);
}