package mayton.ip2loc;
import java.util.Optional;

public interface Ipv4LocationService extends Countable, AutoCloseable {

    Optional<Ipv4Loc> resolve(String ipv4);

}
