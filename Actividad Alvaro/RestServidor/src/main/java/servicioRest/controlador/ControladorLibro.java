package servicioRest.controlador;

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
import org.springframework.web.bind.annotation.RestController;

import servicioRest.entidad.Libro;
import servicioRest.persistencia.DaoLibro;

@RestController
public class ControladorLibro {
	
	@Autowired
	private DaoLibro daoLibro;
	
	//GET libro por ID
	@GetMapping(path="libros/{id}",produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Libro> getLibro(@PathVariable("id") int id) {
		System.out.println("Buscando el libro con Id " + id);
		Libro l = daoLibro.getById(id);
		if(l != null) {
			return new ResponseEntity<Libro>(l,HttpStatus.OK);
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
		}
	}
	
	//POST para dar de alta un libro que no exista previamente
	@PostMapping(path="libros",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> altaLibro(@RequestBody Libro l) {
		Libro libro = daoLibro.add(l);
		if(libro!=null) {
			return new ResponseEntity<Libro>(l,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Libro>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//GET listado completo de libros
	@GetMapping(path="libros",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Libro>> listarLibros() {
		List<Libro> listaLibros = null;
		System.out.println("Listando todos los libros...");
		listaLibros = daoLibro.list();			
		System.out.println(listaLibros);
		return new ResponseEntity<List<Libro>>(listaLibros,HttpStatus.OK);
	}
	
	//PUT para modificar libros por su Id
	@PutMapping(path="libros/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> modificarLibro(@PathVariable("id") int id, @RequestBody Libro l) {
		System.out.println("ID del libro a modificar: " + id);
		System.out.println("Datos a modificar: " + l);
		l.setId(id);
		Libro lUpdate = daoLibro.update(l);
		if(lUpdate != null) {
			return new ResponseEntity<Libro>(HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	//DELETE para borrar libros por su Id
	@DeleteMapping(path="libros/{id}")
	public ResponseEntity<Libro> borrarLibro(@PathVariable("id") int id) {
		System.out.println("Id del libro a borrar: " + id);
		Libro l = daoLibro.delete(id);
		if(l != null) {
			return new ResponseEntity<Libro>(l,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}

}
