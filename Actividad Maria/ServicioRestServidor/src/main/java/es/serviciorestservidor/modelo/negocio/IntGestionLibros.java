package es.serviciorestservidor.modelo.negocio;


import java.util.List;

import es.serviciorestservidor.modelo.entidad.Libro;

public interface IntGestionLibros {
	Libro altaLibro (Libro libro);
	Libro borrarLibro (int iD);
	Libro modificarPorID (Libro libro);
	Libro buscarPorID(int iD);
	List<Libro> buscarTodos();
}
