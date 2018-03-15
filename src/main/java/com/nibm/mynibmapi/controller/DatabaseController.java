package com.nibm.mynibmapi.controller;

import com.nibm.mynibmapi.database.DBConnection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("database")
public class DatabaseController {

    @RequestMapping("all")
    public String getRecords(){

        System.out.println("\n\n Called \n \n");

        String data = "";
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            String sql = "SELECT * FROM `subjectcredits`";

            ResultSet rst = stm.executeQuery(sql);

            while(rst.next()){
                data = data + "\n" + rst.getString(1) + "  " + rst.getString(2) + "  " + rst.getString(3 );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
