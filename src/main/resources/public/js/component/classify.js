var classify = {
	template: "#user2",
	data: function() {
		return {
			tableData: [{
				date: '分类1',
				name: '查看子分类',
				address: '修改',
				age: "删除"
			}, {
				date: '分类1',
				name: '查看子分类',
				address: '修改',
				age: "删除"
			}, {
				date: '分类1',
				name: '查看子分类',
				address: '修改',
				age: "删除"
			}, ]
		}
	},
	//提醒一下，当使用路由参数时，例如从 /user/foo 导航到 user/bar，原来的组件实例会被复用。因为两个路由都渲染同个组件，比起销毁再创建，复用则显得更加高效。不过，这也意味着组件的生命周期钩子不会再被调用。
	created: function() {
		console.log('创建了！22')
	},
	//watch检测路由的变化。to和from包含了路由切换前和切换后的信息。
	watch: {
		'$route' (to, from) {
			console.log(to)
			console.log(from)
		}
	}
}

Vue.component("classify", classify);