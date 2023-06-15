<template>
  <div id="app">
    <h1>流程模型管理</h1>
    <div style="margin-left:200px;margin-top:80px;">
    <el-button class="addBtn" type="primary" icon="el-icon-plus" @click="addProcessModel">新增流程</el-button>
    </div>
    <div style="margin-left:200px;margin-top:30px;">
    <el-table 
      :data="tableData"
      style="width: 100%;">
      <el-table-column 
        label="流程标识"
        prop="id"
        width="180">
      </el-table-column>
      <el-table-column
        label="流程名称"
        prop="name"
        width="180">
      </el-table-column>
     
      <el-table-column
        label="版本"
        prop="version"
        width="180">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button type="primary"
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          
            <el-button type="warning"
            size="mini"
            @click="handleDeploy(scope.$index, scope.row)">部署</el-button> 
            <el-button type="info"
            size="mini"
            @click="workStart(scope.$index, scope.row)">启动工作流</el-button> 
            <el-button type="success"
            size="mini"
            @click="handleView(scope.$index, scope.row)">查看</el-button>
            <el-button type="danger"
            size="mini"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          

        </template>
      </el-table-column>
    </el-table>
    </div>
    <el-dialog
      title="新增模型"
      :visible.sync="modelVisible"
      width="30%"
      :before-close="handleModelClose">
      <el-form>
            <el-form-item label="请输入模型名称">
              <el-input v-model="name"></el-input>
            </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleModelClose">取 消</el-button>
        <el-button type="primary" @click="handleModelOk">确 定</el-button>
      </span>
    </el-dialog>
    <ProcessDesign :dialogVisible="dialogVisible" :params="params" v-on:handleVisiable="handleVisiable(visible)" v-if="dialogVisible">
    </ProcessDesign>
    <ProcessView :dialogViewModelVisible="dialogViewModelVisible" :params="params" 
    v-on:handleViewModelVisiable="handleViewModelVisiable(visible)" v-if="dialogViewModelVisible">
    </ProcessView> 
  
  
  </div>
</template>

<script>
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import ProcessDesign from './edit-modeler/ProcessDesign'
import ProcessView from './edit-modeler/ProcessView'
import request from './util/request'
Vue.use(ElementUI)
export default {
  name: 'App',
  components: {
    ProcessDesign,
    ProcessView,
    
  },
  data () {
    return {
      tableData: [],
      params:{
        row: ''
      },
      modelVisible: false,
      dialogVisible: false,
      dialogViewModelVisible: false,
      dialogModelWorkVisible:false,
      key: '',
      name: '',
      
    }
  },
  mounted() {
    const that = this;
    request({
      method: 'get',
      url: 'workflow/model/list',
    })
    .then(function(res) {
      that.tableData = res.data
    })
    .catch(function(err) {
      that.$message("查询失败")
    });
  }, 
//async
  methods: {

    addProcessModel() {
      this.modelVisible = true
    },
    handleModelClose() {
      this.modelVisible = false
    },
    handleModelOk() {
      const that = this;
      that.modelVisible = false;
      request({
        method: 'post',
        url: 'workflow/model/insert',
        data: {
          key: that.key,
          name: that.name,
         
        }
      })
      .then(function(res) {
        that.key = ''
        that.name = ''
  
        that.$message("新增成功")
        that.handleDataChange()
        //that.reload()
      })
      .catch(function(err) {
        that.$message("新增失败")
      });
    },
    async handleEdit (index, row) {
      const that = this;
      that.params.row = row
      // 到后台请求xml文件
      // await request({
      //   url: `workflow/model/${row.id}/xml`
      // })
      // .then(function(res) {
      //   that.params.bpmnXml = res.data.bpmnXml;
      //   console.log("handledit")
      // })
      // .catch(function(err) {
      //   console.log('获取反显流程xml失败')
      // })
      // 注意放的位置，避免异步请求导致实际设计器中没有bpmnXml值
      console.log("app.vue:handlEdit start")
      that.dialogVisible = true;
      
    },
    async handleView (index, row) {
      const that = this;
      that.params.row = row
      // // 到后台请求xml文件
      // request({
      //   url: `workflow/model/${row.id}/xml`
      // })
      // .then(function(res) {
      //   //debugger
      //   that.params.bpmnXml = res.data.bpmnXml;
      //   console.log("app.vue:handleView start")
      //   console.log(row.id)
      //   console.log(res.data)
      //   console.log("app.vue:handleView end")
      // })
      // .catch(function(err) {
      //   console.log('获取反显流程xml失败')
      // })
      // 注意放的位置，避免异步请求导致实际设计器中没有bpmnXml值
      that.dialogViewModelVisible = true;
      // console.log(this.dialogViewModelVisible)
      // this.dialogViewModelVisible=true
      // console.log(this.dialogViewModelVisible)
      // this.handleDataChange()
    },

    handleDelete(index, row) {
      const that = this;
      request({
        url: 'workflow/deleteModel',
        method: 'get',
        params: {
          modelId: row.id
        }
      })
      .then(function(res) {
        that.$message("删除成功")
        that.handleDataChange()
      })
      .catch(function(err) {
        that.$message("删除失败")
      })
    },

    handleDeploy(index, row) {
      const that = this;
      request({
        url: 'workflow/model/deploy',
        method: 'get',
        params: {
          modelId: row.id
        }
      })
      .then(function(res) {
        //debugger
        if(res.data === 'success') {
          that.$message("部署成功")
        } else {
          that.$message(res.data)
        }
        
      })
      .catch(function(err) {
        that.$message("部署失败")
      })
    },

    workStart(index, row){

      const that = this;
      request({
        url: 'workflow/model/workstart',
        method: 'get',
        params: {
          modelId: row.id
        }
      })
      .then(function(res) {
        
        that.$message("工作流启动成功")
        
      })
      .catch(function(err) {
        that.$message("工作流启动失败")
      })

    },

    handleVisiable(visiable) {
      this.dialogVisible = false
      console.log("handleVisiable work: "+visiable)
      console.log(this.dialogVisible)
    },
    handleViewModelVisiable(visiable) {
      
      this.dialogViewModelVisible = false
      console.log("handleViewModelVisiable work: "+visiable)
    },

    
    handleDataChange(){
      const that = this
      request({
      method: 'get',
      url: 'workflow/model/list',
    })
    .then(function(res) {
      that.tableData = res.data
      console.log("handleDataChange")
      
    })
    .catch(function(err) {
      that.$message("查询失败")
    });
    },

  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.addBtn {
  display: flex;
  text-align:right;
  margin-left: 10px;
}
</style>
