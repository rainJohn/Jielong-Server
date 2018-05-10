var slider = {
	template: "#slider",
	data: function() {
		return {
			dialogImageUrl: '',
			dialogVisible: false,
			descirbe:"我是描述",
			dataArr:[{a:1,deacirbe:"ssss"}]
		};
	},
	//提醒一下，当使用路由参数时，例如从 /user/foo 导航到 user/bar，原来的组件实例会被复用。因为两个路由都渲染同个组件，比起销毁再创建，复用则显得更加高效。不过，这也意味着组件的生命周期钩子不会再被调用。
	created: function() {
		console.log('创建了！！！')
	},
	//watch检测路由的变化。to和from包含了路由切换前和切换后的信息。
	watch: {
		'$route' (to, from) {
			console.log(to)
			console.log(from)
		}
	},
	methods: {
		handleRemove(file, fileList) {
			console.log(file, fileList);
		},
		handlePictureCardPreview(file) {
			this.dialogImageUrl = file.url;
			this.dialogVisible = true;
		},
		startSlider(e){
			console.log(this)
			console.log(e)
			this.descirbe = "我是描述我是描述"

		},
		stopSlider(){
			console.log(this)
			this.descirbe = "我是描述我是描述"

		},
		deteleSlider(){
			console.log(this)
			this.descirbe = "我是描述我是描述"

		},		
	}
}
Vue.component("slider", slider);