<template>
  <el-dialog 
  title="流程设计器"
  :visible.sync="dialogVisible"
  width="90%"
  :before-close="handleClose">
  <Modeler ref="modeler" :params="params"></Modeler>
  <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="handleOk">保 存</el-button>
  </span>
  </el-dialog>
</template>
<script>
import Modeler from './components/Modeler'

  export default {
    props: ['dialogVisible', 'params'],
    components: {
      Modeler
    },
    data() {
      return {
        
      };
    },
    methods: {
      handleOk() {
        this.$emit('handleVisiable', { visiable: false })
        this.$refs.modeler.saveXML();
        console.log("desigen data change")
      },
      handleClose(done) {
        this.$emit('handleVisiable', { visiable: false })
      },
     
    }
  };
</script>