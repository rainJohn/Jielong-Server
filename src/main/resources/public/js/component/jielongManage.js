var jielongManage = {
	template: "#jielongManage",
	data: function() {
		return {
			isShow:true,
			domainUpload: "https://upload.95cfun.com/",
			tableData: [],//显示数据
			sonTableData: [],//显示数据
			tableDataCount: null, //数据长度
			tableDataPageNum: 0, //当前页数
			tableDataPageSize: 10, //每页页数
			currentPage: 1, //当前页数
			dialogTableVisible:false,
			goodsAddrs:[],//接龙详情自提点及时间
			searchConditions: {topic:null,goodsName:null,userNickName:null,pageNum:0,pageSize:10},
			isWebPagination:false,
		}
	},
	created: function() {
		this.initData();
		this.initDataCount();
	},
	methods: {
		//初始化数据
		initData: function() {
			this.$http.post("/jielong/selectAll",{
				'pageNum': this.tableDataPageNum,
				'pageSize': this.tableDataPageSize
			}).then((res) => {
//				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableData = res.body.data;
				}
			}).catch((err) => {
//				console.log(err)
			})
		},
		initDataCount:function(){
			if(!this.isWebPagination){
			this.$http.get("/jielong/selectAllCount").then((res)=>{
//				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0){
					this.tableDataCount = res.body.data;
				}
			})
			}else{
			console.log(this.searchConditions);
			this.$http.post("/jielong/selectByConditions",this.searchConditions).then((res)=>{
//				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0){
					this.tableData = res.body.data.jielongList;
					this.tableDataCount = res.body.data.count;
				}
			})					
			}
		},
		//搜索
		selectByConditions:function(){
//			console.log(this.searchConditions);
			this.searchConditions.pageNum = 0;
			this.searchConditions.pageSize = 10;
			this.$http.post("/jielong/selectByConditions",this.searchConditions).then((res)=>{
//				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0){
					this.isWebPagination = true;
					this.tableData = res.body.data.jielongList;
					this.tableDataCount = res.body.data.count;
					this.tableDataPageNum = 0;
					this.currentPage = 1;
				}
			})			
			
		},
		//页签按钮
		handleCurrentChange:function(val){
//				console.log(val)
				if(!this.isWebPagination){
					this.tableDataPageNum = val;
					this.initData();
				}else{
					this.searchConditions.pageNum = val;
					this.initDataCount();
					
				}
			
		},
		//查看详情
		handleDetails: function(scope) {
//			console.log(scope)
			//格式化商品图片
			scope.row.goodsList.map(function(item,index){
//				console.log(item.serverPaths instanceof Array)
				if(!(item.serverPaths instanceof Array)){
					item.serverPaths = item.serverPaths.split(",");
					return item;
				}
			})
//			console.log(scope)
			this.formatGoodsAddr(scope.row);
			this.sonTableData = scope.row;
			this.isShow = !this.isShow;
			this.dialogTableVisible = true;
		},
		//转换售货地址和时间格式
		formatGoodsAddr:function(item){
//			console.log(item);
			var goodsAddr = item.goodsAddresses.split(",");
			this.goodsAddrs = [];
			var that = this;
			this.$http.get("/userAddress/selectByUserId",{params:{userId:item.userId}}).then((res)=>{
				if(res.status == 200 && res.body.errorCode == 0){
					res.body.data.forEach(function(allAddrItem){
						goodsAddr.forEach(function(theAddrId){
						if(allAddrItem.id == theAddrId){
							that.goodsAddrs.push(allAddrItem.detail.replace(/\*\*\*/g," "));
						}
						})
					})
				}
			})			
			
		},
		//删除接龙
		handleDelete: function(scope) {
//			console.log(scope)
			this.$confirm("是否确认把此接龙删除","提示",{
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then((res)=>{
				this.$http.get("/jielong/deleteJielong",{params:{id:scope.row.id}}).then((res)=>{
//					console.log(res);
					if(res.status == 200 && res.body.errorCode == 0){
						if(!this.isWebPagination){
							this.selectByConditions();
						}else{
							this.initDataCount();
						}
						this.$message({message:"删除接龙成功！",type:"success"});
					}
				})
			}).catch((res)=>{
//				console.log(res)
			})
		}

	}

}

Vue.component("jielongManage", jielongManage);