package tacos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController 
{
	private final IngredientRepository ingredientRepository;
	private TacoRepository tacoRepository;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository,TacoRepository tacoRepository)
	{
		this.ingredientRepository=ingredientRepository;
		this.tacoRepository=tacoRepository;
	}
	@ModelAttribute(name="order")
	public Order order()
	{
		return new Order();
	}
	
	@ModelAttribute(name="taco")
	public Taco taco()
	{
		return new Taco();
	}
	@ModelAttribute
	public void addIngredientsToModel(Model model) 
	{
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));
		Type[] types = Ingredient.Type.values();
		for (Type type : types)
		{
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}
	
	@GetMapping
	public String showDesignForm(Model model) 
	{
	    return "designForm";
	}
	
	
	private List<Ingredient> filterByType(
		      List<Ingredient> ingredients, Type type) 
	{
		    return ingredients
		              .stream()
		              .filter(x -> x.getType().equals(type))
		              .collect(Collectors.toList());
	}
	
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, Order order) 
	{
	    if(errors.hasErrors()) {
	    	return "designForm";
	    }
	    tacoRepository.save(taco);
	    order.addTaco(taco);
	    log.info("Processing design: " + taco);
	    return "redirect:/orders/current";
	}
}

