package com.system.common;

/**
 * 
 *      
 *     
 * @author zhuyuping       
 * @version 1.0     
 * @created 2014-4-01 上午11:57:17 
 * @function:
 */
public enum FileState {

    
    NEEDDELETE,//表示需要同步 只需要发送一个删除指令 以及相关的path并确认即可
    
    NEEDADD,//需要添加 传输时候整体传输
    
    NEEDUPDATE,//后期改为RSYNC协议 前期同NeedADD一样

    NONEED;
}
