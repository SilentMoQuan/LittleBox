package cn.moquan.tools.modbus;

import java.util.Objects;

/**
 *  CRC计算方法：
 *      1.预置1个16位的寄存器为十六进制FFFF(即全为1);称此寄存器为CRC寄存器;
 *      2.把第一个8位二进制数据(既通讯信息帧的第一个字节)与16位的CRC寄存器的低8位相异或，把结果放于CRC寄存器;
 *      3.把CRC寄存器的内容右移一位(朝低位)用0填补最高位，并检查右移后的移出位;
 *      4.如果移出位为0:重复第3步(再次右移一位);如果移出位为1:CRC寄存器与多项式A001(1010 0000 0000 0001)进行异或;
 *      5.重复步骤3和4，直到右移8次，这样整个8位数据全部进行了处理;
 *      6.重复步骤2到步骤5，进行通讯信息帧下一个字节的处理;
 *      7.将该通讯信息帧所有字节按上述步骤计算完成后，得到的16位CRC寄存器的高、低字节进行交换;
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/21 16:32 </b><br />
 */
public class CRC {

    /**
     * 计算字符的 crc 16 的结果
     *
     * @param source 原文
     * @return String 计算结果
     */
    public String calculateCRC16(String source) {

        String[] _0xStrArray = source.split("(?<=\\G[0-9A-Z]{2})");

        int crc = 0xFFFF;

        for (String _0xStr : _0xStrArray) {
            int _0x = Integer.parseInt(_0xStr, 16);
            crc ^= _0x;

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x1) == 1) {
                    crc = crc >>> 1 ^ 0xa001;
                }else {
                    crc = crc >>> 1;
                }
            }

        }

        String result = Integer.toHexString(((crc & 0x00ff) << 8) + ((crc & 0xff00) >>> 8));
        if (result.length() < 2){
            result = "0" + result;
        }

        return result;
    }

    public boolean checkCRC16(String source){
        String originCRC = source.substring(source.length() - 4);
        return Objects.equals(originCRC, calculateCRC16(source.substring(0, source.length() - 4)));
    }

}
