package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jesper on 2017-03-13.
 */
public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera(){};
  //public Camera(Vector3f position, float pitch, float yaw, float roll) {
  //    this.position = position;
  //    this.pitch = pitch;
  //    this.yaw = yaw;
  //    this.roll = roll;
  //}

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.2f;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
            position.y+=0.2f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            position.y-=0.2f;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            yaw -=0.2f;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            yaw +=0.2f;
        }


    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
