package cn.moquan.tools.generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/2/28 14:30 </b><br />
 */
public class GenerateSqlInList {

    private static final Logger logger = LoggerFactory.getLogger(GenerateSqlInList.class);

    public void generate() {

        ClassLoader classLoader = GenerateSqlInList.class.getClassLoader();
        URL fileUrl = classLoader.getResource("ancillaryFiles/GenerateSqlInListFile");

        Objects.requireNonNull(fileUrl);
        String generateSqlInListFile = fileUrl.getPath();
        logger.info("generateSqlInListFile : {}", generateSqlInListFile);

        File ancillaryFile = new File(generateSqlInListFile);

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(ancillaryFile.toPath()))
        )) {

            String line;
            StringBuilder builder = new StringBuilder();

            // 处理文件
            while (null != (line = bufferedReader.readLine())) {
                builder.append(",").append("'").append(line).append("'");
            }

            if (builder.length() >= 1) {
                builder.deleteCharAt(0);
            }

            logger.info("\n" + "result: {}", builder);

            // 将结果输出到文件末尾
            appendStringToFile(ancillaryFile, builder.toString());

        } catch (IOException e) {
            recordException(e);
        }

    }

    private void appendStringToFile(File file, String str) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(file, true))
        ) {

            bufferedWriter.write("\n");
            bufferedWriter.write(str);
            bufferedWriter.flush();
            logger.info("追加的文本保存在 target/classes/ancillaryFiles/GenerateSqlInListFile 中");
            logger.info("如果输出的内容出现异常请执行 mvn clean");

        } catch (IOException e) {
            recordException(e);
        }

    }

    private void recordException(Exception e) {
        logger.error("生成数据时出现异常, ", e);
    }

}
