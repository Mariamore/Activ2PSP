package es.serviciorestcliente.controlador;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.serviciorestcliente.modelo.entidad.Libro;

@Service 
public class ServicioProxyLibro {

	public static final String URL = "http://localhost:8080/biblioteca";
	
	@Autowired @Lazy
	private RestTemplate restTemplate;
	
	
	/**
	 * Método que da de alta un libro en el servidor REST
	 * 
	 * @param l el libro que queremos dar de alta
	 * @return el libro con el ID actualizado que se ha dado de alta en el
	 * servicio REST. Null en caso de que no se haya podido dar de alta
	 */
	public Libro alta(Libro l) {
	
		try {
			ResponseEntity<Libro> re = restTemplate.postForEntity(URL, l, Libro.class);
			System.out.println("alta -> Código de respuesta: " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("alta -> el libro con ID " + l + " no se ha dado de alta");
			System.out.println("alta -> Código de respuesta: " + e.getStatusCode());
			return null;
		}
		
	}
	
	
	/**
	 * 
	 * Borra un libro en el servicio REST
	 * 
	 * @param id el id del libro a borrar.
	 * @return true en caso de que se haya podido borrar el libro, false en caso contrario.
	 */
	public boolean borrar(int iD) {
		try {
			restTemplate.delete(URL + "/" + iD);
			System.out.println("borrar -> Libro con ID " + iD + " borrado.");
			return true;
		} catch(HttpClientErrorException e) {
			System.out.println("borrar -> El libro con ID " + iD + " no se ha borrado");
			System.out.println("borrar -> Código de respuesta: " + e.getStatusCode());
			return false;
			
		}
	}
	
	/**
	 * 
	 * Modifica un libro en el servicio REST
	 * 
	 * @param l el ibro que queremos modificar, como se hace mediante el ID, este ha de estar relleno
	 * @return true en caso de que se haya podido modificar el libro, false en caso contrario.
	 */
	public boolean modificar(Libro l) {
		try {
			restTemplate.put(URL + "/" + l.getiD(), l, Libro.class);
			return true;
		} catch(HttpClientErrorException e) {
			System.out.println("modificar -> El libro con ID " + l.getiD() + " no se ha modificado");
			System.out.println("modificar -> Código de respuesta: " + e.getStatusCode());
			return false;
			
		}
	}
	
	
	/**
	 * Método que obtiene un libro del servicio REST a partir de un ID
	 * Si el ID no existe, se arroja una excepción, que se captura para conseguir el código de respuesta
	 * 
	 * @param ID el ID que queremos obtener
	 * @return retorna el libro que estamos buscando, null en caso de que el libro no se encuentre en el 
	 * servidor (devuelve el código NOT FOUND) o si ha habido algún otro error
	 * 
	 */
	public Libro obtenerPorID(int iD) {
		try {
			ResponseEntity<Libro> re = restTemplate.getForEntity(URL + "/" +iD, Libro.class);
			HttpStatus hs = re.getStatusCode();
			if(hs == HttpStatus.OK) {	
				
				return re.getBody();
			}else {
				System.out.println("obtenerPOrID -> Respuesta no contemplada");
				return null;
			}
		} catch(HttpClientErrorException e) {
			System.out.println("obtenerPorID -> El libro con ID " + iD + " no se ha encontrado");
			System.out.println("obtenerPorID -> Código de respuesta: " + e.getStatusCode());
			return null;
			
		}
	}
	
	
	/**
	 * Metodo que devuelve todas los libros del servidor
	 * 
	 * @return el listado de libros o null en caso de algun error con el servicio REST
	 */
	public List<Libro> listar(){

		try {
			
			ResponseEntity<Libro[]> response = restTemplate.getForEntity(URL ,Libro[].class);
			Libro[] arrayLibros = response.getBody();
			return Arrays.asList(arrayLibros);
			
		} catch (HttpClientErrorException e) {
			
			System.out.println("listar -> Error al obtener la lista de libros");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		    
		}
	}
}

