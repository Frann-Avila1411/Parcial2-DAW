/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libcode.webapp.controladores;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import libcode.webapp.entidades.Alumnos;
import libcode.webapp.entidades.Materia;
import libcode.webapp.entidades.Inscripciones;
import libcode.webapp.negocio.DataService;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class IndexController {
    
    private List<Alumnos> alumnosList = new ArrayList<>();
    private List<Materia> materiasList = new ArrayList<>();
    private Alumnos alumnoSeleccionado;
    private Materia materiaSeleccionada;

    @EJB 
    private DataService servicio;
    
    @PostConstruct
    public void cargarDatos(){
         alumnosList = servicio.getAlumnos();
         materiasList = servicio.getMaterias();
         alumnoSeleccionado = new Alumnos();
    }

    public void guardarInscripcion(){
        Inscripciones inscripcion = new Inscripciones();
        inscripcion.setAlumnos(alumnoSeleccionado);
        inscripcion.setMateria(materiaSeleccionada);
        servicio.saveInscripcion(inscripcion);
    }
    
    public void eliminarInscripcion(Inscripciones inscripcion){
        servicio.deleteInscripcion(inscripcion);
    }
    
    public void guardarAlumnos() {
        if (alumnoSeleccionado.getId() == null) {
            servicio.saveAlumnos(alumnoSeleccionado);
        } else {
            servicio.editAlumnos(alumnoSeleccionado);
        }
        alumnoSeleccionado = new Alumnos();
        alumnosList = servicio.getAlumnos();
    }

    public void eliminarAlumnos(Alumnos alumno) {
        servicio.deleteAlumnos(alumno);
        alumnosList = servicio.getAlumnos();
    }

    public void llenarFormEditar(Alumnos alumno) {
        this.alumnoSeleccionado = alumno;
    }

    //Getters y Setters
    
    public List<Alumnos> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<Alumnos> alumnosList) {
        this.alumnosList = alumnosList;
    }

    public List<Materia> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materia> materiasList) {
        this.materiasList = materiasList;
    }

    public Alumnos getAlumnoSeleccionado() {
        return alumnoSeleccionado;
    }

    public void setAlumnoSeleccionado(Alumnos alumnoSeleccionado) {
        this.alumnoSeleccionado = alumnoSeleccionado;
    }

    public Materia getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(Materia materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }
}
