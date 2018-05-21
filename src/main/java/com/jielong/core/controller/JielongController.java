package com.jielong.core.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jielong.core.beans.JlConditionsBean;
import com.jielong.core.beans.PageBean;
import com.jielong.core.beans.ResponseBean;
import com.jielong.core.domain.Jielong;
import com.jielong.core.domain.Order;
import com.jielong.core.service.JielongService;
import com.jielong.core.service.OrderGroupService;
import com.jielong.core.service.OrderService;

@RestController
@RequestMapping("/jielong")
public class JielongController {

	@Autowired
	JielongService jielongService;
	@Autowired
	OrderGroupService orderGroupService;
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/insert")
	public ResponseBean<Integer> insert(@RequestBody Jielong jielong){
		return  jielongService.insert(jielong);
	}
	
	/**
	 * 分页查询可用接龙
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/selectByPage")
	public ResponseBean<List<Jielong>> selectByPage(@RequestBody PageBean pageBean){
		return jielongService.selectByPage(pageBean);
	}
	
	/**
	 * 分页查询所有接龙
	 * @param pageBean
	 * @return
	 */
	@RequestMapping("/selectAll")
	public ResponseBean<List<Jielong>> selectAll(@RequestBody PageBean pageBean){
		return jielongService.selectAll(pageBean);
	}
	
	/**
	 * 按条件查询接龙
	 * @param bean
	 * @return
	 */
	@RequestMapping("/selectByConditions")
	public ResponseBean<List<Jielong>> selectByConditions(@RequestBody JlConditionsBean bean){
		return jielongService.selectByConditions(bean);
		
	}
	
	
	/**
	 * 根据userId查询
	 *
	 */
	@RequestMapping("/selectByUserId")
	public ResponseBean<List<Jielong>> selectByUserId(@RequestParam("userId") Integer userId){
		return jielongService.selectByUserId(userId);
	}
    /**
     * 更新接龙
     * 
     */
	@RequestMapping("/update")
	public ResponseBean<Integer> update(@RequestBody Jielong jielong){
		return jielongService.update(jielong);
	}
	/**
	 * 结束接龙
	 */
	@RequestMapping("/closeJielong")
	public ResponseBean<Integer> closeJielong(@RequestParam("id") Integer id){
		 orderGroupService.closeJieLong(id);
         return jielongService.closeJielong(id);                   		
	}
	/**
	 * 删除接龙(假删除)
	 * @param id 接龙的id
	 * @return
	 */
	@RequestMapping("/deleteJielong")
	public ResponseBean<Integer> deleteJielong(@RequestParam("id") Integer id){
		return jielongService.deleteJielong(id);
	}
	
	/**
	 * 更新浏览人数
	 * @param jieLongId
	 * @return
	 */
	@RequestMapping("/updateBrowse")
	public ResponseBean<Integer> updateBrowse(@RequestParam("id") Integer jieLongId){
		return jielongService.updateBrowse(jieLongId);
	}
	
	/**
	 * 查询所有可用记录数
	 */
	@RequestMapping("/selectCount")
	public ResponseBean<Integer> selectCount(){
		return jielongService.selectCount();
	}
	
	/**
	 * 查询所有接龙记录数量
	 * @return
	 */
	@RequestMapping("/selectAllCount")
	public ResponseBean<Integer> selectAllCount(){
		return jielongService.selectAllCount();
	}
	
	/**
	 * 根据接龙id查询
	 */
	@RequestMapping("/selectById")
	public ResponseBean<Jielong> selectById(@RequestParam("id") Integer jieLongId){
		
		return jielongService.selectById(jieLongId);
		
	}
	
	/**
	 * 查询参与记录
	 */
	@RequestMapping("/selectJoin")
	public ResponseBean<List<Order>> selectJoin(@RequestParam("id") Integer jieLongId){
		     List<Order> orderList1=orderService.selectByJielongId(jieLongId).getData();
		     
		     List<Order> orderList2=orderGroupService.selectJoinByJielongId(jieLongId).getData();
		     List<Order> orderList=new ArrayList<Order>();
		     if (orderList1!=null) {
				orderList.addAll(orderList1.stream().filter(order->order.getState()==2||order.getState()==3).collect(Collectors.toList()));
			 }
		     if (orderList2!=null) {
		    	 orderList.addAll(orderList2);
			 }
		     
		     List<Order> joinList=orderList.stream().sorted(Comparator.comparing(Order::getCreatedAt).reversed()).collect(Collectors.toList());
		     
		     return new ResponseBean<List<Order>>(joinList);
	}
    	
}
