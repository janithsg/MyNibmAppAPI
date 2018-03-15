package com.nibm.mynibmapi.controller;

import com.nibm.mynibmapi.database.DBConnection;
import com.nibm.mynibmapi.model.ResultDTO;
import com.nibm.mynibmapi.model.SubjectDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("result")
public class ResultController {

    // ------------- All Results -------------------
    @RequestMapping("all")
    public ArrayList<ResultDTO> getAllResults(@RequestParam("sid") String sid) {
        ArrayList<ResultDTO> results = new ArrayList<ResultDTO>();
        Document nibmRstPage = null;
        int resloc = 0;

        try {
            // Goto URL and Load the page and go through tag by tag
            nibmRstPage = Jsoup.connect("https://www.nibm.lk/students/exams/results?q=" + sid).timeout(10000000).validateTLSCertificates(false).get();
            Element rstTable = nibmRstPage.select("table.w0").get(0);
            Elements tbody = rstTable.select("tbody");
            Elements rows = tbody.select("tr");

            // Iterate through the rows and print the data inside the row.
            firstloop:
            for (Element row : rows) {

                // Check duplicate results
                for (ResultDTO rsd : results) {

                    // Keep the position of the result
                    resloc = results.indexOf(rsd);

                    // If a duplicate result found in the results set,
                    if (row.getElementsByTag("td").get(1).text().equals(rsd.getSubject())) {

                        // Compare the new result and old result
                        if (compareToLast(rsd.getGrade(), row.getElementsByTag("td").get(3).text())) {

                            // If the new results is better then remove the bad result and put the new results to the list
                            results.remove(resloc);
                            results.add(new ResultDTO(row.getElementsByTag("td").get(0).text(), row.getElementsByTag("td").get(1).text(), row.getElementsByTag("td").get(2).text(), row.getElementsByTag("td").get(3).text()));
                            continue firstloop;
                        }

                        // continue the step if the results isnt better
                        continue firstloop;
                    }
                }

                // Since there are no duplicates, add the result to the list
                results.add(new ResultDTO(row.getElementsByTag("td").get(0).text(), row.getElementsByTag("td").get(1).text(), row.getElementsByTag("td").get(2).text(), row.getElementsByTag("td").get(3).text()));
            }

        } catch (IOException ex) {
            System.out.println("\n\nError occured : " + ex.getMessage());
        }
        return results;
    }

    // Compare the new result with old result
    private boolean compareToLast(String firstRes, String secRes) {
        double fval = 0, sval = 0;

        fval = getSubjectValue(firstRes);
        sval = getSubjectValue(secRes);

        return fval < sval;

    }

    // Assign value to each result for comparison
    private double getSubjectValue(String rst) {
        double val;

        if (rst.equals("A+")) {
            val = 15;
        } else if (rst.equals("A")) {
            val = 14;
        } else if (rst.equals("A-")) {
            val = 13;
        } else if (rst.equals("B+")) {
            val = 12;
        } else if (rst.equals("B")) {
            val = 11;
        } else if (rst.equals("B-")) {
            val = 10;
        } else if (rst.equals("C+")) {
            val = 9;
        } else if (rst.equals("C")) {
            val = 8;
        } else if (rst.equals("C-")) {
            val = 7;
        } else if (rst.equals("D+")) {
            val = 6;
        } else if (rst.equals("D")) {
            val = 5;
        } else if (rst.equals("D-")) {
            val = 4;
        } else if (rst.equals("E")) {
            val = 3;
        } else {
            val = 0;
        }

        return val;
    }

    // ------------------ GPA -----------------------
    @RequestMapping("gpa")
    public String getGPA(@RequestParam("sid") String sid){
        ArrayList<ResultDTO> results = getAllResults(sid);
        ArrayList<SubjectDTO> subjects = getSubjects("DSE16.1");

        double gpa = 0;
        double sumOfGC = 0;
        int noOfCredits = 0;

        for (ResultDTO rst : results) {
            for (SubjectDTO sub:subjects){
                if (rst.getSubject().equals(sub.getSubjectName())){
                    if(getGradePoint(rst.getGrade())!=0 && sub.getNoOfCredits()!=0){
                        sumOfGC += (getGradePoint(rst.getGrade()) * sub.getNoOfCredits());
                    }else {
                        sumOfGC += 0;
                    }
                    noOfCredits += sub.getNoOfCredits();
                }
            }
        }

        if (sumOfGC != 0 && noOfCredits != 0){
            gpa = sumOfGC / noOfCredits;
        }

        String finalGPA = String.format("%.4f", gpa);

        return finalGPA;

    }

    // Get Grade Point according to the result
    private double getGradePoint(String rst) {
        double val;

        if (rst.equals("A+")) {
            val = 4.0;
        } else if (rst.equals("A")) {
            val = 4.0;
        } else if (rst.equals("A-")) {
            val = 3.7;
        } else if (rst.equals("B+")) {
            val = 3.3;
        } else if (rst.equals("B")) {
            val = 3.0;
        } else if (rst.equals("B-")) {
            val = 2.7;
        } else if (rst.equals("C+")) {
            val = 2.3;
        } else if (rst.equals("C")) {
            val = 2.0;
        } else if (rst.equals("C-")) {
            val = 1.7;
        } else if (rst.equals("D+")) {
            val = 1.3;
        } else if (rst.equals("D")) {
            val = 1;
        } else {
            val = 0;
        }

        return val;
    }

    // Get no of credits for each subject.
    private ArrayList<SubjectDTO> getSubjects(String batch){

        ArrayList<SubjectDTO> subjects = new ArrayList<SubjectDTO>();

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            String sql = "SELECT * FROM `subjectcredits` WHERE batchNo='"+ batch +"'";

            ResultSet rst = stm.executeQuery(sql);

            while(rst.next()){
                subjects.add(new SubjectDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getInt(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return subjects;
    }

}

