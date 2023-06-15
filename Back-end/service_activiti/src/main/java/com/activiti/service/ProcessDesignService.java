package com.activiti.service;

import org.activiti.engine.repository.Model;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ProcessDesignService {
    
    //void createModel(String key,String name, String category, String descp) throws UnsupportedEncodingException;
    void createModel(String key,String name) throws UnsupportedEncodingException;
    
    List<Model> listModel();
    
    void deleteModel(String modelId);
    
    String deployModel(String modelId) throws Exception;
}
