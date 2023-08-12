package mayton.ip2loc;

import java.util.Optional;

public class DummyLocationService implements Ipv4LocationService {

    @Override
    public Optional<Ipv4Loc> resolve(String ipv4) {
        return Optional.empty();
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public int count() {
        return 0;
    }
}
