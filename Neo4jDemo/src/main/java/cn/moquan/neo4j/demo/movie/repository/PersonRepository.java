package cn.moquan.neo4j.demo.movie.repository;

import cn.moquan.neo4j.demo.movie.domain.node.Movie;
import cn.moquan.neo4j.demo.movie.domain.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:51 </b><br />
 */
public interface PersonRepository extends Neo4jRepository<Person, String> {



}
