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
			goodsAddrs:[]
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
				console.log(res);
				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0) {
					this.tableData = res.body.data;
					
				}
			}).catch((err) => {
				console.log(err)

			})
		},
		initDataCount:function(){
			this.$http.get("/jielong/selectAllCount").then((res)=>{
				console.log(res.body.data);
				if(res.status == 200 && res.body.errorCode == 0){
					this.tableDataCount = res.body.data;
				}
			})
		},
		handleCurrentChange:function(val){
				console.log(val)
				this.tableDataPageNum = val;
				this.initData();
			
		},
		beforeEnter:function(){
			console.log("222")
			
		},
		//查看详情
		handleDetails: function(scope) {
			console.log(scope)
			//格式化商品图片
			scope.row.goodsList.map(function(item,index){
				console.log(item.serverPaths instanceof Array)
				if(!(item.serverPaths instanceof Array)){
					item.serverPaths = item.serverPaths.split(",");
					return item;
				}
			})
			console.log(scope)
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
		
		handleDelete: function(scope) {
			console.log(scope)
		}

	}

}

Vue.component("jielongManage", jielongManage);