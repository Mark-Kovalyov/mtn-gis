package mayton.gis;

import mayton.ip2loc.Ipv4Loc;
import mayton.ip2loc.CachedIpv4LocationService;
import mayton.ip2loc.Ipv4LocationService;
import mayton.network.NetworkUtils;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@Tag("external")
class TestIp2loc {

    static Ipv4LocationService ipv4locationService = null;

    @BeforeAll
    static void init() {
        ipv4locationService = (CachedIpv4LocationService.getInstance());
    }

    @Test
    void test() {
        Optional<Ipv4Loc> res = ipv4locationService.resolve("50.93.95.232");
        //
        assertTrue(res.isPresent());
        Ipv4Loc resval = res.get();
        assertEquals("CA", resval.cc);
        assertEquals("Canada", resval.country);
        assertEquals("Alberta", resval.region);
        assertEquals("50.93.92.0", resval.beginIpFormatted());
        assertEquals("50.93.95.255", resval.endIpFormatted());

        res = ipv4locationService.resolve("92.150.38.166");
        assertTrue(res.isPresent());
        resval = res.get();
        assertEquals("FR", resval.cc);
        assertEquals("France", resval.country);
        assertEquals("Provence-Alpes-Cote-d'Azur", resval.region);
        assertEquals("92.150.38.0", resval.beginIpFormatted());
        assertEquals("92.150.39.255", resval.endIpFormatted());
    }

    @Test
    void testEmptyBlock() {
        assertEquals("0.0.0.0",       NetworkUtils.formatIpV4(0L));
        assertEquals("0.255.255.255", NetworkUtils.formatIpV4(16777215L));

        assertEquals(1553344166L,       NetworkUtils.parseIpV4("92.150.38.166"));
        assertEquals( 844980200L,       NetworkUtils.parseIpV4("50.93.95.232"));
    }

    @AfterAll
    static void afterAll() {

    }

}
