package renderEngine;

import de.matthiasmann.twl.utils.PNGDecoder;
import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import textures.TextureData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
/**
 * Created by Jesper on 2017-03-11.
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions, float[]textureCoords, float[] normals,  int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);      //Store X, Y, Z coords in VAO[0]
        storeDataInAttributeList(1, 2, textureCoords);  //Store U, V coords in VAO[1]
        storeDataInAttributeList(2, 3, normals);        //Store Normals in VAO[3]
        undbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float [] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void undbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int [] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDateInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDateInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }


    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer((data.length));
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        this.storeDataInAttributeList(0, 2, positions);
        undbindVAO();
        return new RawModel(vaoID, positions.length/2);


    }

    public int loadTexture(String fileName){
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/"+fileName+".png"));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_MAX_TEXTURE_LOD_BIAS, -0.3f);
        } catch (FileNotFoundException e) {
            System.out.print("Texture File " + fileName + " not found...");
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    private TextureData decodeTextureFile(String fileName){
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        try{
            FileInputStream in = new FileInputStream(fileName);
            PNGDecoder decoder = new PNGDecoder(in);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Tried to load " + fileName + ", did not work...");
            System.exit(-1);
        }
        return new TextureData(buffer, width, height);



    }

    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            GL11.glDeleteTextures(texture);
        }
    }
}
