package serviciorest.servidor.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serviciorest.servidor.entidad.Libros;
import serviciorest.servidor.persistencia.DaoLibros;


//Abrimos comunicacion 
@RestController
public class Controlador {
	
	
	@Autowired
	private DaoLibros daoLibros; //Contexto Spring LowerCamelCase
	
	/*
	 * GET LIBRO POR ID
	 * 
	 * Al ser una busqueda por clave primaria, lo añadimos como parte del path en la URL.
	 * Como queremos que el resultado sea un JSON, SpringBoot lo hace a través de librerias,lo hacemos
	 * mediante el atributo "produces"
	 * 
	 * Usaremos la clase ResponseEntity para dar una respuesta adeacuada a la peticion, en caso de que todo este OK
	 * devolveremos el código 200 y en caso de que no exista el codigo no encotrado 404.
	 */
	
	@GetMapping(path="libros/{idLibro}",produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Libros>buscarLibro(@PathVariable("id") int id) {
		System.out.println("Buscando Libro con id: " + id);
		Libros l = daoLibros.get(id);
		if(l != null) {
			return new ResponseEntity<Libros>(l,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libros>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	/*
	 * POST 
	 * 
	 * Vamos a dar de alta a un libro, queremos como resultado un JSON (Produces) y el resultado
	 * que nos tienen que enviar tambien es un JSON (Consumes).
	 * 
	 * En el caso de que se cree el objeto devolveremos la respueta adecuada a la peticion en este caso 200. 
	 * 
	 */
	
	@PostMapping(path="libros", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libros>altaLibro(@RequestBody Libros l){
		System.out.println("altaLibro: objeto libro: " + l);
		daoLibros.add(l);;
		return new ResponseEntity<Libros>(l,HttpStatus.CREATED);//200 OK
	}
	

	/*
	 * GET LISTAR LIBROS
	 * 
	 * Produce resultados en formato JSON. Toma el parámetro  "titulo", el cual es opcional.
	 * Si no hay parámetro o está vacío, devuelve la lista completa de libros guardada.
	 * Si se proporcionamos el "titulo", filtra la lista de libros.
	 * 
	 * En caso este caso devolveremos la respuesta adecuada a la peticion, 200(OK).
	 * 
	 */
	
	@GetMapping(path="libros",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Libros>> listarLibros() {
		List<Libros> listaLibros = null;
		System.out.println("Listando todos los libros");
		listaLibros = daoLibros.list();	
		return new ResponseEntity<List<Libros>>(listaLibros,HttpStatus.OK);
		
	}
	
	
	/*
	 * PUT MODIFICAR UN LIBRO A PARTIR DEL ID
	 * 
	 *
	 *  Con el parametro "id" (lo pasamos en el path) identificamos  el libro a modificar y enviamos la informacion 
	 *  del libro a modificar en formato JSON.
	 *  
	 *  Si la modificacion tiene éxito, devolvemos el código de respuesta 200 OK, si no devolvemos la respuesta 404 NOT FOUND.
	 */
	@PutMapping(path="libros/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libros> modificarLibros(
			@PathVariable("id") int id, 
			@RequestBody Libros l) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + l);
		l.setId(id);
		Libros pUpdate = daoLibros.update(l);//pasamos el objeto nuevo
		if(pUpdate != null) {
			return new ResponseEntity<Libros>(HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libros>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	
	
	/*
	 * DELETE DE UN LIBRO A PARTIR DEL ID
	 * 
	 *  Segun el "id" del libro a borrar (lo pasamos en el path) eliminaremos ese libro.
	 *  Si se elimina exitosamente, devolveremos el codigo de respuesta  200 OK. 
	 *  Si no puede encontrar el id, devolveremos el código 404 NOT FOUND.
	 * 
	 * 
	 */
	@DeleteMapping(path="libros/{id}")
	public ResponseEntity<Libros> borrarLibro(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		Libros l = daoLibros.delete(id);
		if(l != null) {
			return new ResponseEntity<Libros>(l,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libros>(l,HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
}
