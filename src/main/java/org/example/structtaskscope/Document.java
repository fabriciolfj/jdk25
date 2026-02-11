package org.example.structtaskscope;

public class Document {

    private String statusNf;
    private String statusEmail;

    public String getStatusNf() {
        return statusNf;
    }

    public void setStatusNf(String statusNf) {
        this.statusNf = statusNf;
    }

    public String getStatusEmail() {
        return statusEmail;
    }

    public void setStatusEmail(String statusEmail) {
        this.statusEmail = statusEmail;
    }

    @Override
    public String toString() {
        return "Document{" +
                "statusNf='" + statusNf + '\'' +
                ", statusEmail='" + statusEmail + '\'' +
                '}';
    }
}
