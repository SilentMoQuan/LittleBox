package cn.moquan.neo4j.demo.coffee_shop.domain.relationship;

import cn.moquan.neo4j.demo.coffee_shop.domain.node.Customer;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:28 </b><br />
 */
@Data
@RelationshipProperties
public class Receipt {

    @RelationshipId
    private Long id;

    private Double orderTotal;

    @TargetNode
    private Customer customer;


}
