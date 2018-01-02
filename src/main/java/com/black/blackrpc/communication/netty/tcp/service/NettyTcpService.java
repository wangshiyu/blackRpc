package com.black.blackrpc.communication.netty.tcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
/***
 * netty tcp 服务端
 * @author v_wangshiyu
 *
 */
public class NettyTcpService {
	private static final Logger log = LoggerFactory.getLogger(NettyTcpService.class);
	private String host;
	private int port;
	
	public NettyTcpService(String host,int port) throws Exception{
		this.host=host;
		this.port=port;
	}
    /**用于分配处理业务线程的线程组个数 */  
	private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2; //默认  
    /** 业务出现线程大小*/  
	private static final int BIZTHREADSIZE = 4;  
    /* 
     * NioEventLoopGroup实际上就是个线程,
     * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件, 
     * 每一个NioEventLoop负责处理m个Channel, 
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel 
     */  
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);  
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);  
      
    public void start() throws Exception {
    	log.info("Netty Tcp Service Run...");
        ServerBootstrap b = new ServerBootstrap();  
        b.group(bossGroup, workerGroup);  
        b.channel(NioServerSocketChannel.class);  
        b.childHandler(new ChannelInitializer<SocketChannel>() {  
            @Override  
            public void initChannel(SocketChannel ch) throws Exception {  
                ChannelPipeline pipeline = ch.pipeline();  
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));  
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));  
                pipeline.addLast("decoder", new ByteArrayDecoder());  
                pipeline.addLast("encoder", new ByteArrayEncoder());  
              //  pipeline.addLast(new Encoder());
              //  pipeline.addLast(new Decoder());
                pipeline.addLast(new TcpServerHandler());
            }  
        });  
  
        b.bind(host, port).sync();  
        log.info("Netty Tcp Service Success!");
    }  
      
    /**
     * 停止服务并释放资源
     */
    public void shutdown() {  
        workerGroup.shutdownGracefully();  
        bossGroup.shutdownGracefully();  
    }
}  