package nl.cmyrsh.functions;

import java.util.Map;

public interface Handler {

    Handler init(Map<String, String> config);
}
