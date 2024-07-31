package cn.moquan.tools.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/24 11:16 </b><br />
 */
public class ImageHandlerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageHandlerUtil.class);

    private ImageHandlerUtil() {
        throw new IllegalStateException("util class");
    }

    public static BufferedImage readImage(String uri) {

        // 测试图片地址 uri : ../resources/images/oilPaint.jpg

        File imageFile = new File(uri);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            logger.error("读取图像失败, uri: {}", uri, e);
        }

        return bufferedImage;
    }

    public static void outputImage(BufferedImage buff, String formatName, String uri) {
        try {
            ImageIO.write(buff, formatName, new File(uri));
        } catch (IOException e) {
            logger.error("输出图像失败, uri: {}", uri, e);
        }
    }

    public static void convertGrayscale(BufferedImage buff) {
        Objects.requireNonNull(buff);

        int maxX = buff.getWidth();
        int maxY = buff.getHeight();

        // 按列进行处理
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {

                int rgb = buff.getRGB(x, y);

                int r = getR(rgb);
                int g = getG(rgb);
                int b = getB(rgb);

                int base = (r + g + b) / 3;

                int new_r = (base & 0x000000ff) << 16;
                int new_g = (base & 0x000000ff) << 8;
                int new_b = (base & 0x000000ff);

                int newRGB = new_r + new_g + new_b;

                buff.setRGB(x, y, newRGB);
            }
        }

    }

    public static int getR(int rgb) {
        return rgb & 0x00FF0000 >>> 16;
    }

    public static int getG(int rgb) {
        return rgb & 0x0000FF00 >>> 8;
    }

    public static int getB(int rgb) {
        return rgb & 0x000000FF;
    }


}
