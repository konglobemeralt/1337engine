package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.*;

/**
 * Created by Jesper on 2017-03-10.
 */
public class MainGameLoop {

    public static void main(String[] args){

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

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

        RawModel model = loader.loadToVAO(vertices, indices);


        while(!Display.isCloseRequested()){
            renderer.prepare();
            //Game logic
            renderer.render(model);
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();


    }

}
