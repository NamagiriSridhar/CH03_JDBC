package tacos.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import tacos.domain.Order;
import tacos.domain.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository
{
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;
	
	@Autowired
	  public JdbcOrderRepository(JdbcTemplate jdbc) {
	    this.orderInserter = new SimpleJdbcInsert(jdbc)
	        .withTableName("Taco_Order")
	        .usingGeneratedKeyColumns("id");

	    this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
	        .withTableName("Taco_Order_Tacos");

	    this.objectMapper = new ObjectMapper();
	  }
	
	 @Override
	  public Order save(Order order) {
	    order.setPlacedAt(new Date());
	    long orderId = saveOrderDetails(order);
	    order.setId(orderId);
	    List<Taco> tacos = order.getTacos();
	    System.out.println("Tacos that are part of one order "+tacos);
	    for (Taco taco : tacos) {
	      saveTacoToOrder(taco, orderId);
	    }

	    return order;
	  }

	  private long saveOrderDetails(Order order) {
	    @SuppressWarnings("unchecked")
	    Map<String, Object> values =
	        objectMapper.convertValue(order, Map.class);
	    values.put("placedAt", order.getPlacedAt());

	    System.out.println(values);
	    long orderId =
	        orderInserter
	            .executeAndReturnKey(values)
	            .longValue();
	    System.out.println(values);
	    return orderId;
	  }

	  private void saveTacoToOrder(Taco taco, long orderId) {
	    Map<String, Object> values = new HashMap<>();
	    values.put("tacoOrder", orderId);
	    values.put("taco", taco.getId());
	    orderTacoInserter.execute(values);
	  }
	
}
