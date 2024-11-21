package cn.moquan.neo4j.demo.coffee_shop.repository;

import cn.moquan.neo4j.demo.coffee_shop.domain.node.Order;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:17 </b><br />
 */
public interface OrderRepository {

    @Query("MATCH (o:Order)<-[rel]-(person) RETURN * LIMIT 10;")
    List<Order> findTenOrders();

}
