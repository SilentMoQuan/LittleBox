package cn.moquan.neo4j.demo.coffee_shop.domain.node;

import cn.moquan.neo4j.demo.coffee_shop.domain.relationship.Receipt;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:07 </b><br />
 */
@Data
@Node
public class Order {

    @Id
    private String transactionId;
    private String orderNumber;
    private LocalDate orderDate;
    private LocalTime orderTime;

    @Relationship(value = "BOUGHT", direction = Relationship.Direction.INCOMING)
    private Customer customer;

    @Relationship(value = "BOUGHT", direction = Relationship.Direction.INCOMING)
    private Receipt customerReceipt;

    @Relationship(value = "SOLD", direction = Relationship.Direction.INCOMING)
    private Employee employee;

}
