package tacos.converters;

import org.springframework.stereotype.Component;

import tacos.domain.Ingredient;
import tacos.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private IngredientRepository ingredientRepository;

  @Autowired
  public IngredientByIdConverter(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }	
  //Convert from String Ingredient to type "Ingredient"
  @Override
  public Ingredient convert(String ingredientID) 
  {
	//System.out.println(Ingredient);
	//String id=Ingredient.substring(Ingredient.indexOf("=")+1,Ingredient.indexOf(",")); 
	// Only this is required had the form check boxes had th:value="${ingredient}"
	//Validation stopped working when I used th:value="${ingredient}" IMPORTANT. Had to change it
		System.out.println("conversion happening ******************************");
    return ingredientRepository.findById(ingredientID);
  }

}