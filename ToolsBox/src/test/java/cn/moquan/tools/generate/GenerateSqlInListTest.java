package cn.moquan.tools.generate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 */
class GenerateSqlInListTest {

    private final GenerateSqlInList generator = new GenerateSqlInList();

    @Test
    @DisplayName("生成相关的文本内容")
    void generate() {
        assertDoesNotThrow(generator::generate);
    }

}