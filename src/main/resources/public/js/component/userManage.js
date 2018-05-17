var userManage = {
	template:"#userManage",
	data:function(){
		return{
			tableData:[{
				className:"22"
			},{
				className:"33"
			}],
			multipleSelection: []
		}
	},
	methods:{
		handleDetails:function(scope){
			console.log(scope)
		},
		handleDelete:function(scope){
			console.log(scope)
		},
		toggleSelection:function(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
     	 },
     	 handleSelectionChange:function(val) {
     	 	console.log(val)
        	this.multipleSelection = val;
      }
		
	}
	
	
	
	
	
	
	
	
}

Vue.component("userManage",userManage);
