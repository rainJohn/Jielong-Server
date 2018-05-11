
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
	//watch检测路由的变化
	watch: {
		'$route' (to, from) {
			console.log(to)
			console.log(from)
		}
	},
	methods: {
		//加载所以数据
		initData(){
       	  this.$http.get("/queryCarousels").then((response) => {
      		  console.log(response)
      		  	if(response.body){
      		  		this.dataArr = response.body;
      		  	}
              }).catch((err) => {
              	console.log(err)
              })  			
		},
		handleProgress(file, fileList) {
			this.judeLoading = true;
		},
		handleSuccess(res){
			console.log(this)
			this.initData();
			var _this = this;
			setTimeout(function(){
				_this.judeLoading = false;
			},500)
		},
		handleError(err){
			console.log(err)
			this.$message({message:'上传图片失败'+err.type,type:"error"});
			this.judeLoading = false;
		},
		//增加标记
		addRemark(index){
			console.log(index)
			this.dialogRemarkVisible = true;
			this.newRemarkIndex = index;
			if(this.dataArr[index].remark){
				this.newRemark = this.dataArr[index].remark;
			}

		},
		closeAddReMark(){
			console.log(this.newRemark)
			this.$http.get("/addRemarkById?id="+this.dataArr[this.newRemarkIndex].id+"&remake="+this.newRemark).then((res)=>{
				this.$message({message:'增加备注成功！',type:"success"});
				this.initData();
				console.log(res);
			}).catch((err)=>{
				console.log(err);
				this.$message({message:'增加备注失败！',type:"error"});
			})
			this.dialogRemarkVisible = false;
			
		},
		//启用轮播图片
		startSlider(index){
			console.log(this)
			console.log(index)
			this.$http.get("/startCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
				this.$message({message:'启动轮播图片成功！',type:"success"});
				console.log(res);
			}).catch((err)=>{
				console.log(err);
				this.$message({message:'启动轮播图片失败！',type:"error"});
			})

		},
		//暂停轮播图片
		stopSlider(index){
			console.log(this)
			console.log(index)
			this.$http.get("/forbiddenCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
				console.log(res);
				this.$message({message:'暂停轮播图片成功！',type:"success"});
			}).catch((err)=>{
				console.log(err);
				this.$message({message:'暂停轮播图片失败！',type:"error"});
			})
		},
		//删除轮播图片
		deteleSlider(index){
			console.log(this)
			console.log(index)
			this.$http.get("/deleteCarouselByKey?id="+this.dataArr[index].id).then((res)=>{
				console.log(res);
				this.$message({message:'删除轮播图片成功！',type:"success"});
				this.initData();
			}).catch((err)=>{
				console.log(err);
				this.$message({message:'删除轮播图片失败！',type:"error"});
			})

		},		
	}
}
Vue.component("slider", slider);