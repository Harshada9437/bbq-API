package com.barbeque.util;

/**
 * Created by System-2 on 3/2/2017.
 */

import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.FeedbackResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExcelCreator {
    public void getExcelSheet(List<FeedbackResponse> feedbackRequestDTOs, String date) throws SQLException {
                    /*String filename = ConfigProperties.app_path + "/feedback/Feedbacks.xls";*/
        String filename = "D:/Feedbacks_" +date+".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");


        sheet.addMergedRegion(CellRangeAddress.valueOf("E1:J1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("N1:P1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("Q1:T1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("K1:L1"));

        HSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Verdana");


        HSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);

        HSSFCellStyle style1 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style1.setFont(font);

        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);

        HSSFCellStyle style3 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style3.setFillForegroundColor(IndexedColors.RED.getIndex());
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style3.setFont(font);

        int k,m;
        List<String> uniqIds = new ArrayList<String>();
        List<Que> uniObj = new ArrayList<Que>();
        for (FeedbackResponse feedbackResponse : feedbackRequestDTOs) {
            for (FeedbackDetails feedbackDetails : feedbackResponse.getFeedbacks()) {
                if (!uniqIds.contains(feedbackDetails.getQuestionDesc())) {
                    uniqIds.add(feedbackDetails.getQuestionDesc());
                    List<AnswerDTO> answers = AnswerDAO.getAnswer(feedbackDetails.getQuestionId());
                    Que que = new Que();
                    que.setId(feedbackDetails.getQuestionDesc());
                    que.setType(feedbackDetails.getQuestionType());
                    que.setAns(answers);
                    uniObj.add(que);
                }
            }
            break;
        }


        HSSFRow rowhead = sheet.createRow((short) 0);

        rowhead.createCell(0).setCellValue("Response No.");
        rowhead.createCell(1).setCellValue("Date");
        rowhead.createCell(2).setCellValue("Outlet");
        rowhead.createCell(3).setCellValue("Table No");
        rowhead.createCell(4).setCellValue("Customer");
        rowhead.createCell(5).setCellValue("");
        rowhead.createCell(6).setCellValue("");
        rowhead.createCell(7).setCellValue("");
        rowhead.createCell(8).setCellValue("");
        rowhead.createCell(9).setCellValue("");
        rowhead.createCell(10).setCellValue("isAddressed Details");
        rowhead.createCell(11).setCellValue("");
        rowhead.createCell(12).setCellValue("isNegative");
        k = 13;
        m = 13;
        for (Que q : uniObj) {
            rowhead.createCell(k).setCellValue(q.getId());
            k++;
            if (q.getType() == '3' || q.getType() == '2') {
                for (int l = 0; l < q.getAns().size() - 1; l++) {
                    rowhead.createCell(k).setCellValue("");
                    k++;
                }
            }
        }
        for (Cell cell : rowhead) {
            cell.setCellStyle(style);
        }


        HSSFRow rowhead1 = sheet.createRow((short) 1);

        rowhead1.createCell(0).setCellValue("");
        rowhead1.createCell(1).setCellValue("");
        rowhead1.createCell(2).setCellValue("");
        rowhead1.createCell(3).setCellValue("");
        rowhead1.createCell(4).setCellValue("Name");
        rowhead1.createCell(5).setCellValue("Email");
        rowhead1.createCell(6).setCellValue("Phone");
        rowhead1.createCell(7).setCellValue("Date of Birth");
        rowhead1.createCell(8).setCellValue("Date of Anniversary");
        rowhead1.createCell(9).setCellValue("Locality");
        rowhead1.createCell(10).setCellValue("isAddressed?");
        rowhead1.createCell(11).setCellValue("View Date");
        rowhead1.createCell(12).setCellValue("");

        k = 13;
        for (Que q : uniObj) {
            if (q.getType() == '3' || q.getType() == '2') {
                for (AnswerDTO answerDTO : q.getAns()) {
                    rowhead1.createCell(k).setCellValue(answerDTO.getAnswerText());
                    k++;
                }

            } else {
                for (int l = 0; l < q.getAns().size(); l++) {
                    rowhead1.createCell(k).setCellValue("");
                    k++;
                }
            }
        }

        for (Cell cell : rowhead1) {
            cell.setCellStyle(style1);
            workbook.getSheetAt(0).autoSizeColumn(cell.getColumnIndex());
        }

        rowhead1.getCell(0).setCellStyle(style);


        int i = 2, l;
        for (
                FeedbackResponse feedbackResponse : feedbackRequestDTOs)

        {
            HSSFRow row = sheet.createRow((short) i);
            row.createCell(0).setCellValue(feedbackResponse.getId());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(feedbackResponse.getFeedbackDate());
            workbook.getSheetAt(0).autoSizeColumn(1);
            row.createCell(2).setCellValue(feedbackResponse.getOutletDesc());
            workbook.getSheetAt(0).autoSizeColumn(2);
            row.createCell(3).setCellValue(feedbackResponse.getTableNo());
            row.createCell(4).setCellValue(feedbackResponse.getCustomerName());
            workbook.getSheetAt(0).autoSizeColumn(4);
            row.createCell(5).setCellValue(feedbackResponse.getEmail());
            workbook.getSheetAt(0).autoSizeColumn(5);
            row.createCell(6).setCellValue(feedbackResponse.getMobileNo());
            workbook.getSheetAt(0).autoSizeColumn(6);
            if(feedbackResponse.getDob().equals("")){
                row.createCell(7).setCellValue("-");
            }else {
                Timestamp dob = DateUtil.getTimeStampFromString(feedbackResponse.getDob());
                row.createCell(7).setCellValue(DateUtil.format(dob, "dd-MMM"));
            }
            if(feedbackResponse.getDoa().equals("")){
                row.createCell(7).setCellValue("-");
            }else {
                Timestamp doa = DateUtil.getTimeStampFromString(feedbackResponse.getDoa());
                row.createCell(8).setCellValue(DateUtil.format(doa,"dd-MMM"));
            }

            row.createCell(9).setCellValue(feedbackResponse.getLocality());
            workbook.getSheetAt(0).autoSizeColumn(9);
            if (feedbackResponse.getIsAddressed() == 0) {
                row.createCell(10).setCellValue("NO");
                row.createCell(11).setCellValue("-");
            } else {
                row.createCell(10).setCellValue("YES");
                row.createCell(11).setCellValue(feedbackResponse.getViewDate());
                workbook.getSheetAt(0).autoSizeColumn(11);
            }
            if (feedbackResponse.getIsNegative() == 0) {
                row.createCell(12).setCellValue("NO");
            } else {
                row.createCell(12).setCellValue("YES");
            }
            l = 13;
            List<FeedbackDetails> feedbacks = feedbackResponse.getFeedbacks();
            for (FeedbackDetails feedbackDetails : feedbacks) {
                if (feedbackDetails.getQuestionType() == '3' || feedbackDetails.getQuestionType() == '2') {
                    AnswerDTO answer = getAnswer(feedbackDetails);
                    if(answer.getRating()==0){
                        row.createCell(l).setCellValue("Skipped");
                    }else {
                        row.createCell(l).setCellValue(getReview(answer.getRating()));
                    }
                    if(answer.getIsPoor()==1){
                        row.getCell(l).setCellStyle(style3);
                    }
                    workbook.getSheetAt(0).autoSizeColumn(l);
                    l++;
                } else if (feedbackDetails.getQuestionType() == '4') {
                    if(feedbackDetails.getAnswerText().equals("")){
                        row.createCell(l).setCellValue("Skipped");
                    }else {
                        row.createCell(l).setCellValue(feedbackDetails.getAnswerText());
                        row.getCell(l).setCellStyle(style2);
                    }
                    l++;
                } else {
                    if(feedbackDetails.getAnswerDesc()==null){
                        row.createCell(l).setCellValue("Skipped");
                    }else {
                        row.createCell(l).setCellValue(feedbackDetails.getAnswerDesc());
                    }
                    if(feedbackDetails.getIsNegative()==1){
                        row.getCell(l).setCellStyle(style3);
                    }
                    l++;
                }
            }
            i++;
        }

        FileOutputStream fileOut = null;
        try

        {
            fileOut = new FileOutputStream(filename);
        } catch (
                FileNotFoundException e)

        {
            e.printStackTrace();
        }
        try

        {
            workbook.write(fileOut);
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        try

        {
            fileOut.close();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        System.out.println("Your excel file has been generated!");
        EmailService.sendOtp("thakur.harshada.09@gmail.com","",1234);
    }

    public static String getReview(int rating) {
        String review = "";
        if (rating <= 20) {
            review = "Poor";
        } else if (rating <= 40) {
            review = "Average";
        } else if (rating <= 60) {
            review = "Good";
        } else if (rating <= 80) {
            review = "Very Good";
        } else if (rating <= 100) {
            review = "Excellent";
        }
        return review;
    }

    public static AnswerDTO getAnswer(FeedbackDetails feedbackDetails) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswerDesc(feedbackDetails.getAnswerDesc());
        answerDTO.setRating(feedbackDetails.getRating());
        answerDTO.setIsPoor(feedbackDetails.getIsNegative());
        return answerDTO;
    }

}
