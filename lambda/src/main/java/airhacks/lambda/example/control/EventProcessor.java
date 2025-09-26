package airhacks.lambda.example.control;

import airhacks.lambda.example.entity.ExampleEvent;
import airhacks.logging.control.Log;

/**
 * Processes domain events extracted from AWS service events.
 * Implements business logic for event handling.
 */
public interface EventProcessor {

    /**
     * Processes the domain event with business logic.
     * Replace with actual processing logic for your domain.
     *
     * @param event domain event to process
     */
    static void process(ExampleEvent event) {
        Log.info("Processing domain event with payload: %s", event.payload());

        // Implement actual business logic here:
        // - Validate event data
        // - Transform data
        // - Call downstream services
        // - ...
        Log.info("Domain event processed successfully");
    }
}