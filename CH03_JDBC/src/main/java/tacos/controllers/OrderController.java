package tacos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Order;
import tacos.repository.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepository;
	
	@Autowired
	public OrderController(OrderRepository orderRepository)
	{
		this.orderRepository = orderRepository;
	}
	
	@ModelAttribute(name="order")
	public Order order()
	{
		return new Order();
	}

	@GetMapping("/current")
	public String orderForm()
	{
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors,SessionStatus sessionStatus)
	{
	    if(errors.hasErrors()) 
	    {
	    	return "orderForm";
	    }
	    orderRepository.save(order);
	    sessionStatus.setComplete();
		log.info("Order submitted: " + order);
		return "redirect:/";
	}
}
