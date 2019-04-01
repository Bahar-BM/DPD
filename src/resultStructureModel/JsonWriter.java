/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultStructureModel;
    
import resultMaker.WriterStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/**
 *
 * @author amirEbrahimi.um@gmail.com
 */
public class JsonWriter implements WriterStrategy
{
    
        
    
    @Override
    public String makeResult(PatternsGroupInfo patternsGroupInfo) 
    {
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        
        Gson gson1 = new Gson();
//        
//        return gson1.toJson(patternsGroupInfo);
//        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(gson1.toJson(patternsGroupInfo));
        String prettyJsonString = gson.toJson(je);
        
        return prettyJsonString;
    }
    
   
    
}
