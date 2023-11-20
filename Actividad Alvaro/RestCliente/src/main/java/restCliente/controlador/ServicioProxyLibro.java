package restCliente.controlador;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import restCliente.entidad.Libro;

@Service
public class ServicioProxyLibro {
	
	public static final String URL = "http://localhost:8080/libros";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Libro obtener(int id){
		try {
			ResponseEntity<Libro> re = restTemplate.getForEntity(URL + "/" + id, Libro.class);
			HttpStatus hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {	
				return re.getBody();
			}else {
				System.out.println("Obtener - Respuesta no contemplada");
				return null;
			}
		}catch (HttpClientErrorException e) {
			System.out.println("Obtener - El libro no se ha encontrado. Id: " + id);
		    System.out.println("Obtener - Código de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	public Libro alta(Libro l) {
		try {
			ResponseEntity<Libro> re = restTemplate.postForEntity(URL, l, Libro.class);
			System.out.println("ALTA - Código de respuesta: " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("ALTA - El libro no se ha dado de alta. Id: " + l.getId());
		    System.out.println("ALTA - Código de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	public boolean modificar(Libro l){
		try {
			restTemplate.put(URL + "/"+ l.getId(), l, Libro.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("Modificar - El libro no se ha modificado. Id: " + l.getId());
		    System.out.println("Modificar - Código de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	public boolean borrar(int id){
		try {
			restTemplate.delete(URL + "/" + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("Borrar - El libro no se ha borrado. Id: " + id);
		    System.out.println("Borrar - Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	public List<Libro> listar(){
		try {
			ResponseEntity<Libro[]> response = restTemplate.getForEntity(URL, Libro[].class);
			Libro[] arrayLibros = response.getBody();
			return Arrays.asList(arrayLibros);
		} catch (HttpClientErrorException e) {
			System.out.println("Listar - Error al obtener la lista de libros");
		    System.out.println("Listar - Código de respuesta: " + e.getStatusCode());
		    return null;
		    
		}
	}

}
