 new Vue({
      el: '#app',
      data: function() {      
      return {
    	routerLinkArray:[
    	{link:"#/home",name:"首页",icon:"el-icon-menu"},
    	{link:"#/slider",name:"轮播图管理",icon:"el-icon-picture"},
    	{link:"#/classify",name:"商品分类管理",icon:"el-icon-goods"},
    	{link:"#/jielongManage",name:"接龙管理",icon:"el-icon-refresh"},
    	{link:"#/userManage",name:"用户管理",icon:"el-icon-view"},
    	{link:"#/helpCenter",name:"帮助中心",icon:"el-icon-setting"}],
      }
      },
      created:function(){
    	  this.logo2 = "logo2";
      },
      mounted:function(){
      	this.$refs.allRouter[0].id = "router_active";
      },
      components:{

      },
      methods:{
      	allRouterActive:function(index){
      		this.$refs.allRouter.forEach((item,itemIndex)=>{
      			if(itemIndex == index){
      				this.$refs.allRouter[index].id = "router_active";
      			}else{
      				this.$refs.allRouter[itemIndex].id = "";
      			}
      		})
      	}
      },
      router:new VueRouter({
			routes:[{
				path:"/",
				redirect:"/home"
			},{
				path:"/home",
				component:{
					template:'#homeTemplate'
				}
			},{
				path:'/slider',
				component:slider
			},{
				path:'/classify',
				component:classify
			},{
				path:'/jielongManage',
				component:jielongManage
			},{
				path:'/userManage',
				component:userManage
			},{
				path:'/helpCenter',
				component:helpCenter
			}]
		})
    })