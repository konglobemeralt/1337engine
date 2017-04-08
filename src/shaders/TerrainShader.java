package shaders;

import entities.Camera;
import entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

import java.util.List;

/**
 * Created by Jesper on 2017-03-16.
 */
public class TerrainShader extends ShaderProgram {
    private static final int MAX_LIGHTS = 4;
    private static final String VERTEX_FILE = "src/shaders/terrainVertexShader";
    private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];

    private int location_shineDamper;
    private int location_reflectivity;

    private int location_skyColour;

    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;


    public TerrainShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUnifromLocation("transformationMatrix");
        location_projectionMatrix = super.getUnifromLocation("projectionMatrix");
        location_viewMatrix = super.getUnifromLocation("viewMatrix");
        location_shineDamper = super.getUnifromLocation("shineDamper");
        location_reflectivity= super.getUnifromLocation("reflectivity");
        location_skyColour = super.getUnifromLocation("skyColour");
        location_backgroundTexture = super.getUnifromLocation("backgroundTexture");
        location_rTexture = super.getUnifromLocation("rTexture");
        location_gTexture = super.getUnifromLocation("gTexture");
        location_bTexture = super.getUnifromLocation("bTexture");
        location_blendMap = super.getUnifromLocation("blendMap");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];
        for(int i=0;i<MAX_LIGHTS;i++){
            location_lightPosition[i] = super.getUnifromLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUnifromLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUnifromLocation("attenuation[" + i + "]");
        }
    }

    public void connectTexturesUnits(){
        super.loadInt(location_backgroundTexture, 0);
        super.loadInt(location_rTexture, 1);
        super.loadInt(location_gTexture, 2);
        super.loadInt(location_bTexture, 3);
        super.loadInt(location_blendMap, 4);
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);

    }

    public void loadLights(List<Light> lights){
        for(int i=0;i<MAX_LIGHTS;i++){
            if(i<lights.size()){
                super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
                super.loadVector(location_lightColour[i], lights.get(i).getColour());
                super.loadVector(location_attenuation[i], lights.get(i).getAttenutation());
            }else{
                super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
                super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
                super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
            }
        }
    }

    public void loadSkyColour(float r, float g, float b){
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }

    public void loadTransformationMatrix(Matrix4f transformation){
        super.loadMatrix(location_transformationMatrix, transformation);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }


}
