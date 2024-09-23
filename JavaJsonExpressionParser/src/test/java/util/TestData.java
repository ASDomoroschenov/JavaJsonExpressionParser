package util;

import java.io.File;

public class TestData {

    private final File content;

    private final Integer expectedValue;

    public TestData(File content, Integer expectedValue) {
        this.content = content;
        this.expectedValue = expectedValue;
    }

    public File getContent() {
        return content;
    }

    public Integer getExpectedValue() {
        return expectedValue;
    }
}
