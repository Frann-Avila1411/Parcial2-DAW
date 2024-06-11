/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libcode.webapp.negocio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import libcode.webapp.entidades.Alumnos;
import libcode.webapp.entidades.Inscripciones;
import libcode.webapp.entidades.Materia;

@Stateless
public class DataService {

    @PersistenceContext(unitName = "pu")
    EntityManager entityManager;

    public List<Alumnos> getAlumnos() {
        Query query = entityManager.createQuery("SELECT e FROM Alumnos e ORDER BY e.id ASC");
        return query.getResultList();
    }

    @Transactional
    public void saveAlumnos(Alumnos alumnos) {
        entityManager.persist(alumnos);
    }

    @Transactional
    public void editAlumnos(Alumnos alumnos) {
        entityManager.merge(alumnos);
    }

    @Transactional
    public void deleteAlumnos(Alumnos alumnos) {
        Alumnos alumnosEliminar = entityManager.find(Alumnos.class, alumnos.getId());
        entityManager.remove(alumnosEliminar);
    }

    public List<Materia> getMaterias() {
        Query query = entityManager.createQuery("SELECT m FROM Materia m ORDER BY m.id ASC");
        return query.getResultList();
    }

    @Transactional
    public void saveMateria(Materia materia) {
        entityManager.persist(materia);
    }

    @Transactional
    public void editMateria(Materia materia) {
        entityManager.merge(materia);
    }

    @Transactional
    public void deleteMateria(Materia materia) {
        Materia materiaEliminar = entityManager.find(Materia.class, materia.getId());
        entityManager.remove(materiaEliminar);
    }

    public Alumnos getAlumnoById(Integer id) {
        return entityManager.find(Alumnos.class, id);
    }

    public Materia getMateriaById(Integer id) {
        return entityManager.find(Materia.class, id);
    }

    public Alumnos getAlumnoByNombre(String nombre) {
        Query query = entityManager.createQuery("SELECT e FROM Alumnos e WHERE lower(e.nombre) = :nombre");
        query.setParameter("nombre", nombre.toLowerCase());
        List<Alumnos> resultados = query.getResultList();
        if (!resultados.isEmpty()) {
            // Devuelve el primer resultado encontrado
            return resultados.get(0);
        } else {
            // No se encontró ningún alumno con ese nombre
            return null;
        }
    }

    public List<Inscripciones> getInscripciones() {
        Query query = entityManager.createQuery("SELECT i FROM Inscripciones i ORDER BY i.id ASC");
        return query.getResultList();
    }

    @Transactional
    public void saveInscripcion(Inscripciones inscripcion) {
        entityManager.persist(inscripcion);
    }

    @Transactional
    public void deleteInscripcion(Inscripciones inscripcion) {
        Inscripciones inscripcionEliminar = entityManager.find(Inscripciones.class, inscripcion.getId());
        entityManager.remove(inscripcionEliminar);
    }

    public Inscripciones getInscripcionById(Integer id) {
        return entityManager.find(Inscripciones.class, id);
    }

    // Método para verificar si el alumno existe
    public boolean existeAlumno(Integer id) {
        Alumnos alumno = entityManager.find(Alumnos.class, id);
        return alumno != null;
    }

    // Método para verificar si la materia existe
    public boolean existeMateria(Integer id) {
        Materia materia = entityManager.find(Materia.class, id);
        return materia != null;
    }

    // Método para guardar una inscripción con validaciones
    @Transactional
    public void saveInscripcionConValidacion(Inscripciones inscripcion) {
        if (!existeAlumno(inscripcion.getAlumnos().getId())) {
            throw new IllegalArgumentException("El alumno no existe");
        }
        if (!existeMateria(inscripcion.getMateria().getId())) {
            throw new IllegalArgumentException("La materia no existe");
        }
        entityManager.persist(inscripcion);
    }
}
