package com.jielong.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.ErrorCode;
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
		int i = goodsClassDao.insert(goodsClass);
		ResponseBean<Integer>  call= new ResponseBean<>(i);
		if(i!=0){}else{
			call.setErrorCode(ErrorCode.INSERT_EXCEPTION);
			call.setErrorMessage("插入失败");
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> updateGoodsClass(GoodsClass goodsClass) {
		// TODO Auto-generated method stub
		goodsClass.setUpdatedAt(new Date());
		goodsClass.setFlag(0);
		int i = goodsClassDao.update(goodsClass);
		ResponseBean<Integer>  call= new ResponseBean<>(i);
		if(i!=0){}else{
			call.setErrorCode(ErrorCode.UPDATE_EXCEPTION);
			call.setErrorMessage("更新失败");
		}
		return call;
	}

	@Override
	public ResponseBean<Integer> deleteGoodsClassById(Integer id) {
		// TODO Auto-generated method stub
		//找到所有子类
		List<GoodsSubClass> goodsSubClassList = goodsSubClassDao.findByParentId(id);
		//假删除子类的所有类别
		for(int i=0;i<goodsSubClassList.size();i++){
			goodsSubClassList.get(i).setFlag(1);
			
			int j = goodsSubClassDao.deleteById(goodsSubClassList.get(i));
			ResponseBean<Integer>  call= new ResponseBean<>(j);
			if(j!=0){
				
			}else{
				call.setErrorCode(ErrorCode.DELETE_EXCEPTION);
				call.setErrorMessage("子类型删除失败");
				return call;
			}
		}
		
		GoodsClass goodsClass = goodsClassDao.findById(id); 
		//假删除父类
		goodsClass.setFlag(1);
		
		int k = goodsClassDao.deleteById(goodsClass);
		ResponseBean<Integer>  orthercall= new ResponseBean<>(k);
		if(k!=0){
			
		}else{
			orthercall.setErrorCode(ErrorCode.DELETE_EXCEPTION);
			orthercall.setErrorMessage("父类型失败");
		}
		return orthercall;
	}
}
