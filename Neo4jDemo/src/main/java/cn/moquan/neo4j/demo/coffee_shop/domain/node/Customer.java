package cn.moquan.neo4j.demo.coffee_shop.domain.node;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:08 </b><br />
 */
@Data
@Node
public class Customer {

    @Id
    private String customerId;

    private String customerName;

    private String loyaltyId;

}
