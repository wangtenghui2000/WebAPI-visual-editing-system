package com.activiti.controller;

import com.activiti.service.ProcessWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow")
public class ProcessWorkController {


    @Autowired
    private ProcessWorkService processWorkService;

    /**
     * 获取待完成的任务列表
     */
    @RequestMapping(value = "/model/worklist", method = RequestMethod.GET)
    public List getWorkList(@RequestParam(name = "modelId") String modelId){
        List list = processWorkService.getWorkList(modelId);
        return list;
    }

    /**
     * 完成任务
     */
    @RequestMapping(value = "/model/workdone", method = RequestMethod.GET)
    public void completeWork(@RequestParam(name = "modelId") String modelId){

        processWorkService.completeWork(modelId);

    }
    /**
     * 启动流程实例
     */
    @RequestMapping(value = "/model/workstart", method = RequestMethod.GET)
    public void startWork(@RequestParam(name = "modelId") String modelId){

        processWorkService.startWork(modelId);
    }

    /**
     * 执行文心API
     */
    @RequestMapping(value = "/model/wenxinAPI", method = RequestMethod.GET)
    public String doAPI(@RequestParam(name = "wenxindata") String wenxindata){
        String apidata=processWorkService.completeAPI(wenxindata);
        return apidata;
    }



}
