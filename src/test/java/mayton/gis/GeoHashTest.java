package mayton.gis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoHashTest {

    @Test
    void encode_throws_an_error_when_lattitude_incorrect() {
        assertThrows(
                IllegalArgumentException.class,
                () -> GeoHash.encode(-200.0, 0.0)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> GeoHash.encode(200.0, 0.0)
        );
    }


    void testEncode() {
    }
}