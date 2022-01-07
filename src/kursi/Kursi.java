package kursi;

public class Kursi {

    private int courseId;

    private String courseName;

    public Kursi(String courseName) {
        this.courseName = courseName;
    }

    public Kursi(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
