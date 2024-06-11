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
import libcode.webapp.entidades.Inscripciones;
import libcode.webapp.entidades.Materia;
import libcode.webapp.negocio.DataService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class InscripcionController implements Serializable {

    @EJB
    private DataService dataService;

    private Inscripciones inscripcionSeleccionada;
    private List<Inscripciones> inscripciones;
    private List<Alumnos> alumnosList;
    private List<Materia> materiasList;
    private Integer selectedAlumnoId;
    private Integer selectedMateriaId;
    private String ciclo;
    private Integer anio;
    private int counter; // Variable para llevar la cuenta del número de inscripciones :)

    @PostConstruct
    public void init() {
        inscripcionSeleccionada = new Inscripciones();
        inscripciones = dataService.getInscripciones();
        alumnosList = dataService.getAlumnos();
        materiasList = dataService.getMaterias();
        counter = 0; // Inicializar el contador
    }

    //Método para obtener los datos del alumno para las inscripciones
    public List<Alumnos> completeAlumnos(String query) {
        List<Alumnos> filteredAlumnos = new ArrayList<>();
        for (Alumnos alumno : alumnosList) {
            if (alumno.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredAlumnos.add(alumno);
            }
        }
        return filteredAlumnos;
    }
    //Método para obtener los datos de las materias para las inscripciones
    public List<Materia> completeMaterias(String query) {
        List<Materia> filteredMaterias = new ArrayList<>();
        for (Materia materia : materiasList) {
            if (materia.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredMaterias.add(materia);
            }
        }
        return filteredMaterias;
    }

    public void save() {
        try {
            Alumnos alumno = dataService.getAlumnoById(selectedAlumnoId);
            Materia materia = dataService.getMateriaById(selectedMateriaId);
            inscripcionSeleccionada.setAlumnos(alumno);
            inscripcionSeleccionada.setMateria(materia);
            inscripcionSeleccionada.setCiclo(ciclo);
            inscripcionSeleccionada.setAnio(anio);
            dataService.saveInscripcionConValidacion(inscripcionSeleccionada);
            inscripciones = dataService.getInscripciones(); // actualizar la lista de inscripciones

            // Resetear los campos de Año y Ciclo al presionar el boton para Guardar.
            ciclo = "";
            anio = null;
        } catch (Exception e) {
            // Manejo de errores, por ejemplo, mostrar un mensaje de error en la vista
            e.printStackTrace();
        }
    }

    // Getters y Setters
    public Inscripciones getInscripcionSeleccionada() {
        return inscripcionSeleccionada;
    }

    public void setInscripcionSeleccionada(Inscripciones inscripcionSeleccionada) {
        this.inscripcionSeleccionada = inscripcionSeleccionada;
    }

    public List<Inscripciones> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripciones> inscripciones) {
        this.inscripciones = inscripciones;
    }

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

    public Integer getSelectedAlumnoId() {
        return selectedAlumnoId;
    }

    public void setSelectedAlumnoId(Integer selectedAlumnoId) {
        this.selectedAlumnoId = selectedAlumnoId;
    }

    public Integer getSelectedMateriaId() {
        return selectedMateriaId;
    }

    public void setSelectedMateriaId(Integer selectedMateriaId) {
        this.selectedMateriaId = selectedMateriaId;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
