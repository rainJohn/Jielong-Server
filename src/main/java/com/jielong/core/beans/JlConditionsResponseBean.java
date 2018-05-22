package com.jielong.core.beans;

import java.util.List;

import com.jielong.core.domain.Jielong;

public class JlConditionsResponseBean {

	// 总记录数
	private Integer count;

	// 接龙列表
	private List<Jielong> jielongList;

	public Integer getCount() {
		return count;
	}

	public List<Jielong> getJielongList() {
		return jielongList;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setJielongList(List<Jielong> jielongList) {
		this.jielongList = jielongList;
	}

}
