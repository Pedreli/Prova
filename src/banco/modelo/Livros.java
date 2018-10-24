package banco.modelo;

public class Livros {
	private long id;
	private String titulo;
	private String autor;
	private String editora;
	 
	public Livros() {
	}
	 
	public Livros(long id, String titulo, String autor, String editora) {
	this.id = id;
	this.titulo = titulo;
	this.autor = autor;
	this.editora = editora;
	}
	 
	public String getAutor() {
	return autor;
	}
	 
	public void setAutor(String autor) {
	this.autor = autor;
	}
	 
	public String getEditora() {
	return editora;
	}
	 
	public void setEditora(String editora) {
	this.editora = editora;
	}
	 
	public long getId() {
	return id;
	}
	 
	public void setId(long id) {
	this.id = id;
	}
	 
	 
	public String getTitulo() {
	return titulo;
	}
	 
	public void setTitulo(String titulo) {
	this.titulo = titulo;
	}
	 
	}