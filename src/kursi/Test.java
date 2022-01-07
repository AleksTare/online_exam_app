package kursi;

import java.sql.Date;

public class Test {

    private int testId;

    private String description;

    private int courseId;

    public Test(String description, int courseId) {
        this.description = description;
        this.courseId = courseId;
    }

    public Test(int testId, String description, int courseId) {
        this.testId = testId;
        this.description = description;
        this.courseId = courseId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public int hashCode() {
        return testId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test test = (Test) o;
        return testId == test.testId;
    }
}
