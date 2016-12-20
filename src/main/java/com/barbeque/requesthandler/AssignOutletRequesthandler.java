package com.barbeque.requesthandler;

import com.barbeque.dao.outlet.OutletDAO;
import com.barbeque.dto.request.OutletDTO;
import com.barbeque.dto.request.OutletListDTO;
import com.barbeque.exceptions.TemplateNotFoundException;
import com.barbeque.request.bo.AssignOutletRequestBO;
import com.barbeque.response.outlet.OutletResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class AssignOutletRequesthandler
{
    public Boolean assignoutlet(AssignOutletRequestBO assignOutletRequestBO,int outletId)throws SQLException
    {
        OutletDAO outletDAO=new OutletDAO();
        Boolean isCreated=outletDAO.assignoutlet(buildotletDTOFromBO(assignOutletRequestBO), outletId);
        return isCreated;
    }

    private OutletDTO buildotletDTOFromBO(AssignOutletRequestBO assignOutletRequestBO)
    {
        OutletDTO outletDTO=new OutletDTO();
        outletDTO.setTemplateId(assignOutletRequestBO.getTemplateId());
        outletDTO.setFromDate(assignOutletRequestBO.getFromDate());
        outletDTO.setToDate(assignOutletRequestBO.getToDate());
        return outletDTO;
    }

   public List<OutletResponseList> getOutlate()throws  SQLException,TemplateNotFoundException
   {
       OutletDAO outletDAO=new OutletDAO();
       List<OutletResponseList> outletResponseLists=new ArrayList<OutletResponseList>();
       try
       {
           outletResponseLists=getOutletListDTOsFromBO(outletDAO.getOutlate());
       }catch (SQLException s) {
           s.printStackTrace();
       }
       return outletResponseLists;
   }

    public List<OutletResponseList> getOutletListDTOsFromBO(List<OutletListDTO>outletListDTOs)throws SQLException
    {
        List<OutletResponseList> outletResponseLists=new ArrayList<OutletResponseList>();
        Iterator<OutletListDTO>outletListDTOIterator=outletListDTOs.iterator();
        while (outletListDTOIterator.hasNext())
        {
            OutletListDTO outletListDTO=outletListDTOIterator.next();
            OutletResponseList outletResponseList=new OutletResponseList(outletListDTO.getId(),outletListDTO.getOutletDesc(),
                    outletListDTO.getShortDesc(),outletListDTO.getClusterId(),outletListDTO.getRegionId(),
                    outletListDTO.getCompanyId(),outletListDTO.getGroupId(),outletListDTO.getPosStoreId());
            outletResponseLists.add(outletResponseList);
        }
        return outletResponseLists;
    }
}
