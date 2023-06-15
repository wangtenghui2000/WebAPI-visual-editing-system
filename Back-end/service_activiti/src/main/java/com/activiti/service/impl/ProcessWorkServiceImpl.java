package com.activiti.service.impl;

import com.activiti.service.ProcessWorkService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;

@Service
public class ProcessWorkServiceImpl implements ProcessWorkService {


    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public void startWork(String modelId) {

        //根据modelId拿到部署id
        String DeploymentId=repositoryService.createModelQuery().modelId(modelId).singleResult().getDeploymentId();
        //根据部署的id可以拿到执行流程实例所需要的id
        String ProcessInstanceId=repositoryService.createProcessDefinitionQuery().deploymentId(DeploymentId)
                .singleResult().getId();
        //用ProcessInstanceId启动流程实例
        runtimeService.startProcessInstanceById(ProcessInstanceId);

    }

    @Override
    public List getWorkList(String modelId) {

        //根据modelId拿到部署id
        String DeploymentId=repositoryService.createModelQuery().modelId(modelId).singleResult().getDeploymentId();
        //根据部署的id可以拿到执行流程实例所需要的id
        String ProcessInstanceId=repositoryService.createProcessDefinitionQuery().deploymentId(DeploymentId).singleResult().getId();
        //获取到modelid下正在流转的任务
        Task task =  taskService.createTaskQuery().processDefinitionId(ProcessInstanceId).singleResult();
        //获取到modelid下的任务名称
        String name=repositoryService.createModelQuery().modelId(modelId).singleResult().getName();

        String taskname=task.getName();
        String api=task.getOwner();

        List list = new ArrayList<>();

        Map map = new HashMap();

        map.put("id",modelId);
        map.put("name",name);
        map.put("task",taskname);
        map.put("API",api);
        list.add(map);

        return list;

    }

    @Override
    public void completeWork(String modelId) {

        //根据modelId拿到部署id
        String DeploymentId=repositoryService.createModelQuery().modelId(modelId)
                .singleResult().getDeploymentId();
        //根据部署的id可以拿到执行流程实例所需要的id
        String ProcessInstanceId=repositoryService.createProcessDefinitionQuery()
                .deploymentId(DeploymentId).singleResult().getId();
        //获取到modelid下正在流转的任务
        Task task =  taskService.createTaskQuery().processDefinitionId(ProcessInstanceId)
                .singleResult();
        //完成任务
        taskService.complete(task.getId());

    }

    @Override
    public String completeAPI(String data) {
        Process proc;
        String apidata = "";
        try {

            String[] args = new String[]{"python", "D:\\demo2.py", String.valueOf(data)};
            proc = Runtime.getRuntime().exec(args);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                apidata=line;
            }
            in.close();
            proc.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return apidata;


    }


}
