package com.black.blackrpc.code.base.entity;

import java.util.Arrays;

/**
 * 请求
 * @author wangshiyu
 */
public class RpcRequest {
    private String requestId;//请求id
    private String serviceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

	@Override
	public String toString() {
		return "RpcRequest [requestId=" + requestId + ", serviceName=" + serviceName + ", methodName=" + methodName
				+ ", parameterTypes=" + Arrays.toString(parameterTypes) + ", parameters=" + Arrays.toString(parameters)
				+ "]";
	}
}