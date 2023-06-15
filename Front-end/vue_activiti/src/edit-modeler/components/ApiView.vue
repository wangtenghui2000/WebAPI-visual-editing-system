<template>

  <div>
    <el-container>
    <el-header>wth</el-header>
    <el-main>当前待执行的任务</el-main>
    <el-table 
      :data="tableData"
      style="width: 100%;">
      <el-table-column 
        label="当前任务所属流程ID"
        prop="id"
        width="180">
      </el-table-column>
      <el-table-column
        label="当前任务所属流程名称"
        prop="name"
        width="180">
      </el-table-column>
      <el-table-column
        label="待执行任务名称"
        prop="task"
        width="180">
      </el-table-column>
      <el-table-column
        label="待执行任务API"
        prop="API"
        width="180">
      </el-table-column>
    </el-table>

    <el-footer>
        API执行处

    </el-footer>

  </el-container>
</div>

</template>
    
    <script>
        export default {
            name: "ApiView",
            data(){
              return{
                detailVisible:false
              }
            },
          methods:{
          //3.定义一个init函数，通过设置detailVisible值为true来让弹窗弹出，这个函数会在父组件的方法中被调用
            init(){
              this.detailVisible=true;
              //data是父组件弹窗传递过来的值，我们可以打印看看
            }
          }
        }
    </script>
 
