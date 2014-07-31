package com.system.distribute.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;



import com.system.distribute.config.IConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;


/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-7-9 下午3:40:51 
 * @function:conf 格式的配置加载工具 
 */
public class TypeSafeConfigLoadUtils {

	
	//private static Config conf = ConfigFactory.load();//default=reference.conf
	
	
	/**
	 * 从conf文件中加载
	 * @param file
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public static Config loadFromFile(File file){
		
		 return ConfigFactory.parseFile(file);
	}
	/**
	 * 用户可以从属性文件中配置加载
	 * @param fproperty
	 * @return
	 * @throws Exception
	 * 添加（修改）人：zhuyuping
	 */
	public static Config loadFromProps(File fproperty) throws Exception{
		
		Properties props=new Properties();
		props.load(new FileInputStream(fproperty));
		
		
		return ConfigFactory.parseProperties(props);
		
	}
	/**
	 * 从用户提供的json字符串加载配置
	 * @param json
	 * @return
	 * 添加（修改）人：zhuyuping
	 */
	public static Config loadFromString(String json){
		
		return ConfigFactory.parseString(json);
	}
	/**
	 * 从远程加载配置
	 * @param cur
	 * @param url
	 * @return
	 * 添加（修改）人：zhuyuping
	 * @throws Exception 
	 */
	public static Config loadFormRemote(IConfig cur,String url) throws Exception{
		//new URL("http://www.baidu.com/xx/xxx")
		return ConfigFactory.parseURL(new URL(url));
	}
    
	public static void main(String[] args) {
		Config config = ConfigFactory.parseFile(new File("c:/reference.conf"));
	    String str = config.root().render(
	        ConfigRenderOptions.concise().setFormatted(true).setJson(true).setComments(true));
            //    response.getWriter().write(ConfigFactory.load().root().render());
	    /**
	     * 
	     * 
	     *     ConfigParseOptions options = ConfigParseOptions.defaults()
 *         .setSyntax(ConfigSyntax.JSON)
 *         .setAllowMissing(false)
	     * 
	     * 
	     */
	    System.out.println(str);
	}
	
	
}
