# Simplest Possible AWS Lambda Function with Cloud Development Kit (CDK) Boilerplate

A lean starting point for building, testing and deploying AWS Lambdas with Java.

# TL;DR

A simple Java AWS Lambda without any AWS dependencies:

```java

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

```

...deployed with AWS Cloud Development Kit:


```java

import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

//...

Function createUserListenerFunction(String functionName,String functionHandler, int memory, int timeout) {
    return Function.Builder.create(this, id(functionName))
            .runtime(Runtime.JAVA_11) //https://aws.amazon.com/corretto
            .code(Code.fromAsset("../target/function.jar"))
            .handler(functionHandler)
            .memorySize(memory)
            .functionName(functionName)
            .timeout(Duration.seconds(timeout))
            .build();
}

```

...provisioned with maven and cdk:

```
mvn clean package
cd cdk && mvn clean package && cdk deploy
```

...and (blackbox) tested with [AWS SDK for Java 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide):

```java

@BeforeEach
public void initClient() {
    var credentials = DefaultCredentialsProvider
    .builder()
    .profileName("airhacks.live")
    .build();

    this.client = LambdaClient.builder()
                   .credentialsProvider(credentials)
                   .build();
}

@Test
public void invokeLambdaAsynchronously() {
        String json = "{\"user \":\"duke\"}";
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
