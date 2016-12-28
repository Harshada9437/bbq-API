package com.barbeque.requesthandler;

import com.barbeque.bo.AssignTemplateRequestBO;
import com.barbeque.bo.UpdateSettingsRequestBO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.UpdateSettingsDTO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.response.outlet.OutletResponseL;
import com.barbeque.response.outlet.OutletResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletRequesthandler
{
    public Boolean assignoutlet(AssignTemplateRequestBO assignTemplateRequestBO, int outletId)throws SQLException
    {
        OutletDAO outletDAO=new OutletDAO();
        Boolean isCreated=outletDAO.assignoutlet(buildotletDTOFromBO(assignTemplateRequestBO), outletId);
        return isCreated;
    }

    private TempDTO buildotletDTOFromBO(AssignTemplateRequestBO assignTemplateRequestBO)
    {
        TempDTO tempDTO =new TempDTO();
        tempDTO.setTemplateId(assignTemplateRequestBO.getTemplateId());
        tempDTO.setFromDate(assignTemplateRequestBO.getFromDate());
        tempDTO.setToDate(assignTemplateRequestBO.getToDate());
        return tempDTO;
    }

   public List<OutletResponseL> getOutlate()throws  SQLException,TemplateNotFoundException
   {
       OutletDAO outletDAO=new OutletDAO();
       List<OutletResponseL> outletResponseLists=new ArrayList<OutletResponseL>();
       try
       {
           outletResponseLists=getOutletListDTOsFromBO(outletDAO.getOutlate());
       }catch (SQLException s) {
           s.printStackTrace();
       }
       return outletResponseLists;
   }

    public List<OutletResponseL> getOutletListDTOsFromBO(List<OutletDTO> outletDTOs)throws SQLException
    {
        List<OutletResponseL> outletResponseLists=new ArrayList<OutletResponseL>();
        Iterator<OutletDTO>outletListDTOIterator= outletDTOs.iterator();
        while (outletListDTOIterator.hasNext())
        {
            OutletDTO outletDTO =outletListDTOIterator.next();
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
        OutletResponseList outletResponse = new OutletResponseList();
        outletResponse = buildResponseFromDTO(outletDAO.getOutletById(outletId));
        return outletResponse;
    }

    private OutletResponseList buildResponseFromDTO(OutletDTO outletDTO) {
        OutletResponseList outletResponse = new OutletResponseList();
        outletResponse.setPosStoreId(outletDTO.getPosStoreId());
        outletResponse.setId(outletDTO.getId());
        outletResponse.setTemplateId(outletDTO.getTemplateId());
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

        return outletResponse;
    }

    public OutletResponseList getOutletByStoreId(int storeId) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        OutletResponseList outletResponse = new OutletResponseList();
        outletResponse = buildResponseFromDTO(outletDAO.getOutletByStoreId(storeId));
        return outletResponse;
    }

    public Boolean updateSettings(UpdateSettingsRequestBO updateSettingsRequestBO, int outletId) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        OutletDAO outletDAO = new OutletDAO();
        try {
            isProcessed = outletDAO.updateSettings(buildDTOFromBO(updateSettingsRequestBO),outletId);
        } catch (SQLException sq) {
            isProcessed = false;
        }
        return isProcessed;
    }

    private UpdateSettingsDTO buildDTOFromBO(UpdateSettingsRequestBO updateSettingsRequestBO) {
        UpdateSettingsDTO updateSettingsDTO = new  UpdateSettingsDTO();
        updateSettingsDTO.setBannerUrl(updateSettingsRequestBO.getBannerUrl());
        updateSettingsDTO.setMobileNoLength(updateSettingsRequestBO.getMobileNoLength());
        updateSettingsDTO.setTableNoRange(updateSettingsRequestBO.getTableNoRange());
        return updateSettingsDTO;
    }
}
