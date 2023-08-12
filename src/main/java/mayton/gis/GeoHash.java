package mayton.gis;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

public class GeoHash {

    private GeoHash() {}

    /**
     * See the : https://en.wikipedia.org/wiki/Geohash
     */
    static final String GEO_HASH_ALPHABET = "0123456789bcdefghjkmnpqrstuvwxyz";

    public static String encode(double lat, double lon) {
        Validate.exclusiveBetween(-90.0, 90.0,   lat, "Lattitude must be [90..90]");
        Validate.exclusiveBetween(-180.0, 180.0, lon, "Longitude must be [-180..180]");

        return "";
    }

    public static String encode(BigDecimal lat, BigDecimal lon) {
        Validate.notNull(lat, "Lattitude must be filled");
        Validate.notNull(lon, "Longitude must be filled");
        Validate.isTrue(lat.compareTo(BigDecimal.valueOf(90.0)) > 0 && lat.compareTo(BigDecimal.valueOf(-90.0)) < 0,
                "Lattitude must be [90..90]");
        Validate.isTrue(lat.compareTo(BigDecimal.valueOf(180.0)) > 0 && lat.compareTo(BigDecimal.valueOf(-180.0)) < 0,
                "Longitude must be [-180..180]");

        return "";
    }

}
