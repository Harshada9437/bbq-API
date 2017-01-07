package com.barbeque.xlxFiles;

/**
 * Created by System-2 on 1/7/2017.
 */
import  java.io.*;
import java.util.List;

import com.barbeque.config.ConfigProperties;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.FeedbackResponse;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelCreator {
        public static void getExcelSheet(List<FeedbackResponse> feedbackRequestDTOs)
        {
            String filename = ConfigProperties.app_path +"/feedback/Feedbacks.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Feedback No.");
            rowhead.createCell(1).setCellValue("Customer Name");
            rowhead.createCell(2).setCellValue("Customer Number");
            rowhead.createCell(3).setCellValue("Outlet Desc");
            rowhead.createCell(4).setCellValue("Table ");
            rowhead.createCell(5).setCellValue("Question Id");
            rowhead.createCell(6).setCellValue("Answer Id");
            rowhead.createCell(7).setCellValue("Answer Text");
            rowhead.createCell(8).setCellValue("Rating");
            rowhead.createCell(9).setCellValue("Modified On");
            rowhead.createCell(10).setCellValue("Created On");

            int i =1;
            for (FeedbackResponse feedbackRequestDTO : feedbackRequestDTOs) {
                for(FeedbackDetails feedbackDetails : feedbackRequestDTO.getFeedbacks()){
                    HSSFRow row = sheet.createRow((short) i);
                    row.createCell(0).setCellValue(feedbackRequestDTO.getId());
                    row.createCell(1).setCellValue(feedbackRequestDTO.getCustomerName());
                    row.createCell(2).setCellValue(feedbackRequestDTO.getMobileNo());
                    row.createCell(3).setCellValue(feedbackRequestDTO.getOutletDesc());
                    row.createCell(4).setCellValue(feedbackRequestDTO.getTableNo());
                    row.createCell(5).setCellValue(feedbackDetails.getQuestionId());
                    row.createCell(6).setCellValue(feedbackDetails.getAnswerId());
                    row.createCell(7).setCellValue(feedbackDetails.getAnswerText());
                    row.createCell(8).setCellValue(feedbackDetails.getRating());
                    row.createCell(9).setCellValue(feedbackRequestDTO.getModifiedOn());
                    row.createCell(10).setCellValue(feedbackRequestDTO.getCreatedOn());
                    i++;
                }
            }

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Your excel file has been generated!");
        }
}

