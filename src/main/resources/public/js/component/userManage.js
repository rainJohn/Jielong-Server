var userManage = {
	template: "#userManage",
	data: function() {
		return {
			tableAllData: [], //所有数据
			tableData: [],	  //显示数据
			searchConditions:{},
			tableDataCount:null, //数据长度
			tableDataPageNum:0,	 //当前页数
			tableDataPageSize:10,	//每页页数
			multipleSelection: [],	//选择的用户
			newAndUpdate:{},
			currentPage:1, //当前页数
			dialogFormVisible:false
		}
	},
	created: function() {
		this.initData();
	},
	methods: {
		initData: function() {
			this.$http.get("/userInfo/selectAll").then((res) => {
//				console.dir(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableAllData = res.body.data;
					this.tableDataCount = res.body.data.length;
					this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10,this.tableDataPageNum * 10 + 10);
					console.dir(this.tableData)
				}
			})
		},
		//分页
		handleCurrentChange(val) {
			this.tableDataPageNum = val - 1;
				this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10,this.tableDataPageNum * 10 + 10);
		},
		
		
		handleDeleteUser: function(scope) {
			console.log(scope)
		},
		//选择的用户
		handleSelectionChange: function(val) {
			
			console.log(val)
			this.newAndUpdate.multipleSelection = val;
			this.multipleSelection = val;
		},
		//搜索用户
		selectByConditions:function(){
//			console.log(this.searchConditions)
			this.$http.post("/userInfo/selectByConditions",this.searchConditions).then((res)=>{
//				console.log(res)
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableAllData = res.body.data;
					this.tableDataCount = res.body.data.length;		
					this.tableDataPageNum = 0;
					this.currentPage = 1;
					this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10,this.tableDataPageNum * 10 + 10);
				}
			})
		},
		//给选中用户发送消息
		smtSelectedUser:function(){
			console.log(this.multipleSelection)
			
			if(this.multipleSelection.length){
				var users=this.multipleSelection;
				var userIdList=new Array();
			   
				for(var i=0;i<users.length;i++){
					userIdList.push(users[i].userId);
				}
				
				var obj = {
					userIdList:userIdList,
					title:"测试",
					message:"测试msg"
				}
				console.log(obj)
				this.$http.post("/userMessage/insertBatch",obj).then((res)=>{
					console.log(res)
				})
			}
			else{
								this.$message({
									message: '请勾选需要发送的用户！',
									type: "error"
								});
				}
			
		},
		closeAddUpdate:function(){
				console.log(this.newAndUpdate)
					this.$http.post("/userMessage/insertBatch",this.newAndUpdate).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0) {
	
					}
				}).catch((err)=>{
					console.log(err)
				})		
		},
		//给所有用户发送消息
		smtAllUser:function(){
			
		},

	}

}

Vue.component("userManage", userManage);