package com.system.distribute.rpc;
/**
 * 
 * @author zhuyuping
 * @date 2014-8-12
 * @functions:功能是:rpc异步回调的接口 
 * @operator:添加修改人:zhuyuping
 */
public interface RpcAsyncCallBack {

	public void success(RPCResponse response,Object... ots);
	public void fail(RPCResponse response,RPCRequest request,RPCException rpce);
	
}
