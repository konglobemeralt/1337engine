package skybox;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;

import org.lwjgl.util.vector.Vector3f;
import shaders.ShaderProgram;
import toolbox.Maths;
/**
 * Created by Jesper on 2017-04-09.
 */
public class SkyboxShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/skybox/skyboxVertexShader";
    private static final String FRAGMENT_FILE = "src/skybox/skyboxFragmentShader";

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColour;

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


        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadFogColour(float r, float g, float b){
        super.loadVector(location_fogColour, new Vector3f(r, g, b));
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUnifromLocation("projectionMatrix");
        location_viewMatrix = super.getUnifromLocation("viewMatrix");
        location_fogColour = super.getUnifromLocation("fogColour");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}