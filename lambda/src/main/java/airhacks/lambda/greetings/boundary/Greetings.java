package airhacks.lambda.greetings.boundary;

import java.util.Map;
import java.util.stream.Collectors;


public class Greetings {
    
    static String message = System.getenv("message");

    public Greetings() {
        System.out.println("initialized with configuration: " + message);
    }

    public String onEvent(Map<String, String> event) {
        System.out.println("received: " + event);
        return event
        .entrySet()
        .stream()
        .map(e -> e.getKey() + "->" + e.getValue())
        .collect(Collectors.joining(","));
    }
    
}
