package com.barbeque.util;

/**
 * Created by System-2 on 3/2/2017.
 */

import com.barbeque.config.ConfigProperties;
import com.barbeque.dao.answer.AnswerDAO;
import com.barbeque.dto.request.AnswerDTO;
import com.barbeque.request.feedback.FeedbackDetails;
import com.barbeque.response.feedback.FeedbackResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Colour;

import java.io.File;
import java.lang.Boolean;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExcelCreator {
    public static VerticalAlignment CENTRE = VerticalAlignment.getAlignment(1);
    public static String getExcelSheet(List<FeedbackResponse> feedbackRequestDTOs, String date) throws Exception {
        String filename = ConfigProperties.app_path + "/feedback/Feedback_"+date+".xls";
      /*  String filename = "D:/Feed_" +date+".xls";*/
        int sheetNo = 1,i = 2, l;
        Label label = null;

        WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
        WritableSheet sheet = workbook.createSheet("FirstSheet", 0);

        /*sheet.getSettings().setDefaultColumnWidth(12); */

        WritableFont wf = new WritableFont(WritableFont.createFont("Verdana"));
        wf.setBoldStyle(wf.BOLD);
        wf.setPointSize(10);
        wf.setColour(Colour.WHITE);


        WritableCellFormat style = new WritableCellFormat();
        style.setBackground(jxl.format.Colour.GREEN);
        style.setAlignment(Alignment.CENTRE);
        style.setVerticalAlignment(CENTRE);
        style.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        style.setFont(wf);
        style.setWrap(Boolean.TRUE);

        WritableCellFormat style1 = new WritableCellFormat();
        style1.setBackground(jxl.format.Colour.SKY_BLUE);
        style1.setAlignment(Alignment.CENTRE);
        style1.setVerticalAlignment(CENTRE);
        style1.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        style1.setFont(wf);
        style1.setWrap(Boolean.TRUE);

        WritableCellFormat style2 = new WritableCellFormat();
        style2.setWrap(Boolean.TRUE);
       // style.setVerticalAlignment(CENTRE);

        WritableCellFormat style3 = new WritableCellFormat();
        style3.setBackground(jxl.format.Colour.RED);
        //style.setVerticalAlignment(CENTRE);
        //style3.setAlignment(Alignment.CENTRE);
        style3.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
        style3.setFont(wf);
        //style3.setWrap(Boolean.TRUE);

        /*sheet.getSettings().setDefaultColumnWidth(60);
        sheet.getSettings().setDefaultRowHeight(28 * 20);
*/
        int k, m;
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


        label= new Label(0, 0, "Response No.", style);
        sheet.setColumnView(0, 18);
        sheet.addCell(label);
        label = new Label(1, 0, "Date", style);
        sheet.setColumnView(1, 18);
        sheet.addCell(label);
        label = new Label(2, 0, "Outlet", style);
        sheet.setColumnView(2, 25);
        sheet.addCell(label);
        label = new Label(3, 0, "Table No", style);
        sheet.addCell(label);

        k = 4;
        m = 4;
        for (Que q : uniObj) {
            sheet.setColumnView(k, 300);
            sheet.addCell( new Label(k, 0, q.getId(), style));
            k++;
            if (q.getType() == '3') {
                for (int x = 0; x < q.getAns().size() - 1; x++) {
                    label = new Label(k, 0, "", style);
                    sheet.addCell(label);
                    k++;
                }
                sheet.mergeCells(m, 0, k-1, 0 );
                m=k;
            }
        }
        m=k;
        label = new Label(k++, 0, "Customer", style);
        sheet.setColumnView(k-1, 30);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.setColumnView(k-1, 25);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.setColumnView(k-1, 13);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.addCell(label);
        sheet.mergeCells(m, 0, k-1, 0 );
        label = new Label(k++, 0, "isNegative", style);
        sheet.setColumnView(k-1, 18);
        sheet.addCell(label);
        m=k;
        label = new Label(k++, 0, "isAddressed Details", style);
        sheet.addCell(label);
        label = new Label(k++, 0, "", style);
        sheet.addCell(label);
        sheet.mergeCells(m, 0, k-1, 0 );

        sheet.mergeCells(0, 0, 0, 1 );

        label = new Label(0, 1, "", style);
        sheet.setColumnView(0, 18);
        sheet.addCell(label);
        label = new Label(1, 1, "", style1);
        sheet.setColumnView(1, 18);
        sheet.addCell(label);
        label = new Label(2, 1, "", style1);
        sheet.setColumnView(2, 18);
        sheet.addCell(label);
        label = new Label(3, 1, "", style1);
        sheet.addCell(label);
        k = 4;
        for (Que q : uniObj) {
            if (q.getType() == '3' || q.getType() == '2') {
                for (AnswerDTO answerDTO : q.getAns()) {
                    label = new Label(k, 1, answerDTO.getAnswerText(), style1);
                    sheet.setColumnView(k, 18);
                    sheet.addCell(label);
                    k++;
                }

            } else {
                label = new Label(k, 1, "", style1);
                sheet.addCell(label);
                k++;
            }
        }
        label = new Label(k++, 1, "Name", style1);
        sheet.addCell(label);
        label = new Label(k++, 1, "Email", style1);
        sheet.addCell(label);
        label = new Label(k++, 1, "Phone", style1);
        sheet.addCell(label);
        label = new Label(k++, 1, "Date of Birth", style1);
        sheet.setColumnView(k-1, 18);
        sheet.addCell(label);
        label = new Label(k++, 1, "Date of Anniversary", style1);
        sheet.setColumnView(k-1, 18);
        sheet.addCell(label);
        label = new Label(k++, 1, "Locality", style1);
        sheet.addCell(label);
        label = new Label(k++, 1, "", style1);
        sheet.addCell(label);
        label = new Label(k++, 1, "isAddressed?", style1);
        sheet.setColumnView(k-1, 18);
        sheet.addCell(label);
        label = new Label(k++, 1, "View Date", style1);
        sheet.setColumnView(k-1, 18);
        sheet.addCell(label);


        for (FeedbackResponse feedbackResponse : feedbackRequestDTOs) {
            List<String> ques = new ArrayList<String>();

            label = new Label(0, i, String.valueOf(feedbackResponse.getId()), style);
            sheet.addCell(label);
            label = new Label(1, i, feedbackResponse.getFeedbackDate());
            sheet.setColumnView(1, 18);
            sheet.addCell(label);
            label = new Label(2, i,feedbackResponse.getOutletDesc());
            sheet.addCell(label);
            label = new Label(3, i,feedbackResponse.getTableNo());
            sheet.addCell(label);

            l = 4;
            List<FeedbackDetails> feedbacks = feedbackResponse.getFeedbacks();
            for (FeedbackDetails feedbackDetails : feedbacks) {
                if (feedbackDetails.getQuestionType() == '3' || feedbackDetails.getQuestionType() == '2') {
                    AnswerDTO answer = getAnswer(feedbackDetails);
                    if (answer.getRating() == 0) {
                        Label m5 = new Label(l, i,"Skipped");
                        sheet.addCell(m5);
                    } else {
                        if (answer.getIsPoor() == 1) {
                            label = new Label(l, i,getReview(answer.getRating()),style3);
                            sheet.addCell(label);
                        }else {
                            label = new Label(l, i, getReview(answer.getRating()));
                            sheet.addCell(label);
                        }
                    }
                    l++;
                } else if (feedbackDetails.getQuestionType() == '4') {
                    if (feedbackDetails.getAnswerText() == null || feedbackDetails.getAnswerText().equals("")) {
                        label = new Label(l, i,"Skipped");
                        sheet.addCell(label);
                    } else {
                        label = new Label(l, i,feedbackDetails.getAnswerText(),style2);
                        sheet.addCell(label);
                    }
                    l++;
                } else if (feedbackDetails.getQuestionType() == '1') {
                    if (feedbackDetails.getAnswerDesc() == null || feedbackDetails.getAnswerDesc().equals("")) {
                        label = new Label(l, i,"Skipped");
                        sheet.addCell(label);
                    } else {
                        label = new Label(l, i,feedbackDetails.getAnswerDesc());
                        sheet.addCell(label);
                    }
                    l++;
                } else if (feedbackDetails.getQuestionType() == '5') {
                    if (feedbackDetails.getAnswerDesc() == null || feedbackDetails.getAnswerDesc().equals("")) {
                        label = new Label(l, i,"Skipped");
                        sheet.addCell(label);
                    } else {
                        List<String> answers = new ArrayList<String>();
                        String answer = "";
                        if (!ques.contains(feedbackDetails.getQuestionDesc())) {
                            for (FeedbackDetails detail : feedbacks) {
                                if (detail.getQuestionDesc().equals(feedbackDetails.getQuestionDesc())) {
                                    answers.add(detail.getAnswerDesc());
                                }
                            }
                                int p = 1;
                                for (String ans : answers) {
                                    if (p == 1) {
                                        answer = ans;
                                    } else {
                                        answer = answer + "," + ans;
                                    }
                                    p++;
                            }
                            label = new Label(l, i,answer,style2);
                            sheet.addCell(label);
                            ques.add(feedbackDetails.getQuestionDesc());
                        }else{
                            l=l-1;
                        }
                    }
                    l++;
                } else {
                    if (feedbackDetails.getAnswerDesc() == null || feedbackDetails.getAnswerDesc().equals("")) {
                        label = new Label(l, i,"Skipped");
                        sheet.addCell(label);
                    } else {
                        if (feedbackDetails.getIsNegative() == 1) {
                            label = new Label(l, i, feedbackDetails.getAnswerDesc(),style3);
                            sheet.addCell(label);
                        }else {
                            label = new Label(l, i, feedbackDetails.getAnswerDesc());
                            sheet.addCell(label);
                        }
                    }
                    l++;
                }
            }
            label = new Label(l++, i,feedbackResponse.getCustomerName());
            sheet.addCell(label);

            if (feedbackResponse.getEmail() == null || feedbackResponse.getEmail().equals("")) {
                label = new Label(l++, i,"-");
                sheet.addCell(label);
            } else {
                label = new Label(l++, i,feedbackResponse.getEmail());
                sheet.addCell(label);
            }
            label = new Label(l++, i,feedbackResponse.getMobileNo());
            sheet.addCell(label);

            if (feedbackResponse.getDob() == null || feedbackResponse.getDob().equals("")) {
                label = new Label(l++, i,"-");
                sheet.addCell(label);
            } else {
                Timestamp dob = DateUtil.getTimeStampFromString(feedbackResponse.getDob());
                label = new Label(l++, i,DateUtil.format(dob, "dd-MMM"));
                sheet.addCell(label);
            }
            if (feedbackResponse.getDoa() == null || feedbackResponse.getDoa().equals("")) {
                label = new Label(l++, i,"-");
                sheet.addCell(label);
            } else {
                Timestamp doa = DateUtil.getTimeStampFromString(feedbackResponse.getDoa());
                label = new Label(l++, i,DateUtil.format(doa, "dd-MMM"));
                sheet.addCell(label);
            }
            if (feedbackResponse.getLocality() == null || feedbackResponse.getLocality().equals("")) {
                label = new Label(l++, i,"-");
                sheet.addCell(label);
            } else {
                label = new Label(l++, i,feedbackResponse.getLocality());
                sheet.addCell(label);
            }
            if (feedbackResponse.getIsNegative() == 0) {
                label = new Label(l++, i,"NO");
                sheet.addCell(label);
            } else {
                label = new Label(l++, i,"YES",style3);
                sheet.addCell(label);
            }
            if (feedbackResponse.getViewDate() == null || feedbackResponse.getViewDate().equals("")) {
                label = new Label(l++, i,"NO");
                sheet.addCell(label);
                label = new Label(l++, i,"-");
                sheet.addCell(label);
            } else {
                label = new Label(l++, i,"YES");
                sheet.addCell(label);
                label = new Label(l++, i,feedbackResponse.getViewDate());
                sheet.addCell(label);
            }

            i++;

            if (i == 50000) {
                String sheetName = "Document-" + sheetNo;
                sheet = workbook.createSheet(sheetName,sheetNo);
                /*sheet.getSettings().setDefaultColumnWidth(12);*/
                sheetNo++;
                i = 0;
            }
        }

        workbook.write();
        workbook.close();

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