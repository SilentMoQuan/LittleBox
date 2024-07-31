package cn.moquan.miot.tool;

import java.util.Locale;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/7/4 11:45 </b><br />
 */
public class StringUtil {

    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }

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
        boolean isInStr = false;

        StringBuilder builder = new StringBuilder();

        // 遍历格式化
        for (char c : array) {

            if (c == '"' || c == '\'') {
                isInStr = !isInStr;
                builder.append(c);
                continue;
            }

            if (isInStr) {
                builder.append(c);
                continue;
            }

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

    /**
     * 16进制 string 转 2 进制 String
     * <br />
     *
     * @param hexStr 16进制数据
     * @return String
     * @author wyh
     * @date 2023/5/12 9:05
     */
    public static String hexToBitString(String hexStr) {

        StringBuilder builder = new StringBuilder();

        char[] arr = hexStr.toLowerCase(Locale.ROOT).toCharArray();

        for (char c : arr) {
            switch (c){
                case '0': builder.append("0000"); break;
                case '1': builder.append("0001"); break;
                case '2': builder.append("0010"); break;
                case '3': builder.append("0011"); break;
                case '4': builder.append("0100"); break;
                case '5': builder.append("0101"); break;
                case '6': builder.append("0110"); break;
                case '7': builder.append("0111"); break;
                case '8': builder.append("1000"); break;
                case '9': builder.append("1001"); break;
                case 'a': builder.append("1010"); break;
                case 'b': builder.append("1011"); break;
                case 'c': builder.append("1100"); break;
                case 'd': builder.append("1101"); break;
                case 'e': builder.append("1110"); break;
                case 'f': builder.append("1111"); break;
                default: throw new IllegalArgumentException("16 进制字符串转 2 进制字符串失败, 当前字符不属于 16 进制字符: " + c);
            }
        }

        return builder.toString();
    }


    /**
     * 二进制补码转二进制原码
     * TODO 2023/7/31 16:29 这个位置的数据需要指定位数, 扩展 指定返回 int long 等类型
     * <br />
     *
     * @param bitStr 二进制字符串
     * @return Integer
     * @author wyh
     * @date 2023/7/31 16:27
     */
    public static Integer twoComplementToTrueForm(String bitStr){

        char[] arr = bitStr.toCharArray();

        int result = 0;

        if (arr[0] == '0'){
            // 正数
            result = Integer.parseInt(bitStr, 2);
        } else {
            // 负数
            for (int i = 0; i < arr.length; i++) {

                switch (arr[i]){
                    case '0': arr[i] = '1'; break;
                    case '1': arr[i] = '0'; break;
                    default: throw new IllegalArgumentException("补码转 int 失败, 当前字符不属于 2 进制字符: " + arr[i]);
                }

            }

            result = Integer.parseInt(new String(arr), 2) + 1;
            result = -result;
        }

        return result;
    }

    public static String stackTraceElementFormat(){
        return stackTraceElementFormat(Thread.currentThread().getStackTrace());
    }

    public static String stackTraceElementFormat(StackTraceElement[] stackArr){

        StringBuilder builder = new StringBuilder("\n");
        builder.append("\t").append("########## ThreadStackTrance ##############").append("\n");

        for (StackTraceElement element : stackArr) {

            builder.append("\t")
                    .append(element.getClassName())
                    .append(".")
                    .append(element.getMethodName())
                    .append("(").append(element.getFileName()).append(":").append(element.getLineNumber()).append(")")
                    .append("\n");

        }
        builder.append("\t").append("########## ThreadStackTrance End ##########").append("\n");
        return builder.toString();
    }
}
