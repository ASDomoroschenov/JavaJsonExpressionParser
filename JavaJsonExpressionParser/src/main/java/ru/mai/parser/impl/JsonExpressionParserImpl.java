package ru.mai.parser.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mai.exception.JsonExpressionParserException;
import ru.mai.parser.JsonExpressionParser;
import ru.mai.parser.OperationExecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonExpressionParserImpl implements JsonExpressionParser {

    private static final String OPERATION_FIELD = "operation";
    private static final String ARRAY_VALUES_FIELD = "values";
    private static final String VALUE_FIELD = "value";
    private static final String WRONG_FORMAT = "Wrong format";
    private static final String WRONG_OPERATION = "Wrong operation";
    private final OperationExecutor operationExecutor;

    public JsonExpressionParserImpl() {
        this.operationExecutor = new OperationExecutorImpl();
    }

    @Override
    public Integer calculate(JsonNode json) throws JsonExpressionParserException {
        if (!json.has(OPERATION_FIELD)) {
            JsonNode valueNode = json.get(VALUE_FIELD);

            if (valueNode == null || !valueNode.isInt()) {
                throw new JsonExpressionParserException(WRONG_FORMAT);
            }

            return valueNode.asInt();
        } else {
            Iterator<String> fields = json.fieldNames();

            while (fields.hasNext()) {
                String fieldName = fields.next();

                if (!(fieldName.equals(OPERATION_FIELD) || fieldName.equals(ARRAY_VALUES_FIELD))) {
                    throw new JsonExpressionParserException(WRONG_FORMAT);
                }
            }
        }

        JsonNode operationNode = json.get(OPERATION_FIELD);

        if (operationNode != null) {
            String operation = operationNode.asText();

            if (!checkOperation(operation)) {
                throw new JsonExpressionParserException(WRONG_OPERATION);
            }

            JsonNode arrayValues = json.get(ARRAY_VALUES_FIELD);
            List<Integer> intValues = new ArrayList<>();

            if (arrayValues != null && arrayValues.isArray() && !arrayValues.isEmpty()) {
                for (JsonNode arrayValue : arrayValues) {
                    intValues.add(calculate(arrayValue));
                }
            } else {
                throw new JsonExpressionParserException(WRONG_FORMAT);
            }

            return execute(operation, intValues);
        }

        return null;
    }

    public Integer execute(String operation, List<Integer> values) throws JsonExpressionParserException {
        return switch (operation) {
            case "+" -> operationExecutor.sum(values);
            case "-" -> operationExecutor.subtraction(values);
            default -> throw new JsonExpressionParserException(WRONG_OPERATION);
        };
    }

    public boolean checkOperation(String operation) {
        return operation.equals("+") || operation.equals("-");
    }

}
