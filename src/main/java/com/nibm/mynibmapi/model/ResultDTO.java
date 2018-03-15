package com.nibm.mynibmapi.model;

public class ResultDTO {
    private String publishedDate;
    private String subject;
    private String batch;
    private String grade;

    public ResultDTO() {
    }

    public ResultDTO(String publishedDate, String subject, String batch, String grade) {
        this.publishedDate = publishedDate;
        this.subject = subject;
        this.batch = batch;
        this.grade = grade;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
