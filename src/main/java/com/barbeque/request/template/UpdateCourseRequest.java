package com.barbeque.request.template;

/**
 * Created by System1 on 9/16/2016.
 */
public class UpdateCourseRequest {
    private int id;
    private String name;
    private String description;
    private String code;
    private String attendanceType;
    private int totalWorkingDays;
    private String syllabusName;
    private int subjectAssignType;

    public int getSubjectAssignType() {return subjectAssignType;}

    public void setSubjectAssignType(int subjectAssignType) {this.subjectAssignType = subjectAssignType;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(String attendanceType) {
        this.attendanceType = attendanceType;
    }

    public int getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(int totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

    public String getSyllabusName() {
        return syllabusName;
    }

    public void setSyllabusName(String syllabusName) {
        this.syllabusName = syllabusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateCourseRequest that = (UpdateCourseRequest) o;

        if (id != that.id) return false;
        if (totalWorkingDays != that.totalWorkingDays) return false;
        if (subjectAssignType != that.subjectAssignType) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (attendanceType != null ? !attendanceType.equals(that.attendanceType) : that.attendanceType != null)
            return false;
        return syllabusName != null ? syllabusName.equals(that.syllabusName) : that.syllabusName == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (attendanceType != null ? attendanceType.hashCode() : 0);
        result = 31 * result + totalWorkingDays;
        result = 31 * result + (syllabusName != null ? syllabusName.hashCode() : 0);
        result = 31 * result + subjectAssignType;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateCourseRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", attendanceType='" + attendanceType + '\'' +
                ", totalWorkingDays=" + totalWorkingDays +
                ", syllabusName='" + syllabusName + '\'' +
                ", subjectAssignType=" + subjectAssignType +
                '}';
    }
}
