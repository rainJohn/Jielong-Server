
var slider = {
	template: "#slider",
	data: function() {
		return {
			dataArr:[],
			dialogVisible: false,
			judeLoading:false,
			newRemark:"",//备注
			newRemarkIndex:"",//备注索引
			dialogRemarkVisible:false,//
		};
	},
	//初始化
	created: function() {
		this.initData();
	},
	methods: {
		//加载所有数据
		initData(){
       	  this.$http.get("/queryCarousels").then((response) => {
      		  console.log(response.body)
      		  	if(response.body){
      		  		this.dataArr = response.body;
      		  	}
              })			
		},
		handleProgress(file, fileList) {
			this.judeLoading = true;
		},
		handleSuccess(res){
//			console.log(this)
			this.initData();
			var _this = this;
			setTimeout(function(){
				_this.judeLoading = false;
			},500)
		},
		handleError(err){
//			console.log(err)
			this.$message({message:'上传图片失败'+err.type,type:"error"});
			this.judeLoading = false;
		},
		//添加备注
		addRemark(index){
			this.dialogRemarkVisible = true;
			this.newRemarkIndex = index;
			if(this.dataArr[index].remark){
				this.newRemark = this.dataArr[index].remark;
			}else{
				this.newRemark = "";
			}
//			console.log(this.newRemark)

		},
		closeAddReMark(){
//			console.log(this.newRemark)
			this.$http.get("/addRemarkById?id="+this.dataArr[this.newRemarkIndex].id+"&remark="+this.newRemark).then((res)=>{
				this.$message({message:'增加备注成功！',type:"success"});
				this.initData();
			}).catch((err)=>{
				this.$message({message:'增加备注失败！',type:"error"});
			})
			this.dialogRemarkVisible = false;
			
		},
		//启用轮播图片
		startSlider(index){
			this.$http.get("/startCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
				this.initData();
				this.$message({message:'启动轮播图片成功！',type:"success"});
//				console.log(res);
			}).catch((err)=>{
//				console.log(err);
				this.$message({message:'启动轮播图片失败！',type:"error"});
			})

		},
		//暂停轮播图片
		stopSlider(index){
			this.$http.get("/forbiddenCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
				this.initData();
				this.$message({message:'暂停轮播图片成功！',type:"success"});
			}).catch((err)=>{
				this.$message({message:'暂停轮播图片失败！',type:"error"});
			})
		},
		//删除轮播图片
		deteleSlider(index){
			this.$confirm('是否确定删除此分类？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then((res)=>{
				this.$http.get("/deleteCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
					this.$message({message:'删除轮播图片成功！',type:"success"});
					this.initData();
				}).catch((err)=>{
					this.$message({message:'删除轮播图片失败！',type:"error"});
				})				
			}).catch((res)=>{
				
			})

		},		
	}
}
Vue.component("slider", slider);