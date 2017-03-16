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
import terrains.Terrain;
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

        //Camera and light
        Light light = new Light(new Vector3f(0,17, 33), new Vector3f(0.6f, 0.7f, 0.7f));
        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 1, 0));

        //terrain
        Terrain terrain00 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("floor")));
        Terrain terrain01 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("floor")));
        Terrain terrain10 = new Terrain(-1, 0, loader, new ModelTexture(loader.loadTexture("floor")));
        Terrain terrain11 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("floor")));

        List<Entity> models = new ArrayList<Entity>();
        Random random = new Random();

        //Grass
        RawModel grass = ObjLoader.loadObjModel("grass", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grass"));
        grassTexture.setShineDamper(10);
        grassTexture.setReflectivity(0.1f);
        grassTexture.setUseFakeLighting(true);
        grassTexture.setHasTransparancy(true);
        TexturedModel staticGrassModel = new TexturedModel(grass, grassTexture);

        for(int i = 0; i < 350; i ++){
            float x = random.nextFloat() * 200 -50;
            float y = random.nextFloat() * 200 -50;
            float z = random.nextFloat() * -400;
            models.add(new Entity(staticGrassModel, new Vector3f(x, 0f, z), 0, random.nextFloat() * 360f, 0f, 0.73f));
        }


        //Houses
        RawModel model = ObjLoader.loadObjModel("hus", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("wall1"));
        texture.setShineDamper(10);
        texture.setReflectivity(0.01f);
        TexturedModel staticModel = new TexturedModel(model, texture);

        for(int i = 0; i < 950; i ++){
            float x = random.nextFloat() * 400 -50;
            float y = random.nextFloat() * 400 -50;
            float z = random.nextFloat() * -500;
            models.add(new Entity(staticModel, new Vector3f(x, -0.2f, z), 0, random.nextFloat() * 360f, 0f, 0.04f));
        }

        //Dragons
        RawModel dragonModel = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture dragonText = new ModelTexture(loader.loadTexture("floor"));
        dragonText.setShineDamper(10);
        dragonText.setReflectivity(1);
        TexturedModel TexturedDragonModel = new TexturedModel(dragonModel, dragonText);

        for(int i = 0; i < 100; i ++){
            float x = random.nextFloat() * 100 -50;
            float y = random.nextFloat() * 100 -50;
            float z = random.nextFloat() * -300;
            models.add(new Entity(TexturedDragonModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
        }


        while(!Display.isCloseRequested()){
            camera.move();
            //Game logic

            renderer.processTerrain(terrain00);
            renderer.processTerrain(terrain01);
            renderer.processTerrain(terrain10);
            renderer.processTerrain(terrain11);

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
