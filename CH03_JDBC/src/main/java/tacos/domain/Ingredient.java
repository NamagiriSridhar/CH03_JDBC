package tacos.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
/*The use of @RequiredArgsConstructor is not strictly necessary here, 
 * because @Data implies the generation of a required arguments constructor. 
 * (That said, using @RequiredArgsConstructor here does no harm.)
*/
public class Ingredient {
  
  private final String id;
  private final String name;
  private final Type type;
  
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}