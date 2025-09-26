package airhacks.lambda.example.boundary;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import airhacks.logging.control.Log;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;

/**
 * Integration test for Lambda function invocation.
 * Requires AWS credentials configured via environment variables, AWS CLI configuration,
 * or IAM instance role. The Lambda function must be deployed before running this test.
 */
public class InvokeEventListenerIT {
        String functionName = "airhacks_EventListener";
        LambdaClient client;

        @BeforeEach
        public void initClient() {
                var credentials = DefaultCredentialsProvider.builder().build();
                this.client = LambdaClient.builder()
                                .credentialsProvider(credentials)
                                .build();
        }

        @Test
        public void invokeLambdaAsynchronously() {
                var json = """
                        {
                                "user":"duke"
                        }
                                """;
                var payload = SdkBytes.fromUtf8String(json);

                var request = InvokeRequest.builder()
                                .functionName(functionName)
                                .payload(payload)
                                .invocationType(InvocationType.REQUEST_RESPONSE)
                                .build();

                var response = this.client.invoke(request);
                var error = response.functionError();
                assertNull(error);
                var value = response.payload().asUtf8String();
                Log.info("Function executed. Response: " + value);
        }
}
