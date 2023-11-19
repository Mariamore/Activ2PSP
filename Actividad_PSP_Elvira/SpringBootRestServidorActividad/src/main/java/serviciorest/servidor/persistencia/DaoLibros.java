package serviciorest.servidor.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciorest.servidor.entidad.Libros;


@Component
public class DaoLibros {
	
	private List<Libros>listaLibros;
	public int contador;
	
	
	//Constructor 
	
	public DaoLibros() {
		
		System.out.println("DaoLibros: Creando la lista de libros");
		
		listaLibros = new ArrayList<Libros>();
		//Cargo los datos en el constructor
		listaLibros.add(new Libros(contador++, "Mujercitas", "Teide", "Siete"));
		listaLibros.add(new Libros(contador++, "El Jardín Secreto", "Libros Modernos", "Diez"));
		listaLibros.add(new Libros(contador++, "El Código Da Vinci", "AlfaLibros", "Seis"));
		listaLibros.add(new Libros(contador++, "La Sombra del Viento", "Gutenberg Editores", "Seis"));
		listaLibros.add(new Libros(contador++, "Ciencia Ficción en Marte", "Estelar Books", "Nueve"));
		listaLibros.add(new Libros(contador++, "El Principito", "Estrella Editorial", "Ocho"));
		listaLibros.add(new Libros(contador++, "Orgullo y Prejuicio", "Clásicos Literarios", "Ocho"));
		listaLibros.add(new Libros(contador++, "Harry Potter y la Piedra Filosofal", "MagiaLibros", "Diez"));
		listaLibros.add(new Libros(contador++, "El Hobbit", "Tierras Medias", "Siete"));
		listaLibros.add(new Libros(contador++, "Sherlock Holmes: Estudio en Escarlata", "Detective Press", "Seis"));
		listaLibros.add(new Libros(contador++, "Crónicas Marcianas", "Planeta Rojo", "Nueve"));
		listaLibros.add(new Libros(contador++, "Las Aventuras de Tom Sawyer", "Aventuras Editoriales", "Seis"));
		listaLibros.add(new Libros(contador++, "Anna Karenina", "Literatura Rusa", "Ocho"));
		
	}
	
	
	/**
	 * Metodo que añade un libro si no esta ya contenido en la lista
	 * @param libro que queremos añadir
	 * @return devuelve la lista con el libro añadido, false si el libro ya existe en la lista.
	 */
	

	public void add(Libros libro) {
		
		 boolean libroRepetido = false;

		    for (Libros l : listaLibros) { //recorremos la lista
		        if (l.getId() == libro.getId() || l.getTitulo().equals(libro.getTitulo())) {
		            libroRepetido = true;
		            break;
		        }
		    }
		    if (libroRepetido) { //Si el libro esta repetido
		        System.out.println("Id o título repetido.");
		    } else {
		        libro.setId(contador++);
		        listaLibros.add(libro);
		    }
	}


	/**
	 * Borramos un libro a partir de una posicion del array
	 * @param posicion (id) que vamos a borrar
	 * @return devolvemos la lista sin el elemento borrado, null en caso que el id no exita.
	 */
	
	public Libros delete(int id) {
	    Libros libro = new Libros();
		libro.setId(id);
		int posicion =listaLibros.indexOf(libro);
		if(posicion == -1) {
			System.out.println("Eliminar: NO se ha podido eliminar el libro " + id);
			return null;
		} else {
			
			System.out.println("Eliminamos el libro con id :" + id);
			return listaLibros.remove(posicion);
		}
	}
	
	
	/**
	 * Metodo que actualiza un libro de la lista.
	 * @param libro contiene todos los datos que queremos modificar
	 * @return libro  modificado, null si hay un Id repetido, título  o el libro no existe.
	 */
	public Libros update(Libros libro) {
		try {
			for (Libros lAux : listaLibros) { //recorremoms la lista
	            if (lAux.getId() == libro.getId() && lAux.getId() != libro.getId()) {
	                System.out.println("Id repetido, imposible actualizar");
	                return null;
	            }
	            if (lAux.getTitulo().equals(libro.getTitulo()) && lAux.getId() != libro.getId()) {
	                System.out.println("Titulo repetido, imposible actualizar");
	                return null;
	            }
	        }
			Libros lAux = listaLibros.get(libro.getId());  //libro.getId() posicion del array que queremos modificar
			lAux.setTitulo(libro.getTitulo());
			lAux.setEditorial(libro.getEditorial());
			lAux.setNota(libro.getNota());

			return lAux;
			
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Modificar -> Libro no existe");
		}
		
		return null;
	}

	
	/**
	 * Devuelve un Libro a partir de su posicion del array
	 * @param posicion la posicion que buscamos
	 * @return El libro que este en la posicion del id del array, null en caso de
	 * que no exista.
	 */
	
	
	public Libros get(int id) {
		Libros libro = new Libros();
		libro.setId(id);
		
		int posicion = listaLibros.indexOf(id); //devolvemos con indexOf
		
		if(posicion != -1) {
			System.out.println("Buscando libro: " +  id);
			return listaLibros.get(posicion);
		}else {
			System.out.println("Libro no encontrado: " + id);
			return null;
		}
	}
	
	
	/**
	 * Metodo que devuelve todos los libros de la lista
	 * @return  lista con todas los libros del Array.
	 */
	public List<Libros> list() {
		
		/* Buscamos donde estan todos los libros: Y donde estan?? En la lista
		 * que hemos cargado con los datos. 
		 * A la hora de devolver que tenemos que devolver?? lo que buscamos, 
		 * la lista
		 */
		return listaLibros;
	}
	
	
	/**
	 * Metodo que devuelve una  lista de libros que contiene el título, 
	 *
	 * @param titulo, El título del libro que queremos buscar
	 * @return Una lista de libros que tienen el título proporcionado
	 */
	public List<Libros> listaTitulos(String titulo){
		List<Libros> aux = new ArrayList<Libros>();
		for(Libros l : listaLibros) {
			if(l.getTitulo().equalsIgnoreCase(titulo)) {//contains()
				aux.add(l);
			}
		}
		return aux;
	}

}
