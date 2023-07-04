package cn.moquan.tools.string;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/7/4 11:45 </b><br />
 */
public class StringUtil {

    public static <T> String toStringFormat(T target){
        return toStringFormat(target.toString());
    }

    public static String toStringFormat(String target){

        if (null == target || target.isEmpty()){
            return "";
        }

        // 获取参数 Sting 相应的字符数组
        char[] array = target.toCharArray();

        int tableSize = 0;

        StringBuilder builder = new StringBuilder();

        // 遍历格式化
        for (char c : array) {

            switch (c){
                case '(':
                case '[':
                case '{': {
                    tableSize++;
                    builder.append(c).append("\n");
                    appendTab(builder, tableSize);
                    break;
                }
                case ')':
                case ']':
                case '}':{
                    tableSize--;
                    builder.append("\n");
                    appendTab(builder, tableSize);
                    builder.append(c);
                    break;
                }
                case ',':{
                    builder.append(c);
                    builder.append("\n");
                    appendTab(builder, tableSize);
                    break;
                }
                case '=': {
                    builder.append(" = ");
                    break;
                }
                case ' ':{
                    break;
                }
                default:
                    builder.append(c);
            }
        }

        return builder.toString();
    }

    private static void appendTab(StringBuilder builder, int size) {
        for (int i = 0; i < size; i++) {
            builder.append("\t");
        }
    }

    /**
     * bytes 转 16 进制 String
     * <br />
     *
     * @param bytes 字节数组
     * @return String
     * @author wyh
     * @date 2023/5/12 9:05
     */
    public static String bytesToHexString(byte[] bytes) {

        StringBuilder builder = new StringBuilder();

        for (byte item : bytes) {

            String hexString = Integer.toHexString(0xFF & item);

            if (hexString.length() < 2) {
                builder.append("0");
            }

            builder.append(hexString.toUpperCase());

        }

        return builder.toString();
    }

    /**
     * byte 转 16 进制 String
     * <br />
     *
     * @param item 字节数据
     * @return String
     * @author wyh
     * @date 2023/5/12 9:05
     */
    public static String byteToHexString(byte item) {

        String hexString = Integer.toHexString(0xFF & item);

        if (hexString.length() < 2) {
            hexString = "0" + hexString;
        }

        return hexString.toUpperCase();
    }

}
