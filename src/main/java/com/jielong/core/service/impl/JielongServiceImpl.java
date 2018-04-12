package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.jielong.base.util.ErrorCode;
import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.GoodsMapper;
import com.jielong.core.dao.JielongMapper;
import com.jielong.core.dao.OrderGroupConsoleMapper;
import com.jielong.core.dao.UserAddressMapper;
import com.jielong.core.dao.UserInfoMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Jielong;
import com.jielong.core.domain.OrderGroupConsole;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;

@Service
public class JielongServiceImpl implements JielongService {

	@Autowired
	JielongMapper jielongMapper;

	@Autowired
	GoodsMapper goodsMapper;

	@Autowired
	CommonDao commonDao;

	@Autowired
	UserAddressMapper addressMapper;

	@Autowired
	UserInfoMapper userInfoMapper;
	
	@Autowired
	OrderGroupConsoleMapper orderGroupConsoleMapper;
	
	@Autowired
	OrderGroupService orderGroupService;

	@Transactional
	@Override
	public ResponseBean<Integer> insert(Jielong jielong) {
		ResponseBean<Integer> responseBean = new ResponseBean<>();

		try {
			jielong.setStatus(1); // 状态：进行中
			Integer result = jielongMapper.insertSelective(jielong);

			// 商品列表
			List<Goods> goodsList = jielong.getGoodsList();

			if (goodsList != null && goodsList.size() > 0) {
				Integer jieLongId = commonDao.getLastId(); // 最新插入的id
				// 插入该接龙对应的所有商品
				for (Goods goods : goodsList) {
					goods.setJielongId(jieLongId);
					goodsMapper.insertSelective(goods);
					OrderGroupConsole orderGroupConsole = new OrderGroupConsole();
					orderGroupConsole.setJielongId(jieLongId);					
					orderGroupConsole.setGoodsId(goods.getId());
					if(goods.getIsSetGroup() == 1) {
						//是成团接龙
						orderGroupConsole.setGroupOkFlg(0);;
					} else {
						orderGroupConsole.setGroupOkFlg(2);
					}
					orderGroupConsole.setConsoleFlg(0);
					orderGroupConsoleMapper.insertSelective(orderGroupConsole);
				}
			}

			responseBean.setData(result);

		} catch (Exception e) {
			e.printStackTrace();
			responseBean.setErrorCode(ErrorCode.INSERT_EXCEPTION);
			responseBean.setErrorMessage("插入数据错误");

		}
		return responseBean;

	}

	@Override
	public ResponseBean<Integer> update(Jielong jielong) {
		ResponseBean<Integer> responseBean = new ResponseBean<>();

		try {
			// jielong.setStatus(1); //状态：进行中
			jielong.setUpdatedAt(new Date());
			Integer result = jielongMapper.updateByPrimaryKeySelective(jielong);

			// 商品列表
			List<Goods> goodsList = jielong.getGoodsList();

			if (goodsList != null) {
				Integer jieLongId = jielong.getId();
				// 删除原来的商品
				goodsMapper.deleteByJielongId(jieLongId);
				// 插入该接龙对应的所有商品
				for (Goods goods : goodsList) {
					goods.setJielongId(jieLongId);
					goodsMapper.insertSelective(goods);

				}
			}
			responseBean.setData(result);

		} catch (Exception e) {
			e.printStackTrace();
			responseBean.setErrorCode(ErrorCode.UPDATE_EXCEPTION);
			responseBean.setErrorMessage("更新数据错误");

		}
		return responseBean;

	}

	@Transactional
	@Override
	public ResponseBean<List<Jielong>> selectByPage(PageBean pageBean) {
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		// 查询状态为1的接龙
		List<Jielong> jielongs = jielongMapper.selectAll().stream().filter(j -> j.getStatus() == 1)
				.collect(Collectors.toList());

		for (Jielong jielong : jielongs) {
			// 发布用户信息
			UserInfo userInfo = userInfoMapper.selectByUserId(jielong.getUserId()).get(0);
			jielong.setUserInfo(userInfo);
			// 商品列表
			List<Goods> goodsList = goodsMapper.selectByJielongId(jielong.getId());
			jielong.setGoodsList(goodsList);

			// 图片列表
			String images = jielong.getIntroImages();
			if (StringUtil.isNotEmpty(images)) {
				String[] introImages = images.split(",");
				List<String> imagList = Arrays.asList(introImages);
				jielong.setImageList(imagList);
			}

			// 自提地址列表
			String address = jielong.getGoodsAddresses();
			if (StringUtil.isNotEmpty(address)) {
				List<UserAddress> addressList = new ArrayList<UserAddress>();
				String[] addresses = address.split(",");
				for (int i = 0; i < addresses.length; i++) {
					Integer addressId = Integer.parseInt(addresses[i]);
					UserAddress ads = addressMapper.selectByPrimaryKey(addressId);
					addressList.add(ads);
				}
				jielong.setTakeGoodsAddressList(addressList);

			} //

		}

		ResponseBean<List<Jielong>> responseBean = new ResponseBean<List<Jielong>>(jielongs);
		return responseBean;
	}

	@Transactional
	@Override
	public ResponseBean<List<Jielong>> selectByUserId(Integer userId) {

		List<Jielong> jielongs = jielongMapper.selectByUserId(userId);
		for (Jielong jielong : jielongs) {
			// 发布用户信息
			UserInfo userInfo = userInfoMapper.selectByUserId(jielong.getUserId()).get(0);
			jielong.setUserInfo(userInfo);

			// 商品列表
			List<Goods> goodsList = goodsMapper.selectByJielongId(jielong.getId());
			jielong.setGoodsList(goodsList);

			// 图片列表
			String images = jielong.getIntroImages();
			if (StringUtil.isNotEmpty(images)) {
				String[] introImages = images.split(",");
				List<String> imagList = Arrays.asList(introImages);
				jielong.setImageList(imagList);
			}

			// 自提地址列表
			String address = jielong.getGoodsAddresses();
			if (StringUtil.isNotEmpty(address)) {
				List<UserAddress> addressList = new ArrayList<UserAddress>();
				String[] addresses = address.split(",");
				for (int i = 0; i < addresses.length; i++) {
					Integer addressId = Integer.parseInt(addresses[i]);
					UserAddress ads = addressMapper.selectByPrimaryKey(addressId);
					addressList.add(ads);
				}
				jielong.setTakeGoodsAddressList(addressList);

			} //

		}
		return new ResponseBean<List<Jielong>>(jielongs);
	}

	@Override
	public ResponseBean<Integer> updateBrowse(Integer id) {
		ResponseBean<Integer> responseBean = new ResponseBean<>();
		Integer result = jielongMapper.updateBrowse(id);
		if (result != 0) { // 更新成功
			responseBean.setData(result);
		} else {
			responseBean.setData(ErrorCode.UPDATE_EXCEPTION);
			responseBean.setErrorMessage("更新数据错误");
		}
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> updateJoin(Integer id, BigDecimal joinMoney) {
		ResponseBean<Integer> responseBean = new ResponseBean<>();
		Integer result = jielongMapper.updateJoin(id, joinMoney);
		if (result != 0) { // 更新成功
			responseBean.setData(result);
		} else {
			responseBean.setData(ErrorCode.UPDATE_EXCEPTION);
			responseBean.setErrorMessage("更新数据错误");
		}
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> selectCount() {
		return new ResponseBean<Integer>(jielongMapper.selectCount());
	}

	@Transactional
	@Override
	public ResponseBean<Jielong> selectById(Integer id) {
		ResponseBean<Jielong> responseBean = new ResponseBean<Jielong>();
		Jielong jielong = jielongMapper.selectByPrimaryKey(id);
		
		// 发布用户信息
		UserInfo userInfo = userInfoMapper.selectByUserId(jielong.getUserId()).get(0);
		jielong.setUserInfo(userInfo);
		// 商品列表
		List<Goods> goodsList = goodsMapper.selectByJielongId(jielong.getId());
		for(Goods goods : goodsList) {
			if (goods.getIsSetGroup()==1) {
			  	Integer remainSum=orderGroupService.getGroupPeople(goods.getJielongId(), goods.getId());
				goods.setRemainSum(remainSum);
			}
		}
		
		jielong.setGoodsList(goodsList);

		// 图片列表
		String images = jielong.getIntroImages();
		if (StringUtil.isNotEmpty(images)) {
			String[] introImages = images.split(",");
			List<String> imagList = Arrays.asList(introImages);
			jielong.setImageList(imagList);
		}

		// 自提地址列表
		String address = jielong.getGoodsAddresses();
		if (StringUtil.isNotEmpty(address)) {
			List<UserAddress> addressList = new ArrayList<UserAddress>();
			String[] addresses = address.split(",");
			for (int i = 0; i < addresses.length; i++) {
				Integer addressId = Integer.parseInt(addresses[i]);
				UserAddress ads = addressMapper.selectByPrimaryKey(addressId);
				addressList.add(ads);
			}
			jielong.setTakeGoodsAddressList(addressList);

		} 
		
		responseBean.setData(jielong);
		
		return responseBean;
		
		
	}

}
