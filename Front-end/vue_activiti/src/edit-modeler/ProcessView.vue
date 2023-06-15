<template>
  <el-dialog
  :visible.sync="dialogViewModelVisible"
  width="80%"
  :before-close="handleClose">
  <!-- <Viewer ref="viewer" :params="params"></Viewer> -->
  <Worker ref="worker" :params="params"></Worker>
</el-dialog>
</template>
<script>
import Viewer from './components/Viewer'
import Worker from './components/Worker'

  export default {
    props: ['dialogViewModelVisible', 'params'],
    components: {
      //Viewer
      Worker
    },
    data() {
      return {
        //dialogViewModelVisible:false
      };
    },
    methods: {
    
      handleClose(done) {
        this.$emit('handleViewModelVisiable',{ visiable: false })
        console.log("handleClose")
      },
    }
  };
</script>