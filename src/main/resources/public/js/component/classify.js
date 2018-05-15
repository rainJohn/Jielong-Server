var classify = {
	template: "#classify",
	data: function() {
		return {
			tableData:[],
			sonTableData:[],//子分类数据
			sonTableTitle:'',//子分类标题
			dialogTableVisible: false,//查看子分类
        	dialogFormVisible: false,//新增编辑
        	newAndEdit:{
	           	newClassify:"",//新分类
	        	judeEditId:"",//编辑id
	        	isSon:false
        	},
        	
		}
	},
	created: function() {
		this.initData();
	},
	watch: {
		'$route' (to, from) {
			console.log(to)
			console.log(from)
		}
	},
	methods: {
		initData(){
			this.$http.get("/getAllGoodsClass").then((res)=>{
				console.log(res.body.data)
				this.tableData = res.body.data;
			}).catch((err)=>{
				console.log(err)
			})			
		},
		//查看子分类
		watchNextClassify(scope) {
			console.log(scope)
			console.log(scope.row.goodsSubClasses)
			this.sonTableTitle = scope.row.className;
			this.sonTableData = scope.row.goodsSubClasses;
			this.dialogTableVisible = true;
			
		},
		//删除
		handleDelete(scope) {
			console.log(scope)
			 this.$confirm('是否确定删除此分类？', '提示', {
	          confirmButtonText: '确定',
	          cancelButtonText: '取消',
	          type: 'warning'
			}).then((res)=>{
				this.$http.get("/deleteGoodsClassById?id="+scope.row.id).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0){
						this.$message({message:'删除成功！',type:"success"});
						this.initData();
					}
				})

			}).catch((res)=>{
				console.log('444')
				
			})

		},
		//打开分类模特框（父类）
		addClassify(scope,isSon) {
			console.log(scope);
			console.log(isSon);
			if(isSon){
				this.newAndEdit.isSon = true;
			}else{
				this.newAndEdit.isSon = false;				
			}
			this.newAndEdit.newClassify = "";
			this.newAndEdit.judeEditId = "";
			if(scope.row){
				this.newAndEdit.judeEditId = scope.row.id;
				this.newAndEdit.newClassify = scope.row.className;
			}
			console.log(this.newAndEdit)
			this.dialogFormVisible =true;

		},
		//关闭增加分类模特框（父类/子类）
		closeAddClassify(){
			console.log(this.newAndEdit)
			console.log(this.newAndEdit.isSon)
			//父类
			if(!this.newAndEdit.isSon){
			//修改
			console.log("父类")
			
			if(this.newAndEdit.newClassify && this.newAndEdit.judeEditId){
				this.$http.post("/updateGoodsClass",{className:this.newAndEdit.newClassify,id:this.newAndEdit.judeEditId}).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0){
						this.initData();
					}
				})
			//新增
			}else if(this.newAndEdit.newClassify){
				this.$http.post("/addGoodsClass",{className:this.newAndEdit.newClassify}).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0){
						this.initData();
					}
				})			
			}
			
			//子类
			}else{
			//修改
			console.log("子类")
			if(this.newAndEdit.newClassify && this.newAndEdit.judeEditId){
				this.$http.post("/updateGoodsSubClass",{className:this.newAndEdit.newClassify,id:this.newAndEdit.judeEditId}).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0){
						this.initData();
					}
				})
			//新增
			}else if(this.newAndEdit.newClassify){
				this.$http.post("/addGoodsSubClass",{className:this.newAndEdit.newClassify}).then((res)=>{
					console.log(res)
					if(res.status == 200 && res.body.errorCode == 0){
						this.initData();
					}
				})			
			}				
			}
			
			
			
			
			this.dialogFormVisible = false;
		}
		
		
	}
}

Vue.component("classify", classify);