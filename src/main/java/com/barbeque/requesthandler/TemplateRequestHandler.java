package com.barbeque.requesthandler;

import com.barbeque.bo.UpdateAssignQuestionRequestBO;
import com.barbeque.dao.template.QueTempDAO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.dto.request.QueTempDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.dto.request.TemplateDTO;
import com.barbeque.dto.request.UpdateAssignQuestionDTO;
import com.barbeque.exceptions.QuestionNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.bo.AssignQuestionRequestBO;
import com.barbeque.bo.TemplateRequestBO;
import com.barbeque.bo.UpdateTemplateRequestBO;
import com.barbeque.request.template.UpdateAssignQuestionRequest;
import com.barbeque.response.template.GetTemplateResponse;
import com.barbeque.response.template.QueResponse;
import com.barbeque.response.template.TemplateResponseList;

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
        return templateDTO;
    }

    public Boolean assignQuestion(AssignQuestionRequestBO assignQuestionRequestBO, int templateId) throws SQLException {
        QueTempDAO queTempDAO = new QueTempDAO();
        Boolean isCreated = queTempDAO.assignQuestion(buildDTOFromBO(assignQuestionRequestBO), templateId);
        return isCreated;
    }

    private QueTempDTO buildDTOFromBO(AssignQuestionRequestBO assignQuestionRequestBO) {
        QueTempDTO queTempDTO = new QueTempDTO();

        queTempDTO.setQueId(assignQuestionRequestBO.getQuestionId());
        queTempDTO.setPriority(assignQuestionRequestBO.getPriority());

        return queTempDTO;
    }

    public List<QueResponse> getAssignedQuestions(int templateId) throws SQLException, TemplateNotFoundException, QuestionNotFoundException {
        QueTempDAO queTempDAO = new QueTempDAO();
        List<QueResponse> queList = getTempQueResponseListFromDTOs(queTempDAO.getAssignedQuestions(templateId));
        return queList;
    }

    private List<QueResponse> getTempQueResponseListFromDTOs(List<QueTempDTO> QueTempDTOs) throws QuestionNotFoundException, SQLException {
        List<QueResponse> queResponses = new ArrayList<QueResponse>();
        Iterator<QueTempDTO> queTempDTOIterator = QueTempDTOs.iterator();
        while (queTempDTOIterator.hasNext()) {
            QueTempDTO queTempDTO = queTempDTOIterator.next();
            QueResponse queResponse = new QueResponse();
            queResponse.setId(queTempDTO.getQueId());
            queResponse.setAnswerSymbol(queTempDTO.getAnswerSymbol());
            queResponse.setParentAnswerId(queTempDTO.getParentAnswerId());
            queResponse.setParentQuestionId(queTempDTO.getParentQuestionId());
            queResponse.setQuestionDesc(queTempDTO.getQuestionDesc());
            queResponse.setParentQuestionDesc(queTempDTO.getParentQuestionDesc());
            queResponse.setParentAnswerDesc(queTempDTO.getParentAnswerDesc());
            queResponse.setQuestionType(queTempDTO.getQuestionType());
            queResponse.setThreshold(queTempDTO.getThreshold());
            queResponse.setOptions(QuestionRequestHandler.getAnswer(queTempDTO.getQueId()));
            queResponse.setPriority(queTempDTO.getPriority());
            queResponses.add(queResponse);
        }
        return queResponses;
    }

    public void removeQuestionDetails(int templateId, int queId) throws SQLException, TemplateNotFoundException {
        QueTempDAO tempDAO = new QueTempDAO();
        tempDAO.removeQuestionDetails(templateId, queId);
    }

    public boolean updateTemplate(UpdateTemplateRequestBO updateTemplateRequestBO) {
        Boolean isProcessed;
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

    public boolean updateAssignQuestion(UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO) throws SQLException
    {
        Boolean isProcessed;
        QueTempDAO queTempDAO = new QueTempDAO();
        try {
            isProcessed = queTempDAO.updateupdateAssignQuestion(buildUpdateBOFromDTO(updateAssignQuestionRequestBO));
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private UpdateAssignQuestionDTO buildUpdateBOFromDTO(UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO)
    {
        UpdateAssignQuestionDTO updateAssignQuestionDTO=new UpdateAssignQuestionDTO();
        updateAssignQuestionDTO.setTemplateId(updateAssignQuestionRequestBO.getTemplateId());
        updateAssignQuestionDTO.setPriority(updateAssignQuestionRequestBO.getPriority());
        updateAssignQuestionDTO.setQuestionId(updateAssignQuestionRequestBO.getQuestionId());
        return updateAssignQuestionDTO;
    }


    public List<TemplateResponseList> getTemplate() throws SQLException, TemplateNotFoundException {
        TemplateDAO templateDAO = new TemplateDAO();
        List<TemplateResponseList> templateResponseLists = new ArrayList<TemplateResponseList>();
        try {
            templateResponseLists = getTemplateListDTOsFromBO(templateDAO.getTemplate());
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return templateResponseLists;
    }

    public List<TemplateResponseList> getTemplateListDTOsFromBO(List<TemplateDTO> templateDTOs) throws SQLException {
        List<TemplateResponseList> templateResponseList = new ArrayList<TemplateResponseList>();
        Iterator<TemplateDTO> templateDTOIterator = templateDTOs.iterator();
        while (templateDTOIterator.hasNext()) {
            TemplateDTO templateDTO = templateDTOIterator.next();
            TemplateResponseList templateResponseList1 = new TemplateResponseList(templateDTO.getId(), templateDTO.getOutletId(),
                    templateDTO.getTemplateDesc(),
                    templateDTO.getStatus(), templateDTO.getOutletDesc(), templateDTO.getShortDesc());
            templateResponseList.add(templateResponseList1);
        }
        return templateResponseList;
    }

    public GetTemplateResponse getTemplateById(int templateId, int outletId) throws SQLException, TemplateNotFoundException {
        TemplateDAO templateDAO = new TemplateDAO();
        GetTemplateResponse response = buildResponseFromDTO(templateDAO.getTemplateInfo(templateId,outletId));
        return response;
    }

    private GetTemplateResponse buildResponseFromDTO(TempDTO tempDTO) {
        GetTemplateResponse response = new GetTemplateResponse(tempDTO.getTemplateId(),
                tempDTO.getDesc(),
                tempDTO.getStatus(),
                tempDTO.getFromDate(),
                tempDTO.getToDate(),
                tempDTO.getOutletId());
        return response;
    }
}