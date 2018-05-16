var helpCenter = {
	template:"#helpCenter",
	data:function(){
		return{
			tableData:[],
			sonTableData:[],
			newAndUpdate:{},
			dialogTableVisible:false,
			dialogFormVisible:false
		}
	},
	created:function(){
		this.initData();
	},
	methods:{
		//初始化列表
		initData:function(){
			this.$http.get("/helpMessage/selectAll").then((res)=>{
				if(res.status == 200 && res.body.errorCode == 0) {
//					console.log(res.body.data)
					this.tableData = res.body.data;
				}
			})
		},
		//查看详细
		handleDetails:function(scope){
			this.sonTableData = scope.row;
			this.dialogTableVisible = true;
		},
		//新增/修改
		handleAddUpdate:function(scope){
			if (scope) {
				this.newAndUpdate.title = scope.row.title;				
				this.newAndUpdate.message = scope.row.message;
				this.newAndUpdate.id = scope.row.id;
			}else{
				this.newAndUpdate.title = "";				
				this.newAndUpdate.message = "";	
				this.newAndUpdate.id = "";
			}
			this.dialogFormVisible = true;
		},		
		//删除
		handleDelete:function(scope){
			this.$confirm("是否确定删除此帮助信息？","提交",{
				confirmButtonText:"确认",
				cancelButtonText:"取消",
				type:"warning"
			}).then((res)=>{
				this.$http.post("/helpMessage/delete?id="+scope.row.id).then((res)=>{
					this.initData();
					this.$message({message:'删除此帮助信息成功！',type:"success"});
				})				
			})
		},
		
		closeAddUpdate:function(res){
			//修改
			if (this.newAndUpdate.id && this.newAndUpdate.title && this.newAndUpdate.message) {
				this.$http.post("/helpMessage/update",{
					title:this.newAndUpdate.title,
					message:this.newAndUpdate.message,
					id:this.newAndUpdate.id
				}).then((res)=>{
					this.initData();
					this.dialogFormVisible = false;
					this.$message({message:'修改此帮助信息成功！',type:"success"});
				})
			//新增
			}else if(this.newAndUpdate.title && this.newAndUpdate.message){
				this.$http.post("/helpMessage/insert",{
					title:this.newAndUpdate.title,
					message:this.newAndUpdate.message,
				}).then((res)=>{
					this.initData();
					this.dialogFormVisible = false;
					this.$message({message:'新增此帮助信息成功！',type:"success"});
				})				
			}else{
				this.$message({message:'请填写正确的标题和内容！',type:"error"});
			}
			
		}
		
		
	}
	
	
	
	
	
	
	
	
}

Vue.component("helpCenter",helpCenter);
