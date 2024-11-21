package cn.moquan.neo4j.demo.movie.domain.node;

import cn.moquan.neo4j.demo.movie.domain.relationship.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:40 </b><br />
 */
@Data
@Node
public class Movie {

    @Id
    private String movieId;

    private String title;

    private String plot;

    private String poster;

    private String url;

    private String imdbId;

    private String tmdbId;

    private String released;

    private Long year;

    private Long runtime;

    private Long budget;

    private Long revenue;

    private Long imdbVotes;

    private Double imdbRating;

    private String[] languages;

    private String[] countries;

    @Relationship(value = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<Role> actors;

}
