import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.mai.exception.JsonExpressionParserException;
import ru.mai.parser.JsonExpressionParser;
import ru.mai.parser.impl.JsonExpressionParserImpl;
import util.TestData;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class JsonExpressionParserTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @DataProvider(name = "positiveCaseDataProvider")
    public Object[][] positiveCaseDataProvider() {
        return new Object[][]{
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_1.json").getPath()), 0)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_2.json").getPath()), 7)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_3.json").getPath()), -7)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_4.json").getPath()), -9)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_5.json").getPath()), -2)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_6.json").getPath()), 1000996)},
                {new TestData(new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/positive/positive_case_7.json").getPath()), 986341)}
        };
    }

    @DataProvider(name = "wrongFormatExceptionDataProvider")
    public Object[][] wrongFormatExceptionDataProvider() {
        return new Object[][]{
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_1.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_3.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_4.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_5.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_6.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_7.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_8.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_9.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_10.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_11.json").getPath())},
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_12.json").getPath())},
        };
    }

    @DataProvider(name = "wrongOperationExceptionDataProvider")
    public Object[][] wrongOperationExceptionDataProvider() {
        return new Object[][]{
                {new File(JsonExpressionParserTest.class.getClassLoader().getResource("./test/negative/negative_case_2.json").getPath())},
        };
    }

    @Test(dataProvider = "positiveCaseDataProvider")
    public void testPositiveCase(TestData testData) throws IOException, JsonExpressionParserException {
        JsonExpressionParser parser = new JsonExpressionParserImpl();
        JsonNode jsonNode = mapper.readTree(testData.getContent());
        Integer expectedValue = testData.getExpectedValue();

        System.out.println("Positive test with json:");
        System.out.println(jsonNode.toPrettyString());

        Integer actualValue = parser.calculate(jsonNode);

        System.out.println("Actual value: " + actualValue);

        assertEquals(actualValue, expectedValue);
    }

    @Test(dataProvider = "wrongFormatExceptionDataProvider")
    public void testThrowsWrongFormat(File testData) throws IOException {
        JsonExpressionParser parser = new JsonExpressionParserImpl();
        JsonNode jsonNode = mapper.readTree(testData);

        System.out.println("Throws wrong format test with json:");
        System.out.println(jsonNode.toPrettyString());

        assertThrows(JsonExpressionParserException.class, () -> parser.calculate(jsonNode));
    }

    @Test(dataProvider = "wrongOperationExceptionDataProvider")
    public void testThrowsWrongOperation(File testData) throws IOException {
        JsonExpressionParser parser = new JsonExpressionParserImpl();
        JsonNode jsonNode = mapper.readTree(testData);

        System.out.println("Throws wrong operation test with json:");
        System.out.println(jsonNode.toPrettyString());

        assertThrows(JsonExpressionParserException.class, () -> parser.calculate(jsonNode));
    }
}
