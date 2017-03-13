package engineTester;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

/**
 * Created by Jesper on 2017-03-10.
 */
public class MainGameLoop {

    public static void main(String[] args){

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        //Test verts
        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0f
        };
        //Test indices
        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("floor"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(-1, 0, 0), 0, 0, 0, 1);

        while(!Display.isCloseRequested()){
            entity.increasePosition(0.002f, 0, 0);
            entity.increaseRotation(0, 1, 0.5f);
            renderer.prepare();
            //Game logic
            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();


    }

}
