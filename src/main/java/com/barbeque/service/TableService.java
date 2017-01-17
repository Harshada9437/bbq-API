package com.barbeque.service;

import com.barbeque.requesthandler.TableRequestHandler;
import com.barbeque.response.table.TableResponseList;
import com.barbeque.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-3 on 12/10/2016.
 */

@Path("/table")
public class TableService {
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTables( ) throws Exception {
        TableRequestHandler statusRequestHandler = new TableRequestHandler();
        TableResponseList tableResponseList = new TableResponseList();
        tableResponseList.setTableResponse(statusRequestHandler.getTables());
        return ResponseGenerator.generateSuccessResponse(tableResponseList, "tables list.");
    }
}
