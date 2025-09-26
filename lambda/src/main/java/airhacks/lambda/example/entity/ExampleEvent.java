package airhacks.lambda.example.entity;

/**
 * Domain-specific event representation extracted from AWS service events.
 * Rename this record to match your domain context, e.g., OrderEvent, PaymentEvent, BuildStatusEvent
 */
public record ExampleEvent(String payload) {
    
}
