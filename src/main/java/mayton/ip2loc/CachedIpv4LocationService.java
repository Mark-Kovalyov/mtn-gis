package mayton.ip2loc;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.RadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;

import mayton.lib.Uniconf;
import mayton.network.Ip2locUtils;
import mayton.network.NetworkUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This is related to https://www.ip2location.com/
 */
public class CachedIpv4LocationService implements Ipv4LocationService {

    static Logger logger = LoggerFactory.getLogger("ipv4-location-service-radix");

    private String path;

    private RadixTree<Ipv4Loc> radixTree;

    private static class Singleton {
        public static CachedIpv4LocationService INSTANCE = new CachedIpv4LocationService();
    }

    public static CachedIpv4LocationService getInstance() {
        return Singleton.INSTANCE;
    }

    public CachedIpv4LocationService(String path) {
        this.path = path;
        init();
    }

    public CachedIpv4LocationService() {
        Uniconf uniconf = new Uniconf();
        path = uniconf.lookupProperty("mayton.ip2loc.Ipv4LocationServiceRadix.path").orElseThrow();
        init();
    }

    private void init() {
        logger.info("path = {}", path);
        try {
            logger.info("constructor");
            radixTree = new ConcurrentRadixTree<>(new DefaultCharArrayNodeFactory());
            InputStream is = new FileInputStream(path);
            CSVParser csvParser = CSVParser.parse(
                    is,
                    StandardCharsets.UTF_8,
                    CSVFormat.newFormat(',').withQuote('"'));
            Iterator<CSVRecord> i = csvParser.iterator();
            StringBuilder reuse = new StringBuilder();
            while(i.hasNext()) {
                CSVRecord r = i.next();
                String beginIp = r.get(0);
                String endIp   = r.get(1);
                String cc      = r.get(2);
                String country = r.get(3);
                String region  = r.get(4);

                logger.trace("beginIp = {}, endIp = {}", beginIp, endIp);

                long beginIpLong = Long.parseLong(beginIp);
                long endIpLong   = Long.parseLong(endIp);

                String beginIpBin2 = StringUtils.leftPad(Long.toString(beginIpLong, 2), 32, '0');
                String endIpBin2   = StringUtils.leftPad(Long.toString(endIpLong, 2),   32, '0');

                logger.trace("beginIpBin = {}, endIpBin = {}", beginIpBin2, endIpBin2);

                String samePrefix      = Ip2locUtils.samePrefix(reuse, beginIpBin2, endIpBin2);

                if (logger.isTraceEnabled()) {
                    logger.trace("IpOcted = {} : {}",
                            NetworkUtils.formatIpV4(beginIpLong),
                            NetworkUtils.formatIpV4(endIpLong)
                    );
                }

                logger.trace("samePrefix = {}, prefixLen  = {}", samePrefix, samePrefix.length());


                if (samePrefix.isEmpty()) {
                    logger.warn("Empty!");
                    continue;
                }

                Ipv4Loc ipv4Loc = new Ipv4Loc(beginIp, endIp, cc, country, region);
                radixTree.put(samePrefix, ipv4Loc);
                logger.trace("{}", ipv4Loc);
            }
            csvParser.close();
        } catch (Exception ex) {
            logger.error("{}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Ipv4Loc> resolve(String ipv4) {
        Optional<String> binary = Ip2locUtils.ipv4toBinaryString(ipv4);
        if (binary.isEmpty())
            return Optional.empty();
        Iterator<Ipv4Loc> res = radixTree.getValuesForClosestKeys(binary.get()).iterator();
        return res.hasNext() ?
                Optional.of(res.next()) :
                Optional.empty();
    }

    @Override
    public int count() {
        return radixTree.size();
    }

    @Override
    public void close() throws Exception {
        radixTree = null;
    }
}
