package com.asciiblues.paperqz;

import fi.iki.elonen.NanoHTTPD;

public class httpsrv extends NanoHTTPD {

    // Dynamically generate the content of the file
   public String fileContent = "echo Hello";

    public httpsrv(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if ("/cmd.sh".equals(session.getUri())) {
            // Return the file as a downloadable response
            Response response = newFixedLengthResponse(Response.Status.OK, "application/octet-stream", fileContent);
            response.addHeader("Content-Disposition", "attachment; filename=\"cmd.sh\"");
            return response;
        } else {
            // Default response for undefined paths
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 - Not Found");
        }
    }
}
