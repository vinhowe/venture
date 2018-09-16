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


// uniform float brightness = 1;

uniform float lightDistance = 0;
uniform float intensity = 1;
uniform vec2 mousePos;

uniform float lightCount = 0;

const float RADIUS = 0.9;

const float SOFTNESS = 0.25;

uniform float lightRadius = 500;

struct PointLight {
    vec2 position;

    float intensity;
    float radius;

    vec3 color;
};
#define NR_POINT_LIGHTS 64
uniform PointLight pointLights[NR_POINT_LIGHTS];

vec3 CalcPointLight(PointLight light, vec3 color, vec3 ambient) {

    float distance = distance(light.position, gl_FragCoord.xy);

    float att = clamp(1.0 - distance/light.radius, 0.0, 1.0); att *= att;

    vec3 diffuse = att * light.color * light.intensity;

    return diffuse+ambient;
}

void main() {
    vec2 centerPosition = (gl_FragCoord.xy / screenSize.xy) - vec2(0.5);

    float len = length(centerPosition);

    float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);



     // texColor.rgb = v_color.rgb * texColor.rgb * vignette;

    //	float gcaleMix = ((texColor.r + texColor.g + texColor.b) / 3);rays
    //	vec3 grayscale = vec3(mix(texColor.r, grayscaleMix, 0.25)/15, mix(texColor.g*texColor.b, grayscaleMix, 0.75)/2.5, texColor.b*0.75);

	// vec3 gray = mix(texColor.rgb, grayscale.rgb, 1);

    vec3 ambientColor = vec3(0,0,1);
    float ambientIntensity = 0.1;
    vec3 ambient = ambientColor * ambientIntensity;

	vec4 final = v_color * texture2D(u_texture, v_texCoord0);

	vec3 lightedColor = vec3(0,0,0);

	for(int i = 0; i < lightCount; i++) {
	    lightedColor += final.rgb*CalcPointLight(pointLights[i], final.rgb, ambient);
    }

    gl_FragColor = vec4(lightedColor, final.a);
}

