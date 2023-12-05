package airhacks;

import java.util.Map;
import java.util.Objects;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.constructs.Construct;

public class LambdaStack extends Stack {

    public static class Builder {

        private Construct construct;
        private String stackId;
        private String functionName;
        private String functionHandler;
        private Map<String, String> configuration = Map.of();
        private final int ONE_CPU = 1700;
        private int ram = ONE_CPU;

        public Builder(Construct construct, String stackNamePrefix) {
            this.construct = construct;
            this.stackId = stackNamePrefix.toLowerCase() + "-function-pojo-stack";
        }

        public Builder functionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public Builder functionHandler(String handler) {
            this.functionHandler = handler;
            return this;
        }

        public Builder ram(int ram) {
            this.ram = ram;
            return this;
        }

        public Builder withOneCPU() {
            this.ram = ONE_CPU;
            return this;
        }

        public Builder withHalfCPU() {
            this.ram = ONE_CPU / 2;
            return this;
        }

        public Builder withTwoCPUs() {
            this.ram = ONE_CPU * 2;
            return this;
        }

        public Builder configuration(Map<String, String> configuration) {
            this.configuration = configuration;
            return this;
        }

        public LambdaStack build() {
            Objects.requireNonNull(this.functionName, "Function name is required");
            Objects.requireNonNull(this.functionHandler, "Function handler (fqn::methodName) is required");
            return new LambdaStack(this);
        }

    }


    public LambdaStack(Builder builder) {
        super(builder.construct, builder.stackId);
        var timeout = 10;
        
        var function = createFunction(builder.functionName, builder.functionHandler, builder.configuration, builder.ram, timeout);
        
        CfnOutput.Builder.create(this, "FunctionARN").value(function.getFunctionArn()).build();
    }
    

    Function createFunction(String functionName,String functionHandler, Map<String,String> configuration, int memory, int timeout) {
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
    
}
