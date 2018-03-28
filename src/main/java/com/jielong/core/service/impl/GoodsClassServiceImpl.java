package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.core.beans.ResponseBean;
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

	@Override
	public ResponseBean<Integer> addGoodsClass(GoodsClass goodsClass) {
		// TODO Auto-generated method stub
		return goodsClassDao.insert(goodsClass);
	}

	@Override
	public ResponseBean<Integer> updateGoodsClass(GoodsClass goodsClass) {
		// TODO Auto-generated method stub
		goodsClass.setUpdatedAt(new Date());
		return goodsClassDao.update(goodsClass);
	}

	@Override
	public ResponseBean<Integer> deleteGoodsClassById(Integer id) {
		// TODO Auto-generated method stub
		//删除子类的所有类别
		goodsSubClassDao.deleteByParentId(id);
		//删除父类
		return goodsClassDao.deleteById(id);
	}
}
