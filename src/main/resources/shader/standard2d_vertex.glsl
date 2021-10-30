#version 140

in vec3 vertexData;
in vec3 colorData;

out vec3 vertexColor;

void main()
{
    vertexColor = colorData;
    gl_Position = vec4(vertexData.xyz, 1.0);
}