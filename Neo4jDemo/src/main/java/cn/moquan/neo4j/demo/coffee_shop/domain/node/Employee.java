package cn.moquan.neo4j.demo.coffee_shop.domain.node;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDate;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:09 </b><br />
 */
@Data
@Node
public class Employee {

    @Id
    private String employeeId;

    private String employeeName;

    private LocalDate startDate;

}
