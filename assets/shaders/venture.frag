//#ifdef GL_ES
//#define LOWP lowp
//precision mediump float;
//#else
//#define LOWP
//#endif
//varying LOWP vec4 v_color;
//varying vec2 v_texCoords;
//uniform sampler2D u_texture;
//void main()
//{
//  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
//}

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 screenSize;

uniform sampler2D u_texture;
uniform vec4 v_time;

uniform float brightness = 2;

const float RADIUS = 0.9;

const float SOFTNESS = 1;



uniform float blurSize;

uniform float grayScaleScale;

uniform vec2 center = vec2(1920/2, 1080/2);
uniform float radius = 500;
uniform float scale = 500;

float random(float p) {
  return fract(sin(p)*100000.);
}

float noise(vec2 p) {
  return random(p.x*10000. + p.y*10000.);
}

void main() {
    vec2 position = (gl_FragCoord.xy / screenSize.xy) - vec2(0.5);
    vec2 p = position;
	//vec3 color = texture2D(u_texture, v_texCoords).rgb;

    vec4 texColor = vec4(0.0); // texture2D(u_texture, v_texCoord0)
    texColor = texture2D(u_texture, v_texCoord0);

    vec4 timedColor = (v_color + 1);


    float len = length(position);

    float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);

    texColor.rgb = v_color.rgb * mix(texColor.rgb, texColor.rgb * vignette, 0.5);

	float grayscaleMix = ((texColor.r + texColor.g + texColor.b) / 3);

	vec3 grayscale = vec3(mix(texColor.r, grayscaleMix, 0.25)/15, mix(texColor.g*texColor.b, grayscaleMix, 0.75)/2.5, texColor.b*0.75);

	vec3 gray = mix(texColor.rgb, grayscale.rgb, 1);

	vec3 final = mix(texColor.rgb, gray, 0);

    gl_FragColor = vec4(final*1.5, texColor.a);
}