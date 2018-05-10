 new Vue({
      el: '#app',
      data: function() {      
      	const item = {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      };
      return {
    	routerLinkArray:[{link:"/slider",name:"轮播图管理"}],
        tableData: Array(20).fill(item)
      }
      },
      created:function(){
/*     	  this.$http.get("/slider").then((response) => {
    		  console.log(response)
            }).catch((err) => {
            	console.log(err)
            })  */
      },
      components:{
    	"head-top":{
    		template:"#headTop",
    		data:function(){
    			return{
    				routerData:[1,2,3]
    			}
    		},
    		methods:{
    			handleCommand:function(){
    				
    			}
    		}
    	},
      },
      router:new VueRouter({
			routes:[{
				path:"/",
				redirect:"/home"
			},{
				path:"/home",
				component:{
					template:'#myTemplate'
				}
			},{
				path:'/slider',
				component:slider
			},{
				path:'/classify',
				component:classify
			}]
		})
    })