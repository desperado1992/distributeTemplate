package com.system.distribute.file.helper;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.distribute.config.IConfig;
import com.system.distribute.core.Node;
import com.system.distribute.core.NodeFactory;
import com.system.distribute.file.FileChunk;
import com.system.distribute.file.FileQuery;
import com.system.distribute.file.FileSystem;
import com.system.distribute.sqlparser.Query;
import com.system.distribute.util.FileUtil;



public class HttpUploadServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger logger = Logger.getLogger(HttpUploadServerHandler.class.getName());

    private HttpRequest request;

    private boolean readingChunks;
    
    private Node node=null;

    private final StringBuilder responseContent = new StringBuilder();

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(true); //Disk
                                                                                                              // if
    private List<String> paths=null; 
    
    private Map<String,String> checks=Maps.newHashMap();
    
    private  int type=-1;
    
    private long chunksizes=0;
    
    boolean ischunk=false;
    
    boolean iscreate=false;
    
    // exceed
    
    
    
  
    private HttpPostRequestDecoder decoder;
    public HttpUploadServerHandler(Node node) {
		super();
		this.node = node;
	}
	static {
        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
                                                         // on exit (in normal
                                                         // exit)
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
                                                        // exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
    	Map<String, List<String>> uriAttributes=null;
    	if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            //GET请求解析 
            QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
            uriAttributes = decoderQuery.parameters();
           
            //POST请求解析
            try {
                decoder = new HttpPostRequestDecoder(factory, request);
            } catch (Exception e1) {
              
                return;
            }
            //分段接受
            readingChunks = HttpHeaders.isTransferEncodingChunked(request);
         
            if (readingChunks) {
               
                readingChunks = true;
            }
            
        }
       
        	
			switch (type) {
			
			case 1:
				//删除命令
				 for (Entry<String, List<String>> attr: uriAttributes.entrySet()) {
		            	String key=attr.getKey();
		            	 paths=Lists.newArrayList(); 
		            	List<String> vals=attr.getValue();
		            	if("name".equalsIgnoreCase(key)){
		                	//文件删除发送过来的path
		                	paths=Lists.newArrayList();
		                }else if("commandType".equalsIgnoreCase(key)){
		                	type=Integer.parseInt(vals.get(0));
		                }
		                
		            }
				if(paths!=null){
				for (String path : paths) {

					FileUtil.rmFile(path);
				}
				}
				
				break;
			case 2:
				//修改
				
				break;
			case 3:
				//查询
				//用户是查询
				//分装成query 以后
				//node.getClass().getClassLoader().loadClass(Node.class.)
				BooleanQuery query=wrapAttrs(uriAttributes.entrySet());	 
//		         String packName=Node.class.getPackage().getName();
//		         String fclassName=packName.substring(0, packName.lastIndexOf("."))+".file."+FileSystem.class.getSimpleName();
//		         Class<? extends FileSystem> fileSystemClazz=Node.class.getClassLoader().loadClass(fclassName).asSubclass(FileSystem.class);
//                 fileSystemClazz.getConstructor(IConfig.class).newInstance(node.getConfig());
				//String packName=Node.class.getPackage().getName();
				FileSystem fileSystem=null;
				if((fileSystem=(FileSystem) NodeFactory.caches.get(FileSystem.class))!=null){
					TopDocs docs=fileSystem.search(query);
					for (ScoreDoc scoreDoc : docs.scoreDocs) {
						System.out
								.println("查询的结果测试++++++++++++++++ "+scoreDoc);
					}
				}
				break;
			case 4:
				//同步操作
				break;
			default:
				//其他操作
			
				break;
			}
        	
        	
      
        if (decoder != null) {
            if (msg instanceof HttpContent) {
              
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (ErrorDataDecoderException e1) {
                    
                    ctx.channel().close();
                    return;
                }
               
            
                readHttpDataChunkByChunk();
          
                if (chunk instanceof LastHttpContent) {
                  
                    readingChunks = false;
                    reset();
                }
            }
        }
    }
    /**
     * RPC生成的 用于调用 
     * @param attrs
     * @return
     * 添加（修改）人：zhuyuping
     */
    private   BooleanQuery  wrapAttrs(Set<Entry<String, List<String>>> attrs) {
    	try{
    	BooleanQuery muquery = new BooleanQuery();
		for (Entry<String, List<String>> entry : attrs) {
			String fieldName=entry.getKey();
			List<String> vals=entry.getValue();
			for (String need : vals) {
				if(need.contains(".")){
			    	String[] keys=need.split("\\.");
			    	need=keys[1];
			    	}
				if(fieldName.equalsIgnoreCase(FileQuery.FILE_QUERY.FILENAME)){
					
					QueryParser parser = new QueryParser(Version.LUCENE_4_9, FileQuery.FILE_QUERY.FILENAME, FileSystem.analyzer);
					
					org.apache.lucene.search.Query q;
					
						q = parser.parse(need);
					
					muquery.add(q, Occur.MUST); 
					
				}else if(fieldName.equalsIgnoreCase(FileQuery.FILE_QUERY.FILESIZE)){
					int min=Integer.valueOf(need)-1024*1024;
					min=min<0?0:min;

				    muquery.add(NumericRangeQuery.newIntRange(
				    		FileQuery.FILE_QUERY.FILESIZE, min, Integer.valueOf(need)+1024*1024, true, true), Occur.MUST);
				}else if(fieldName.equalsIgnoreCase(FileQuery.FILE_QUERY.FILEPATH)){
					
					Term t = new Term(FileQuery.FILE_QUERY.FILEPATH, need);  
					muquery.add(new FuzzyQuery(t), Occur.MUST);
					
				}else if(fieldName.equalsIgnoreCase(FileQuery.FILE_QUERY.FILETYPE)){
					Term t = new Term(FileQuery.FILE_QUERY.FILETYPE, need);  
					muquery.add(new TermQuery(t), Occur.MUST);
				}else if(fieldName.equalsIgnoreCase(FileQuery.FILE_QUERY.FILEOUT)){
					
				}
			}
			
		}
		return muquery;
    	}catch(Exception e){
    		
    		return null;
    	}
		
	}

	private void reset() {
        request = null;
        type=-1;
        paths=null;
        decoder.destroy();
        decoder = null;
        ischunk=false;
        chunksizes=0;
        iscreate=false;
    }

   
    private void readHttpDataChunkByChunk() {
    	int chunk=0;
    	String checksum="";
    	int sum=0;
        try {
            while (decoder.hasNext()) {
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    try {
  
                        writeHttpData(data,chunk,checksum,sum);
                    } finally {
                        data.release();
                    }
                }
            }
        } catch (EndOfDataDecoderException e1) {
              return;
        } 
    }

    private void writeHttpData(InterfaceHttpData data,int chunk,String checksum,int sum){
    	 
        if (data.getHttpDataType() == HttpDataType.Attribute) {//字段属性
            Attribute attribute = (Attribute) data;
            String value;
            try {
            	
                value = attribute.getValue();
                if("commandType".equalsIgnoreCase(attribute.getName())){
                	type=Integer.parseInt(value);
                	
            	}else if("name".equalsIgnoreCase(attribute.getName())){
                	if(paths==null) paths=Lists.newArrayList();
                	paths.add(ServerHelper.gennerFilePath(value));
              //chunks
            	}else if("checksum".equalsIgnoreCase(attribute.getName())){
            		checksum=value;
            	}else if("chunksizes".equalsIgnoreCase(attribute.getName())){
            		
            		chunksizes=Long.valueOf(value);
            		ischunk=true;
            	}else if("chunks".equalsIgnoreCase(attribute.getName())){
            		chunk=Integer.parseInt(value);
            	}else if("chunksum".equalsIgnoreCase(attribute.getName())){
            		sum=Integer.valueOf(value);
            	}
                
                
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            
        } else {
           
            if (data.getHttpDataType() == HttpDataType.FileUpload) {//文件上传
                FileUpload fileUpload = (FileUpload) data;
                if (fileUpload.isCompleted()) {
                	
                	try {
                		if(type==0){
                			//新增
                			//if(!paths.isEmpty()){
                				//for (String path : paths) {
                			if(paths==null||paths.isEmpty()) throw new RuntimeException("请提供一个正确的文件路径");
                			 String name=fileUpload.getFilename();
                			
                			
                                for (String path : paths) {
									if(path.contains(name)){
										 
										if(ischunk){
											//分块接受的 首先构建一个
											Integer start=chunk*FileChunk.CHUNKSIZE;
			                				Integer lsize=(int) (chunk!=(sum-1)?FileChunk.CHUNKSIZE:chunksizes-chunk*FileChunk.CHUNKSIZE);
			                				RandomAccessFile raf =null;
			                				if(!iscreate){
			                				
			                				raf= new RandomAccessFile(path, "rw");
				                		    raf.setLength(chunksizes);
											raf.close();
											iscreate=true;
											}
											raf = new RandomAccessFile(path, "rw");
			                				FileChannel fileChannel=raf.getChannel();
			                				
			                				MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, start, lsize);   
			                				FileLock flock=fileChannel.lock(start,lsize, false);
			                				out.put(fileUpload.get(), start, lsize);
			                				/**下一步将添加chesum文件校验
			                				 * messageDigest.update(byteBuffer);  
                                             * String chesum = bufferToHex(messageDigest.digest()); 
			                				 */
			                				out.force();
			                			    flock.release();
			                				raf.close();
			                				clean(out);
										}else{
											  File file=new File(path);
				                          	 if(!file.exists())
				                          		    file.getParentFile().mkdirs();
				          							file.createNewFile();
		               							fileUpload.renameTo(file);
										}
									}
								}
                			
                					
                    
                		}
//                	 
							
						} catch (Exception e) {
						
							e.printStackTrace();
						}
                   
                }
            }
        }
    }
    
    public static void clean(final Object buffer) throws Exception {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
            try {
               Method getCleanerMethod = buffer.getClass().getMethod("cleaner",new Class[0]);
               getCleanerMethod.setAccessible(true);
               sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(buffer,new Object[0]);
               
               cleaner.clean();
            } catch(Exception e) {
               e.printStackTrace();
            }
               return null;}});
        
}
//    private void writeResponse(Channel channel) {
//        // Convert the response content to a ChannelBuffer.
//        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
//        responseContent.setLength(0);
//
//        // Decide whether to close the connection or not.
//        boolean close = HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(CONNECTION))
//                || request.getProtocolVersion().equals(HttpVersion.HTTP_1_0)
//                && !HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(CONNECTION));
//
//        // Build the response object.
//        FullHttpResponse response = new DefaultFullHttpResponse(
//                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
//        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
//
//        if (!close) {
//            // There's no need to add 'Content-Length' header
//            // if this is the last response.
//            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
//        }
//
//        Set<Cookie> cookies;
//        String value = request.headers().get(COOKIE);
//        if (value == null) {
//            cookies = Collections.emptySet();
//        } else {
//            cookies = CookieDecoder.decode(value);
//        }
//        if (!cookies.isEmpty()) {
//            // Reset the cookies if necessary.
//            for (Cookie cookie : cookies) {
//                response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
//            }
//        }
//        // Write the response.
//        ChannelFuture future = channel.writeAndFlush(response);
//        // Close the connection after the write operation is done if necessary.
//        if (close) {
//            future.addListener(ChannelFutureListener.CLOSE);
//        }
//    }

//    private void writeMenu(ChannelHandlerContext ctx) {

//
//        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
//        // Build the response object.
//        FullHttpResponse response = new DefaultFullHttpResponse(
//                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
//
//        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
//        response.headers().set(CONTENT_LENGTH, buf.readableBytes());
//
//        // Write the response.
//        ctx.channel().writeAndFlush(response);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.WARNING, responseContent.toString(), cause);
        ctx.channel().close();
    }
}
