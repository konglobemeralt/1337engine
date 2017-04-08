package entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jesper on 2017-03-14.
 */
public class Light {

    private Vector3f position;
    private Vector3f colour;
    private Vector3f attenutation = new Vector3f(1, 0, 0);

    public Light(Vector3f position, Vector3f colour){
        this.position = position;
        this.colour = colour;
    }

    public Light(Vector3f position, Vector3f colour, Vector3f attenutation){
        this.position = position;
        this.colour = colour;
        this.attenutation = attenutation;
    }

    public Vector3f getAttenutation() {
        return attenutation;
    }

    public void setAttenutation(Vector3f attenutation) {
        this.attenutation = attenutation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
