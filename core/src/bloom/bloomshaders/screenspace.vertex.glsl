#ifdef GL_ES
#define MED mediump
#define HIGH highp
#else  
#define MED
#endif
attribute vec4 a_position; 
attribute vec2 a_texCoord0; 
varying MED vec2 v_texCoords;
uniform vec2 random;

MED float rand(vec2 co)
{
    MED float a = 12.9898;
    MED float b = 78.233;
    MED float c = 43758.5453;
    MED float dt= dot(co.xy ,vec2(a,b));
    MED float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}

void main()
{
	v_texCoords = a_texCoord0;
	gl_Position = a_position - rand(random);
}