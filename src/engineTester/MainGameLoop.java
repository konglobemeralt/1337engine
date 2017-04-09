package engineTester;

import entities.Camera;
import entities.Entity;
import entities.*;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

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
        MasterRenderer renderer = new MasterRenderer(loader);

        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("gui"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);

        GuiRenderer guiRenderer = new GuiRenderer(loader);

        //terrain
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassGround"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("wall1"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("floor"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        Terrain terrain00 = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
        Terrain terrain01 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        Terrain terrain10 = new Terrain(-1, 0, loader, texturePack, blendMap, "heightmap");
        Terrain terrain11 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");

        List<Entity> models = new ArrayList<Entity>();
        Random random = new Random();


        //Ferns
        RawModel fern = ObjLoader.loadObjModel("fern", loader);
        ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
        fernTexture.setNumberOfRows(2);
        fernTexture.setShineDamper(10);
        fernTexture.setReflectivity(0.1f);
        fernTexture.setUseFakeLighting(true);
        fernTexture.setHasTransparancy(true);
        TexturedModel staticFernModel = new TexturedModel(fern, fernTexture);

        for(int i = 0; i < 150; i ++){
            float x = random.nextFloat() * 200 -50;
            float z = random.nextFloat() * -400;
            float y = terrain01.getHeightOfTerrain(x, z);
            models.add(new Entity(staticFernModel, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360f, 0f, 0.73f));
        }

        //Grass
        RawModel grass = ObjLoader.loadObjModel("grass", loader);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("grass"));
        grassTexture.setShineDamper(10);
        grassTexture.setReflectivity(0.1f);
        grassTexture.setUseFakeLighting(true);
        grassTexture.setHasTransparancy(true);
        TexturedModel staticGrassModel = new TexturedModel(grass, grassTexture);

        for(int i = 0; i < 123; i ++){
            float x = random.nextFloat() * 200 -50;
            float z = random.nextFloat() * -400;
            float y = terrain01.getHeightOfTerrain(x, z);
            models.add(new Entity(staticGrassModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360f, 0f, 0.73f));
        }

        //Houses
        RawModel model = ObjLoader.loadObjModel("hus", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("wall1"));
        texture.setShineDamper(10);
        texture.setReflectivity(0.01f);
        TexturedModel staticModel = new TexturedModel(model, texture);

        for(int i = 0; i < 150; i ++){
            float x = random.nextFloat() * 200 -50;
            float z = random.nextFloat() * -400;
            float y = terrain01.getHeightOfTerrain(x, z);
            models.add(new Entity(staticModel, new Vector3f(x, y-0.2f, z), 0, random.nextFloat() * 360f, 0f, 0.04f));
        }

        //Dragons
        RawModel dragonModel = ObjLoader.loadObjModel("dragon", loader);
        ModelTexture dragonText = new ModelTexture(loader.loadTexture("floor"));
        dragonText.setShineDamper(10);
        dragonText.setReflectivity(1);
        TexturedModel TexturedDragonModel = new TexturedModel(dragonModel, dragonText);

        for(int i = 0; i < 100; i ++){
            float x = random.nextFloat() * 200 -50;
            float y = random.nextFloat() * 200 + 5;
            float z = random.nextFloat() * -300;
            models.add(new Entity(TexturedDragonModel, new Vector3f(x, y, z), random.nextFloat() * 180f, random.nextFloat() * 180f, 0f, 1f));
        }

        //Player
        RawModel bunnyModel =  ObjLoader.loadObjModel("bunny", loader);
        ModelTexture bunnyText = new ModelTexture(loader.loadTexture("mud"));
        TexturedModel texturedBunnyModel = new TexturedModel(bunnyModel, bunnyText);
        Player player = new Player(texturedBunnyModel, new Vector3f(0, 0,0), 0, 180, 0, 0.3f);

        //Camera and light
        List<Light> lights = new ArrayList<Light>();
        Light light1 = new Light(new Vector3f(0,1200, 6500), new Vector3f(0.8f, 0.85f, 0.8f));
        Light light2 = new Light(new Vector3f(0,4, -100), new Vector3f(1.1f, 0.1f, 0.6f), new Vector3f(0.1f, 0.01f, 0.002f));
        Light light3 = new Light(new Vector3f(46,5, -159), new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0.1f, 0.01f, 0.002f));
        Light light4 = new Light(new Vector3f(23,7, -200), new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f(0.1f, 0.01f, 0.002f));
        lights.add(light1);
        lights.add(light2);
        lights.add(light3);
        lights.add(light4);

        models.add(new Entity(texturedBunnyModel, new Vector3f(0,6, 13), 0, 180, 0, 0.3f));
        models.add(new Entity(texturedBunnyModel, new Vector3f(10,7, 00), 0, 180, 0, 0.3f));
        models.add(new Entity(texturedBunnyModel, new Vector3f(-10,9, 00), 0, 180, 0, 0.3f));


        Camera camera = new Camera(player);
        camera.setPosition(new Vector3f(0, 15, 0));
        camera.setPitch(15);

        while(!Display.isCloseRequested()){
            camera.move();
            player.move(terrain01);

            renderer.processEntity(player);
            renderer.processEntity(player);

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
            renderer.render(lights, camera);
           // guiRenderer.render(guis);

            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
