package es.serviciorestservidor.modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.serviciorestservidor.modelo.entidad.Libro;

@Component
public class Biblioteca implements IntGestionLibros {

	private ArrayList<Libro> libros;
	
	
	/**
	 * Cuando se cree el objeto, se eecuta el contructor, ejecutándose el método cargarDatos()
	 * 
	 */
	public Biblioteca() {
		libros = new ArrayList<>();
		cargarDatos();
	}
	

	/**
	 * Método que introduce un libro al final de la lista
	 * @param libro el libro a dar de alta
	 * @return devuelve el libro añadido, a no ser que  que exista ya un 
	 * libro con el título introducido, en ese caso devuelve null.
	 */
	@Override
	public Libro altaLibro(Libro libro) {
		if(libros.contains(libro)) {
			System.out.println("AltaLibro -> El libro " + libro + " ya está en la biblioteca.");
			return null;
		} else {
			ArrayList<Libro> aux = new ArrayList<>();
			for(Libro l: libros) {
				if(l.getTitulo().equalsIgnoreCase(libro.getTitulo())) {
					
					aux.add(l);
				}
			}
			if(aux.isEmpty()) {
				int generadorID = 1;
				for (Libro l: libros) {
					while(l.getiD() == generadorID) {
						generadorID++;
					}
					libro.setiD(generadorID);
				}
				System.out.println("AltaLibro -> El libro " + libro + " se acaba de añadir a la biblioteca.");
				libros.add(libro);
				return libro;
			} else {
				return null;
			}
		}
		
	}
	/**
	 * Método que elimina un libro de la lista según su ID
	 * @param ID el ID del libro a borrar
	 * @return devuelve el libro eliminado de la lista, a no ser que no exista un libro con el ID introducido,
	 * en cuyo caso devuelve null.
	 */
	@Override
	public Libro borrarLibro(int iD) {
		Libro l = new Libro();
		l.setiD(iD);
		int posicion =libros.indexOf(l);
		if(posicion == -1) {
			System.out.println("BorrarLibro -> El libro con ID " + iD + " no está incluido en la biblioteca.");
			return null;
		} else {
			
			System.out.println("BorrarLibro -> Se procede a borrar el libro con ID " + iD);
			return libros.remove(posicion);
		}
	}
	
	
	/**
	 * Método que modifica un libro, para ello usamos el método indexOf() para comprobar si existe el libro
	 * dentro de la lista
	 * @param libro el libro a modificar
	 * @return devuelve el libro modificado, a no ser que no exista un libro con el ID introducido o que exista ya un 
	 * libro con el título introducido; en esos dos caso, devuelve null.
	 */
	@Override
	public Libro modificarPorID(Libro libro) {
		
		int posicion = libros.indexOf(libro);
		if (posicion == -1) {
			System.out.println("ModificarPorID -> No se puede modificar el libro porque no existe un libro en la biblioteca con ID " + libro.getiD());
			return null;
		} else {
			ArrayList<Libro> aux = new ArrayList<>();
			for(Libro l: libros) {
				if(l.getTitulo().equalsIgnoreCase(libro.getTitulo()) && l.getiD() != libro.getiD()) {
					System.out.println("AltaLibro -> El libro " + libro + " ya está en la biblioteca.");
					aux.add(l);
				}
			}
			if(aux.isEmpty()) {
				Libro libroAModificar = libros.get(posicion);
				libroAModificar.setTitulo(libro.getTitulo());
				libroAModificar.setEditorial(libro.getEditorial());
				libroAModificar.setNota(libro.getNota());
				System.out.println("ModificarPorID -> Libro con ID " + libro.getiD() + " modicado.");
				return libroAModificar;
			} else {
				return null;
			}
			

					
		}
	}

	
	/**
	 * Devuelve un libro según su ID
	 * @param ID el ID del libro a buscar
	 * @return el libro que tenga dicho ID dentro de la Lista, null en caso de
	 * que no exista o se haya ido fuera de rango de la Lista
	 */
	@Override
	public Libro buscarPorID(int iD) {
		Libro l = new Libro();
		l.setiD(iD);
		int posicion = libros.indexOf(l);
		
		if(posicion != -1) {
			System.out.println("BuscarPorID -> Encontrado el libro con ID " + iD);
			return libros.get(posicion);
		} else {
			System.out.println("BuscarPorID -> No existe en la biblioteca un libro con ID " + iD);
			return null;
		}
			
		
	}

	/**
	 * Devuelve una Lista con todos los libros
	 * @return la lista con todos los libros
	 */
	@Override
	public List<Libro> buscarTodos() {
		return libros;
	}
	
	//Creamos el método cargarDatos() para poder tener cargados 5 libros en el servidor
	private void cargarDatos() {
		libros.add(new Libro(1, "El nombre del viento", "Patrick Rothfuss", 9.75));
		libros.add(new Libro(2, "Rebelión en la granja", "George Orwell", 8.5));
		libros.add(new Libro(3, "La chica del tren", "Paula Hawkins", 3));
		libros.add(new Libro(4, "La verdad sobre el caso Harry Quebert", "Joël Dicker", 7.5));
		libros.add(new Libro(5, "La sombra del viento", "Carlos Ruiz Zafón", 9.5));
		
	}

}
