<template>
    <div>
      
      <div style="margin-right:1500px;">
        <el-button type="primary" @click="apiComplete()">执行当前API</el-button>
      </div>
      
      <div >
        
      <el-table 
      :data="tableData"
      border
      style="width: 100%;"
      empty-text="暂无数据">
      <el-table-column 
        label="当前任务所属流程ID"
        prop="id"
        width="180"
        >
        
      </el-table-column>
      <el-table-column
        label="当前任务所属流程名称"
        prop="name"
        width="180"
        >
      </el-table-column>
      <el-table-column
        label="待执行任务名称"
        prop="task"
        width="180"
        >
      </el-table-column>

      <el-table-column
        label="待执行任务API"
        prop="API"
        width="180"
        >
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="workDone()">API执行完毕</el-button>
        </template>
      </el-table-column>
      

    </el-table>
  </div>
  <h2>API执行区</h2>
    <div style="margin-top:10px;">
      <h3 align="left">请输入内容</h3>
      <el-input
          type="textarea"
          :rows="2"
          
          placeholder="请输入内容"
          v-model="ChatGPTInput">
      </el-input>
      <h3 align="left">执行结果</h3>
      <el-input
        v-loading="loading"
          type="textarea"
          autosize
          v-model="ChatGPTReturn"
          readonly
          >
      </el-input>

        <img :src="imageurl" alt style="width: 500px;height: 500px;" >

    </div>

    </div> 

  </template>
<script>
import request from '@/util/request';
import axios from 'axios'
export default{
    props: ["params"],
    comments: {
        
    },
    data() {
        return {
            tableData: [],
            imageurl: "",
            wenxindata: "",
            wenxinReturnData: "",
            ChatGPTInput:"",
            ChatGPTReturn:"",
            loading: false
            
        };
    },
    mounted() {
        const that = this;
        const modelId = that.params.row.id;
        
        request({
            method: "get",
            url: `workflow/model/worklist`,
            params: {
                modelId: modelId
            }
        })
            .then(function (res) {
            that.tableData = res.data;
        })
            .catch(function (err) {
            that.$message("获取失败");
        });
    },
    methods: {

      workDone(){

        const that = this
        const modelId = that.params.row.id;
            request({
              method: 'get',
              url: `workflow/model/workdone`,
              params: {
                  modelId: modelId
                }
            })
            .then(function(res) {
              that.tableData=undefined
              that.imageurl=undefined
              that.wenxindata=undefined
              that.wenxinReturnData=undefined
              that.ChatGPTInput=undefined
              that.ChatGPTReturn=undefined
              that.dataChange()
              that.$message("操作成功")
            })
            .catch(function(err) {
              that.$message("操作失败")
            });
            // axios请求
            // axios.get('https://img.paulzzh.tech/touhou/random').then(res=>{
            //     console.log(res);
            //     console.log(res.data)
            //     console.log(res.data[0])
            //     console.log(res.data[0].url)
            //     this.imageurl=res.data[0].url
            //     console.log(this.imageurl)
            //   }).catch(err=>{
            //     console.log(err);
            //   })

      },


        apiComplete() {
  
          const that=this
          const modelId = that.params.row.id;
          const wenxindata=that.wenxindata
          var ExecuteAPI
          
            request({
              method: 'get',
              url: `workflow/model/worklist`,
              params: {
                  modelId: modelId
                }
            })
            .then(function(res) {
              that.loading=true
              ExecuteAPI=res.data[0].API
              console.log(ExecuteAPI)
            //Bing每日壁纸API的请求
             if(ExecuteAPI=='Bing每日壁纸')
             {
              axios.get('https://api.isoyu.com/bing_images.php',{
              responseType: 'arraybuffer',
              })
              .then(response => {
                return 'data:image/png;base64,' + btoa(
                  new Uint8Array(response.data)
                    .reduce((data, byte) => data + String.fromCharCode(byte), ''));
              }).then(data => {
              that.imageurl = data
              that.loading=false
              })
      

             }
             //随机表情包API的请求
             if(ExecuteAPI=='随机表情包'){

              axios.get('https://api.isoyu.com/ARU_GIF_S.php',{
              responseType: 'arraybuffer',
              })
              .then(response => {
                return 'data:image/png;base64,' + btoa(
                  new Uint8Array(response.data)
                    .reduce((data, byte) => data + String.fromCharCode(byte), ''));
              }).then(data => {
              that.imageurl = data
              that.loading=false
              })
              
             }
             //文心一言API的请求
             if(ExecuteAPI=='文心一言'){

              console.log("wenx")
              axios.get('http://127.0.0.1:8084/workflow/model/wenxinAPI',{
                params:{
                  wenxindata:wenxindata
                }
              })
              .then(
                (res) => {
                    //执行成功后代码处理
                    console.log("its work")
                    console.log(res.data)
                    that.wenxinReturnData=res.data
                }
              )
             }
             //ChatGPTAPI的请求
             if(ExecuteAPI=='ChatGPT'){
              axios({
                  method: 'post',
                  headers:{
                          'Content-Type':'application/json',
                          'Authorization':'Bearer API密钥'
                            },
                  url: 'https://twilightwang.top/v1/chat/completions',
                  data: {
                        "model": "gpt-3.5-turbo",
                        "messages": [{"role": 'user', "content": that.ChatGPTInput}],
                        "temperature": 0.7
                          }
                  }).then((res)=>{
                    
                      that.ChatGPTReturn=res.data.choices[0].message.content
                      that.loading=false

                    })
             }

                          
            })
            .catch(function(err) {
              
            });
            // axios.get('https://api.isoyu.com/bing_images.php',{
            //   responseType: 'arraybuffer',
            // })
            //   .then(response => {
            //     return 'data:image/png;base64,' + btoa(
            //       new Uint8Array(response.data)
            //         .reduce((data, byte) => data + String.fromCharCode(byte), ''));
            //   }).then(data => {
            //   that.imageurl = data
            // })
     

        },
        dataChange() {
            const that = this;
            const modelId = that.params.row.id;
            request({
                method: "get",
                url: `workflow/model/worklist`,
                params: {
                    modelId: modelId
                }
            })
                .then(function (res) {
                
                that.tableData = res.data;
                console.log(res.data);
                console.log("获取成功");
            })
                .catch(function (err) {
                that.$message("获取失败");
                console.log("获取失败");
            });
        }
    },
    
}





</script>