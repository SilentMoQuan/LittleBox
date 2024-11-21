package cn.moquan.tools.neo4j;

import org.neo4j.driver.*;

import java.util.Objects;

import static org.neo4j.driver.Values.parameters;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/11/18 11:36 </b><br />
 */
public class Neo4jDemo2 {

    public static void main(String[] args) {
        Neo4jDemo2 demo2 = new Neo4jDemo2();
        Driver driver = demo2.getDriver();
        demo2.printGreeting(driver, "hello world");
        demo2.closeDriver(driver);
    }

    private void printGreeting(Driver driver, String message) {
        if (Objects.isNull(driver)) {
            System.out.println("driver is null.");
            return;
        }

        if (Objects.isNull(message) || message.isEmpty()) {
            System.out.println("message is empty.");
            return;
        }

        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(tx -> {
                Result result = tx.run("CREATE (a:Greeting) " +
                                "SET a.message = $message " +
                                "RETURN a.message + ', from node ' + id(a)",
                        parameters("message", message));
                return result.single().get(0).asString();
            });
            System.out.println(greeting);
        }
    }

    private void closeDriver(Driver driver) {
        if (Objects.nonNull(driver)) {
            driver.close();
        }
    }

    private Driver getDriver() {
        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"
        final String dbUri = "neo4j://192.168.7.94:7687";
        final String dbUser = "neo4j";
        final String dbPassword = "neo4jneo4j";
        Driver driver = null;

        driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
        // 若连接失败, 该方法会抛出异常
        driver.verifyConnectivity();
        return driver;
    }

}
