package cn.moquan.tools.generate;

import org.junit.jupiter.api.Test;


/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/5 17:23 </b><br />
 */
class GenerateMackFolderTest {

    private final GenerateMackFolder generator = new GenerateMackFolder();

    private static final String baseUrl = "C:\\Users\\17005\\Desktop\\春考考试信息";

    @Test
    void createDirectory() {

        for (int i = 1; i <= 20; i++) {
            String fileName = String.valueOf(i);
            if (i < 10){
                fileName = "0" + fileName;
            }

            generator.createDirectory(baseUrl, fileName);
        }

    }
}