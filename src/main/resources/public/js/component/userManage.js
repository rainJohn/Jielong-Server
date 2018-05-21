var userManage = {
	template: "#userManage",
	data: function() {
		return {
			tableAllData: [], //所有数据
			tableData: [], //显示数据
			searchConditions: {nickName:null,name:null,phoneNumber:null},
			tableDataCount: null, //数据长度
			tableDataPageNum: 0, //当前页数
			tableDataPageSize: 10, //每页页数
			multipleSelection: [], //选择的用户
			sendAllMsg: {
				multipleSelection: [],
				title: null,
				message: null
			},
			currentPage: 1, //当前页数
			dialogFormVisible: false,
			userstate:{userStateTitle:"加入黑名单"}
		}
	},
	created: function() {
		this.initData();
	},
	methods: {
		initData: function() {
			this.$http.get("/userInfo/selectAll").then((res) => {
//								console.dir(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableAllData = res.body.data;
					this.tableDataCount = res.body.data.length;
					this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10, this.tableDataPageNum * 10 + 10);
//					console.dir(this.tableData)
				}
			})
		},
		//分页
		handleCurrentChange(val) {
			this.tableDataPageNum = val - 1;
			this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10, this.tableDataPageNum * 10 + 10);
		},
		//拉黑		
		handleDeleteUser: function(scope) {
//			console.log(scope)
			var dialogMsg = null;
			if (scope.row.state) {
				var dialogMsg = scope.row.nickName +" 加入黑名单"
			}else{
				var dialogMsg = scope.row.nickName +" 移除黑名单"
			}
			this.$confirm("是否确认把 "+dialogMsg+"？","提示",{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then((res)=>{
				scope.row.state = scope.row.state == 1 ? 0 : 1;
				var user = {
					id:scope.row.userId,
					state:scope.row.state
				}
//				console.log(user)
				this.$http.post("/user/updateState",user).then((res)=>{
//					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0) {
					if(this.searchConditions.nickName || this.searchConditions.name || this.searchConditions.phoneNumber){
						this.selectByConditions();
					}else{
						this.initData();
					}
					this.$message({message:'成功把此 '+dialogMsg+"！",type:"success"});
					}
				})
			}).catch((res)=>{
//				console.log(res)
			})
		},
		//选择的用户
		handleSelectionChange: function(val) {
//			console.log(val)
			this.sendAllMsg.multipleSelection = val;
		},
		//搜索用户
		selectByConditions: function() {
//			console.log(this.searchConditions)
			this.$http.post("/userInfo/selectByConditions", this.searchConditions).then((res) => {
//				console.log(res.body.data)
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableAllData = res.body.data;
					this.tableDataCount = res.body.data.length;
					this.tableDataPageNum = 0;
					this.currentPage = 1;
					this.tableData = this.tableAllData.slice(this.tableDataPageNum * 10, this.tableDataPageNum * 10 + 10);
				}
			})
		},
		//给选中用户发送消息
		smtSelectedUser: function() {
//			console.log(this.sendAllMsg.multipleSelection)
			if(this.sendAllMsg.multipleSelection.length) {
				this.sendAllMsg.title = "";
				this.sendAllMsg.message = "";
				this.sendAllMsg.isAllMsg = false;
				this.dialogFormVisible = true;
			} else {
				this.$message({
					message: '请勾选需要发送的用户！',
					type: "warning"
				});
			}

		},
		//给所有用户发送消息
		smtAllUser: function() {
			this.sendAllMsg.title = "";
			this.sendAllMsg.message = "";
			this.sendAllMsg.isAllMsg = true;
			this.dialogFormVisible = true;
		},
		closeAddUpdate: function() {
//			console.log(this.sendAllMsg)
			if(this.sendAllMsg.title && this.sendAllMsg.message) {
				var users = this.sendAllMsg.multipleSelection;
				var userIdList = new Array();
				for(var i = 0; i < users.length; i++) {
					userIdList.push(users[i].userId);
				}
				var obj = {
					title: this.sendAllMsg.title,
					message: this.sendAllMsg.message
				}
				if(!this.sendAllMsg.isAllMsg) {
					obj.userIdList = userIdList;
//					console.log(obj)
					this.$http.post("/userMessage/insertBatch", obj).then((res) => {
//						console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '发送消息成功！',
								type: "success"
							});
							this.dialogFormVisible = false;
						}
					})
				} else {
//					console.log(obj)
					this.$http.post("/userMessage/insertAll", obj).then((res) => {
//						console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '发送消息成功！',
								type: "success"
							});
							this.dialogFormVisible = false;
						}
					})
				}
			} else {
				this.$message({
					message: '请填写正确的标题和内容！',
					type: "warning"
				});
			}

		},

	}

}

Vue.component("userManage", userManage);