package cn.plou.web.heatManage.monitor.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.plou.web.common.utils.PageUtils;
import cn.plou.web.common.utils.Query;
import cn.plou.web.common.utils.R;
import cn.plou.web.heatManage.monitor.domain.SystemMarkersDO;
import cn.plou.web.heatManage.monitor.service.SystemMarkersService;

/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-07-06 09:59:43
 */
 
@Controller
@RequestMapping("${heatManagePath}/systemMarkers")
public class SystemMarkersController {
	@Autowired
	private SystemMarkersService systemMarkersService;
	
	@GetMapping()
	@RequiresPermissions("web:systemMarkers:systemMarkers")
	String SystemMarkers(){
	    return "web/systemMarkers/systemMarkers";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("web:systemMarkers:systemMarkers")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SystemMarkersDO> systemMarkersList = systemMarkersService.list(query);
		int total = systemMarkersService.count(query);
		PageUtils pageUtils = new PageUtils(systemMarkersList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("web:systemMarkers:add")
	String add(){
	    return "web/systemMarkers/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("web:systemMarkers:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		SystemMarkersDO systemMarkers = systemMarkersService.get(id);
		model.addAttribute("systemMarkers", systemMarkers);
	    return "web/systemMarkers/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("web:systemMarkers:add")
	public R save( SystemMarkersDO systemMarkers){
		if(systemMarkersService.save(systemMarkers)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("web:systemMarkers:edit")
	public R update( SystemMarkersDO systemMarkers){
		systemMarkersService.update(systemMarkers);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("web:systemMarkers:remove")
	public R remove( Integer id){
		if(systemMarkersService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("web:systemMarkers:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		systemMarkersService.batchRemove(ids);
		return R.ok();
	}
	
}
