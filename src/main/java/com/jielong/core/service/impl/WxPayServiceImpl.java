package com.jielong.core.service.impl;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jielong.base.util.Constants;
import com.jielong.base.util.NetworkConnection;
import com.jielong.base.util.WxPayUtils;
import com.jielong.base.util.Constants.SignType;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGroup;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.OrderService;

@Service
public class WxPayServiceImpl {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGroupService orderGroupService;
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderGroupMapper orderGroupMapper;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(WxPayServiceImpl.class);
	
    /**
     * 
     * @param openId    微信登录用户openId
     * @param goodsDetail 商品介绍
     * @param orderNum 订单编号
     * @param totalFee 订单总金额   单位分
     * @param orderType  订单类型 0:直接下单 1:团购
     * @return
     */
	public Map<String, String> wxPay(String openId,String goodsDetail, String orderNum, int totalFee,int orderType) {
		Map<String, String> resMap=paySignature(openId,goodsDetail, orderNum, totalFee,orderType);
		if (resMap!=null) {
			resMap.forEach((k,v)->System.out.println(k+"————>"+v));
			return resMap;
			
		}
		
		return null;
		
	}
	
	/**
	 * 1、第一步获取预支付签名
	 * 
	 * @param goodsDetail
	 *            商品详情 按格式 商家名称-商品类目
	 * @param orderNum
	 *            订单编号，需唯一
	 * @param totalFee
	 *            总金额，单位分
	 * @return prepayId 预支付会话标识
	 */
	private Map<String, String> paySignature(String openId,String goodsDetail, String orderNum, int totalFee,int orderType) {
		Map<String, String> resMap=null;
		String ip=Constants.SERVER_IP;
		// 32位随机字符串
		LOGGER.info("订单编号："+orderNum);
		String randomStr=WxPayUtils.generateNonceStr();
        LOGGER.info("随机串："+randomStr);
		Map<String, String> params = new HashMap<>();
		params.put("openid", openId);
		params.put("appid", Constants.APPID);
		params.put("mch_id", Constants.MCH_ID);
		// 不长于32位的随机数
		params.put("nonce_str", randomStr);
		// 订单信息
		if (StringUtils.isEmpty(goodsDetail)) {
			goodsDetail="VanMart-景点门票";
			params.put("body", goodsDetail);
		} else {
			params.put("body", goodsDetail);
		}
		//自定义参数,这里设置为订单类型
		params.put("attach",orderType+"");
		
		// 订单编号：自定义,要唯一
		params.put("out_trade_no", orderNum);
		// 总额：单位分
		params.put("total_fee", totalFee+"");
		// 服务器IP地址
		params.put("spbill_create_ip", ip);
		// 异步通知url
		params.put("notify_url", Constants.NOTIFY_URL);
		params.put("trade_type", "JSAPI");

		try {
			LOGGER.info("最终签名数据：");
			LOGGER.info(params.toString());
			String signature = WxPayUtils.generateSignature(params, Constants.PAY_KEY, SignType.MD5);
			LOGGER.info("预支付签名："+signature);
			//调用与支付接口返回prepayId
			resMap=unifiedorder(signature, goodsDetail, orderNum, totalFee, randomStr,openId,orderType);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return resMap;
	}

	
	/**
	 * 2、调用预支付接口
	 * @param signature
	 * @param goodsDetail
	 * @param orderNum
	 * @param totalFee
	 * @param randomStr
	 * @return
	 */
	private Map<String, String> unifiedorder(String signature, String goodsDetail, String orderNum, int totalFee, String randomStr,String openId,int orderType) {
		Map<String, String> resMap=null;
		
		if (signature != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("<xml>");
			sb.append("<appid>").append(Constants.APPID).append("</appid>");
			sb.append("<mch_id>").append(Constants.MCH_ID).append("</mch_id>");
			sb.append("<nonce_str>").append(randomStr).append("</nonce_str>");
			sb.append("<sign>").append(signature).append("</sign>");
			sb.append("<body>").append(goodsDetail).append("</body>");
			// 可设置商品详情，暂时不设
			// sb.append("<detail>").append("商品详情").append("</detail>");
			// 自定义参数，会原样返回
			sb.append("<attach>").append(orderType).append("</attach>");
			sb.append("<out_trade_no>").append(orderNum).append("</out_trade_no>");
			sb.append("<total_fee>").append(totalFee).append("</total_fee>");
			sb.append("<spbill_create_ip>").append(Constants.SERVER_IP).append("</spbill_create_ip>");
			sb.append("<notify_url>").append(Constants.NOTIFY_URL).append("</notify_url>");
			sb.append("<trade_type>").append("JSAPI").append("</trade_type>");
			sb.append("<openid>").append(openId).append("</openid>");
			sb.append("</xml>");
			
			LOGGER.info("最终提交数据：");
			LOGGER.info(sb.toString());

			try {
				String result = NetworkConnection.post(Constants.UNIFIEDORDER_URL, sb.toString());
				if (StringUtils.isNotEmpty(result)) {
					LOGGER.info("预支付接口返回内容"+result);
					//TODO:解析xml
					String prepayId=parseXMl(result);
                    //再次签名
					resMap=paySign(prepayId);
				}
                
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			

		}
		return resMap;		

	}
	
	/**
	 * 3、再次签名
	 * @param prepayId  统一下单接口返回的 prepay_id(上一步) 
	 * @return
	 */
	private Map<String, String> paySign(String prepayId) {
       Map<String,String> map=new HashMap<>();
       //当前时间戳
       String timeStamp=System.currentTimeMillis()+"";
       //随机串
       String nonceStr=WxPayUtils.generateNonceStr();
       //package
       String dataPackage="prepay_id="+prepayId;     
       
       //签名字段共5个
       map.put("appId", Constants.APPID);
       map.put("timeStamp", timeStamp);
       map.put("nonceStr", nonceStr);
       map.put("package", dataPackage);
       map.put("signType", "MD5");
       //签名
       try {
		String signature = WxPayUtils.generateSignature(map, Constants.PAY_KEY, SignType.MD5);
		LOGGER.info("获取到的再次签名："+signature);
		
		Map<String, String> resMap=new HashMap<>();
		resMap.put("timeStamp", timeStamp);
		resMap.put("nonceStr", nonceStr);
		resMap.put("package", dataPackage);
		resMap.put("signType", "MD5");
		resMap.put("paySign", signature);
		
		return resMap;
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		return null;
	}
       
	}
	
	private String parseXMl(String xml) {
		String prepayId=null;
		Document doc=null;
		try {
			doc=DocumentHelper.parseText(xml);
			Element rootEle=doc.getRootElement();
			System.out.println("根节点名称："+rootEle.getName());
			
			String returnCode=rootEle.elementTextTrim("return_code");
			System.out.println("错误码："+returnCode);
			if (returnCode.equals("SUCCESS")) {
			    //获取业务结果
				String resultCode=rootEle.elementTextTrim("result_code");
				if (resultCode.equals("SUCCESS")) {
	               //获取预支付交易会话标识	
				   prepayId=rootEle.elementTextTrim("prepay_id");	
				   LOGGER.info("获取预支付交易会话标识成功:"+prepayId);
					
				}else {
					//获取错误代码和错误描述
					StringBuilder sb=new StringBuilder("错误码:");
					sb.append(rootEle.elementTextTrim("err_code"));
					sb.append(",错误描述:").append(rootEle.elementTextTrim("err_code_des"));
					LOGGER.error("预支付接口业务结果返回错误:"+sb.toString());
					
				}
				
			}else {
			   //获取错误信息	
		       String errorMsg=rootEle.elementTextTrim("return_msg");		
		       LOGGER.error("预支付接口返回错误："+errorMsg);	
			}
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prepayId;
	}
	
	public void handleNotify(String xml,PrintWriter out) throws Exception {
		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("return_code", "SUCCESS");
		responseMap.put("return_msg", "OK");
		
		Map<String, String> failMap = new HashMap<>();
		failMap.put("return_code", "FAIL");
		
		
		Map<String, String> resultMap = WxPayUtils.xmlToMap(xml);
		String returnCode = resultMap.get("return_code");
		if (returnCode.equals("SUCCESS")) {
			String resultCode = resultMap.get("result_code");
			if (resultCode.equals("SUCCESS")) {
				// 处理业务逻辑

				// 获取订单编号
				String orderNum = resultMap.get("out_trade_no");
				// 获取订单类型 0:直接下单 1:团购
				String orderType = resultMap.get("attach");
				// 订单总金额
				int totalFee = Integer.valueOf(resultMap.get("total_fee"));
				//1、 直接下单
				if (orderType.equals("0")) {
					// 根据订单编号查询订单
					Order order = orderMapper.selectByOrderNum(orderNum);
					if (order.getState() == 2) {
						// 支付已经完成，直接返回成功标志
						out.write(WxPayUtils.mapToXml(responseMap));
						return;
                        
					} else {
						//验证签名
						boolean isSignature=WxPayUtils.isSignatureValid(xml, Constants.PAY_KEY);
						if (!isSignature) {
							//签名失败
							failMap.put("return_msg", "验证签名失败");
							LOGGER.warn("微信支付通知："+"验证签名失败");
							out.write(WxPayUtils.mapToXml(failMap));
							return;
							
						}
						//验证订单总金额
						int orderFee=(order.getSumMoney().multiply(new BigDecimal(100))).intValue();
						if (totalFee!=orderFee) {
							//总金额不对
							failMap.put("return_msg", "订单总金额不对");
							out.write(WxPayUtils.mapToXml(failMap));
							return;
						}
						
						// 处理业务逻辑
						orderService.updateOrder(order);
						out.write(WxPayUtils.mapToXml(responseMap));
						return;
					}
				}
				//2、 团购
				if (orderType.equals("1")) {
					OrderGroup orderGroup = orderGroupMapper.selectByOrderId(orderNum).get(0);
					if (orderGroup.getTradeFlg() == 1) {
						
						// 支付已经完成，直接返回成功标志
						out.write(WxPayUtils.mapToXml(responseMap));
						return;
					} else {
						//验证签名和订单总金额
						boolean isSignature=WxPayUtils.isSignatureValid(xml, Constants.PAY_KEY);
						if (!isSignature) {
							//签名失败
							failMap.put("return_msg", "签名失败");
							LOGGER.warn("微信支付通知："+"验证签名失败");
							out.write(WxPayUtils.mapToXml(failMap));
							return;
							
						}
						//验证订单总金额
						int orderFee=(orderGroup.getCustBuyAllMoney().multiply(new BigDecimal(100))).intValue();
						if (totalFee!=orderFee) {
							//总金额不对
							failMap.put("return_msg", "订单总金额不对");
							LOGGER.warn("微信支付通知："+"订单总金额不对");
							out.write(WxPayUtils.mapToXml(failMap));
							return;
						}
						
						// 处理业务逻辑
						orderGroupService.updateOrder(orderGroup);
						out.write(WxPayUtils.mapToXml(responseMap));
						return;
						
						
					}

				}

			} else {

				LOGGER.error("errorCode:" + resultMap.get("err_code") + " errorMessage:" + resultMap.get("err_code_des"));

			}

		} else {
			LOGGER.error("返回错误：" + resultMap.get("return_msg"));
		}

		
	}
	
	
	
	

}
