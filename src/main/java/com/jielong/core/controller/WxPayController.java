package com.jielong.core.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jielong.base.util.ErrorCode;
import com.jielong.base.util.Utils;
import com.jielong.base.util.WxPayUtils;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.dao.OrderGroupMapper;
import com.jielong.core.dao.OrderMapper;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.OrderService;
import com.jielong.core.service.impl.WxPayServiceImpl;

@RestController
@RequestMapping("/wxpay")
public class WxPayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);

	@Autowired
	WxPayServiceImpl wxPayService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGroupService orderGroupService;

	@Autowired
	OrderMapper orderMapper;

	@Autowired
	OrderGroupMapper orderGroupMapper;

	// 异步接收通知
	@RequestMapping("/notify")
	public synchronized void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {		
	

		PrintWriter out = null;
		LOGGER.info("微信支付异步接收通知");
		StringBuilder sb = new StringBuilder();
		try {
			out = response.getWriter();
			BufferedReader reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			LOGGER.info("异步通知消息：" + sb.toString());
			
			wxPayService.handleNotify(sb.toString(), out);
		} catch (Exception e) {			
			e.printStackTrace();
			//返回失败标志
			Map<String, String> failMap = new HashMap<>();
			failMap.put("return_code", "FAIL");
			failMap.put("return_msg", "更新订单失败");
			out.write(WxPayUtils.mapToXml(failMap));
			return;
			
		} finally {
			out.close();
		}
	}

	@RequestMapping("/pay")
	public ResponseBean<Map<String, String>> pay() {
		// String ip=request.getRemoteAddr();
		String ip = "47.88.54.113";
		LOGGER.info("终端ip地址：" + ip);

		String orderNum = Utils.createFileName();
		String openId = "oQ_fh5FbPEail4QKnFnVYgDOSc14";

		ResponseBean<Map<String, String>> responseBean = new ResponseBean<>();
		// 支付1分钱
		Map<String, String> map = wxPayService.wxPay(openId, null, orderNum, 1, 0);
		if (null != map) {
			responseBean.setData(map);
		} else {
			responseBean.setErrorCode(ErrorCode.PAY_EXCEPTION);
			responseBean.setErrorMessage("支付发生错误");
		}

		return responseBean;

	}

}
