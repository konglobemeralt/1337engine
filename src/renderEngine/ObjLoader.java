package renderEngine;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesper on 2017-03-14.
 */
public class ObjLoader {

    public static RawModel loadObjModel(String fileName, Loader loader){
        FileReader fr = null;
        try {
            fr = new FileReader(new File("res/models/" + fileName + ".obj"));
        }catch (FileNotFoundException e){
            System.err.println("Could not load " + fileName + ".obj");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> verticies = new ArrayList<Vector3f>();
        List<Vector2f> uvs = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();

        float[] verticesArray = null;
        float[] uvsArray = null;
        float[] normalsArray = null;
        int[] indicesArray = null;
        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    verticies.add(vertex);
                }else if(line.startsWith("vt ")){
                    Vector2f uv = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    uvs.add(uv);
                }else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }else if(line.startsWith("f ")){
                    normalsArray = new float[verticies.size()*3];
                    uvsArray = new float[verticies.size()*2];
                    break;
                }

            }
            while(line!=null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, uvs, normals, uvsArray, normalsArray);
                processVertex(vertex2, indices, uvs, normals, uvsArray, normalsArray);
                processVertex(vertex3, indices, uvs, normals, uvsArray, normalsArray);

                line= reader.readLine();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        verticesArray = new float[verticies.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex:verticies){
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;

        }

        for(int i = 0; i<indices.size(); i++ ){
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, uvsArray, indicesArray);

    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> uvs, List<Vector3f> normals, float[] uvsArray, float[] normalsArray){

        int currentVertexPointer = Integer.parseInt(vertexData[0]) -1;
        indices.add(currentVertexPointer);
        Vector2f currentUV = uvs.get(Integer.parseInt(vertexData[1])-1);
        uvsArray[currentVertexPointer*2] = currentUV.x;
        uvsArray[currentVertexPointer*2+1] = 1 - currentUV.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNorm.x;
        normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;
    }

}
