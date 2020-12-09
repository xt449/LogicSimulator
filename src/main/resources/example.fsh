#version 330 core

in vec2 TexCoords;
out vec4 color;

uniform sampler2D image;

void main()
{
    vec4 texel = texture(image, TexCoords);
    if(texel.a < 0.5) {
        discard;
    }
    color = texel;
}