package airhacks.lambda.boundary;

public class POJOLambda {
    
    static String message = System.getenv("message");

    public POJOLambda() {
        System.out.println("initialized with configuration: " + message);
    }

    public void onEvent(Object event) {
        System.out.println("event received: %s".formatted(event));
    }
    
}
