package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

/**
 * Created by Jesper on 2017-03-10.
 */
public class MainGameLoop {

    public static void main(String[] args){

        DisplayManager.createDisplay();

        while(!Display.isCloseRequested()){

            //Game logic
            //render
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();


    }

}
