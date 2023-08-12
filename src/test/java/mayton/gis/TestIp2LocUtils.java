package mayton.gis;


import mayton.network.Ip2locUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestIp2LocUtils {

    @Test
    void testBinaryString() {
        assertEquals("01011100100101100010011010100110", Ip2locUtils.ipv4toBinaryString("92.150.38.166").get());
        assertEquals("00110010010111010101111111101000", Ip2locUtils.ipv4toBinaryString("50.93.95.232").get());
    }
}
