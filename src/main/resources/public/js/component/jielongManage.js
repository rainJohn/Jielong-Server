var jielongManage = {
	template:"#jielongManage",
	data:function(){
		return{
			tableData:[{
				className:"22"
			},{
				className:"33"
			}]
		}
	},
	methods:{
		handleDetails:function(scope){
			console.log(scope)
		},
		handleDelete:function(scope){
			console.log(scope)
		}		
		
	}
	
	
	
	
	
	
	
	
}

Vue.component("jielongManage",jielongManage);
