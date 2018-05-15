var classify = {
	template: "#classify",
	data: function() {
		return {
			tableData: [],
			sonTableData: [], //子分类数据
			sonTableTitle: '', //子分类标题
			dialogTableVisible: false, //查看子分类
			dialogFormVisible: false, //新增编辑
			newAndEdit: {
				newClassify: "", //新分类
				judeEditId: "", //编辑id
				isSon: false
			},

		}
	},
	created: function() {
		this.initData();
	},
	methods: {
		//初始化数据
		initData() {
			this.$http.get("/getAllGoodsClass").then((res) => {
				//				console.log(res.body.data)
				this.tableData = res.body.data;
			}).catch((err) => {
				console.log(err)
			})
		},
		initSonData() {
			this.$http.get("/getAllGoodsClass").then((res) => {
				this.tableData = res.body.data;
				this.tableData.forEach((item) => {
					if(item.id == this.newAndEdit.parentClassId) {
						this.sonTableData = item.goodsSubClasses;
					}
				})
			})
		},
		//查看子分类
		watchNextClassify(scope) {
			//			console.log(scope)
			this.sonTableTitle = scope.row.className;
			this.sonTableData = scope.row.goodsSubClasses;
			this.newAndEdit.parentClassId = scope.row.id;
			this.dialogTableVisible = true;
		},
		//删除
		handleDelete(scope, isSon) {
			//			console.log(scope)
			this.$confirm('是否确定删除此分类？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then((res) => {
				if(!isSon) {
					this.$http.get("/deleteGoodsClassById?id=" + scope.row.id).then((res) => {
						//						console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '删除成功！',
								type: "success"
							});
							this.initData();
						}
					})
				} else {
					this.$http.get("/deleteGoodsSubClassById?id=" + scope.row.id + "&parentClassId=" + this.newAndEdit.parentClassId).then((res) => {
						//						console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '删除成功！',
								type: "success"
							});
							this.initSonData();
						}
					})
				}

			})

		},
		//打开分类模特框（父类）
		addClassify(scope, isSon) {
			//			console.log(scope);
			if(isSon) {
				this.newAndEdit.isSon = true;
			} else {
				this.newAndEdit.isSon = false;
			}
			this.newAndEdit.newClassify = "";
			this.newAndEdit.judeEditId = "";
			if(scope.row) {
				this.newAndEdit.judeEditId = scope.row.id;
				this.newAndEdit.newClassify = scope.row.className;
			}
			//			console.log(this.newAndEdit)
			this.dialogFormVisible = true;

		},
		//关闭增加分类模特框（父类/子类）
		closeAddClassify() {
			//			console.log(this.newAndEdit)
			//父类
			if(!this.newAndEdit.isSon) {
				//修改
				if(this.newAndEdit.newClassify && this.newAndEdit.judeEditId) {
					this.$http.post("/updateGoodsClass", {
						className: this.newAndEdit.newClassify,
						id: this.newAndEdit.judeEditId
					}).then((res) => {
						//					console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '修改成功！',
								type: "success"
							});
							this.initData();
						}
					})
					//新增
				} else if(this.newAndEdit.newClassify) {
					this.$http.post("/addGoodsClass", {
						className: this.newAndEdit.newClassify
					}).then((res) => {
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '新增成功！',
								type: "success"
							});
							this.initData();
						}
					})
				}
				//子类
			} else {
				//修改
				if(this.newAndEdit.newClassify && this.newAndEdit.judeEditId) {
					this.$http.post("/updateGoodsSubClass", {
						parentClassId: this.newAndEdit.parentClassId,
						className: this.newAndEdit.newClassify,
						id: this.newAndEdit.judeEditId
					}).then((res) => {
						//					console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '修改成功！',
								type: "success"
							});
							this.initSonData();
						}
					})
					//新增
				} else if(this.newAndEdit.newClassify) {
					this.$http.post("/addGoodsSubClass", {
						parentClassId: this.newAndEdit.parentClassId,
						className: this.newAndEdit.newClassify
					}).then((res) => {
						//					console.log(res)
						if(res.status == 200 && res.body.errorCode == 0) {
							this.$message({
								message: '新增成功！',
								type: "success"
							});
							this.initSonData();
						}
					})
				}
			}
			this.dialogFormVisible = false;
		}

	}
}

Vue.component("classify", classify);