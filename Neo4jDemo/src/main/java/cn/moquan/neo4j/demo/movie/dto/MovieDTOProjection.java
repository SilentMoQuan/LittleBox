package cn.moquan.neo4j.demo.movie.dto;

import lombok.Data;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 14:29 </b><br />
 */
@Data
public class MovieDTOProjection {

    String title;

    String released;

    String poster;

    Long castSize;

}
