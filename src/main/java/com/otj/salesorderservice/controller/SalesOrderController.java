package com.otj.salesorderservice.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents.UriTemplateVariables;

import com.otj.salesorderservice.model.Customer;
import com.otj.salesorderservice.model.CustomerSOS;
import com.otj.salesorderservice.model.Item;
import com.otj.salesorderservice.model.OrderLineItem;
import com.otj.salesorderservice.model.SalesOrder;
import com.otj.salesorderservice.repository.CustomerSOSRepo;
import com.otj.salesorderservice.repository.OrderLineItemRepo;
import com.otj.salesorderservice.repository.SalesOrderRepo;
import com.otj.salesorderservice.service.ItemServiceProxy;

@RestController
public class SalesOrderController {
	
	@Autowired
	private CustomerSOSRepo customersosrepo;
	
	@Autowired
	private SalesOrderRepo salesOrderRepo;
	
	@Autowired
	private ItemServiceProxy itemServiceProxy;

	@PostMapping("service3/orders")
	public ResponseEntity<Object> createOrder(@RequestBody SalesOrder salesOrder) {
		
		  long total_price=0;
		  
		  
			
		  Optional<CustomerSOS> customer = customersosrepo.findById(salesOrder.getCust_id());
			//verify customer
		  if(customer.isPresent()) {
			List<OrderLineItem> item=salesOrder.getOrderLineItem();
			
			for(int i=0;i<item.size();i++) {
				System.out.println(i);
				//Item listitem = new RestTemplate().getForObject("http://localhost:8001/service2/items/{itemname}", Item.class,item.get(i).getItem_name());
				Item listitem = itemServiceProxy.getItems(item.get(i).getItem_name());
				//Item listitem = itemServiceProxy.getItems("Apple");
				
				if(!listitem.getName().equals("dummyItem")){
					System.out.println(listitem.toString());
				total_price += item.get(i).getItem_quantity() * listitem.getPrice();		
				}else {
					
					System.out.println("Item not present");
					salesOrder.getOrderLineItem().remove(i);
				}
		    }
			salesOrder.setTotal_price(total_price);
			SalesOrder savedOrder=salesOrderRepo.save(salesOrder);
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedOrder.getId()).toUri();
		
		return ResponseEntity.created(location).build();
			
		 }else
		 {
			
			 System.out.println("Customer Not present");
		 }
		return null;
		
	}
	
	@PostMapping("service3/customer")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer ) {
		
		CustomerSOS cust = new CustomerSOS(customer.getId(), customer.getFirst_name(), customer.getLast_name(), customer.getEmail());
		CustomerSOS savedCustomer =customersosrepo.save(cust);
		
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedCustomer.getCust_id()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
