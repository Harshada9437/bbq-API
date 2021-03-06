package com.barbeque.requesthandler;

import com.barbeque.bo.AssignTemplateRequestBO;
import com.barbeque.bo.OutletListRequestBO;
import com.barbeque.bo.UpdateSettingsRequestBO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.dto.request.UpdateSettingsDTO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.UserNotFoundException;
import com.barbeque.response.outlet.OutletResponseL;
import com.barbeque.response.outlet.OutletResponseList;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletRequestHandler {
    public Boolean assignTemplate(AssignTemplateRequestBO assignTemplateRequestBO, int outletId) throws SQLException {
        OutletDAO outletDAO = new OutletDAO();
        Boolean isCreated;
        List<TempDTO> tempDTOs = TemplateDAO.getTemplateByOutletId(outletId);
        if (tempDTOs.size() == 0) {
             isCreated = outletDAO.assignTemplate(buildOutletDTOFromBO(assignTemplateRequestBO), outletId);
        }else{
            isCreated = outletDAO.updateAssignTemplate(buildOutletDTOFromBO(assignTemplateRequestBO), outletId);
        }
        return isCreated;
    }

    private TempDTO buildOutletDTOFromBO(AssignTemplateRequestBO assignTemplateRequestBO) {
        TempDTO tempDTO = new TempDTO();
        tempDTO.setTemplateId(assignTemplateRequestBO.getTemplateId());
        tempDTO.setFromDate(assignTemplateRequestBO.getFromDate());
        tempDTO.setToDate(assignTemplateRequestBO.getToDate());
        return tempDTO;
    }

    public List<OutletResponseL> getOutlate(OutletListRequestBO outletListRequestBO) throws SQLException, UserNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        List<OutletResponseL> outletResponseLists = getOutletListDTOsFromBO(outletDAO.getOutlet(outletListRequestBO.getOutletId(), outletListRequestBO.getUserId()));
        return outletResponseLists;
    }

    public List<OutletResponseL> getOutletListDTOsFromBO(List<OutletDTO> outletDTOs) throws SQLException {
        List<OutletResponseL> outletResponseLists = new ArrayList<OutletResponseL>();
        Iterator<OutletDTO> outletListDTOIterator = outletDTOs.iterator();
        while (outletListDTOIterator.hasNext()) {
            OutletDTO outletDTO = outletListDTOIterator.next();
            OutletResponseL outletResponse = new OutletResponseL();
            outletResponse.setPosStoreId(outletDTO.getPosStoreId());
            outletResponse.setId(outletDTO.getId());
            outletResponse.setTemplateName(outletDTO.getTemplateName());
            outletResponse.setTemplateId(outletDTO.getTemplateId());
            outletResponse.setGroupId(outletDTO.getGroupId());
            outletResponse.setRegionId(outletDTO.getRegionId());
            outletResponse.setCompanyId(outletDTO.getCompanyId());
            outletResponse.setClusterId(outletDTO.getClusterId());
            outletResponse.setShortDesc(outletDTO.getShortDesc());
            outletResponse.setClusterName(outletDTO.getClusterName());
            outletResponse.setGroupName(outletDTO.getGroupName());
            outletResponse.setOutletDesc(outletDTO.getOutletDesc());
            outletResponse.setRegionName(outletDTO.getRegionName());
            outletResponse.setCompanyName(outletDTO.getCompanyName());
            outletResponseLists.add(outletResponse);
        }
        return outletResponseLists;
    }

    public OutletResponseList getOutletById(int outletId) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        OutletResponseList outletResponse = buildResponseFromDTO(outletDAO.getOutletById(outletId));
        return outletResponse;
    }

    private OutletResponseList buildResponseFromDTO(OutletDTO outletDTO) {
        OutletResponseList outletResponse = new OutletResponseList();
        outletResponse.setPosStoreId(outletDTO.getPosStoreId());
        outletResponse.setId(outletDTO.getId());
        outletResponse.setGroupId(outletDTO.getGroupId());
        outletResponse.setRegionId(outletDTO.getRegionId());
        outletResponse.setCompanyId(outletDTO.getCompanyId());
        outletResponse.setClusterId(outletDTO.getClusterId());
        outletResponse.setBannerUrl(outletDTO.getBannerUrl());
        outletResponse.setShortDesc(outletDTO.getShortDesc());
        outletResponse.setClusterName(outletDTO.getClusterName());
        outletResponse.setGroupName(outletDTO.getGroupName());
        outletResponse.setMobileNoLength(outletDTO.getMobileNoLength());
        outletResponse.setOutletDesc(outletDTO.getOutletDesc());
        outletResponse.setRegionName(outletDTO.getRegionName());
        outletResponse.setCompanyName(outletDTO.getCompanyName());
        outletResponse.setTemplateName(outletDTO.getTemplateName());
        outletResponse.setTemplateId(outletDTO.getTemplateId());
        outletResponse.setTableNoRange(outletDTO.getTableNoRange());
        outletResponse.setPocName(outletDTO.getPocName());
        outletResponse.setPocEmail(outletDTO.getPocEmail());
        outletResponse.setPocMobile(outletDTO.getPocMobile());
        outletResponse.setSmsGatewayId(outletDTO.getSmsGatewayId());
        outletResponse.setMgrName(outletDTO.getMgrName());
        outletResponse.setMgrMobile(outletDTO.getMgrMobile());
        outletResponse.setMgrEmail(outletDTO.getMgrEmail());
        outletResponse.setProgrammeId(outletDTO.getProgrammeId());
        outletResponse.setReferType(outletDTO.getReferType());

        return outletResponse;
    }

    public OutletResponseList getOutletByStoreId(String storeId) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        OutletResponseList outletResponse = buildResponseFromDTO(outletDAO.getOutletByStoreId(storeId));
        return outletResponse;
    }

    public Boolean updateSettings(UpdateSettingsRequestBO updateSettingsRequestBO, int outletId) throws SQLException {
        Boolean isProcessed;
        OutletDAO outletDAO = new OutletDAO();
        UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(outletId);
        if (updateSettingsDTO != null) {
            isProcessed = outletDAO.updateSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
        } else {
            isProcessed = outletDAO.createSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
        }
        return isProcessed;
    }

    private UpdateSettingsDTO buildDTOFromBO(UpdateSettingsRequestBO updateSettingsRequestBO) {
        UpdateSettingsDTO updateSettingsDTO = new UpdateSettingsDTO();
        updateSettingsDTO.setBannerUrl(updateSettingsRequestBO.getBannerUrl());
        updateSettingsDTO.setMobileNoLength(updateSettingsRequestBO.getMobileNoLength());
        updateSettingsDTO.setTableNoRange(updateSettingsRequestBO.getTableNoRange());
        updateSettingsDTO.setPocName(updateSettingsRequestBO.getPocName());
        updateSettingsDTO.setPocMobile(updateSettingsRequestBO.getPocMobile());
        updateSettingsDTO.setPocEmail(updateSettingsRequestBO.getPocEmail());
        updateSettingsDTO.setMgrEmail(updateSettingsRequestBO.getMgrEmail());
        updateSettingsDTO.setMgrMobile(updateSettingsRequestBO.getMgrMobile());
        updateSettingsDTO.setMgrName(updateSettingsRequestBO.getMgrName());
        updateSettingsDTO.setSmsGatewayId(updateSettingsRequestBO.getSmsGatewayId());
        updateSettingsDTO.setProgramId(updateSettingsRequestBO.getProgrammeId());
        updateSettingsDTO.setReferType(updateSettingsRequestBO.getReferType());
        return updateSettingsDTO;
    }
}
