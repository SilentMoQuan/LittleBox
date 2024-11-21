package cn.moquan.neo4j.demo.movie.domain.relationship;

import cn.moquan.neo4j.demo.movie.domain.node.Person;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 11:43 </b><br />
 */
@Data
@RelationshipProperties
public class Role {

    @RelationshipId
    private Long id;

    private String role;

    @TargetNode
    private Person person;

}
