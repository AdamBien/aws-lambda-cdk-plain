package airhacks.lambda.example.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import airhacks.lambda.example.entity.ExampleEvent;

/**
 * Tests event extraction logic.
 * Real-world implementations should test with actual AWS event structures
 * from EventBridge, SNS, SQS, CodeBuild, etc. to ensure proper parsing and
 * transformation.
 */
public class EventListenerTest {

    @Test
    void awsToDomainEventConversion() {
        var awsEvent = """
                {
                    "type":"aws",
                    "payload:"test"
                }
                """;
        var actualEvent = EventListener.extract(awsEvent);
        var expectedEvent = new ExampleEvent(awsEvent);
        assertThat(actualEvent).isEqualTo(expectedEvent);
    }
}
