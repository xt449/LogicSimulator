#version 330 core

out vec2 texCoord;

uniform mat4 model;

void main()
{
    vec2 position = vec2(0.5);
    gl_Position = model * vec4(position, 0.0, 1.0);
    texCoord = position;
}