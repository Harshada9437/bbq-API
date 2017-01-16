package com.barbeque.requesthandler;

import com.barbeque.bo.AssignTemplateRequestBO;
import com.barbeque.bo.UpdateSettingsRequestBO;
import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dao.template.TemplateDAO;
import com.barbeque.dto.UpdateSettingsDTO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.TempDTO;
import com.barbeque.exceptions.OutletNotFoundException;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.response.outlet.OutletResponseL;
import com.barbeque.response.outlet.OutletResponseList;
import com.barbeque.util.DateUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletRequesthandler
{
    public Boolean assignTemplate(AssignTemplateRequestBO assignTemplateRequestBO, int outletId) throws SQLException, ParseException {
        Boolean isCreated ;
        OutletDAO outletDAO=new OutletDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        TempDTO tempDTO = TemplateDAO.getTemplateByOutletId(outletId);
        Timestamp fromDateR = DateUtil.getTimeStampFromString(assignTemplateRequestBO.getFromDate());
        Timestamp fromDate = DateUtil.getTimeStampFromString(tempDTO.getFromDate());
        Timestamp toDateR = DateUtil.getTimeStampFromString(assignTemplateRequestBO.getToDate());
        Timestamp toDate = DateUtil.getTimeStampFromString(tempDTO.getToDate());
            if(fromDateR.after(fromDate) && toDateR.before(toDate))
            {
                outletDAO.removeAssugnTemplate(outletId,tempDTO.getTemplateId());

                outletDAO.assignTemplate(buildotletDTOFromBO(assignTemplateRequestBO), outletId);

                assignTemplateRequestBO.setTemplateId(tempDTO.getTemplateId());
                calendar.setTime(sdf.parse(assignTemplateRequestBO.getFromDate()));
                calendar.add(Calendar.DATE, -1);
                Date yesterday = calendar.getTime();
                assignTemplateRequestBO.setToDate(sdf.format(yesterday.getTime()));
                assignTemplateRequestBO.setFromDate(tempDTO.getFromDate());
                outletDAO.assignTemplate(buildotletDTOFromBO(assignTemplateRequestBO), outletId);

                assignTemplateRequestBO.setToDate(tempDTO.getToDate());
                calendar.setTime(sdf.parse(DateUtil.getDateStringFromTimeStamp(toDateR)));
                calendar.add(Calendar.DATE, 1);
                Date tommorow = calendar.getTime();
                assignTemplateRequestBO.setFromDate(sdf.format(tommorow.getTime()));
                isCreated = outletDAO.assignTemplate(buildotletDTOFromBO(assignTemplateRequestBO), outletId);

            }else {
                isCreated = outletDAO.assignTemplate(buildotletDTOFromBO(assignTemplateRequestBO), outletId);
            }
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
        outletResponse.setTableNoRange(outletDTO.getTableNoRange());

        return outletResponse;
    }

    public OutletResponseList getOutletByStoreId(String storeId) throws SQLException, OutletNotFoundException {
        OutletDAO outletDAO = new OutletDAO();
        OutletResponseList outletResponse = buildResponseFromDTO(outletDAO.getOutletByStoreId(storeId));
        return outletResponse;
    }

    public Boolean updateSettings(UpdateSettingsRequestBO updateSettingsRequestBO, int outletId) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        OutletDAO outletDAO = new OutletDAO();
        try {
            if(OutletDAO.getSetting(outletId)) {
                isProcessed = outletDAO.updateSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
            }else{
                isProcessed = outletDAO.createSettings(buildDTOFromBO(updateSettingsRequestBO), outletId);
            }
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
