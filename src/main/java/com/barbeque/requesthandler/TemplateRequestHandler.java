package com.barbeque.requesthandler;

import com.barbeque.dao.template.QueTempDAO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.dto.request.QueTempDTO;
import com.barbeque.dto.request.TemplateDTO;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.request.bo.AssignQuestionRequestBO;
import com.barbeque.request.bo.TemplateRequestBO;
import com.barbeque.request.bo.UpdateTemplateRequestBO;
import com.barbeque.response.template.TempQueLResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateRequestHandler {
    public Integer createTemplate(TemplateRequestBO templateRequestBO) throws SQLException {
        TemplateDAO templateDAO = new TemplateDAO();
        int id = templateDAO.createTemplate(buildTemplateDTOFromBO(templateRequestBO));
        return id;
    }

    private TemplateDTO buildTemplateDTOFromBO(TemplateRequestBO templateRequestBO) {
        TemplateDTO templateDTO = new TemplateDTO();
        templateDTO.setTemplateDesc(templateRequestBO.getTemplateDesc());
        templateDTO.setStatus(templateRequestBO.getStatus());
        return templateDTO;
    }

    public Boolean assignQuestion(AssignQuestionRequestBO assignQuestionRequestBO, int templateId) throws SQLException {
        QueTempDAO queTempDAO = new QueTempDAO();
        Boolean isCreated = queTempDAO.assignQuestion(buildDTOFromBO(assignQuestionRequestBO), templateId);
        return isCreated;
    }

    private QueTempDTO buildDTOFromBO(AssignQuestionRequestBO batchRequestBO) {
        QueTempDTO queTempDTO = new QueTempDTO();

        queTempDTO.setQuestionId(batchRequestBO.getQuestionId());
        queTempDTO.setPriority(batchRequestBO.getPriority());

        return queTempDTO;
    }

    public List<TempQueLResponse> getAssignedQuestions(int templateId) throws SQLException, TemplateNotFoundException {
        List<TempQueLResponse> queList = new ArrayList<TempQueLResponse>();
        QueTempDAO queTempDAO = new QueTempDAO();
        queList = getTempQueResponseListFromDTOs(queTempDAO.getAssignedQuestions(templateId));
        return queList;
    }

    private List<TempQueLResponse> getTempQueResponseListFromDTOs(List<QueTempDTO> QueTempDTOs) {
        List<TempQueLResponse> tempQueLResponseList = new ArrayList<TempQueLResponse>();
        Iterator<QueTempDTO> queTempDTOIterator = QueTempDTOs.iterator();
        while (queTempDTOIterator.hasNext()) {
            QueTempDTO queTempDTO = queTempDTOIterator.next();
            TempQueLResponse tempQueLResponse = new TempQueLResponse(queTempDTO.getId(),
                    queTempDTO.getQuestionId(),
                    queTempDTO.getPriority(),
                    queTempDTO.getQuestionText());
            tempQueLResponseList.add(tempQueLResponse);
        }
        return tempQueLResponseList;
    }

    public void removeQuestionDetails(int templateId, int queId) throws SQLException, TemplateNotFoundException {
        QueTempDAO tempDAO = new QueTempDAO();
        tempDAO.removeQuestionDetails(templateId, queId);
    }

    public boolean updateTemplate(UpdateTemplateRequestBO updateTemplateRequestBO) {
        Boolean isProcessed = Boolean.FALSE;
        TemplateDAO templateDAO = new TemplateDAO();
        try {
            isProcessed = templateDAO.updateTemplate(buildUpdateBOFromDTO(updateTemplateRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private TemplateDTO buildUpdateBOFromDTO(UpdateTemplateRequestBO updateTemplateRequestBO) {
        TemplateDTO templateDTO = new TemplateDTO();
        templateDTO.setId(updateTemplateRequestBO.getId());
        templateDTO.setTemplateDesc(updateTemplateRequestBO.getTemplateDesc());
        templateDTO.setStatus(updateTemplateRequestBO.getStatus());
        return templateDTO;
    }
/*
    public List<CourseResonse> getCoursesList() throws SQLException {
        TemplateDAO courseDAO = new TemplateDAO();
        List<CourseResonse> coursesList = new ArrayList<CourseResonse>();
        try {
            List<TemplateDTO> templateDTOList = courseDAO
                    .getAllCourses();

            for (TemplateDTO templateDTO : templateDTOList) {
                CourseResonse getCourseResonse = new CourseResonse();
                getCourseResonse.setId(templateDTO.getId());
                getCourseResonse.setName(templateDTO.getTemplateDesc());
                getCourseResonse.setDescription(templateDTO.getStatus());
                getCourseResonse.setCode(templateDTO.getCode());
                getCourseResonse.setAttendanceType(templateDTO.getAttendanceType());
                getCourseResonse.setTotalWorkingDays(templateDTO.getTotalWorkingDays());
                getCourseResonse.setSyllabusName(templateDTO.getAttendanceType());
                getCourseResonse.setSubjectAssignType(templateDTO.getSubjectAssignType());
                coursesList.add(getCourseResonse);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return coursesList;
    }
}*/
}