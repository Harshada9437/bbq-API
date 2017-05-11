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
    public static String getExcelSheet(List<FeedbackResponse> feedbackRequestDTOs, String date) throws SQLException {
                    /*String filename = ConfigProperties.app_path + "/feedback/Feedbacks.xls";*/
        String filename = "D:/Feedbacks_" +date+".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");
        sheet.addMergedRegion(CellRangeAddress.valueOf("S1:X1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("Z1:AA1"));

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
        style.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFont(font);

        HSSFCellStyle style1 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style1.setFont(font);

        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);

        HSSFCellStyle style3 = workbook.createCellStyle();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
        style3.setFillForegroundColor(IndexedColors.RED.getIndex());
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
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

        k = 4;
        m = 4;
        for (Que q : uniObj) {
            rowhead.createCell(k).setCellValue(q.getId());
            k++;
            if (q.getType() == '3') {
                for (int l = 0; l < q.getAns().size() - 1; l++) {
                    rowhead.createCell(k).setCellValue("");
                    k++;
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 0, m, k-1 ));
                m=k;
            }
        }

        rowhead.createCell(k++).setCellValue("Customer");
        rowhead.createCell(k++).setCellValue("");
        rowhead.createCell(k++).setCellValue("");
        rowhead.createCell(k++).setCellValue("");
        rowhead.createCell(k++).setCellValue("");
        rowhead.createCell(k++).setCellValue("");
        rowhead.createCell(k++).setCellValue("isNegative");
        rowhead.createCell(k++).setCellValue("isAddressed Details");
        rowhead.createCell(k++).setCellValue("");

        for (Cell cell : rowhead) {
            cell.setCellStyle(style);
        }


        HSSFRow rowhead1 = sheet.createRow((short) 1);

        rowhead1.createCell(0).setCellValue("");
        rowhead1.createCell(1).setCellValue("");
        rowhead1.createCell(2).setCellValue("");
        rowhead1.createCell(3).setCellValue("");

        k = 4;
        for (Que q : uniObj) {
            if (q.getType() == '3' || q.getType() == '2') {
                for (AnswerDTO answerDTO : q.getAns()) {
                    rowhead1.createCell(k).setCellValue(answerDTO.getAnswerText());
                    k++;
                }
            } else {
                    rowhead1.createCell(k).setCellValue("");
                    k++;
            }
        }

        rowhead1.createCell(k++).setCellValue("Name");
        rowhead1.createCell(k++).setCellValue("Email");
        rowhead1.createCell(k++).setCellValue("Phone");
        rowhead1.createCell(k++).setCellValue("Date of Birth");
        rowhead1.createCell(k++).setCellValue("Date of Anniversary");
        rowhead1.createCell(k++).setCellValue("Locality");
        rowhead1.createCell(k++).setCellValue("");
        rowhead1.createCell(k++).setCellValue("isAddressed?");
        rowhead1.createCell(k++).setCellValue("View Date");


        for (Cell cell : rowhead1) {
            cell.setCellStyle(style1);
            workbook.getSheetAt(0).autoSizeColumn(cell.getColumnIndex());
        }

        rowhead1.getCell(0).setCellStyle(style);


        int i = 2, l;
        for (FeedbackResponse feedbackResponse : feedbackRequestDTOs) {
            List<String> ques = new ArrayList<String>();
            HSSFRow row = sheet.createRow((short) i);
            row.createCell(0).setCellValue(feedbackResponse.getId());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(feedbackResponse.getFeedbackDate());
            workbook.getSheetAt(0).autoSizeColumn(1);
            row.createCell(2).setCellValue(feedbackResponse.getOutletDesc());
            workbook.getSheetAt(0).autoSizeColumn(2);
            row.createCell(3).setCellValue(feedbackResponse.getTableNo());

            l = 4;
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
                    if(feedbackDetails.getAnswerText() == null || feedbackDetails.getAnswerText().equals("")){
                        row.createCell(l).setCellValue("Skipped");
                    }else {
                        row.createCell(l).setCellValue(feedbackDetails.getAnswerText());
                        row.getCell(l).setCellStyle(style2);
                    }
                    l++;
                }else if (feedbackDetails.getQuestionType() == '1') {
                    if(feedbackDetails.getAnswerDesc()== null || feedbackDetails.getAnswerDesc().equals("")){
                        row.createCell(l).setCellValue("Skipped");
                    }else {
                        row.createCell(l).setCellValue(feedbackDetails.getAnswerDesc());
                        row.getCell(l).setCellStyle(style2);
                    }
                    l++;
                }else if(feedbackDetails.getQuestionType()=='5') {
                    if (feedbackDetails.getAnswerDesc() == null || feedbackDetails.getAnswerDesc().equals("")) {
                        row.createCell(l).setCellValue("Skipped");
                    } else {
                        List<String> answers = new ArrayList<String>();
                        String answer = "";
                        if (!ques.contains(feedbackDetails.getQuestionDesc())) {
                            for (FeedbackDetails detail : feedbacks) {
                                if (detail.getQuestionDesc().equals(feedbackDetails.getQuestionDesc())) {
                                    answers.add(detail.getAnswerDesc());
                                }
                            }
                            int p=1;
                            for (String ans : answers) {
                                if(answers.size()==1){
                                    answer=ans;
                                }else{
                                    if(p==answers.size()){
                                        answer = answer + ans;
                                    }
                                    if(p!=answers.size()) {
                                        answer = answer + ans + ",";
                                    }
                                }
                                p++;
                            }
                            row.createCell(l).setCellValue(answer);
                            row.getCell(l).setCellStyle(style2);
                            ques.add(feedbackDetails.getQuestionDesc());
                            l++;
                        }
                    }
                }else {
                    if(feedbackDetails.getAnswerDesc() == null || feedbackDetails.getAnswerDesc().equals("")){
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
            if(feedbackResponse.getCustomerName() == null || feedbackResponse.getCustomerName().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                row.createCell(l++).setCellValue(feedbackResponse.getCustomerName());
                workbook.getSheetAt(0).autoSizeColumn(l - 1);
            }
            if(feedbackResponse.getEmail() == null || feedbackResponse.getEmail().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                row.createCell(l++).setCellValue(feedbackResponse.getEmail());
                workbook.getSheetAt(0).autoSizeColumn(l - 1);
            }
            if(feedbackResponse.getMobileNo() == null || feedbackResponse.getMobileNo().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                row.createCell(l++).setCellValue(feedbackResponse.getMobileNo());
                workbook.getSheetAt(0).autoSizeColumn(l - 1);
            }
            if(feedbackResponse.getDob() == null || feedbackResponse.getDob().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                Timestamp dob = DateUtil.getTimeStampFromString(feedbackResponse.getDob());
                row.createCell(l++).setCellValue(DateUtil.format(dob, "dd-MMM"));
            }
            if(feedbackResponse.getDoa() == null || feedbackResponse.getDoa().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                Timestamp doa = DateUtil.getTimeStampFromString(feedbackResponse.getDoa());
                row.createCell(l++).setCellValue(DateUtil.format(doa,"dd-MMM"));
            }
            if(feedbackResponse.getLocality()== null || feedbackResponse.getLocality().equals("")){
                row.createCell(l++).setCellValue("-");
            }else {
                row.createCell(l++).setCellValue(feedbackResponse.getLocality());
                workbook.getSheetAt(0).autoSizeColumn(l-1);
            }
            if (feedbackResponse.getIsNegative() == 0) {
                row.createCell(l++).setCellValue("NO");
            } else {
                row.createCell(l++).setCellValue("YES");
            }
            if (feedbackResponse.getViewDate() == null || feedbackResponse.getViewDate().equals("")) {
                row.createCell(l++).setCellValue("NO");
                row.createCell(l++).setCellValue("-");
            } else {
                row.createCell(l++).setCellValue("YES");
                row.createCell(l++).setCellValue(feedbackResponse.getViewDate());
                workbook.getSheetAt(0).autoSizeColumn(l-1);
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
       // EmailService.sendOtp("thakur.harshada.09@gmail.com","",1234);

        return filename;
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
