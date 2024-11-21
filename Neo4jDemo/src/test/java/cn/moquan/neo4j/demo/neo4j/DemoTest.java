package cn.moquan.neo4j.demo.neo4j;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/20 15:58 </b><br />
 */
@SpringBootTest
class DemoTest {

    @Resource
    private Driver driver;

    @Test
    void connectTest() {
        driver.verifyConnectivity();
        assertTrue(true);
    }


}