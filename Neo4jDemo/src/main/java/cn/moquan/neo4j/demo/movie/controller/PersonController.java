package cn.moquan.neo4j.demo.movie.controller;

import cn.moquan.neo4j.demo.movie.domain.node.Movie;
import cn.moquan.neo4j.demo.movie.domain.node.Person;
import cn.moquan.neo4j.demo.movie.repository.MovieRepository;
import cn.moquan.neo4j.demo.movie.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/21 11:19 </b><br />
 */
@RestController
@RequestMapping("/demo/person")
public class PersonController {

    @Resource
    private PersonRepository personRepository;

    @GetMapping("/persons")
    public List<Person> findPersons () {
        return personRepository.findAll();
    }

    @GetMapping("/{personId}")
    public Optional<Person> findPersonById(@PathVariable String personId) {
        return personRepository.findById(personId);
    }

}
