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
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.UserAddressMapper;
import com.jielong.core.dao.UserInfoMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Jielong;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.domain.OrderGroupConsole;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.UserMessageService;

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
	
	@Autowired
	OrderGroupMapper orderGroupMapper;

	@Autowired
	UserMessageService userMessageService;

	
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
					Integer goodId = commonDao.getLastId(); // 最新插入的id
					orderGroupConsole.setGoodsId(goodId);
					
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
		//caoxx 团购接龙结束调用 start
		List<Jielong> finishJielong = jielongMapper.selectFinishJielong();
		for (Jielong jielong : finishJielong) {
			orderGroupService.closeJieLong(jielong.getId());
		}
//		for (Jielong jielong : finishJielong) {
//			//当前时间大于结束时间的接龙处理(order_group_console已关闭的console_flg = 1不处理，只处理=0的)
//			//update  order_group_console 的接龙结束状态
//			List<OrderGroupConsole> orderGroupConsoleList= orderGroupConsoleMapper.selectByJieLongId(jielong.getId());
//			for (OrderGroupConsole orderGroupConsole : orderGroupConsoleList) {
//				//查询order_group 表 发送消息 及更新状态
//				List<OrderGroup> orderGroupList = orderGroupMapper.selectByJieLongGoodsId(orderGroupConsole.getJielongId(),orderGroupConsole.getGoodsId());
//				for (OrderGroup orderGroup : orderGroupList) {
//					Goods goods= goodsMapper.selectByPrimaryKey(orderGroup.getGoodsId());
//					if(orderGroupConsole.getGroupOkFlg() == 1){
//						//拼团成功每个下单的客户消息发送，状态更新
//						UserMessage userMessage=new UserMessage();
//						userMessage.setUserId(orderGroup.getCustId());
//						userMessage.setTitle("群发拼团成功通知！");
//						userMessage.setMessage("恭喜接龙"+jielong.getTopic() + "的"+ goods.getName()+"拼团成功，即可上门提货！");
//						userMessageService.insert(userMessage);
//						//更新一个订单的状态，根据ID,正常待提货
//						orderGroupMapper.updateStateById(2,0,orderGroup.getId());
//						
//					} else {
//						//拼团失败每个下单的客户消息发送，状态更新
//						UserMessage userMessage=new UserMessage();
//						userMessage.setUserId(orderGroup.getCustId());
//						userMessage.setTitle("群发拼团失败通知！");
//						userMessage.setMessage("遗憾的告诉您接龙"+jielong.getTopic() + "的"+ goods.getName()+"拼团失败！");
//						userMessageService.insert(userMessage);
//						//关闭接龙后团购失败，状态改变
//						orderGroupMapper.updateStateById(0,1,orderGroup.getId());
//					}
//					
//				}
//				//更新OrderGroupConsole接龙状态
//				orderGroupConsoleMapper.updateLastStateFlg(1, orderGroupConsole.getJielongId(), orderGroupConsole.getGoodsId());
//				
//			}
//			
//		}
		//caoxx 团购接龙结束调用 end
		
		
		//检查接龙的结束状态
		Integer finishResult=jielongMapper.setFinishStatus();
		
		
		
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
	
	/**
	 * 结束接龙
	 */
	@Override
	public ResponseBean<Integer> closeJielong(Integer id){
	  Jielong jielong=new Jielong();
	  jielong.setId(id);
	  jielong.setStatus(2);
	  Integer result=jielongMapper.updateByPrimaryKeySelective(jielong);      
	  return new ResponseBean<Integer>(result);
	}

}
