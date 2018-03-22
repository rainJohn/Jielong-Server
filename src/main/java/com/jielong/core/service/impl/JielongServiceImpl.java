package com.jielong.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.GoodsMapper;
import com.jielong.core.dao.JielongMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Jielong;
import com.jielong.core.service.JielongService;

@Service
public class JielongServiceImpl implements JielongService{

	@Autowired
	JielongMapper jielongMapper;
	
	@Autowired
	GoodsMapper goodsMapper;
	
	@Autowired    
	CommonDao commonDao;
	

	@Transactional
	@Override
	public ResponseBean<Integer> insert(Jielong jielong) {
		ResponseBean<Integer> responseBean=new ResponseBean<>();
		
		try {
			//商品列表
			List<Goods> goodsList=jielong.getGoodsList();
			Integer result=jielongMapper.insertSelective(jielong);
			
			Integer jieLongId=commonDao.getLastId();   //最新插入的id
			
			//插入该接龙对应的所有商品
			for(Goods goods : goodsList) {
			   goods.setJielongId(jieLongId);	
			   goodsMapper.insertSelective(goods) ; 			
				
			}
			responseBean.setData(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			responseBean.setErrorCode(ErrorCode.INSERT_EXCEPTION);
			responseBean.setErrorMessage("插入数据错误");
			
		}
			return responseBean;
		
     }
	
	@Transactional
	@Override
	public ResponseBean<List<Jielong>> selectByPage(PageBean pageBean) {
	    PageHelper.startPage(pageBean.getPageNum(),pageBean.getPageSize()); 
	    List<Jielong> jielongs=jielongMapper.selectAll();
	    
	    for(Jielong jielong : jielongs) {
	      List<Goods> goodsList=goodsMapper.selectByJielongId(jielong.getId());
	      jielong.setGoodsList(goodsList);
	    }
	    
	    ResponseBean<List<Jielong>> responseBean=new ResponseBean<List<Jielong>>(jielongs);
		return responseBean;
	}
	
	@Transactional
	@Override
	public ResponseBean<List<Jielong>> selectByUserId(Integer userId) {
		
		List<Jielong> jielongs=jielongMapper.selectByUserId(userId);
		for(Jielong jielong : jielongs) {
			List<Goods> goodsList=goodsMapper.selectByJielongId(jielong.getId());
			jielong.setGoodsList(goodsList);
		}
		return new ResponseBean<List<Jielong>>(jielongs);
	}
	
	

}
