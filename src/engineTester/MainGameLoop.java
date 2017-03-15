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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jesper on 2017-03-10.
 */
public class MainGameLoop {

    public static void main(String[] args){

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();

        List<Entity> models = new ArrayList<Entity>();
        Random random = new Random();

        RawModel model = ObjLoader.loadObjModel("hus", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("wall"));
        texture.setShineDamper(10);
        texture.setReflectivity(0.01f);
        TexturedModel staticModel = new TexturedModel(model, texture);
        for(int i = 0; i < 100; i ++){
            float x = random.nextFloat() * 200 -50;
            float y = random.nextFloat() * 200 -50;
            float z = random.nextFloat() * -400;
            models.add(new Entity(staticModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 0.07f));
        }

        RawModel dragonModel = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture dragonText = new ModelTexture(loader.loadTexture("floor"));
        dragonText.setShineDamper(10);
        dragonText.setReflectivity(1);
        TexturedModel TexturedDragonModel = new TexturedModel(dragonModel, dragonText);

        Light light = new Light(new Vector3f(0,0, 33), new Vector3f(1, 1, 0.9f));
        Camera camera = new Camera();

        for(int i = 0; i < 100; i ++){
            float x = random.nextFloat() * 100 -50;
            float y = random.nextFloat() * 100 -50;
            float z = random.nextFloat() * -300;
            models.add(new Entity(TexturedDragonModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
        }


        while(!Display.isCloseRequested()){
            camera.move();
            //Game logic

            for(Entity entity: models){
                if(entity.getModel().getRawModel().equals(TexturedDragonModel.getRawModel())){
                    entity.increaseRotation(0.1f, 0.02f, 0.5f);
                }

                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();


    }

}
