 new Vue({
      el: '#app',
      data: function() {      
      return {
    	routerLinkArray:[
    	{link:"#/home",name:"首页"},
    	{link:"#/slider",name:"轮播图管理"},
    	{link:"#/classify",name:"商品分类管理"},
    	{link:"#/jielongManage",name:"接龙管理"},
    	{link:"#/userManage",name:"用户管理"},
    	{link:"#/helpCenter",name:"帮助中心"}],
      }
      },
      created:function(){

      },
      components:{

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