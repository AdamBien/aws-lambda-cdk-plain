package airhacks;

import software.constructs.Construct;

import java.util.Map;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

public class CDKStack extends Stack {

    public CDKStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        Map<String, String> configuration = Map.of("message", "hello,duke");
        var function = createUserListenerFunction("airhacks_lambda_greetings_boundary_Greetings","airhacks.lambda.greetings.boundary.Greetings::onEvent", configuration, 128, 5, 10);
        CfnOutput.Builder.create(this, "function-output").value(function.getFunctionArn()).build();
    }
    

    Function createUserListenerFunction(String functionName,String functionHandler, Map<String,String> configuration, int memory, int maximumConcurrentExecution, int timeout) {
        return Function.Builder.create(this, functionName)
                .runtime(Runtime.JAVA_11)
                .code(Code.fromAsset("../target/function.jar"))
                .handler(functionHandler)
                .memorySize(memory)
                .functionName(functionName)
                .environment(configuration)
                .timeout(Duration.seconds(timeout))
                .reservedConcurrentExecutions(maximumConcurrentExecution)
                .build();
    }
    
}
