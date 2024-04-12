package cn.moquan.tools.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/12 8:39 </b><br />
 */
public class HttpUtil {

    private HttpUtil() {
        throw new IllegalStateException("util class");
    }

    public static void sendPost(String urlStr, String commandStr) {

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            urlConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            String param = "";
            if (null != commandStr && !commandStr.isEmpty()) {
                param = commandStr;
            }

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(param.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            InputStream inputStream = urlConnection.getInputStream();

            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            // noting to do
        }
    }

}
