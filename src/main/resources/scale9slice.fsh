#version 330 core

in vec2 texCoord;
flat in vec2 drawSize;

uniform sampler2D image;
uniform vec3 color = vec3(1.0, 1.0, 1.0);

float map(float value, float originalMin, float originalMax, float newMin, float newMax) {
    return (value - originalMin) / (originalMax - originalMin) * (newMax - newMin) + newMin;
}

// * Hard copied from stackexchange *
// Helper function, because WET code is bad code
// Takes in the coordinate on the current axis and the borders
float processAxis(float coord, float textureBorder, float windowBorder) {
    if (coord < windowBorder) return map(coord, 0, windowBorder, 0, textureBorder);
    if (coord < 1 - windowBorder) return map(coord, windowBorder, 1 - windowBorder, textureBorder, 1 - textureBorder);
    return map(coord, 1 - windowBorder, 1, 1 - textureBorder, 1);
}

void main() {
    vec2 sliceSize = textureSize(image, 0)/3.0;
    vec2 adjustedTexCoord = vec2(
    processAxis(texCoord.x, 1.0/3.0, sliceSize.x / drawSize.x),
    processAxis(texCoord.y, 1.0/3.0, sliceSize.y / drawSize.y)
    );

    gl_FragColor = vec4(color, 1.0) * texture(image, adjustedTexCoord);
}
