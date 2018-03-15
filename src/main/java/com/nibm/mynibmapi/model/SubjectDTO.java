package com.nibm.mynibmapi.model;

public class SubjectDTO {
    String batch;
    String moduleCode;
    String subjectName;
    Integer noOfCredits;

    public SubjectDTO() {
    }

    public SubjectDTO(String batch, String moduleCode, String subjectName, Integer noOfCredits) {
        this.batch = batch;
        this.moduleCode = moduleCode;
        this.subjectName = subjectName;
        this.noOfCredits = noOfCredits;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getNoOfCredits() {
        return noOfCredits;
    }

    public void setNoOfCredits(Integer noOfCredits) {
        this.noOfCredits = noOfCredits;
    }
}
