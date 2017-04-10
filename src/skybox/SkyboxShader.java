package skybox;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import shaders.ShaderProgram;
import toolbox.Maths;
/**
 * Created by Jesper on 2017-04-09.
 */
public class SkyboxShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader";
    private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader";

    private static final float ROTATE_SPEED = 0.4f;

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;
    private int location_cubeMap;
    private int location_cubeMap2;
    private int location_blendFactor;

    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSecond();
        Matrix4f.rotate((float)Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);

        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadFogColour(float r, float g, float b){
        super.loadVector(location_fogColour, new Vector3f(r, g, b));
    }

    public void loadBlendColour(float blendFactor){
        super.loadFloat(location_blendFactor, blendFactor);
    }

    public void connectTextureUnits(){
        super.loadInt(location_cubeMap, 0);
        super.loadInt(location_cubeMap2, 1);


    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUnifromLocation("projectionMatrix");
        location_viewMatrix = super.getUnifromLocation("viewMatrix");
        location_fogColour = super.getUnifromLocation("fogColour");
        location_blendFactor = super.getUnifromLocation("blendFactor");
        location_cubeMap = super.getUnifromLocation("cubeMap");
        location_cubeMap2 = super.getUnifromLocation("cubeMap2");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}