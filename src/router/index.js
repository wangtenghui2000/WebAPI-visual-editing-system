import VueRouter from 'vue-router'


import ProcessDesign from '../edit-modeler/ProcessDesign'
import ProcessView from '../edit-modeler/ProcessView'


//创建并暴露一个路由器
export default new VueRouter({
  routes: [
   
    {
      path: '/ProcessDesign',
      name: 'Design',
      component: ProcessDesign

    },
    {
      
      path: '/ProcessView',
      name: 'View',
      component: ProcessView

    },
    
  ]
})
