package org.jinxd.test;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JsonTest {
    public static void testExcludeProperites() {  
        String str = "{'string':'JSON', 'integer': 1, 'double': 2.0, 'boolean': true}";  
        JsonConfig jsonConfig = new JsonConfig();  
        jsonConfig.setExcludes(new String[] { "double", "boolean" });  
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str, jsonConfig);  
        System.out.println(jsonObject.getString("string"));  
        System.out.println(jsonObject.getInt("integer"));  
        System.out.println(jsonObject.has("double"));  
        System.out.println(jsonObject.has("boolean"));  
    }  
   
    public static void main(String[] args) {  
        JsonTest.testExcludeProperites();  
    }  
}
