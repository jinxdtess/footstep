package org.jinxd.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class StringToDateProcessor implements JsonValueProcessor{
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

	public Object processArrayValue(Object value, JsonConfig arg1) {
		return this.process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig arg2) {
		return this.process(value);
	}
    public Object process(Object value){
    	if(value==null){
    		return "";
    	}else if(value instanceof String && value.toString().matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
    		try {
				return sdf.parse((String)value);
			} catch (ParseException e) {
				e.printStackTrace();
			}finally{
				return value.toString();
			}
    	}else{
    		return value.toString();
    	}
    }
}
