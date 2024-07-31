package cn.moquan.tools.image;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/24 11:52 </b><br />
 */
class ImageTest {

    @Test
    void gray(){
        ClassLoader classLoader = ImageTest.class.getClassLoader();
        URL resource = classLoader.getResource("images/oilPaint.jpg");
        BufferedImage bufferedImage = ImageHandlerUtil.readImage(resource.getPath());
        ImageHandlerUtil.convertGrayscale(bufferedImage);
        ImageHandlerUtil.outputImage(bufferedImage, "jpg", "F:\\grayOilPaint.jpg");
    }

}
