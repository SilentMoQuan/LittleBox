package cn.moquan.neo4j.demo.movie.dto;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 14:09 </b><br />
 */
public interface MovieProjection {

    String getTitle();

    String getReleased();

    String getPoster();

}
