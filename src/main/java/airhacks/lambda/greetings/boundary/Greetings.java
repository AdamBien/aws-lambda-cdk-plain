package airhacks.lambda.greetings.boundary;

import java.util.Map;
import java.util.stream.Collectors;


public class Greetings{

    public String onEvent(Map<String, String> input) {
        System.out.println("received: " + input);
        return input
        .entrySet()
        .stream()
        .map(e -> e.getKey() + "->" + e.getValue())
        .collect(Collectors.joining(","));
    }
    
}
