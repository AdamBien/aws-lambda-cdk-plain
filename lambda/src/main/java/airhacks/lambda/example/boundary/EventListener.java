package airhacks.lambda.example.boundary;

import airhacks.lambda.example.entity.ExampleEvent;
import airhacks.logging.control.Log;

/**
 * Example Lambda handler for processing AWS events from EventBridge, SNS, SQS, CodeBuild or other AWS services.
 * Demonstrates event-driven processing pattern for AWS Lambda functions.
 *
 * Rename this class to match the actual event type, e.g., OrderEventListener, PaymentEventListener, BuildEventListener
 */
public class EventListener {
    
    static String message = System.getenv("message");

    public EventListener() {
        Log.info("initialized with configuration: %s", message);
    }

    /**
     * Lambda function entry point - handles incoming AWS service events.
     * This method is invoked by the Lambda runtime for each event trigger.
     *
     * @param awsEvent AWS service event (EventBridge, SNS, SQS, etc.)
     */
    public void onEvent(Object awsEvent) {
        Log.info("event received: %s", awsEvent);
        var domainEvent = extract(awsEvent);
        Log.info("event converted: %s", domainEvent);
        
    }

    /**
     * Transforms AWS service event into domain-specific representation.
     * Implement actual parsing logic based on the specific AWS service event structure
     * (e.g., EventBridge detail, SNS Message, SQS body, CodeBuild state change)
     *
     * @param event AWS event
     * @return domain specific payload
     */
    static ExampleEvent extract(Object event){
        //placeholder for conversion
        return new ExampleEvent(event.toString());
    }
    
}
