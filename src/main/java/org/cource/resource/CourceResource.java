package org.cource.controller;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.cource.entity.Cource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("api")
public class CourceController {

    public static List<Cource> cources = new ArrayList<>();

@GET
    public Uni<List<Cource>> getAll(){
    return Uni.createFrom().item(cources);
}
/*
    @GET
    public Response getMovies() {
        return Response.ok(cources).build();
    }

 */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
public Uni<Response> createCource(Cource cource)
        {
            cources.add(cource);
    return (Uni<Response>) Response.status(Response.Status.CREATED).entity(cources).build();
}

    @PUT
    @Path("{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCource( Cource cource, @PathParam("courseId") Integer courseId ) {
        cources = cources.stream().map(cour -> {
            if (cour.getCourseId().equals(courseId)) {
                cources.add(cource);
            }
            return cour;
        }).collect(Collectors.toList());
        return Response.ok(cource).build();
}
    @DELETE
    @Path("{courseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> deleteCource(@PathParam("courseId") Long courseId) {
        Optional<Cource> courceDeleted = cources.stream().filter(cust -> cust.getCourseId().equals(courseId)).findFirst();
        boolean removed = false;
        if (courceDeleted.isPresent()) {
            removed = cources.remove(courceDeleted.get());
        }
        if (removed) {
            return (Uni<Response>) Response.noContent().build();
        }
        return (Uni<Response>) Response.status(Response.Status.BAD_REQUEST).build();
    }
}
