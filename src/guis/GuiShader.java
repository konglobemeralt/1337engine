package guis;

import org.lwjgl.util.vector.Matrix4f;
import shaders.ShaderProgram;

/**
 * Created by Jesper on 2017-03-26.
 */
public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/shaders/guiVertexShader";
    private static final String FRAGMENT_FILE = "src/shaders/guiFragmentShader";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUnifromLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }




}
