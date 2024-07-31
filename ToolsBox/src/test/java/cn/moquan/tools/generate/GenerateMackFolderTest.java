package cn.moquan.tools.generate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 */
class GenerateMackFolderTest {

    private final GenerateMackFolder generator = new GenerateMackFolder();

    private static final String baseUrl = "";

    @Test
    @Disabled("仅作为替代main执行的方式而存在")
    @DisplayName("生成文件夹")
    void createDirectory() {

        // 使用断言而防止出现警告
        assertDoesNotThrow(() -> {
            for (int i = 1; i <= 20; i++) {
                String fileName = String.valueOf(i);
                if (i < 10){
                    fileName = "0" + fileName;
                }

                generator.createDirectory(baseUrl, fileName);
            }
        });

    }
}