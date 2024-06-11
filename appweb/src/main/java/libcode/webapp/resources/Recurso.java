/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libcode.webapp.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import libcode.webapp.entidades.Alumnos;
import libcode.webapp.entidades.Inscripciones;
import libcode.webapp.entidades.Materia;
import libcode.webapp.negocio.DataService;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/alumnos")
public class Recurso {
    
    @EJB DataService servicio;
    
    @GET
    public Response getAlumnos(){
        List<Alumnos> alumnos = servicio.getAlumnos();
        
        return Response.ok(alumnos).build();
        
    }
    
    @POST
    public Response saveAlumnos(Alumnos alumnos){
    
        servicio.saveAlumnos(alumnos);
        
        return Response.ok("Alumno creado exitosamente").build();
    }
    
    @PUT
    public Response editAlumnos(Alumnos alumnos){
    
        servicio.editAlumnos(alumnos);
        
        return Response.ok("Alumno editado exitosamente").build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteAlumnos(@PathParam("id") Integer id){
    Alumnos alumno = servicio.getAlumnoById(id);
    if (alumno == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Alumno no encontrado").build();
    }
    servicio.deleteAlumnos(alumno);
    return Response.ok("Alumno eliminado exitosamente").build();
}
    
    // Métodos para gestionar Materias
    @GET
    @Path("/materias")
    public Response getMaterias() {
        List<Materia> materias = servicio.getMaterias();
        return Response.ok(materias).build();
    }

    @POST
    @Path("/materias")
    public Response saveMateria(Materia materia) {
        servicio.saveMateria(materia);
        return Response.ok("Materia creada exitosamente").build();
    }

    @PUT
    @Path("/materias")
    public Response editMateria(Materia materia) {
        servicio.editMateria(materia);
        return Response.ok("Materia editada exitosamente").build();
    }

    @DELETE
    @Path("/materias/{id}")
    public Response deleteMateria(@PathParam("id") Integer id) {
        Materia materia = servicio.getMateriaById(id);
        if (materia == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Materia no encontrada").build();
        }
        servicio.deleteMateria(materia);
        return Response.ok("Materia eliminada exitosamente").build();
    }
    
    //Gestionar Inscripciones
    @POST
    @Path("/{alumnoId}/inscripciones")
    public Response saveInscripcion(@PathParam("alumnoId") Integer alumnoId, Inscripciones inscripcion) {
        if (!servicio.existeAlumno(alumnoId)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Alumno no encontrado").build();
        }
        inscripcion.getAlumnos().setId(alumnoId); // Establecer el ID del alumno en la inscripción
        if (!servicio.existeMateria(inscripcion.getMateria().getId())) {
            return Response.status(Response.Status.NOT_FOUND).entity("Materia no encontrada").build();
        }
        servicio.saveInscripcion(inscripcion);
        return Response.ok("Inscripción creada exitosamente").build();
    }

    @DELETE
    @Path("/{alumnoId}/inscripciones/{inscripcionId}")
    public Response deleteInscripcion(@PathParam("alumnoId") Integer alumnoId, @PathParam("inscripcionId") Integer inscripcionId) {
        if (!servicio.existeAlumno(alumnoId)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Alumno no encontrado").build();
        }
        Inscripciones inscripcion = servicio.getInscripcionById(inscripcionId);
        if (inscripcion == null || !inscripcion.getAlumnos().getId().equals(alumnoId)) {
            return Response.status(Response.Status.NOT_FOUND).entity("Inscripción no encontrada para este alumno").build();
        }
        servicio.deleteInscripcion(inscripcion);
        return Response.ok("Inscripción eliminada exitosamente").build();
    }
}
