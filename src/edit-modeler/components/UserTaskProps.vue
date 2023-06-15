<template>
    <div>     
        <el-form-item label="当前任务名称" >
            <el-input v-model= "name" readonly></el-input>
        </el-form-item>
        <el-form-item label="请绑定API">
            <el-select v-model= "owner"  > 
                <el-option label="1--Bing每日壁纸API" value="Bing每日壁纸"></el-option>
                <el-option label="2--随机表情包API" value="随机表情包"></el-option>
                <el-option label="3--ChatGPTAPI" value="ChatGPT"></el-option>
                
            </el-select>
        </el-form-item> 
        <el-form-item label="API"  >
            <el-input v-model= "owner" ></el-input>
        </el-form-item>
    </div>
</template>
<script>
const forEach = require('lodash/forEach');
import {findObjFromArrayByField,isNeedUpdate} from '../js/util/CommonUtils'; 
import bpmnHelper from '../js/helper/BpmnHelper';
import {NodeTypeMap,TxTypeMap} from '../../static/js/static'; 
// 用户任务属性组件
export default {
    props:['element'],
    inject: ['bpmnModeler'],
    data(){
        return {
            modeling: null,
            id: this.element.id || '',
            name: '',
            formKey:'',
            documentation: '',
            multiinstance_type: '',
            multiinstance_collection: '',
            multiinstance_condition: '',
            multiinstance_cardinality: null,
            multiinstance_variable: null,
            owner:'',

            // 原子节点属性
            
            positions:[],
            priority:'',
            candidateGroups: '没有受让人'
        }
    },
    methods:{
       
    },
    mounted() {
        const bpmnModeler = this.bpmnModeler();
        this.modeling = bpmnModeler.get('modeling');
    },
    watch:{
        name: {
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{
                    name: newVal
                });
            }
        },
        owner:{
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{
                    owner: newVal
                });
            }
        },
       
        //监视元素变化
        element:{
            deep: true,
            immediate: true, 
             handler(newVal,oldVal){
                 if(newVal.type == 'bpmn:UserTask') {
                     const bpmnModeler = this.bpmnModeler();
                     const modeling = bpmnModeler.get('modeling');
                     const businessObject = newVal.businessObject;
                     this.name = businessObject.name;
                     this.formKey = businessObject.get('formKey');
                     // 原子
                     const candidateGroupsTemp = businessObject.get('candidateGroups');
                     // 解决后端反显和切换节点反显candidateGroupsTemp类型不一致问题
                    if(candidateGroupsTemp && candidateGroupsTemp.length > 0) {
                        if(Array.isArray(candidateGroupsTemp)) {
                            //切换节点反显
                            this.candidateGroups = businessObject.get('candidateGroups');
                        } else {
                            //后端反显
                            this.candidateGroups = businessObject.get('candidateGroups').split(',');
                        }
                        
                    }

                     this.multiinstance_type = businessObject.get('multiinstance_type') || 'None';
                     this.multiinstance_condition = businessObject.get('multiinstance_condition') || '';
                     modeling.updateProperties(newVal,{'multiinstance_type':this.multiinstance_type});
                     modeling.updateProperties(newVal,{'multiinstance_condition':this.multiinstance_condition});
                 }
             }
        },
        formKey:{
            handler(newVal,oldVal){
                this.modeling.updateProperties(this.element,{'formKey':newVal});
            }
        },
        multiinstance_type: {
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{'multiinstance_type':newVal});
            }
        },
        multiinstance_collection: {
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{'multiinstance_collection':newVal});
            }
        },
        multiinstance_condition: {
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{'multiinstance_condition':newVal});
            }
        },
        priority: {
            handler(newVal, oldVal) {
                this.modeling.updateProperties(this.element,{'priority':newVal});
            }
        },
        candidateGroups: {
            handler(newVal,oldVal){
                this.modeling.updateProperties(this.element,{'candidateGroups':newVal});
                
            }
        }
    }
   
}
</script>
<style>

</style>