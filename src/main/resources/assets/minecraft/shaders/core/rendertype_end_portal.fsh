#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
vec3(0.082352, 0.007843, 0.121568),
vec3(0.105882, 0.019607, 0.149019),
vec3(0.980392, 0.929411, 1.0),
vec3(0.109803, 0.054901, 0.129411),
vec3(0.890196, 0.694117, 0.988235),
vec3(0.074509, 0.043137, 0.121568),
vec3(0.866667, 0.549019, 1.0),
vec3(0.933333, 0.890196, 1.0),
vec3(0.105882, 0.050980, 0.149019),
vec3(0.039215, 0.039215, 0.149019),
vec3(0.949019, 0.949019, 1.0),
vec3(1.0, 0.949019, 1.0),
vec3(0.196766, 0.142899, 0.214696),
vec3(0.047281, 0.315338, 0.321970),
vec3(0.204675, 0.390010, 0.302066),
vec3(0.080955, 0.314821, 0.661491)
);

const mat4 SCALE_TRANSLATE = mat4(
0.5, 0.0, 0.0, 0.25,
0.0, 0.5, 0.0, 0.25,
0.0, 0.0, 1.0, 0.0,
0.0, 0.0, 0.0, 1.0
);

mat4 end_portal_layer(float layer) {
    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2(4.5 - layer / 4.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    vec3 color = textureProj(Sampler0, texProj0).rgb * COLORS[0];
    for (int i = 0; i < EndPortalLayers; i++) {
        color += textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb * COLORS[i];
    }
    fragColor = vec4(color, 1.0);
}