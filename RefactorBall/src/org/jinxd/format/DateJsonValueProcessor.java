package org.jinxd.format;

import java.sql.Date;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {
	 
    private String format = "yyyy-MM-dd";   
    private SimpleDateFormat sdf = new SimpleDateFormat(format);   
 
    //处理数组的值
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return this.process(value);
    }
 
    //处理对象的值
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return this.process(value);
    }
     
    //处理Date类型返回的Json数值
    private Object process(Object value) {
        if (value == null) {
            return "";
        } else if (value instanceof Date)
            return sdf.format((Date) value);
        else {
            return value.toString();
        }
    }
}
