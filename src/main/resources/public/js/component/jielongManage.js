var jielongManage = {
	template: "#jielongManage",
	data: function() {
		return {
			tableData: [{
				className: "22"
			}, {
				className: "33"
			}],
			dialogTableVisible: false,
			sonTableData: [{
				className: "22"
			}, {
				className: "33"
			}],
		}
	},
	created: function() {
		this.initData();
	},
	methods: {
		initData: function() {
			this.$http.post("/jielong/selectByPage",{
				'pageNum': 0,
				'pageSize': 10
			}).then((res) => {
				console.log(res)
			}).catch((err) => {
				console.log(err)

			})
		},
		handleDetails: function(scope) {
			console.log(scope)
			this.dialogTableVisible = true;
		},
		handleDelete: function(scope) {
			console.log(scope)
		}

	}

}

Vue.component("jielongManage", jielongManage);