package org.conventionsframework.showcase.rest;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rafael M. Pestano - Oct 26, 2012 9:35:08 PM
 */

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Path("/{path:.+}")
@ApplicationPath("/resouces")
public class StatisticsMBean extends Application implements Serializable{
    
    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
    public Response computAccess(@PathParam("path") String url) {
        System.out.println(url);
        return Response.ok().build();
    }
    
}
