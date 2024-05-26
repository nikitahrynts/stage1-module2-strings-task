package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {

        int argumentsPosition = signatureString.indexOf('(');

        List<MethodSignature.Argument> argumentsList = new ArrayList<>();
        String arguments = signatureString.substring(argumentsPosition + 1, signatureString.length() - 1);
        if (!arguments.isEmpty()) {
            String[] args = arguments.split(", ");
            for (String arg : args) {
                String[] argParts = arg.split(" ");
                MethodSignature.Argument argument = new MethodSignature.Argument(argParts[0], argParts[1]);
                argumentsList.add(argument);
            }
        }

        String methodWithoutArgs = signatureString.substring(0, argumentsPosition);
        String[] methodParts = methodWithoutArgs.split(" ");
        String accessModifier = null;
        String returnType;
        String methodName;
        if (methodParts.length < 3) {
            returnType = methodParts[0];
            methodName = methodParts[1];
        } else {
            accessModifier = methodParts[0];
            returnType = methodParts[1];
            methodName = methodParts[2];
        }

        MethodSignature methodSignature = new MethodSignature(methodName, argumentsList);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }
}