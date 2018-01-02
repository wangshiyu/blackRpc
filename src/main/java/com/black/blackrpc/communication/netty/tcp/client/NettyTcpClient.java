package com.black.blackrpc.communication.netty.tcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.Future;
/**
 * netty tcp 客户端
 * @author v_wangshiyu
 *
 */
public class NettyTcpClient {
	private static final Logger log = LoggerFactory.getLogger(NettyTcpClient.class);
	private String host;  
	private int port;  
	private Bootstrap bootstrap;  
	private Channel channel;
	private EventLoopGroup group;

	public NettyTcpClient(String host,int port){
		bootstrap=getBootstrap();
		channel= getChannel(host,port);
		this.host=host;
		this.port=port;
	}
	
    public String getHost() {
		return host;
	}
    
	public int getPort() {
		return port;
	}

	/** 
     * 初始化Bootstrap 
     * @return 
     */  
    public final Bootstrap getBootstrap(){  
    	group = new NioEventLoopGroup();  
        Bootstrap b = new Bootstrap();  
        b.group(group).channel(NioSocketChannel.class);  
        b.handler(new ChannelInitializer<Channel>() {  
            @Override  
            protected void initChannel(Channel ch) throws Exception {  
                ChannelPipeline pipeline = ch.pipeline();  
                 // pipeline.addLast(new Encoder());
                 // pipeline.addLast(new Decoder());
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));  
                pipeline.addLast("decoder", new ByteArrayDecoder());  
                pipeline.addLast("encoder", new ByteArrayEncoder());  
                
                pipeline.addLast("handler", new TcpClientHandler());
            }  
        });  
        b.option(ChannelOption.SO_KEEPALIVE, true);  
        return b;  
    }  
  
    /**
     * 连接，获取Channel
     * @param host
     * @param port
     * @return
     */
    public final Channel getChannel(String host,int port){  
        Channel channel = null;  
        try {  
            channel = bootstrap.connect(host, port).sync().channel();  
            return channel;
        } catch (Exception e) {  
        	log.info(String.format("connect Server(IP[%s],PORT[%s]) fail!", host,port));  
            return null;  
        }
    }  
  
    /**
     * 发送消息
     * @param msg
     * @throws Exception
     */
    public boolean sendMsg(Object msg) throws Exception {  
        if(channel!=null){
            channel.writeAndFlush(msg).sync();  
            log.debug("msg flush success");
            return true;
        }else{  
        	log.debug("msg flush fail,connect is null");
            return false;
        }  
    }
    
    /**
     * 连接断开
     * 并且释放资源
     * @return
     */
    public boolean disconnectConnect(){
    	//channel.close().awaitUninterruptibly();
    	Future<?> future =group.shutdownGracefully();//shutdownGracefully释放所有资源，并且关闭所有当前正在使用的channel
    	future.syncUninterruptibly();
    	return true;
    }
}