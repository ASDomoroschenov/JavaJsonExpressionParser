package ru.mai.parser;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mai.exception.JsonExpressionParserException;

public interface JsonExpressionParser {

    Integer calculate(JsonNode json) throws JsonExpressionParserException;

}
