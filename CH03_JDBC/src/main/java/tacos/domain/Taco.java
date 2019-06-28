package tacos.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco 
{
	private Long id;
	
	private Date createdAt;
	@NotNull
	@Size(min=5, message="You must have a long taco name")
	private String name;
	
	
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List <Ingredient> ingredients;
}
