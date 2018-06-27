package com.jielong.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.base.util.ErrorCode;
import com.jielong.base.util.Utils;
import com.jielong.core.beans.PickBean;
import com.jielong.core.beans.PickCountBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.beans.SignBean;
import com.jielong.core.beans.SignPickBean;
import com.jielong.core.dao.CommonDao;
import com.jielong.core.dao.GoodsMapper;
import com.jielong.core.dao.JielongMapper;
import com.jielong.core.dao.OrderGroupConsoleMapper;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Jielong;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.domain.OrderGroupConsole;
import com.jielong.core.domain.UserAddress;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.domain.UserMessage;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.UserAddressService;
import com.jielong.core.service.UserInfoService;
import com.jielong.core.service.UserMessageService;
import com.jielong.core.service.UserService;

import net.sf.jsqlparser.statement.update.Update;

@Service
public class OrderGroupServiceImpl implements OrderGroupService {

	@Autowired
	CommonDao commonDao;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderGroupConsoleMapper orderGroupConsoleMapper;
	@Autowired
	OrderGroupMapper orderGroupMapper;
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	JielongService jielongService;
	@Autowired
	UserMessageService userMessageService;
	@Autowired
	UserAddressService userAddressService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	JielongMapper jielongMapper;

	@Autowired
	WxPayServiceImpl wxPayService;

	@Autowired
	UserService userService;

	/**
	 * 不含支付时的下单逻辑
	 */
	@Transactional
	@Override
	public ResponseBean<Integer> insert(Order order) {
		ResponseBean<Integer> responseBean = new ResponseBean<Integer>();
		String orderNum = Utils.createFileName();
		List<OrderGoods> orderGoodsList = order.getOrderGoods();
		// Jielong主题
		String topic = jielongMapper.selectTopic(order.getJielongId());
		UserAddress address = userAddressService.selectById(order.getAddressId()).getData();
		// 下单地址
		String addressInfo = address.getDetail().replace("***", " 提货时间");

		if (orderGoodsList != null && orderGoodsList.size() > 0) {

			StringBuilder goodsInfo = new StringBuilder(); // 商品名称

			for (int i = 0; i < orderGoodsList.size(); i++) {

				OrderGroup orderGroup = new OrderGroup();
				// 订单编号
				orderGroup.setOrderId(orderNum);
				// 接龙ID
				orderGroup.setJielongId(order.getJielongId());
				// 购买者ID
				orderGroup.setCustId(order.getUserId());
				orderGroup.setCustName(order.getUserName());
				orderGroup.setCustPhone(order.getUserPhone());
				orderGroup.setCustNote(order.getRemark());
				orderGroup.setAddressId(order.getAddressId());

				OrderGoods orderGoods = orderGoodsList.get(i);
				orderGroup.setGoodsId(orderGoods.getGoodsId());
				orderGroup.setCustBuyNum(orderGoods.getSum());
				orderGroup.setCustBuyPrice(orderGoods.getMoney());
				BigDecimal buyAllMoney = new BigDecimal(0);
				buyAllMoney = orderGoods.getMoney().multiply(new BigDecimal(orderGoods.getSum()));
				orderGroup.setCustBuyAllMoney(buyAllMoney);
				// 交易状态
				orderGroup.setTradeFlg(0);
				// 订单状态 '0'下单成功待支付
				orderGroup.setOrderFlg(0);

				// 插入数据
				orderGroupMapper.insertSelective(orderGroup);

				// 取得接龙商品的成团数
				Goods goods = new Goods();
				goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
				// 购买商品信息： 商品名称+数量+规格
				goodsInfo.append(goods.getName()).append(orderGoods.getSum()).append(goods.getSpecification());
				// 取得该商品的成团数量
				int groupNum = Integer.valueOf(goods.getGroupSum());
				// 取得 该商品目前已经的成团数量
				int newGroupNum = Optional
						.ofNullable(orderGroupMapper.selectByCustBuyNum(order.getJielongId(), orderGoods.getGoodsId()))
						.orElse(0);

				// 该商品拼团成功
				if (newGroupNum >= groupNum) {
					// 查看原来的成团状态
					Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(),
							orderGoods.getGoodsId());
					// 本来就拼团成功
					if (oldGroupOkFlg == 1) {
						// 只给当前用户发送拼团成功的消息
						StringBuilder message = new StringBuilder();
						message.append("恭喜您成功下单：“").append(topic).append("”的“").append(goodsInfo).append("”，你选择了“")
								.append(addressInfo).append("”取货，请您牢记！如有问题，可以随时与发起人进行沟通。订单详情请前往“我的”-“我参与的Mart”进行查看");
						sendMessage("下单成功通知！", message.toString(), order.getUserId());

					} else {
						// 加上这个用户之后，所有人拼团成功
						// 更新orderGroupConsole表
						int updateRet = orderGroupConsoleMapper.updateGroupOkFlg(1, order.getJielongId(),
								orderGoods.getGoodsId());
						StringBuilder sb = new StringBuilder();
						sb.append("恭喜您成功下单：“").append(topic).append("”的“").append(goodsInfo).append("”，您选择了“")
								.append(addressInfo)
								.append("”取货，请您牢记！如有问题，可以随时与发起人进行沟通。\r\n" + "订单详情请前往“我的”-“我参与的Mart”进行查看");
						// 群发拼团成功消息
						sendGroupMessage(order.getJielongId(), orderGoods.getGoodsId(), 1, sb.toString());
					}

				} else { // 该商品还没有拼团成功

					// 给该用户发送消息
					StringBuilder sb = new StringBuilder();

					sb.append("您已成功预订：\"").append(topic).append("\"的\"").append(goodsInfo).append("\",")
							.append("此Mart需要满足最小成团数量 ").append(groupNum)
							.append(" 才可成功,满足数量后，系统会发送通知。您也可以将此Mart转发到微信群或朋友圈，帮助发起人一起促成。")
							.append("订单详情请前往\"我的\"-\"我参与的Mart\"进行查看");

					sendMessage("下单成功通知！", sb.toString(), order.getUserId());

				}
				// 减少对应商品的库存
				goodsMapper.updateRepertory(orderGoods.getGoodsId(), orderGoods.getSum());
				// 下单之后，更新接龙参与人数、参与金额等信息
				jielongService.updateJoin(order.getJielongId(), buyAllMoney);

			}
			responseBean.setData(1);
			return responseBean;
		}

		return null;
	}

	/**
	 * 包含支付时的下单逻辑
	 * 
	 * @param order
	 * @return
	 */
	@Transactional
	@Override
	public ResponseBean<Map<String, String>> insertWithPay(Order order) {

		ResponseBean<Map<String, String>> responseBean = new ResponseBean<>();
		String orderNum = Utils.createFileName();
		BigDecimal buyAllMoney = new BigDecimal(0);
		List<OrderGoods> orderGoodsList = order.getOrderGoods();

		if (orderGoodsList != null && orderGoodsList.size() > 0) {

			for (OrderGoods orderGoods: orderGoodsList) {
				//检查库存
				Integer repertory=goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId()).getRepertory();
				if (repertory<=0) {
					//库存不足
					responseBean.setErrorCode(ErrorCode.REPERTORY_EXCEPTION);
					responseBean.setErrorMessage("商品库存不足");
					return responseBean;
					
				}
				

				OrderGroup orderGroup = new OrderGroup();
				// 订单编号
				orderGroup.setOrderId(orderNum);
				// 接龙ID
				orderGroup.setJielongId(order.getJielongId());
				// 购买者ID
				orderGroup.setCustId(order.getUserId());
				orderGroup.setCustName(order.getUserName());
				orderGroup.setCustPhone(order.getUserPhone());
				orderGroup.setCustNote(order.getRemark());
				orderGroup.setAddressId(order.getAddressId());

				orderGroup.setGoodsId(orderGoods.getGoodsId());
				orderGroup.setCustBuyNum(orderGoods.getSum());
				orderGroup.setCustBuyPrice(orderGoods.getMoney());

				buyAllMoney = orderGoods.getMoney().multiply(new BigDecimal(orderGoods.getSum()));
				orderGroup.setCustBuyAllMoney(buyAllMoney);
				// 交易状态
				orderGroup.setTradeFlg(0);
				// 订单状态 '0'下单成功待支付
				orderGroup.setOrderFlg(0);

				// 插入数据
				orderGroupMapper.insertSelective(orderGroup);

			}

		}

		// 调用微信支付
		// 1、获取用户的openId
		String openId = userService.selectById(order.getUserId()).getOpenId();
		String goodsDesc = "VanMart-景点门票";
		// 订单总金额，要换成单位 分
		int totalFee=buyAllMoney.multiply(new BigDecimal(100)).intValue();
		//TODO:测试时用1分
		//int totalFee = 1;
		Map<String, String> map = wxPayService.wxPay(openId, goodsDesc, orderNum, totalFee, 1);
		if (null != map) {
			responseBean.setData(map);
		} else {
			responseBean.setErrorCode(ErrorCode.PAY_EXCEPTION);
			responseBean.setErrorMessage("支付发生错误");
		}

		return responseBean;
	}

	/**
	 * 异步通知中更新订单
	 * 
	 * @param orderGroup
	 */
	public void updateOrder(OrderGroup orderGroup) {
		// 减少商品库存
		goodsMapper.updateRepertory(orderGroup.getGoodsId(), orderGroup.getCustBuyNum());

		// 更新订单状态
		OrderGroup newOrderGroup = new OrderGroup();
		newOrderGroup.setId(orderGroup.getId());
		newOrderGroup.setTradeFlg(1);
		orderGroupMapper.updateByPrimaryKeySelective(newOrderGroup);

		// 更新接龙（参与人数、参与金额等）
		jielongService.updateJoin(orderGroup.getJielongId(), orderGroup.getCustBuyAllMoney());

		// 检查是否成团并发送消息
		checkGroup(orderGroup);

	}

	private void checkGroup(OrderGroup orderGroup) {
		// Jielong主题
		String topic = jielongMapper.selectTopic(orderGroup.getJielongId());
		UserAddress address = userAddressService.selectById(orderGroup.getAddressId()).getData();
		// 下单地址
		String addressInfo = address.getDetail().replace("***", " 提货时间");

		StringBuilder goodsInfo = new StringBuilder();
		Goods goods = goodsMapper.selectByPrimaryKey(orderGroup.getGoodsId());
		// 购买商品信息： 商品名称+数量+规格
		goodsInfo.append(goods.getName()).append(orderGroup.getCustBuyNum()).append(goods.getSpecification());
		// 取得该商品的成团数量
		int groupNum = Integer.valueOf(goods.getGroupSum());
		// 取得 该商品目前已经的成团数量
		int newGroupNum = Optional
				.ofNullable(orderGroupMapper.selectByCustBuyNum(orderGroup.getJielongId(), orderGroup.getGoodsId()))
				.orElse(0);

		// 该商品拼团成功
		if (newGroupNum >= groupNum) {
			// 查看原来的成团状态
			Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(orderGroup.getJielongId(),
					orderGroup.getGoodsId());
			// 本来就拼团成功
			if (oldGroupOkFlg == 1) {
				// 只给当前用户发送拼团成功的消息
				StringBuilder message = new StringBuilder();
				message.append("恭喜您成功下单：“").append(topic).append("”的“").append(goodsInfo).append("”，你选择了“")
						.append(addressInfo).append("”取货，请您牢记！如有问题，可以随时与发起人进行沟通。订单详情请前往“我的”-“我参与的Mart”进行查看");
				sendMessage("下单成功通知！", message.toString(), orderGroup.getCustId());

			} else {
				// 加上这个用户之后，所有人拼团成功
				// 更新orderGroupConsole表
				int updateRet = orderGroupConsoleMapper.updateGroupOkFlg(1, orderGroup.getJielongId(),
						orderGroup.getGoodsId());
				StringBuilder sb = new StringBuilder();
				sb.append("恭喜您成功下单：“").append(topic).append("”的“").append(goodsInfo).append("”，您选择了“")
						.append(addressInfo).append("”取货，请您牢记！如有问题，可以随时与发起人进行沟通。\r\n" + "订单详情请前往“我的”-“我参与的Mart”进行查看");
				// 群发拼团成功消息
				sendGroupMessage(orderGroup.getJielongId(), orderGroup.getGoodsId(), 1, sb.toString());
			}

		} else { // 该商品还没有拼团成功

			// 给该用户发送消息
			StringBuilder sb = new StringBuilder();

			sb.append("您已成功预订：\"").append(topic).append("\"的\"").append(goodsInfo).append("\",")
					.append("此Mart需要满足最小成团数量 ").append(groupNum)
					.append(" 才可成功,满足数量后，系统会发送通知。您也可以将此Mart转发到微信群或朋友圈，帮助发起人一起促成。")
					.append("订单详情请前往\"我的\"-\"我参与的Mart\"进行查看");

			sendMessage("下单成功通知！", sb.toString(), orderGroup.getCustId());

		}
	}

	// 根据顾客id查询订单(参与的接龙)
	@Transactional
	@Override
	public ResponseBean<List<Order>> selectByCustomerId(Integer userId) {
		ResponseBean<List<Order>> responseBean = new ResponseBean<List<Order>>();
		List<OrderGroup> orderGroupList = orderGroupMapper.selectByCustId(userId);
		// 转换输出格式
		List<Order> orderList = new ArrayList<Order>();
		if (orderGroupList != null && orderGroupList.size() > 0) {
			for (OrderGroup ordergroup : orderGroupList) {
				// 转换输出格式
				Order order = new Order();
				order.setId(ordergroup.getId());
				order.setIsSetGroup(1);
				order.setJielongId(ordergroup.getJielongId());
				order.setOrderNum(ordergroup.getOrderId());
				order.setRemark(ordergroup.getCustNote());
				order.setState(ordergroup.getTradeFlg());

				order.setSumMoney(ordergroup.getCustBuyAllMoney());

				order.setUserId(ordergroup.getCustId());

				order.setUserName(ordergroup.getCustName());
				order.setUserPhone(ordergroup.getCustPhone());
				order.setAddressId(ordergroup.getAddressId());
				order.setCreatedAt(ordergroup.getCreatedAt());
				order.setUpdatedAt(ordergroup.getUpdatedAt());

				// Jielong主题
				String topic = jielongMapper.selectTopic(ordergroup.getJielongId());
				order.setJielongTopic(topic);
				// 提货地址信息
				Integer addressId = ordergroup.getAddressId();
				UserAddress address = userAddressService.selectById(addressId).getData();
				order.setUserAddress(address);
				// 用户信息
				Integer clientId = ordergroup.getCustId();
				UserInfo userInfo = userInfoService.selectByUserId(clientId).getData();
				order.setUserInfo(userInfo);

				// 订单商品信息
				List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();

				// 对应统一接口 ORDER
				Goods goods = goodsMapper.selectByPrimaryKey(ordergroup.getGoodsId());
				OrderGoods orderGoods = new OrderGoods();
				orderGoods.setGoods(goods);
				orderGoods.setGoodsId(ordergroup.getGoodsId());
				// orderGoods.setId();
				orderGoods.setMoney(ordergroup.getCustBuyPrice());
				// orderGoods.setOrderId(orderGroup.getOrderId());
				orderGoods.setSum(ordergroup.getCustBuyNum());

				OrderGroupConsole orderGroupConsole = orderGroupConsoleMapper
						.selectByJielongAndGoods(ordergroup.getJielongId(), ordergroup.getGoodsId());

				Integer orderFlg = ordergroup.getOrderFlg();

				if (orderFlg == 1) {// (Jielong结束,参团失败) 或者 (订单取消) 订单取消state=4
					Integer jielongState = orderGroupConsole.getConsoleFlg();
					if (jielongState == 1) { // 1表示Jielong结束
						orderGoods.setGroupFlg(2); // groupFlg==2 表示参团失败
					} else { // 取消订单情况
						order.setState(4);
					}

				} else {
					Integer groupOkFlg = orderGroupConsole.getGroupOkFlg();
					if (groupOkFlg == 1) {
						// 参团成功
						orderGoods.setGroupFlg(groupOkFlg);
						orderGoods.setJoinGroupNum(0);
					} else {

						orderGoods.setGroupFlg(0);
						// 待拼团成功，差几人计算
						Integer setGroupNum = Integer.valueOf(goods.getGroupSum());

						Integer newGroupNum = orderGroupMapper.selectByCustBuyNum(ordergroup.getJielongId(),
								ordergroup.getGoodsId());

						if (setGroupNum != null && newGroupNum != null) {
							int numtmp = setGroupNum - newGroupNum;
							orderGoods.setJoinGroupNum(numtmp);
						}
					}

				}

				orderGoodsList.add(orderGoods);
				order.setOrderGoods(orderGoodsList);
				orderList.add(order);
			}

		}

		responseBean.setData(orderList);
		return responseBean;
	}

	/**
	 * 根据发布者id查询订单
	 */
	@Transactional
	@Override
	public ResponseBean<List<Order>> selectByPublisherId(Integer userId) {
		ResponseBean<List<Order>> responseBean = new ResponseBean<List<Order>>();
		List<OrderGroup> orderGroupList = orderGroupMapper.selectByPublisherId(userId);
		// 转换输出格式
		List<Order> orderList = new ArrayList<Order>();

		if (orderGroupList != null && orderGroupList.size() > 0) {
			for (OrderGroup orderGroup : orderGroupList) {
				// 转换输出格式
				Order order = new Order();
				order.setId(orderGroup.getId());
				order.setIsSetGroup(1);
				order.setJielongId(orderGroup.getJielongId());
				// order.setJielongTopic(ordergroup);
				// order.setOrderGoods(ordergroup);
				order.setOrderNum(orderGroup.getOrderId());
				order.setRemark(orderGroup.getCustNote());
				order.setState(orderGroup.getTradeFlg());
				order.setSumMoney(orderGroup.getCustBuyAllMoney());
				// order.setUserAddress(userAddress);
				order.setUserId(orderGroup.getCustId());
				// order.setUserInfo(ordergroup.get);
				order.setUserName(orderGroup.getCustName());
				order.setUserPhone(orderGroup.getCustPhone());
				order.setCreatedAt(orderGroup.getCreatedAt());
				order.setUpdatedAt(orderGroup.getUpdatedAt());

				order.setAddressId(orderGroup.getAddressId());

				// Jielong主题
				String topic = jielongMapper.selectTopic(orderGroup.getJielongId());
				order.setJielongTopic(topic);
				// 提货地址信息
				Integer addressId = orderGroup.getAddressId();
				UserAddress address = userAddressService.selectById(addressId).getData();
				order.setUserAddress(address);
				// 用户信息
				Integer clientId = orderGroup.getCustId();
				UserInfo userInfo = userInfoService.selectByUserId(clientId).getData();
				order.setUserInfo(userInfo);

				// 订单商品信息
				List<OrderGroup> orderGroupList2 = orderGroupMapper.selectByOrderId(orderGroup.getOrderId());

				if (orderGroupList2 != null && orderGroupList2.size() > 0) {
					List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
					for (OrderGroup orderGroup2 : orderGroupList2) {

						// 对应统一接口 ORDER
						Goods goods = goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setGoods(goods);
						orderGoods.setGoodsId(orderGroup2.getGoodsId());
						// orderGoods.setId();
						orderGoods.setMoney(orderGroup2.getCustBuyPrice());
						// orderGoods.setOrderId(orderGroup.getOrderId());
						orderGoods.setSum(orderGroup2.getCustBuyNum());

						// 商品成功成团与否FLG
						Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(orderGroup2.getJielongId(),
								orderGroup2.getGoodsId());

						if (groupOkFlg == 1) {
							// 参团成功
							orderGoods.setGroupFlg(groupOkFlg);
							orderGoods.setJoinGroupNum(0);
						} else {
							orderGoods.setGroupFlg(groupOkFlg);
							// 参团不成功，差几人计算
							int setGroupNum = Integer.valueOf(goods.getGroupSum());

							int newGroupNum = orderGroupMapper.selectByCustBuyNum(orderGroup2.getJielongId(),
									orderGroup2.getGoodsId());

							int numtmp = setGroupNum - newGroupNum;
							orderGoods.setJoinGroupNum(numtmp);
						}

						orderGoodsList.add(orderGoods);
					}
					order.setOrderGoods(orderGoodsList);
				}
				orderList.add(order);
			}
		}

		responseBean.setData(orderList);
		return responseBean;
	}

	@Override
	// 计算已参团数量
	public int getGroupPeople(Integer jielongId, Integer goodsId) {
		int peopleSum = 0;
		// 商品成功成团与否FLG
		Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(jielongId, goodsId);
		if (groupOkFlg != null) {

			// 参团不成功，计算已参团数量

			Integer num = orderGroupMapper.selectByCustBuyNum(jielongId, goodsId);
			if (num != null) {
				peopleSum = num;
			}

		}

		return peopleSum;

	}

	/**
	 * 关闭Jielong
	 * 
	 * @param flag:1用户手动结束接龙，2接龙时间到，自动结束
	 */
	@Transactional
	@Override
	public int closeJieLong(Integer jielongId, int flag) {

		// 传入为结束接龙ID
		// 1.判断结束的接龙是否是已经成团的接龙
		// 取得接龙ID的商品清单
		List<OrderGroupConsole> listOrderGroupConsole = orderGroupConsoleMapper.selectByJieLongId(jielongId);

		// 接龙名取得
		String JielongName = jielongMapper.selectTopic(jielongId);
		for (OrderGroupConsole orderGroupConsole : listOrderGroupConsole) {
			// 商品成功成团与否FLG
			if (orderGroupConsole.getGroupOkFlg() == 1) {
				// 拼团成功 最终结果更新
				// 1.关闭order_group_console表，状态关闭
				orderGroupConsoleMapper.updateLastStateFlg(1, orderGroupConsole.getJielongId(),
						orderGroupConsole.getGoodsId());

				// 2.关闭order_group表，状态关闭,trade_flg 0 -> 2,order_flg 0 -> 0 where trade_flg = 0
				// and order_flg = 0
				orderGroupMapper.updateLastStateFlg(2, 0, orderGroupConsole.getJielongId(),
						orderGroupConsole.getGoodsId());

				List<OrderGroup> orderGroupList = orderGroupMapper
						.selectByJieLongGoodsId(orderGroupConsole.getJielongId(), orderGroupConsole.getGoodsId());
				for (OrderGroup orderGroup : orderGroupList) {
					Goods goods = goodsMapper.selectByPrimaryKey(orderGroup.getGoodsId());

					String address = userAddressService.selectById(orderGroup.getAddressId()).getData().getDetail()
							.replace("***", " 提货时间");

					StringBuilder sb = new StringBuilder();
					if (flag == 1) { // 手动关闭Mart
						sb.append("您好！您参与的：“").append(JielongName).append("”已由团长提前截团，请您在：“").append(address)
								.append("”进行提货，请您牢记！").append("如有问题，可以随时与发起人进行沟通。\r\n订单详情请前往“我的”-“我参与的Mart”进行查看");
					}
					if (flag == 2) {
						sb.append("您好！您参与的：“").append(JielongName).append("”已截团，请您在：“").append(address)
								.append("”进行提货，请您牢记！").append("如有问题，可以随时与发起人进行沟通。\r\n订单详情请前往“我的”-“我参与的Mart”进行查看");
					}

					sendMessage("截团通知", sb.toString(), orderGroup.getCustId());

				}

			} else {
				// 拼团失败 最终结果更新
				// 1.关闭order_group_console表，状态关闭
				orderGroupConsoleMapper.updateLastStateFlg(1, orderGroupConsole.getJielongId(),
						orderGroupConsole.getGoodsId());

				// 2.关闭order_group表，状态关闭,trade_flg 0 -> 0,order_flg 0 -> 1 where trade_flg = 0
				// and order_flg = 0
				orderGroupMapper.updateLastStateFlg(0, 1, orderGroupConsole.getJielongId(),
						orderGroupConsole.getGoodsId());

				// 拼团失败每个下单的客户消息发送，状态更新
				List<OrderGroup> orderGroupList = orderGroupMapper
						.selectByJieLongGoodsId(orderGroupConsole.getJielongId(), orderGroupConsole.getGoodsId());
				for (OrderGroup orderGroup : orderGroupList) {
					Goods goods = goodsMapper.selectByPrimaryKey(orderGroup.getGoodsId());

					// 拼团成功每个下单的客户消息发送，状态更新
					String message = "";
					if (flag == 1) { // 手动关闭Mart
						message = "非常遗憾地通知您，由于商家提前终止了Mart(此行为与我们平台无关)，" + JielongName + "的" + goods.getName()
								+ "未满足最小成团数量，本次订单自动关闭。您还可以去首页看看其他Mart哦！";
					}
					if (flag == 2) {
						message = "非常遗憾地通知您，截止Mart结束，" + JielongName + "的" + goods.getName()
								+ "未满足最小成团数量，本次订单自动关闭。您还可以去首页看看其他Mart哦！";
					}

					sendMessage("团购失败通知！", message, orderGroup.getCustId());

				}

			}

		}

		// return 0 为接龙结束异常 1为正常
		return 1;
	}

	@Transactional
	@Override
	public ResponseBean<List<Order>> selectByJielongId(Integer jielongId) {
		// 根据接龙ID查询所有商品订单
		ResponseBean<List<Order>> responseBean = new ResponseBean<List<Order>>();
		List<OrderGroup> orderGroupList = orderGroupMapper.selectByJielongId(jielongId);
		// 转换输出格式
		List<Order> orderList = new ArrayList<Order>();
		if (orderGroupList != null && orderGroupList.size() > 0) {
			for (OrderGroup orderGroup : orderGroupList) {

				// 转换输出格式
				Order order = new Order();
				order.setId(orderGroup.getId());
				order.setIsSetGroup(1);
				order.setJielongId(orderGroup.getJielongId());
				// order.setJielongTopic(ordergroup);
				// order.setOrderGoods(ordergroup);
				order.setOrderNum(orderGroup.getOrderId());
				order.setRemark(orderGroup.getCustNote());
				order.setState(orderGroup.getTradeFlg());
				order.setSumMoney(orderGroup.getCustBuyAllMoney());
				// order.setUserAddress(userAddress);
				order.setUserId(orderGroup.getCustId());
				// order.setUserInfo(ordergroup.get);
				order.setUserName(orderGroup.getCustName());
				order.setUserPhone(orderGroup.getCustPhone());
				order.setCreatedAt(orderGroup.getCreatedAt());
				order.setUpdatedAt(orderGroup.getUpdatedAt());

				order.setAddressId(orderGroup.getAddressId());

				// Jielong主题
				String topic = jielongMapper.selectTopic(orderGroup.getJielongId());
				order.setJielongTopic(topic);
				// 提货地址信息
				Integer addressId = orderGroup.getAddressId();
				UserAddress address = userAddressService.selectById(addressId).getData();
				order.setUserAddress(address);
				// 用户信息
				Integer clientId = orderGroup.getCustId();
				UserInfo userInfo = userInfoService.selectByUserId(clientId).getData();
				order.setUserInfo(userInfo);

				// 订单商品信息
				List<OrderGroup> orderGroupList2 = orderGroupMapper.selectByOrderId(orderGroup.getOrderId());

				if (orderGroupList2 != null && orderGroupList2.size() > 0) {
					List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
					for (OrderGroup orderGroup2 : orderGroupList2) {

						// 对应统一接口 ORDER
						Goods goods = goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setGoods(goods);
						orderGoods.setGoodsId(orderGroup2.getGoodsId());
						// orderGoods.setId();
						orderGoods.setMoney(orderGroup2.getCustBuyPrice());
						// orderGoods.setOrderId(orderGroup.getOrderId());
						orderGoods.setSum(orderGroup2.getCustBuyNum());

						// 商品成功成团与否FLG
						Integer groupOkFlg = orderGroupConsoleMapper.selectGroupOkState(orderGroup2.getJielongId(),
								orderGroup2.getGoodsId());

						if (groupOkFlg == 1) {
							// 参团成功
							orderGoods.setGroupFlg(groupOkFlg);
							orderGoods.setJoinGroupNum(0);
						} else {
							orderGoods.setGroupFlg(groupOkFlg);
							// 参团不成功，差几人计算
							int setGroupNum = Integer.valueOf(goods.getGroupSum());

							int newGroupNum = orderGroupMapper.selectByCustBuyNum(orderGroup2.getJielongId(),
									orderGroup2.getGoodsId());

							int numtmp = setGroupNum - newGroupNum;
							orderGoods.setJoinGroupNum(numtmp);
						}

						orderGoodsList.add(orderGoods);
					}
					order.setOrderGoods(orderGoodsList);

				}
				orderList.add(order);

			}

		} // end if

		responseBean.setData(orderList);
		return responseBean;
	}

	@Override
	public ResponseBean<Integer> signPick(SignPickBean signPickBean) {
		ResponseBean<Integer> responseBean = new ResponseBean<Integer>();
		Integer result = 0;
		List<SignBean> signBeanList = signPickBean.getOrderNumList();
		if (signBeanList != null && signBeanList.size() > 0) {
			for (SignBean signBean : signBeanList) {
				result += orderGroupMapper.signPick(signBean);

			}

		}

		responseBean.setData(result);
		return responseBean;
	}

	@Transactional
	@Override
	public ResponseBean<List<Order>> selectPickByJielongId(Integer jielongId) {
		// 根据接龙ID查询所有商品订单
		ResponseBean<List<Order>> responseBean = new ResponseBean<List<Order>>();
		List<OrderGroup> orderGroupList = orderGroupMapper.selectPickByJielongId(jielongId);
		// 转换输出格式
		List<Order> orderList = new ArrayList<Order>();
		if (orderGroupList != null && orderGroupList.size() > 0) {
			for (OrderGroup orderGroup : orderGroupList) {

				// 转换输出格式
				Order order = new Order();
				order.setId(orderGroup.getId());
				order.setIsSetGroup(1);
				order.setJielongId(orderGroup.getJielongId());
				// order.setJielongTopic(ordergroup);
				// order.setOrderGoods(ordergroup);
				order.setOrderNum(orderGroup.getOrderId());
				order.setRemark(orderGroup.getCustNote());
				order.setState(orderGroup.getTradeFlg());
				order.setSumMoney(orderGroup.getCustBuyAllMoney());
				// order.setUserAddress(userAddress);
				order.setUserId(orderGroup.getCustId());
				// order.setUserInfo(ordergroup.get);
				order.setUserName(orderGroup.getCustName());
				order.setUserPhone(orderGroup.getCustPhone());
				order.setCreatedAt(orderGroup.getCreatedAt());
				order.setUpdatedAt(orderGroup.getUpdatedAt());

				order.setAddressId(orderGroup.getAddressId());

				// Jielong主题
				String topic = jielongMapper.selectTopic(orderGroup.getJielongId());
				order.setJielongTopic(topic);
				// 提货地址信息
				Integer addressId = orderGroup.getAddressId();
				UserAddress address = userAddressService.selectById(addressId).getData();
				order.setUserAddress(address);
				// 用户信息
				Integer clientId = orderGroup.getCustId();
				UserInfo userInfo = userInfoService.selectByUserId(clientId).getData();
				order.setUserInfo(userInfo);

				// 订单商品信息
				List<OrderGroup> orderGroupList2 = orderGroupMapper.selectByOrderId(orderGroup.getOrderId());

				if (orderGroupList2 != null && orderGroupList2.size() > 0) {
					List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
					for (OrderGroup orderGroup2 : orderGroupList2) {

						// 对应统一接口 ORDER
						Goods goods = goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setGoods(goods);
						orderGoods.setGoodsId(orderGroup2.getGoodsId());
						orderGoods.setMoney(orderGroup2.getCustBuyPrice());
						orderGoods.setSum(orderGroup2.getCustBuyNum());

						orderGoodsList.add(orderGoods);
					}
					order.setOrderGoods(orderGoodsList);

				}
				orderList.add(order);

			}

		} // end if

		responseBean.setData(orderList);
		return responseBean;
	}

	@Override
	public ResponseBean<List<PickCountBean>> countPick(Integer jielongId) {
		// 自提统计
		List<PickCountBean> pickCountBeanList = new ArrayList<PickCountBean>();
		// 1、首先根据jielongId查询所有商品
		List<Integer> goodsIdList = goodsMapper.selectIdsByJielongId(jielongId);
		for (Integer goodsid : goodsIdList) {

			PickCountBean pickCountBean = new PickCountBean();
			Goods goods = goodsMapper.selectByPrimaryKey(goodsid);
			pickCountBean.setGoods(goods);

			// 参与人数
			Integer joinPeopleSum = 0;
			// 已售数量
			Integer sellSum = 0;
			// 入账总额
			BigDecimal moneySum = new BigDecimal(0);
			// 2、用商品id去订单商品列表查询所有订单
			List<OrderGroup> orderGroupList = orderGroupMapper.selectByGoodsId(goodsid);

			if (orderGroupList != null && orderGroupList.size() > 0) {
				List<PickBean> pickBeans = new ArrayList<PickBean>();
				for (OrderGroup orderGroup : orderGroupList) {

					joinPeopleSum += 1;
					sellSum += orderGroup.getCustBuyNum();
					// BigDecimal totalMoney=orderGoods.getMoney().multiply(new
					// BigDecimal(orderGoods.getSum()));
					moneySum = moneySum.add(orderGroup.getCustBuyAllMoney());

					PickBean pickBean = new PickBean();
					pickBean.setCreatedAt(orderGroup.getCreatedAtStr());
					pickBean.setGoodsSum(orderGroup.getCustBuyNum());
					pickBean.setPrice(orderGroup.getCustBuyPrice());
					pickBean.setPhoneNumber(orderGroup.getCustPhone());
					pickBean.setUserName(orderGroup.getCustName());
					pickBean.setRemark(orderGroup.getCustNote());
					UserInfo userInfo = userInfoService.selectByUserId(orderGroup.getCustId()).getData();
					pickBean.setUserInfo(userInfo);

					Integer addressId = orderGroup.getAddressId();
					UserAddress address = userAddressService.selectById(addressId).getData();
					pickBean.setUserAddress(address);
					pickBeans.add(pickBean);
				}
				pickCountBean.setPickBeans(pickBeans);

			}

			pickCountBean.setJoinPeopleSum(joinPeopleSum);
			pickCountBean.setMoneySum(moneySum);
			pickCountBean.setSellSum(sellSum);
			pickCountBeanList.add(pickCountBean);
		}
		return new ResponseBean<>(pickCountBeanList);
	}

	@Override
	public ResponseBean<List<Order>> selectJoinByJielongId(Integer jielongId) {
		// 根据接龙ID查询所有商品订单
		ResponseBean<List<Order>> responseBean = new ResponseBean<List<Order>>();
		List<OrderGroup> orderGroupList = orderGroupMapper.selectFinishByJielongId(jielongId);
		// 转换输出格式
		List<Order> orderList = new ArrayList<Order>();
		if (orderGroupList != null && orderGroupList.size() > 0) {
			for (OrderGroup orderGroup : orderGroupList) {

				// 转换输出格式
				Order order = new Order();
				order.setId(orderGroup.getId());
				order.setIsSetGroup(1);
				order.setJielongId(orderGroup.getJielongId());
				order.setOrderNum(orderGroup.getOrderId());
				order.setRemark(orderGroup.getCustNote());
				order.setState(orderGroup.getTradeFlg());
				order.setSumMoney(orderGroup.getCustBuyAllMoney());
				order.setUserId(orderGroup.getCustId());
				order.setUserName(orderGroup.getCustName());
				order.setUserPhone(orderGroup.getCustPhone());
				order.setCreatedAt(orderGroup.getCreatedAt());
				order.setUpdatedAt(orderGroup.getUpdatedAt());

				order.setAddressId(orderGroup.getAddressId());

				// Jielong主题
				String topic = jielongMapper.selectTopic(orderGroup.getJielongId());
				order.setJielongTopic(topic);
				// 提货地址信息
				Integer addressId = orderGroup.getAddressId();
				UserAddress address = userAddressService.selectById(addressId).getData();
				order.setUserAddress(address);
				// 用户信息
				Integer clientId = orderGroup.getCustId();
				UserInfo userInfo = userInfoService.selectByUserId(clientId).getData();
				order.setUserInfo(userInfo);

				// 订单商品信息
				List<OrderGroup> orderGroupList2 = orderGroupMapper.selectByOrderId(orderGroup.getOrderId());

				if (orderGroupList2 != null && orderGroupList2.size() > 0) {
					List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
					for (OrderGroup orderGroup2 : orderGroupList2) {

						// 对应统一接口 ORDER
						Goods goods = goodsMapper.selectByPrimaryKey(orderGroup2.getGoodsId());
						OrderGoods orderGoods = new OrderGoods();
						orderGoods.setGoods(goods);
						orderGoods.setGoodsId(orderGroup2.getGoodsId());
						orderGoods.setMoney(orderGroup2.getCustBuyPrice());
						orderGoods.setSum(orderGroup2.getCustBuyNum());
						orderGoodsList.add(orderGoods);
					}
					order.setOrderGoods(orderGoodsList);

				}
				orderList.add(order);

			}

		} // end if

		responseBean.setData(orderList);
		return responseBean;
	}

	/**
	 * 取消参团
	 */
	@Transactional
	@Override
	public ResponseBean<Integer> cancelJoinGroup(Order order) {

		ResponseBean<Integer> responseBean = new ResponseBean<Integer>();

		Integer result = orderGroupMapper.updateStateById(0, 1, order.getId());
		// 查询该接龙的主题
		String topic = jielongMapper.selectTopic(order.getJielongId());
		// 减少参与人数和接龙金额
		jielongMapper.reduceJoin(order.getJielongId(), order.getSumMoney());

		List<OrderGoods> orderGoodsList = order.getOrderGoods();
		if (orderGoodsList != null && orderGoodsList.size() > 0) {
			for (int i = 0; i < orderGoodsList.size(); i++) {
				OrderGoods orderGoods = orderGoodsList.get(i);
				// 增加库存
				goodsMapper.addRepertory(orderGoods.getGoodsId(), orderGoods.getSum());

				Goods goods = new Goods();
				goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
				int setGroupNum = Integer.valueOf(goods.getGroupSum());
				int newGroupNum = Optional
						.ofNullable(orderGroupMapper.selectByCustBuyNum(order.getJielongId(), orderGoods.getGoodsId()))
						.orElse(0);
				// 取消订单后成团
				if (newGroupNum >= setGroupNum) {

					Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(),
							orderGoods.getGoodsId());
					// 之前就是成团的
					if (oldGroupOkFlg == 1) {
						// 发送单人通知 撤单后还是成功的团
						sendMessage("取消参团通知！", "您已成功取消参团，敬请下次惠顾，谢谢！", order.getUserId());
					} else {
						// 之前不成团，状态应该没有这种情况
						sendMessage("取消参团通知！", "您已成功取消参团，敬请下次惠顾，谢谢！", order.getUserId());
					}

				} else { // 取消订单后不成团

					// 查看ordergroupconsole表的GroupOkFlg状态如果已经是1了
					Integer oldGroupOkFlg = orderGroupConsoleMapper.selectGroupOkState(order.getJielongId(),
							orderGoods.getGoodsId());
					if (oldGroupOkFlg != null) {
						// 本来是成团的，取消订单后不成团
						if (oldGroupOkFlg == 1) {
							// 有撤单的情况!从成团变成了 未成团。群发通知
							int updateRet = orderGroupConsoleMapper.updateGroupOkFlg(0, order.getJielongId(),
									orderGoods.getGoodsId());

							// 给本人发送消息
							sendMessage("取消参团通知！", "您已成功取消参团，敬请下次惠顾，谢谢！", order.getUserId());

							// 给团里其他用户发送消息
							// userMessageService.groupStateModify(order.getJielongId(),
							// orderGoods.getGoodsId(), 0,goods.getName(),"");
							StringBuilder sb = new StringBuilder();
							sb.append("您预订的“").append(topic).append("”的“").append(goods.getName()).append("”")
									.append(",由于有人取消了参团，导致拼团人数暂不足，请等候拼团成功！订单详情请前往“我的”-“我参与的Mart”进行查看。");
							sendGroupMessage(order.getJielongId(), orderGoods.getGoodsId(), 0, sb.toString());

						} else { // 本来就不成团
							// 发送单人通知
							// 下单之后给用户发送消息
							sendMessage("取消参团通知！", "您已成功取消参团，敬请下次惠顾，谢谢！", order.getUserId());
						}
					}

				}

			}
		}

		responseBean.setData(result);
		return responseBean;
	}

	/**
	 * 发送消息
	 * 
	 * @param title
	 * @param message
	 * @param userId
	 */
	public void sendMessage(String title, String message, Integer userId) {
		UserMessage userMessage = new UserMessage();
		userMessage.setUserId(userId);
		userMessage.setTitle(title);
		userMessage.setMessage(message);
		userMessageService.insert(userMessage);
	}

	/**
	 * 群发消息
	 * 
	 * @param jieLongId
	 * @param goodsId
	 * @param flag
	 *            1:成团群发,2不成团群发
	 * @param Message
	 *            消息详情
	 */
	public void sendGroupMessage(Integer jieLongId, Integer goodsId, Integer flag, String Message) {

		List<Integer> listUserId = new ArrayList<Integer>();
		listUserId = orderGroupMapper.selectByUserId(jieLongId, goodsId);

		UserMessage userMessage = new UserMessage();

		if (listUserId != null && listUserId.size() > 0) {
			if (flag == 1) {
				// 成团发送
				userMessage.setTitle("下单成功通知！");

			} else {
				// 不成团发送
				userMessage.setTitle("参团状态改变通知！");
			}
			userMessage.setMessage(Message);
			userMessage.setUserIdList(listUserId);
			userMessageService.insertBatch(userMessage);

		}

	}

}
