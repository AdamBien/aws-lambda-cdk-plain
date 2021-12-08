package airhacks;

import software.constructs.Construct;

import java.util.Map;

import javax.net.ssl.TrustManagerFactorySpi;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.LambdaInsightsVersion;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.amazon.awscdk.services.logs.RetentionDays;

public class LambdaStack extends Stack {

    static Map<String, String> configuration = Map.of("message", "hello,duke");
    static String functionName  = "airhacks_lambda_greetings_boundary_Greetings";
    static String lambdaHandler = "airhacks.lambda.greetings.boundary.Greetings::onEvent";
    static int memory = 128;
    static int timeout = 10;
    static int maxConcurrency = 2;

    public LambdaStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        
        var function = createFunction(functionName, lambdaHandler, configuration, memory, maxConcurrency, timeout);
        
        CfnOutput.Builder.create(this, "function-output").value(function.getFunctionArn()).build();
    }
    

    Function createFunction(String functionName,String functionHandler, Map<String,String> configuration, int memory, int maximumConcurrentExecution, int timeout) {
        return Function.Builder.create(this, functionName)
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../target/function.jar"))
                .handler(functionHandler)
                .memorySize(memory)
                .functionName(functionName)
                .environment(configuration)
                .timeout(Duration.seconds(timeout))
                .logRetention(RetentionDays.ONE_DAY)
                .insightsVersion(LambdaInsightsVersion.VERSION_1_0_98_0)
                .tracing(Tracing.ACTIVE)
                .profiling(true)
                .reservedConcurrentExecutions(maximumConcurrentExecution)
                .build();
    }
    
}
