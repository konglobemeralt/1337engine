package models;

import textures.ModelTexture;

/**
 * Created by Jesper on 2017-03-12.
 */
public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture){
        this.rawModel = model;
        this.texture = texture;
    }

    public ModelTexture getTexture() {
        return texture;
    }
    public RawModel getRawModel() {
        return rawModel;
    }
}
