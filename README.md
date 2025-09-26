# Simplest Possible, BCE structured, AWS Lambda Function with Cloud Development Kit (CDK) Boilerplate

A lean starting point for building, testing and deploying AWS Lambdas with Java. This project serves as a reference implementation for serverless [BCE (Boundary-Control-Entity)](https://bce.design) architecture.

# TL;DR

A simple Java AWS Lambda without any AWS dependencies:

```java
package airhacks.lambda.example.boundary;

public class EventListener {

    static String message = System.getenv("message");

    public void onEvent(Object awsEvent) {
        Log.info("event received: %s", awsEvent);
        var domainEvent = extract(awsEvent);
        EventProcessor.process(domainEvent);
    }

    static ExampleEvent extract(Object event) {
        return new ExampleEvent(event.toString());
    }
}
```

...deployed with AWS Cloud Development Kit:


```java
import software.amazon.awscdk.services.lambda.*;

Function createFunction(String functionName, String functionHandler, Map<String,String> configuration, int memory, int timeout) {
    return Function.Builder.create(this, functionName)
            .runtime(Runtime.JAVA_21)
            .architecture(Architecture.ARM_64)
            .code(Code.fromAsset("../lambda/target/function.jar"))
            .handler(functionHandler)
            .memorySize(memory)
            .functionName(functionName)
            .environment(configuration)
            .timeout(Duration.seconds(timeout))
            .tracing(Tracing.ACTIVE)
            .build();
}
```

...provisioned with maven and cdk:

```bash
cd lambda && mvn clean package
cd ../cdk && mvn clean package && cdk deploy
```

...and (blackbox) tested with [AWS SDK for Java 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide):

```java
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
                    .functionName("airhacks_EventListener")
                    .payload(payload)
                    .invocationType(InvocationType.REQUEST_RESPONSE)
                    .build();

    var response = this.client.invoke(request);
    var error = response.functionError();
    assertNull(error);
    var value = response.payload().asUtf8String();
    Log.info("Function executed. Response: " + value);
}
```

## In Action

[![Plain Java POJOs as AWS Lambdas](https://i.ytimg.com/vi/rHq514-1aHM/mqdefault.jpg)](https://www.youtube.com/embed/rHq514-1aHM?rel=0)

## Java "vs." JavaScript

Cold and "warm" starts of JavaScript and Java Lambdas:

[![Java vs. JavaScript comparison](https://i.ytimg.com/vi/28Da0l0MFms/mqdefault.jpg)](https://www.youtube.com/embed/28Da0l0MFms?rel=0)

## AWS Lambda on Java: How Good / Bad Is The Cold Start?

[![Coldstart with Java](https://i.ytimg.com/vi/EXSZ5TFgUKU/mqdefault.jpg)](https://www.youtube.com/embed/EXSZ5TFgUKU?rel=0)

## Lambda Configuration

[![AWS Lambda Configuration with Java CDK](https://i.ytimg.com/vi/Z3Ir-AQEsKk/mqdefault.jpg)](https://www.youtube.com/embed/Z3Ir-AQEsKk?rel=0)

## References

The deployment is borrowed from: ["Slightly Streamlined AWS Cloud Development Kit (CDK) Boilerplate"](https://github.com/AdamBien/aws-cdk-plain)
