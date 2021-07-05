package com.otj.salesorderservice.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.otj.salesorderservice.model.Item;

@FeignClient(name="item-service")
@RibbonClient("item-service")
public interface ItemServiceProxy {
	
	@GetMapping("service2/items/{itemname}")
	public Item getItems(@PathVariable("itemname") String itemname);


}
