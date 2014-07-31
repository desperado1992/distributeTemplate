package com.system.distribute.file.helper;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MyHttpPostRequestEncoder;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.ImmutableMap;
import com.system.distribute.file.FileChunk;

/**
 * This class is meant to be run against {@link HttpUploadServer}.
 */
public class HttpUploadClient {

    private static final Logger logger = Logger.getLogger(HttpUploadClient.class.getName());
    public static Object post(String baseUri,boolean isChunk,ImmutableMap<String,Object> map,Object obj) throws Exception {
       
    	 String postSimple, postFile, get;
         if (baseUri.endsWith("/")) {
             postSimple = baseUri + "formpost";
             postFile = baseUri + "formpostmultipart";
             get = baseUri + "formget";
         } else {
             postSimple = baseUri + "/formpost";
             postFile = baseUri + "/formpostmultipart";
             get = baseUri + "/formget";
         }
         URI uriSimple = new URI(postSimple);
         String scheme = uriSimple.getScheme() == null ? "http" : uriSimple.getScheme();
         String host = uriSimple.getHost() == null ? "localhost" : uriSimple.getHost();
         int port = uriSimple.getPort();
         if (port == -1) {
             if ("http".equalsIgnoreCase(scheme)) {
                 port = 80;
             } else if ("https".equalsIgnoreCase(scheme)) {
                 port = 443;
             }
         }

         
        
       
         EventLoopGroup group = new NioEventLoopGroup();

         // setup the factory: here using a mixed memory/disk based on size threshold
         HttpDataFactory factory = new DefaultHttpDataFactory(true); // Disk if MINSIZE exceed

         DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
         DiskFileUpload.baseDirectory = null; // system temp directory
         DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
         DiskAttribute.baseDirectory = null; // system temp directory
         Bootstrap b = new Bootstrap();
         b.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
         b.group(group).channel(NioSocketChannel.class).handler(new HttpUploadClientIntializer());
         Channel channel = b.connect(host, port).sync().channel();
         try {
        	 List<InterfaceHttpData> bodylist =null;
        	 if(isChunk){
        		 FileChunk fileChunk=(FileChunk) obj;
        		 bodylist=formPost(channel, host, port, uriSimple, fileChunk, factory, map); 
        	 }else{
        		 
        		 File file = new File(String.valueOf(obj));
                 bodylist = formPost(channel, host, port, uriSimple, file, factory,map);
        	 }
            if (bodylist == null) {
                factory.cleanAllHttpDatas();
                return null;
            }
           
            HttpUploadClientHandler handler=channel.pipeline().get(HttpUploadClientHandler.class);
            Object result=handler.getResult();
            channel.closeFuture().sync();
            return result;
        } finally {
         
            group.shutdownGracefully();

          
            factory.cleanAllHttpDatas();
        }
    }
    
    
    
    /**
     * **
     * 添加文件filechunk分块上传的方法 
     */
    private static List<InterfaceHttpData> formPost(Channel channel,
			String host, int port, URI uriSimple, FileChunk chunk,
			HttpDataFactory factory, ImmutableMap<String, Object> map) {
    	boolean flag=true;
    	if(chunk==null||chunk.getBytes().length==0) flag=false; 

        HttpRequest request =
                new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uriSimple.toASCIIString());

        MyHttpPostRequestEncoder bodyRequestEncoder = null;
        try {
            bodyRequestEncoder = new MyHttpPostRequestEncoder(factory, request, flag); // false not multipart
        } catch (NullPointerException e) {
           
            e.printStackTrace();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
       
        try {
            bodyRequestEncoder.addBodyAttribute("getform", "POST");
            
            for (Entry<String, Object> entry : map.entrySet()) {
        		//TODO 暂时未添加系列化方式
        		Object obj=entry.getValue();
        		 if (obj instanceof String || obj instanceof Integer
        				    || obj instanceof Float || obj instanceof Boolean
        				    || obj instanceof Short || obj instanceof Double
        				    || obj instanceof Long || obj instanceof BigDecimal
        				    || obj instanceof BigInteger || obj instanceof Byte) {
        			 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(obj));
        			             
        				    } else if (obj instanceof Object[]) {
        				      Object[] objs=(Object[]) obj;
        				      for (Object object : objs) {
        				    	  bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
							    }
        				    } else if (obj instanceof List) {
        				    List objs=(List) obj;
        				    for (Object object : objs) {
        				    	 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
							}	
        				    	
        				    } else if (obj instanceof Map) {
        				        //map处理
        				    } else if (obj instanceof Set) {
        				    	Set objs=(Set) obj;
            				    for (Object object : objs) {
            				    	 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
    							}	
        				    } else {
        				       //实体bean
        				    }
        		
     
			}
            bodyRequestEncoder.addBodyStreamUpload("myChunk", chunk, "application/x-zip-compressed", false);
     
        } catch (NullPointerException e) {
           
            e.printStackTrace();
        } catch (Exception e) {
            
            e.printStackTrace();
        }

       
        try {
            request = bodyRequestEncoder.finalizeRequest();
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        List<InterfaceHttpData> bodylist = bodyRequestEncoder.getBodyListAttributes();

        channel.write(request);

        if (bodyRequestEncoder.isChunked()) {
       
            channel.writeAndFlush(bodyRequestEncoder).awaitUninterruptibly();
        }  else {
            channel.flush();
        }

        try {
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

        return bodylist;
	}




	public static Object get(String baseUri,ImmutableMap<String,Object> map) throws Exception {
        
   	 String postSimple, postFile, get;
        if (baseUri.endsWith("/")) {
            postSimple = baseUri + "formpost";
            postFile = baseUri + "formpostmultipart";
            get = baseUri + "formget";
        } else {
            postSimple = baseUri + "/formpost";
            postFile = baseUri + "/formpostmultipart";
            get = baseUri + "/formget";
        }
        URI uriSimple;
        try {
            uriSimple = new URI(postSimple);
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "Invalid URI syntax", e);
            return null;
        }
        String scheme = uriSimple.getScheme() == null ? "http" : uriSimple.getScheme();
        String host = uriSimple.getHost() == null ? "localhost" : uriSimple.getHost();
        int port = uriSimple.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            logger.log(Level.WARNING, "Only HTTP(S) is supported.");
            return null;
        }


       
        EventLoopGroup group = new NioEventLoopGroup();

        //HttpDataFactory factory = new DefaultHttpDataFactory(true); // Disk if MINSIZE exceed
        Bootstrap b = new Bootstrap();
        b.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        b.group(group).channel(NioSocketChannel.class).handler(new HttpUploadClientIntializer());
        Channel channel = b.connect(host, port).sync().channel();
        try {
           List<Entry<String, String>> headers = formGet(channel, host, port, get, uriSimple,map);
           //TODO
           HttpUploadClientHandler handler=channel.pipeline().get(HttpUploadClientHandler.class);
           Object result=handler.getResult();
           channel.closeFuture().sync();
           return result;
       } finally {
           group.shutdownGracefully();

       }
   }
    
   
    private static List<Entry<String, String>> formGet(Channel channel, String host, int port, String get,
            URI uriSimple,ImmutableMap<String,Object> map) throws Exception {
    
        QueryStringEncoder encoder = new QueryStringEncoder(get);
        encoder.addParam("getform", "GET");
        if(map!=null){
        	for (Entry<String, Object> entry : map.entrySet()) {
        		//TODO 暂时未添加系列化方式
        		Object obj=entry.getValue();
        		 if (obj instanceof String || obj instanceof Integer
        				    || obj instanceof Float || obj instanceof Boolean
        				    || obj instanceof Short || obj instanceof Double
        				    || obj instanceof Long || obj instanceof BigDecimal
        				    || obj instanceof BigInteger || obj instanceof Byte) {
        			         encoder.addParam(entry.getKey(),String.valueOf(obj));
        			             
        				    } else if (obj instanceof Object[]) {
        				      Object[] objs=(Object[]) obj;
        				      for (Object object : objs) {
        				    	  encoder.addParam(entry.getKey(),String.valueOf(object));
							    }
        				    } else if (obj instanceof List) {
        				    List objs=(List) obj;
        				    for (Object object : objs) {
        				    	 encoder.addParam(entry.getKey(),String.valueOf(object));
							}	
        				    	
        				    } else if (obj instanceof Map) {
        				        //map处理
        				    } else if (obj instanceof Set) {
        				    	Set objs=(Set) obj;
            				    for (Object object : objs) {
            				    	 encoder.addParam(entry.getKey(),String.valueOf(object));
    							}	
        				    } else {
        				       //实体bean
        				    }
        		
     
			}
        	
        }
        //encoder.addParam("info", String.valueOf(map.get("fpath")));
       
        URI uriGet;
        try {
            uriGet = new URI(encoder.toString());
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "Error: ", e);
            return null;
        }

        FullHttpRequest request =
                new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uriGet.toASCIIString());
        HttpHeaders headers = request.headers();
        headers.set(HttpHeaders.Names.HOST, host);
        headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
        headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP + ','
                + HttpHeaders.Values.DEFLATE);

        headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "fr");
        headers.set(HttpHeaders.Names.REFERER, uriSimple.toString());
        headers.set(HttpHeaders.Names.USER_AGENT, "Netty Simple Http Client side");
        headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

        headers.set(HttpHeaders.Names.COOKIE, ClientCookieEncoder.encode(new DefaultCookie("my-cookie", "foo"),
                new DefaultCookie("another-cookie", "bar")));

     
        List<Entry<String, String>> entries = headers.entries();
        channel.writeAndFlush(request).sync();
        return entries;
    }

    
    private static List<InterfaceHttpData> formPost(Channel channel, String host, int port, URI uriSimple,
            File file, HttpDataFactory factory,ImmutableMap<String,Object> map) throws Exception {

       /**
        * 
        HttpRequest request =
                new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uriFile.toASCIIString());

        HttpPostRequestEncoder bodyRequestEncoder = null;
        try {
            bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, true); // true => multipart
        * 
        */
    	boolean flag=true;
    	if(file==null||!file.exists()) flag=false; 

        HttpRequest request =
                new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uriSimple.toASCIIString());

        HttpPostRequestEncoder bodyRequestEncoder = null;
        try {
            bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, flag); // false not multipart
        } catch (NullPointerException e) {
           
            e.printStackTrace();
        } catch (ErrorDataEncoderException e) {
            
            e.printStackTrace();
        }
       
        try {
            bodyRequestEncoder.addBodyAttribute("getform", "POST");
            
            for (Entry<String, Object> entry : map.entrySet()) {
        		//TODO 暂时未添加系列化方式
        		Object obj=entry.getValue();
        		 if (obj instanceof String || obj instanceof Integer
        				    || obj instanceof Float || obj instanceof Boolean
        				    || obj instanceof Short || obj instanceof Double
        				    || obj instanceof Long || obj instanceof BigDecimal
        				    || obj instanceof BigInteger || obj instanceof Byte) {
        			 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(obj));
        			             
        				    } else if (obj instanceof Object[]) {
        				      Object[] objs=(Object[]) obj;
        				      for (Object object : objs) {
        				    	  bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
							    }
        				    } else if (obj instanceof List) {
        				    List objs=(List) obj;
        				    for (Object object : objs) {
        				    	 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
							}	
        				    	
        				    } else if (obj instanceof Map) {
        				        //map处理
        				    } else if (obj instanceof Set) {
        				    	Set objs=(Set) obj;
            				    for (Object object : objs) {
            				    	 bodyRequestEncoder.addBodyAttribute(entry.getKey(),String.valueOf(object));
    							}	
        				    } else {
        				       //实体bean
        				    }
        		
     
			}
            bodyRequestEncoder.addBodyFileUpload("myfile", file, "application/x-zip-compressed", false);
        } catch (NullPointerException e) {
           
            e.printStackTrace();
        } catch (ErrorDataEncoderException e) {
            
            e.printStackTrace();
        }

       
        try {
            request = bodyRequestEncoder.finalizeRequest();
        } catch (ErrorDataEncoderException e) {
            
            e.printStackTrace();
        }

        List<InterfaceHttpData> bodylist = bodyRequestEncoder.getBodyListAttributes();

        channel.write(request);

        if (bodyRequestEncoder.isChunked()) {
       
            channel.writeAndFlush(bodyRequestEncoder).awaitUninterruptibly();
        }  else {
            channel.flush();
        }

        channel.closeFuture().sync();

        return bodylist;
    }

    public static String stringify(Map<String, Object> map){
        if (map == null) return null;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public static void main(String[] args) throws Exception {
        String baseUri;
        String filePath;
        if (args.length == 2) {
            baseUri = args[0];
            filePath = args[1];
        } else {
            baseUri = "http://192.168.16.145:8080";

//            File f = File.createTempFile("upload", ".txt");
//            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//            bw.write("Some text data in a file to be posted");
//            bw.close();
//            filePath = f.getPath();
//            f.deleteOnExit();
             filePath="d:/a2cc7cd98d1001e98bd6e993b90e7bec55e79740.jpg";
        }

        logger.info("Posting to " + baseUri + ". Using file " + filePath);
         //HttpUploadClient.post(baseUri, filePath);
        HttpUploadClient.get(baseUri,ImmutableMap.<String,Object>of("fpath", filePath));
    }

 
   
}
