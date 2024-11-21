package cn.moquan.neo4j.demo.movie.domain.node;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDate;

/**
 * {
 * born:"DATE",
 * bornIn:"STRING",
 * tmdbId:"STRING",
 * bio:"STRING",
 * died:"DATE",
 * name:"STRING",
 * poster:"STRING",
 * imdbId:"STRING",
 * url:"STRING"
 * }
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:43 </b><br />
 */
@Data
@Node
public class Person {

    @Id
    private String tmdbId;

    private String bornIn;

    private LocalDate born;

    private String bio;

    private LocalDate died;

    private String name;

    private String poster;

    private String imdbId;

    private String url;

}
