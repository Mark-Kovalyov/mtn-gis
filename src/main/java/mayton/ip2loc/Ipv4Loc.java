package mayton.ip2loc;

import mayton.network.NetworkUtils;

public final class Ipv4Loc {
    public final String beginIp;
    public final String endIp;
    public final String cc;
    public final String country;
    public final String region;

    public Ipv4Loc(String beginIp, String endIp, String cc, String country, String region) {
        this.beginIp = beginIp;
        this.endIp   = endIp;
        this.cc      = cc;
        this.country = country;
        this.region  = region;
    }

    @Override
    public String toString() {
        return beginIp + ";" + endIp + ";" + cc + ";" + country + ";" + region;
    }

    public String beginIpFormatted() {
        return NetworkUtils.formatIpV4(Long.parseLong(beginIp));
    }

    public String endIpFormatted() {
        return NetworkUtils.formatIpV4(Long.parseLong(endIp));
    }
}
