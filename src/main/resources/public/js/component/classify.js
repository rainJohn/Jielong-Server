var classify = {
	template: "#classify",
	data: function() {
		return {
//			tableData: [{
//				data: '分类1',
//				sonData: [{
//				data: '分类11',
//			}, {
//				data: '分类22',
//			}, {
//				data: '分类32',
//			}, ]
//			}, {
//				data: '分类2',
//				sonData: [{
//				data: '分类12',
//			}, {
//				data: '分类22',
//			}, {
//				data: '分类32',
//			}, ]
//			}, {
//				data: '分类3',
//				sonData: [{
//				data: '分类13',
//			}, {
//				data: '分类23',
//			}, {
//				data: '分类33',
//			}, ]
//			}, ],
			tableData:[],
			dialogTableVisible: false,//查看子分类
        	dialogFormVisible: false,//新增编辑
        	newClassify:"",//新分类
        	judeEdit:"",//是否是编辑
        	sonTableData:"",//子分类数据
        	
		}
	},
	//提醒一下，当使用路由参数时，例如从 /user/foo 导航到 user/bar，原来的组件实例会被复用。因为两个路由都渲染同个组件，比起销毁再创建，复用则显得更加高效。不过，这也意味着组件的生命周期钩子不会再被调用。
	created: function() {
		this.initData();

		console.log('创建了！22')
	},
	//watch检测路由的变化。to和from包含了路由切换前和切换后的信息641009\
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
		watchNextClassify(item) {
			console.log(item)
			this.sonTableData = item;
			this.dialogTableVisible = true;
			
		},
		handleDelete(scope) {
			console.log(scope)
			this.tableData.splice(scope.$index,1);
		},
		//打开分类模特框（父类）
		addClassify(scope) {
			console.log(scope);
			if(scope.$index + 1){
				this.judeEdit = scope.$index + 1;
				this.newClassify = this.tableData[scope.$index].data;
			}
			console.log(this.newClassify)

			this.dialogFormVisible =true;

		},
		//关闭增加分类模特框（父类）
		closeAddClassify(){
			console.log(this.judeEdit)
			if(this.newClassify && this.judeEdit){
				this.tableData[this.judeEdit - 1].data = this.newClassify;
			}else if(this.newClassify){
				this.tableData.push({
					data: this.newClassify
				})				
			}
			this.dialogFormVisible = false;
			this.newClassify = "";
		}
	}
}

Vue.component("classify", classify);