package engineTester;

import entities.Camera;
import entities.Entity;
import entities.*;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        RawModel model = ObjLoader.loadObjModel("hus", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("wall"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(0, -1.3f, -15), 0, 0, 0, 0.02f);
        Light light = new Light(new Vector3f(0,0, 33), new Vector3f(1, 1, 0.9f));

        Camera camera = new Camera();

        while(!Display.isCloseRequested()){
            entity.increaseRotation(0, 0.03f, 0);
            camera.move();
            renderer.prepare();
            //Game logic
            shader.start();
            shader.loadLight(light); //TODO: Says cannot access entities.Light but it still works so what is going on?
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();


    }

}
