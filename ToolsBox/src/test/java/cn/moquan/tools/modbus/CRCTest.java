package cn.moquan.tools.modbus;

import org.junit.jupiter.api.Test;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/21 16:37 </b><br />
 */
class CRCTest {

    private CRC crc = new CRC();

    @Test
    void getCRC16() {
//         assertEquals("c5cd", crc.getCRC16("01030000000AC5CD"));
        String command = "A10501010101";
        String crc16 = crc.calculateCRC16(command);
        System.out.println(command + crc16);
    }
}

