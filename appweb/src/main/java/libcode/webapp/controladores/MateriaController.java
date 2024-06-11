/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import libcode.webapp.entidades.Materia;
import libcode.webapp.negocio.DataService;

import java.util.List;

@Named
@RequestScoped
public class MateriaController {

    private List<Materia> materiasList;
    private Materia materiaSeleccionada;

    @EJB
    private DataService servicio;

    @PostConstruct
    public void cargarMaterias() {
        materiasList = servicio.getMaterias();
        materiaSeleccionada = new Materia();
    }

    //Método para guardar una Materia
    public void guardarMateria() {
        if (materiaSeleccionada.getId() == null) {
            servicio.saveMateria(materiaSeleccionada);
        } else {
            servicio.editMateria(materiaSeleccionada);
        }
        resetForm();
        materiasList = servicio.getMaterias();
    }

    //Método para editar una materia
    public void llenarFormEditar(Materia materia) {
        this.materiaSeleccionada = materia;
    }

    //Método para eliminar
    public void eliminarMateria(Materia materia) {
        servicio.deleteMateria(materia);
        materiasList = servicio.getMaterias();
    }

    //Resetear el formulario
    private void resetForm() {
        materiaSeleccionada = new Materia();
    }

    // Getters y Setters
    public List<Materia> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materia> materiasList) {
        this.materiasList = materiasList;
    }

    public Materia getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(Materia materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }
}
