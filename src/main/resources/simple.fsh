#version 330 core

in vec2 texCoords;

uniform sampler2D image;
uniform vec3 color = vec3(1.0, 1.0, 1.0);
uniform bool mirror = false;
uniform bool flip = false;
uniform bool invert = false;

void main() {
    float x = mirror ? 1.0 - texCoords.x : texCoords.x;
    float y = flip ? 1.0 - texCoords.y : texCoords.y;

    gl_FragColor = vec4(color, 1.0) * texture(image, invert ? vec2(y, x) : vec2(x, y));
}
