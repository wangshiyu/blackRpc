package com.black.blackrpc.common.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * breakRpc 配置
 * @author v_wangshiyu
 *
 */
@Component
public class BreakRpcConfigure {
@Value("${zk.address}")
private String zkAddress;

@Value("${server.open}")
private Boolean serverOpen;

@Value("${server.tcp.address}")
private String serverTcpAddress;

//@Value("${server.udp.address}")预留
private String serverUdpAddress;

public String getZkAddress() {
	return zkAddress;
}

public Boolean getServerOpen() {
	return serverOpen;
}

public void setServerOpen(Boolean serverOpen) {
	this.serverOpen = serverOpen;
}

public String getServerTcpAddress() {
	return serverTcpAddress;
}

public void setServerTcpAddress(String serverTcpAddress) {
	this.serverTcpAddress = serverTcpAddress;
}

public String getServerUdpAddress() {
	return serverUdpAddress;
}

public void setServerUdpAddress(String serverUdpAddress) {
	this.serverUdpAddress = serverUdpAddress;
}

public void setZkAddress(String zkAddress) {
	this.zkAddress = zkAddress;
}
}
