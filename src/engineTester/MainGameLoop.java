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
        texture.setShineDamper(10);
        texture.setReflectivity(0.01f);
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(-5, -1.3f, -15), 0, 0, 0, 0.02f);
        Light light = new Light(new Vector3f(0,0, 33), new Vector3f(1, 1, 0.9f));

        Camera camera = new Camera();

        RawModel dragon = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture dragonText = new ModelTexture(loader.loadTexture("floor"));
        dragonText.setShineDamper(10);
        dragonText.setReflectivity(1);
        TexturedModel staticDragon = new TexturedModel(dragon, dragonText);
        Entity dragonEntity = new Entity(staticDragon, new Vector3f(5, -1.3f, -15), 0, 0, 0, 0.7f);


        while(!Display.isCloseRequested()){
            entity.increaseRotation(0, 0.03f, 0);
            dragonEntity.increaseRotation(0, -0.05f, 0);
            camera.move();
            renderer.prepare();
            //Game logic
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            renderer.render(dragonEntity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();


    }

}
