/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.basdatjasper;

/**
 *
 * @author Asus
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class BasdatJasper {
    private static String destFileName = "report.pdf";

    public static void main(String[] args) throws FileNotFoundException, JRException {
        System.out.println("generating jasper report...");
        // 1. compile template ".jrxml" file
        JasperReport jasperReport = getJasperReport();
        // 2. parameters "empty"
        Map<String, Object> parameters = getParameters();
        // 3. datasource "java object"
        //JRDataSource datasource = getDataSource();
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;" +
                        "database=University; user=alip; password=123456;" +
                        //"integratedSecurity=false;" +
                        "encrypt=true;" +
                        "trustServerCertificate=true;" +
                        "loginTimeout=30;";

        try (
                Connection con = DriverManager.getConnection(connectionUrl)
        ) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static JasperReport getJasperReport() throws FileNotFoundException, JRException {
        File template = Paths.get("C:\\Users\\Asus\\JaspersoftWorkspace\\MyReports\\Blank_A4.jrxml").toFile();
        return JasperCompileManager.compileReport(template.getAbsolutePath());
    }

    private static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Putra");
        return parameters;
    }
}