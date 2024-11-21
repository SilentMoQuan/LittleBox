package cn.moquan.neo4j.demo.movie.repository;

import cn.moquan.neo4j.demo.movie.domain.node.Movie;
import cn.moquan.neo4j.demo.movie.dto.MovieDTOProjection;
import cn.moquan.neo4j.demo.movie.dto.MovieProjection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 10:51 </b><br />
 */
public interface MovieRepository extends Neo4jRepository<Movie, String> {

    @Query(" " +
            "MATCH(m:Movie)<-[r:ACTED_IN]-(p:Person)" +
            "RETURN m, collect(r), collect(p)" +
            "LIMIT 20;" +
            " ")
    List<Movie> findMovieSub();

    @Query(" " +
            "MATCH(m:Movie)<-[r:ACTED_IN]-(p:Person {name: $name})" +
            "RETURN m, collect(r), collect(p)" +
            " ")
    List<Movie> findMoviesByPerson(@Param("name") String name);

    @Query(" " +
            "MATCH (m:Movie {movieId: $movieId})" +
            "SET m.imdbVotes = coalesce(m.imdbVotes + 1, 1)" +
            "RETURN m;" +
            " ")
    Movie incrementImdbVotes(@Param("movieId") String movieId);

    List<MovieProjection> findAllMovieProjectionsBy();

    @Query(" " +
            "MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) " +
            "RETURN m, COUNT(p) AS castSize " +
            "LIMIT 10;" +
            " ")
    List<MovieDTOProjection> findAllDTOProjectionsWithCustomQuery();

}
