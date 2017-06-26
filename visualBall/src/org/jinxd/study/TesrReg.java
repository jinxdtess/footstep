package org.jinxd.study;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TesrReg {
	@Test
    public void test(){
		String preWinter="1 2 8 9 11 36";
		String[] strs=preWinter.split("\\s");
		for(int i=0;i<strs.length;i++){
			if(strs[i].length()==1){
				strs[i]="\\b"+strs[i]+"\\b";
			}
		}
		
		String target="5 8 10 19 25 36";
		Pattern p;
		Matcher m;
		int count=0;
		for(String regex:strs){
			p=Pattern.compile(regex);
			m=p.matcher(target);
			if(m.find()){
				System.out.println("匹配\""+m.group()+"\"在target的第"+(m.start()+1)+"个字符");
				count++;
			}
			
		}
		System.out.println("matched number:"+count);
		
    }
}
