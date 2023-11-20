package servicioRest.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import servicioRest.entidad.Libro;

@Component
public class DaoLibro {
	
	public List<Libro> listaLibros;
	public int contador;
	
	public DaoLibro() {
		System.out.println("Creando la lista de libros...");
		Libro libro1 = new Libro(contador++,"El mundo de Sofía","Siruela",9);
		Libro libro2 = new Libro(contador++,"El corazón helado","Tusquets",10);
		Libro libro3 = new Libro(contador++,"No digas que fue un sueño","Planeta",7);
		Libro libro4 = new Libro(contador++,"Crónica del pájaro que da cuerda al mundo","Tusquets",9);
		Libro libro5 = new Libro(contador++,"El nombre del viento","Plaza & Janés",8);
		listaLibros = new ArrayList<Libro>();
		listaLibros.add(libro1);
		listaLibros.add(libro2);
		listaLibros.add(libro3);
		listaLibros.add(libro4);
		listaLibros.add(libro5);
	}
	
	// Método add que añade un libro pasado por parámetro
	public Libro add(Libro l) {
		//Primero comprobamos que el libro no exista
		if (listaLibros.contains(l)) {
			System.out.println("El libro ya se encuentra dado de alta");
			l = null;
		}else {
			//Comprobamos que no exista ningún libro con el mismo título
			String titulo = l.getTitulo();
			boolean repetido = false;
			for(Libro b : listaLibros) {
				if(b.getTitulo().equalsIgnoreCase(titulo)) {
					repetido = true;
				}
			}
			if (repetido) {
				System.out.println("Ya existe un libro dado de alta con ese título");
				l = null;
			}else {
				l.setId(contador++);
				listaLibros.add(l);
			}
		}
		return l;
	}
	
	// Método delete que borra el libro con ID pasado por parámetro
	public Libro delete(int posicion) {
		try {
			return listaLibros.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No se encuentra ningún libro con ID " + posicion);
			return null;
		}
	}
	
	// Método update que actualiza el registro de libro que tiene el id del libro pasado por parámetro
	public Libro update(Libro l) {
		try {
			Libro lAux = listaLibros.get(l.getId());
			lAux.setTitulo(l.getTitulo());
			lAux.setEditorial(l.getEditorial());
			lAux.setNota(l.getNota());

			return lAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("No se encuentra ningún libro con ID " + l.getId());
			return null;
		}
	}
	
	// Método list que devuelve toda la lista de libros
	public List<Libro> list() {
		return listaLibros;
	}
	
	// Método getById que devuelve un libro por su posición (ID)
	public Libro getById(int posicion) {
		try {
			return listaLibros.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("No se encuentra ningún libro con ID " + posicion);
			return null;
		}
	}

}
