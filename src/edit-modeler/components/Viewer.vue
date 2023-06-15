<template>
  <div>
    <el-container style="height: 600px">
      <el-main width="100%" style="border: 1px solid #DCDFE6" >
        <div ref="canvas" style="width: 100%;height: 100%;"></div>
      </el-main>
    </el-container>
  </div>
</template>

<script>

import BpmnViewer from 'bpmn-js/lib/Viewer';
import bpmnHelper from '../js/helper/BpmnHelper';
import request from '@/util/request';

export default {
  props: ["params"],
  components: {
  },
  provide: function () {
    return {
      bpmnModeler: this.getBpmnModeler,
    }
  },
  data () {
    return {
    
      bpmnModeler: null,
      canvas: null,
      element: null,
      key: '1',
      defaultData: {
        'bpmn:StartEvent': '开始',
        'bpmn:EndEvent':'完成',
        'bpmn:IntermediateThrowEvent':'终止'

      }
    }
  },
  methods: {
      //初始化BpmnModelerbpmnHelper
      initBpmnModeler(){
        let canvas = this.$refs.canvas;
        this.bpmnModeler = new BpmnViewer({
          container: canvas
        });
        //this.importBpmnXml()
        console.log("viewer初始化BpmnModeler")
        
      },
      //导入xml文档
      // importBpmnXml(){
      //   const that = this;
      //   let bpmnXml = bpmnHelper.getBpmnTempate();
      //   if(that.params.bpmnXml) {
      //     bpmnXml = that.params.bpmnXml
      //   }
      //   that.bpmnModeler.importXML(bpmnXml,function(err){
      //       if(err){
      //           console.log('bpmn文档导入失败');
      //       } else {
      //         //console.log()
      //         console.log("viewer导入xml文档")
      //         console.log('viewer创建成功!')
              
      //       }
      //   });
      // },
      importBpmnXml(){
        const that = this
        const modelId = that.params.row.id;
        let bpmnXml;
        console.log("importBpmnXml: "+modelId)

        request({
          url: `workflow/model/${modelId}/xml`,
          method: 'get',
        })
        .then(function(res) {
          bpmnXml = res.data.bpmnXml;
          console.log(res)
          console.log("request work")
          
          that.bpmnModeler.importXML(bpmnXml,function(err){
            if(err){
                console.log('bpmn文档导入失败');
            } else {
              console.log("viewer导入xml文档")
              console.log('viewer创建成功!')
              
            }
        })

        })
        .catch(function(err) {
          console.log('获取反显流程xml失败')
        })
   
      },
      getBpmnModeler() {
        return this.bpmnModeler
      },
   
  },
  mounted () {
    //初始化模型编辑器
    this.initBpmnModeler();
    this.importBpmnXml();
  }
}

</script>

<style>
.el-form-item__label {
  font-size: 13px
}
.el-main {
  padding: 5px
}
.el-input--mini .el-input__inner {
    height: 23px;
    line-height: 23px;
}
.el-form--label-top .el-form-item__label {
    padding: 0 0 0px;
}
.el-form-item--mini .el-form-item__content, .el-form-item--mini .el-form-item__label {
    line-height: 23px;
}
.el-form-item--mini.el-form-item, .el-form-item--small.el-form-item {
    margin-bottom: 3px;
}
.el-checkbox__label {
    padding-left: 10px;
    line-height: 19px;
    font-size: 13px;
}
.el-input__inner{
      padding: 0 5px;
}
.el-textarea__inner {
  padding: 5px 5px;
}
.el-input--mini .el-input__icon {
    line-height: 23px;
}

</style>
