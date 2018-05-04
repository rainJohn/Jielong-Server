package com.jielong.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jielong.core.domain.Goods;
import com.jielong.core.domain.Order;
import com.jielong.core.domain.OrderGoods;
import com.jielong.core.domain.UserInfo;
import com.jielong.core.service.FileService;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.OrderService;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderGroupService orderGroupService;
	



	public Map<Integer, List<Order>> countOrder(Integer jielongId) {
		List<Order> orderList = new ArrayList<Order>();
		
		List<Order> orderList1 = orderService.selectByJielongId(jielongId).getData();
		List<Order> orderList2 = orderGroupService.selectPickByJielongId(jielongId).getData();		
		
		if (orderList1 != null) {
			orderList.addAll(orderList1.stream().filter(order -> order.getState() == 2).collect(Collectors.toList()));
		}
		if (orderList2 != null) {
			orderList.addAll(orderList2.stream().filter(order->order.getState()==2).collect(Collectors.toList()));
		}
		
		Map<Integer, List<Order>> orderByState=orderList.stream().collect(Collectors.groupingBy(Order::getAddressId));
	    
		
		
		return orderByState;
	}
	
	//导出订单
	@Transactional
	@Override
	public XSSFWorkbook exportOrder(Integer jielongId) {
		  XSSFWorkbook wb = new XSSFWorkbook();
	      XSSFSheet sheet=wb.createSheet("订单详情表");    //表名
	      
	      //表格标题style
	      XSSFCellStyle titleStyle = wb.createCellStyle();
	      XSSFFont font = wb.createFont(); 
	      font.setBold(true);//粗体显示    
	      font.setFontHeightInPoints((short) 10);//设置字体大小
	      titleStyle.setFont(font);
	      titleStyle.setAlignment(HorizontalAlignment.CENTER);  //字体居中
	      
	      XSSFCellStyle headStyle = wb.createCellStyle();
	      XSSFFont headFont = wb.createFont(); 
	      headFont.setBold(true);//粗体显示    
	      headFont.setFontHeightInPoints((short)8);//设置字体大小
	      headStyle.setFont(headFont);
	      headStyle.setAlignment(HorizontalAlignment.CENTER);  //字体居中
		
		int index=0;
		
		Map<Integer, List<Order>> orderMap=this.countOrder(jielongId);		
		if (orderMap!=null) {
			for(Map.Entry<Integer, List<Order>> entry : orderMap.entrySet() ) {
				Row titleRow= sheet.createRow(index);  	
				titleRow.setHeightInPoints(30);    //设置行的高度 
				Cell titleCell = titleRow.createCell(0);
				CellRangeAddress cra=new CellRangeAddress(index, index, 0, 9);   //合并第一行所有的列
			    //在sheet里增加合并单元格  
			    sheet.addMergedRegion(cra);    //第一行: 标题	    
			    titleCell.setCellValue(entry.getValue().get(0).getUserAddress().getDetail());    //第一行标题  "2017年3月工资明细表"	     
			    titleCell.setCellStyle(titleStyle);
			    
			    //表头
			    Row headRow=sheet.createRow(index+1);
			   // headRow.setRowStyle(headStyle);
			    
			    Cell headCell1=headRow.createCell(0);
			    headCell1.setCellValue("序号");
			    headCell1.setCellStyle(headStyle);
			    
			    Cell headCell2=headRow.createCell(1);
			    headCell2.setCellValue("昵称");
			    headCell2.setCellStyle(headStyle);
			    
			    Cell headCell3=headRow.createCell(2);
			    CellRangeAddress cra1=new CellRangeAddress(index+1, index+1, 2, 10);   
			    //在sheet里增加合并单元格  
			    sheet.addMergedRegion(cra1); 
			    headCell3.setCellStyle(headStyle);
			    headCell3.setCellValue("订单详情");
			    
			    //Map<商品Id,购买数量>
			    Map<Integer, Integer> countMap=new HashMap<Integer,Integer>();
			    
			    //Map<商品Id，商品名称>
			    Map<Integer, String> goodsMap=new HashMap<Integer,String>();
			    
			    //表格内容
			    List<Order> orderList=entry.getValue();
			    for(int i=0;i<orderList.size();i++) {
			    	Order order=orderList.get(i);
			    	Row row=sheet.createRow(index+2+i);
			    	Cell numberCell=row.createCell(0);
			    	//序号
			    	numberCell.setCellValue(i+1);
			    	//昵称（用户姓名）
			    	UserInfo userInfo=order.getUserInfo();
			    	Cell nameCell=row.createCell(1);
			    	nameCell.setCellValue(userInfo.getNickName());
			    	
			    	//订单商品信息
			    	List<OrderGoods> goodsList=order.getOrderGoods();
			    	int goodsIndex=1;
			    	for(int j=0;j<goodsList.size();j++) {
			    		OrderGoods orderGoods=goodsList.get(j);
			    		
			    		if (!countMap.containsKey(orderGoods.getGoodsId())) {
			    			countMap.put(orderGoods.getGoodsId(), orderGoods.getSum());
						}else {
							countMap.put(orderGoods.getGoodsId(), countMap.get(orderGoods.getGoodsId())+orderGoods.getSum());
						}
			    		
			    		
			    		Goods goods=orderGoods.getGoods();
			    		
			    		if (!goodsMap.containsKey(goods.getId())) {
			    			goodsMap.put(goods.getId(),goods.getName() );
						}
			    		
			    		//商品名称
			    		Cell goodsCell=row.createCell(goodsIndex+1);
			    		goodsCell.setCellValue(goods.getName());
			    		//商品数量
			    		Cell numCell=row.createCell(goodsIndex+2);
			    		numCell.setCellValue(orderGoods.getSum());
			    		
			    	    goodsIndex+=goodsIndex+2;	
			    	}
			    	
			    	
			    }   //end 订单详情
			    
			    index+=index+2+orderList.size()+1;
			    
			    //合计列合并
			    CellRangeAddress cra2=new CellRangeAddress(index, index+countMap.size(), 0, 0);   
			    //在sheet里增加合并单元格  
			    sheet.addMergedRegion(cra2);    //第一行: 标题	    
			    
			    
			    //订单合计
			    Row countRow=sheet.createRow(index);
			    Cell countCell=countRow.createCell(0);
			    countCell.setCellValue("合计");
			    
			    
			    Cell countCell1=countRow.createCell(1);
			    countCell1.setCellValue("订单");
			    
			    Cell countCell2=countRow.createCell(2);
			    countCell2.setCellValue(orderList.size());
			    
			    index+=1;
			    for(Map.Entry<Integer, Integer> entry1 : countMap.entrySet()) {
			    	 Row goodsRow=sheet.createRow(index);
			    	 Cell nameCell=goodsRow.createCell(1);
			    	 nameCell.setCellValue(goodsMap.get(entry1.getKey()));
			    	 Cell sumCell=goodsRow.createCell(2);
			    	 sumCell.setCellValue(entry1.getValue());
			    	 
			    	 index+=1;
			    } 	    
			    
			    
			    
			}
			
		}
		return wb;
	}
	
	

}
