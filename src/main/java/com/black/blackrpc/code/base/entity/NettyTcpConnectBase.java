package com.black.blackrpc.code.base.entity;

import java.util.Date;

import com.black.blackrpc.communication.netty.tcp.client.NettyTcpClient;

/**
 * netty连接
 * @author Administrator
 *
 */
public class NettyTcpConnectBase {
	/**
	 * 地址
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * netty 客户端 连接实体
	 */
	private NettyTcpClient nettyTcpClient;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 更新时间
	 */
	private Date updateDate;
	
	/**
	 * 调用次数
	 */
	private long invokingMum;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public NettyTcpClient getNettyTcpClient() {
		return nettyTcpClient;
	}

	public void setNettyTcpClient(NettyTcpClient nettyTcpClient) {
		this.nettyTcpClient = nettyTcpClient;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getInvokingMum() {
		return invokingMum;
	}

	public void setInvokingMum(long invokingMum) {
		this.invokingMum = invokingMum;
	}

	@Override
	public String toString() {
		return "NettyConnectBase [host=" + host + ", port=" + port + ", nettyTcpClient=" + nettyTcpClient
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + ", invokingMum=" + invokingMum + "]";
	}
}
