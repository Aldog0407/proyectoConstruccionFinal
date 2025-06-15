package practicasprofesionalespf.model;

import practicasprofesionalespf.model.pojo.Student;
import practicasprofesionalespf.model.pojo.User;

public class SessionManager {

    private static SessionManager instance;
    private User loggedInUser;
    private Student loggedInStudent;

    private SessionManager() { }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Student getLoggedInStudent() {
        return loggedInStudent;
    }

    public void setLoggedInStudent(Student loggedInStudent) {
        this.loggedInStudent = loggedInStudent;
    }

    public void cleanSession() {
        this.loggedInUser = null;
        this.loggedInStudent = null;
    }
}