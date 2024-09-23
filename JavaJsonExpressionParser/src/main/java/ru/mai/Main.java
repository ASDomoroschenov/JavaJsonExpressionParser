package ru.mai;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mai.exception.JsonExpressionParserException;
import ru.mai.parser.impl.JsonExpressionParserImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, JsonExpressionParserException {
        ObjectMapper mapper = new ObjectMapper();
        JsonExpressionParserImpl parser = new JsonExpressionParserImpl();
        Integer result = parser.calculate(mapper.readTree(Main.class.getClassLoader().getResource("example.json")));
        System.out.println(result);
    }
}