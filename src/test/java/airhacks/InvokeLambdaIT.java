package airhacks;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;

public class InvokeLambdaIT {
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
            String json = "{\"user\":\"duke\"}";
            SdkBytes payload = SdkBytes.fromUtf8String(json);

            InvokeRequest request = InvokeRequest.builder()
                    .functionName("airhacks_lambda_greetings_boundary_Greetings")
                    .payload(payload)
                    .invocationType(InvocationType.REQUEST_RESPONSE)
                    .build();

            var response = this.client.invoke(request);
            var error = response.functionError();
            assertNull(error);
            var value = response.payload().asUtf8String();
            System.out.println("Function executed. Response: " + value);
    }    
}
