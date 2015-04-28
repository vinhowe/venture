varying vec4 vColor;
varying vec2 vTexCoord;

uniform vec2 screenSize;

uniform sampler2D u_texture;
uniform vec4 v_time;

uniform float brightness = 1;

const float RADIUS = 0.75;

const float SOFTNESS = 0.6;

varying vec2 position;

uniform float blurSize;

uniform float grayScaleScale;

void main() {
    vec2 p = position;
	//vec3 color = texture2D(u_texture, v_texCoords).rgb;

	
    vec4 texColor = vec4(0.0); // texture2D(u_texture, vTexCoord)
    texColor += texture2D(u_texture, vTexCoord - 4.0*blurSize) * 0.05;
    texColor += texture2D(u_texture, vTexCoord - 3.0*blurSize) * 0.09;
    texColor += texture2D(u_texture, vTexCoord - 2.0*blurSize) * 0.12;
    texColor += texture2D(u_texture, vTexCoord - blurSize) * 0.15;
    texColor += texture2D(u_texture, vTexCoord) * 0.16;
    texColor += texture2D(u_texture, vTexCoord + blurSize) * 0.15;
    texColor += texture2D(u_texture, vTexCoord + 2.0*blurSize) * 0.12;
    texColor += texture2D(u_texture, vTexCoord + 3.0*blurSize) * 0.09;
    texColor += texture2D(u_texture, vTexCoord + 4.0*blurSize) * 0.05;

    vec4 timedColor = (vColor + 1);

    vec2 position = (gl_FragCoord.xy / screenSize.xy) - vec2(0.5);
    float len = length(position);

    float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);

    texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, 0.5);
	
	vec3 grayscale = vec3((texColor.r + texColor.g + texColor.b) / 3.0);

	vec3 gray = mix(texColor.rgb, grayscale.rgb, grayScaleScale);
	
	vec3 final = mix(gray, vec3(noise(p)), 0.5);
	
    gl_FragColor = vec4(final * brightness, texColor.a);
	//gl_FragColor.rgb = final;
	//gl_FragColor.a = 1;
}

float random(float p) {
  return fract(sin(p)*10000.);
}

float noise(vec2 p) {
  return random(p.x + p.y*10000.);
}