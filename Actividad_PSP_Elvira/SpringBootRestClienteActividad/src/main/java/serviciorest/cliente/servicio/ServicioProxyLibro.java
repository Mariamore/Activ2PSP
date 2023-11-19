package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Libros;

@Service
public class ServicioProxyLibro {
	
	public static final String URL = "http://localhost:8090/DaoLibros";
	
	@Autowired @Lazy
	private RestTemplate restTemplate; //Ayudará para las peticiones HTTP al servicio REST
	
	
	/**
	 * Método que obtiene un libro a partir de un id
	 * @param id del libro que queremos obtener
	 * @return nos retorna el libro del id buscado, en caso de que id no exita retonar el error.
	 */
	
	public Libros obtenerLibro (int id) {
		
		try {
			ResponseEntity<Libros> re = restTemplate.getForEntity(URL + "/" + id, Libros.class);
			HttpStatus hs = (HttpStatus) re.getStatusCode(); //Preguntamos por el codigo de respuesta
			if(hs == HttpStatus.OK) {
				
				return re.getBody();
			}else {
				return null;
			}
					
		} catch (HttpClientErrorException e) {
			System.out.println("El libro con el id " + id + " no se ha encontrado");
			System.out.println("Codigo de respuesta: " + e.getStatusCode());
			return null;
		}
		
		
	}
	
	
	/**
	 * Método que da alta a un  libro en el servicio REST
	 * @param libro, el libro que queremos dar de alta
	 * @return el libro con el id nuevo o null si la alta ha fallado..
	 */
	public Libros altaLibro(Libros libro) {
		/*
		 * Para hacer un post, en este caso dar un alta usaremos el metodo postForEntity
		 */
		try {
			ResponseEntity<Libros> re = restTemplate.getForEntity(URL + libro, Libros.class);
			System.out.println("Codigo de respuesta: " + re.getStatusCode()); //respuesta
			return re.getBody();//Nos llega el libro
			
		} catch (HttpClientErrorException e) {
			System.out.println("ALTA LIBRO: El libro no se ha dado de alta " + libro);
			System.out.println("ALTA LIBRO: Codigo de respuesta: " + e.getStatusCode());
			return null;
		}

	}
	
	
	
	/**
	 * Método que modifica un libro 
	 * @param libro, que queremos modificar
	 * @return true en caso que el libro se haya modificado con exito o false si hay algun error.
	 */
	public boolean modificarLibro(Libros libro) {
		try {
			restTemplate.put(URL +  libro.getId(), Libros.class);//Id del libro a modificar
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("MODIFICAR LIBRO: El libro se ha modificado, titulo: " + libro.getTitulo());
			System.out.println("MODIFICAR: Codigo de respuesta: " + e.getStatusCode());
			return false;
		}
		
	}
	
	
	
	/**
	 * Método que elimina un libro
	 * @param id, del libro que queremos eliminar
	 * @return true, en caso que podamos elimiar el libro o false en caso de error
	 */
	public boolean eliminar (int id) {
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("ELIMINAR LIBRO: El libro se ha eliminado, id: " + id);
			System.out.println("ELIMINAR: Codigo de respuesta: " + e.getStatusCode());
			return false;
		}
	
	}
	
	
	/**
	 * Métod devuelve una lista de libros según el título proporcionado.
	 *
	 * @param titulo Título del libro a buscar (puede ser nulo, en ese caso devolveria la lista entera)
	 * @return Lista de los libros que  contienen el título, en caso de error, devuelve null
	 */
	public List<Libros> listaLibros(String titulo){
		
		String parametro = "";
		
		if(titulo != null) {
			parametro += "?titulo=" + titulo;
		}
		
		try {ResponseEntity<Libros[]> respuesta = restTemplate.getForEntity(URL + parametro, Libros[].class);
		
			Libros[] arrayLibros = respuesta.getBody();
			return Arrays.asList(arrayLibros);//usamos asList para convertir el Array en una lista
			
		} catch (HttpClientErrorException e) {
			System.out.println("LISTA: Error al obtener la lista");
			System.out.println("LISTA: Codigo de respuesta: " + e.getStatusCode());
			return null;		
		}
	
		
	}
 }
