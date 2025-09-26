package airhacks.lambda.example.boundary;

/**
 * Example Lambda handler for processing AWS events from EventBridge, SNS, SQS, CodeBuild or other AWS services.
 * Demonstrates event-driven processing pattern for AWS Lambda functions.
 *
 * Rename this class to match the actual event type, e.g., OrderEventListener, PaymentEventListener, BuildEventListener
 */
public class EventListener {
    
    static String message = System.getenv("message");

    public EventListener() {
        System.out.println("initialized with configuration: " + message);
    }

    /**
     * 
     * @param event AWS 
     */
    public void onEvent(Object event) {
        System.out.println("event received: %s".formatted(event));
    }

    /**
     * 
     * @param event AWS event
     * @return domain specific payload
     */
    static String extract(Object event){
        //placeholder for conversion
        return event.toString();
    }
    
}
