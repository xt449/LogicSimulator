#version 330 core

layout (location = 0) in vec4 vertex;// <vec2 position, vec2 texCoords>

out vec2 texCoord;
flat out vec2 drawSize;

uniform mat4 model;
uniform mat4 projection;

void main() {
    texCoord = vertex.zw;

    drawSize = vec2(model[0][0], model[1][1]);

    gl_Position = projection * model * vec4(vertex.xy, 0.0, 1.0);
}
