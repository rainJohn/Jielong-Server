package com.jielong.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.dao.GoodsClassDao;
import com.jielong.core.dao.GoodsSubClassDao;
import com.jielong.core.domain.GoodsClass;
import com.jielong.core.domain.GoodsSubClass;
import com.jielong.core.service.GoodsClassService;

@Service
public class GoodsClassServiceImpl implements GoodsClassService{
    @Autowired
    GoodsClassDao goodsClassDao;
    
    @Autowired
    GoodsSubClassDao goodsSubClassDao;
	
    /**
     * 查询所有的父分类和子分类
     */
	public List<GoodsClass> selectAllClass() {
    //	List<GoodsClass> goodsClassList=new ArrayList<GoodsClass>();
    	
    	List<GoodsClass> goodsClasses=goodsClassDao.selectAllClasses();
    	for(int i=0;i<goodsClasses.size();i++) {
    		GoodsClass goodsClass=goodsClasses.get(i);
    		List<GoodsSubClass> goodsSubClasses=goodsSubClassDao.findByParentId(goodsClass.getId());
    		goodsClass.setGoodsSubClasses(goodsSubClasses);
    	//	goodsClassList.add(goodsClass);
    	}
    	return goodsClasses;
    }
}
