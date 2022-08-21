package sn.ept.git.seminaire.cicd.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilTest {

    int size = 2;
    String data = "0";
    byte[] byteArray;

    @Test
    void createByteArray_ShouldReturnByteArray() {
        byteArray = TestUtil.createByteArray(size, data);
        byte[] testArray = new byte[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = Byte.parseByte(data, 2);
        }
        assertThat(byteArray).isEqualTo(testArray);
    }

}
