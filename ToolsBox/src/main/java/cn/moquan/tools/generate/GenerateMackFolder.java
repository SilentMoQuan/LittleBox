package cn.moquan.tools.generate;

import java.io.File;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/3 14:15 </b><br />
 */
public class GenerateMackFolder {

    public void createDirectory(String baseUrl, String fileName) {
        String fileUrl = baseUrl + "\\" + fileName;

        File file = new File(fileUrl);

        if (file.exists()) {
            throw new IllegalStateException("file is exists, fileUrl: [" + fileUrl + "]");
        }

        if (!file.mkdir()) {
            throw new IllegalStateException("create directory failed, fileUrl: [" + fileUrl + "]");
        }
    }

}
