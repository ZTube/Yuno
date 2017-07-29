varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float u_time;
uniform float u_noise;
uniform float u_rand;
uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;

highp float rand(vec2 co)
{
    highp float a = 12.9898;
    highp float b = 78.233;
    highp float c = 43758.5453;
    highp float dt= dot(co.xy ,vec2(a,b));
    highp float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}

void main()
{
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    color.rg = vec2(color.r * u_time, color.g * u_time);

    float noise = rand(vec2(mix(v_texCoord0.x, v_texCoord0.x * u_rand, 0.2), mix(v_texCoord0.y, v_texCoord0.y * u_rand, 0.2)));

    color.rgb = mix(color.rgb, color.rgb * noise, u_noise);


    vec2 relativePosition = gl_FragCoord.xy / u_resolution - 0.5;
    float len = length(relativePosition);
    float vignette = smoothstep(0.6, 0.3, len);

    color.rgb = mix(color.rgb, color.rgb * vignette, 0.7);

    gl_FragColor = color;
}