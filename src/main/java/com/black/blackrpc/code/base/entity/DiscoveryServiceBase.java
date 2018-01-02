package com.black.blackrpc.code.base.entity;

import java.util.List;

/**
 * 发现服务实体
 * @author v_wangshiyu
 *
 */
public class DiscoveryServiceBase {
	/**
	 * 服务名称
	 * 例如：invokingService
	 * invokingService为服务名称
	 */
	private String serviceName;
	//服务地址
	private List<RemoteServiceBase> addressList;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public List<RemoteServiceBase> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<RemoteServiceBase> addressList) {
		this.addressList = addressList;
	}
	@Override
	public String toString() {
		return "DiscoveryServiceBase [serviceName=" + serviceName + ", addressList=" + addressList + "]";
	}

}
