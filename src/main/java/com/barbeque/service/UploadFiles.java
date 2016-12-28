package com.barbeque.service;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import  com.barbeque.config.ConfigProperties;
import com.barbeque.response.util.ResponseGenerator;
import com.barbeque.response.util.UploadResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by System1 on 10/4/2016.
 */
@Path("/files")
public class UploadFiles {

    private static final String SERVER_UPLOAD_LOCATION_FOLDER = ConfigProperties.data_dir;

    /**
     * Upload a File
     */

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(FormDataMultiPart form,
                               @QueryParam("fn") String fileName) throws Exception {

        FormDataBodyPart filePart = form.getField("file");

        ContentDisposition headerOfFilePart = filePart.getContentDisposition();

        InputStream fileInputStream = filePart.getValueAs(InputStream.class);

        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + fileName + "/"
                + headerOfFilePart.getFileName();

        // save the file to the server
        saveFile(fileInputStream, filePath, fileName);

        UploadResponse response = new UploadResponse();
        String fileResponse = fileName + "/" + headerOfFilePart.getFileName();
        response.setFilePath(fileResponse);

        return ResponseGenerator.generateSuccessResponse(response, "File uploaded successfully");
    }

    // save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream,
                          String serverLocation, String fileName) {

        try {
            File file = new File(SERVER_UPLOAD_LOCATION_FOLDER + fileName);

            if (!file.exists()) {
                file.mkdirs();
            }

            OutputStream outpuStream = new FileOutputStream(new File(
                    serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();

            uploadedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}