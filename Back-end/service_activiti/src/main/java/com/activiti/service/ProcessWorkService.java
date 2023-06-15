package com.activiti.service;

import java.util.List;

public interface ProcessWorkService {
    //启动流程实例
    void startWork(String modelId);
    //获取工作中的任务
    List getWorkList(String modelId);
    //完成任务
    void completeWork(String modelId);
    //执行API
    String completeAPI(String data);


}
