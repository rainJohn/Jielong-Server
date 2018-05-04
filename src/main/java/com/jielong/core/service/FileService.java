package com.jielong.core.service;



import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public interface FileService {
	
	
	
	//导出订单
	XSSFWorkbook exportOrder(Integer jielongId);

}
