package banco.ui;

import java.util.List;

import banco.dao.LivroDao;
import banco.modelo.Livro;

public class InterfaceLivroTexto extends InterfaceModeloTexto {
	
	private LivroDao dao;
	
	public InterfaceLivroTexto() {
		super();
		dao = new LivroDao();
	}
	
	private Livro obtemDadosCliente(Livro livro) {
		
		System.out.print("Insira o titulo do livro: ");
		String nome = entrada.nextLine();
		
		System.out.println("Insira o ano de publicacao do livro (somente números): ");
		long rg = entrada.nextLong();
		entrada.nextLine();
		
		System.out.println("Insira a data de publicacao do livro (somente números): ");
		long cpf = entrada.nextLong();
		entrada.nextLine();
		
		System.out.println("Insira a editora do livro: ");
		String endereco = entrada.nextLine();
		
		System.out.println("Insira o seu telefone para cadastrar (somente números): ");
		long telefone = entrada.nextLong();
		entrada.nextLine();
			
		Livro novoCliente = new Livro(0, nome, endereco, cpf, rg, telefone);
		
		return novoCliente;
	}
	
	@Override
	public void adicionar() {
		System.out.println("Adicionar livros");
		System.out.println();
		
		Livro novoCliente = obtemDadosCliente(null);	
		dao.insert(novoCliente);
		
	}

	@Override
	public void listarTodos() {
		List<Livro> clientes = dao.getAll();
		
		System.out.println("Lista de livros");
		System.out.println();
		
		System.out.println("id\tTitulo\tAnoPublicacao\tEditora\tAutor");
		
		for (Livro cliente : clientes) {
			imprimeItem(cliente);
		}
		
	}

	@Override
	public void editar() {
		listarTodos();
		
		System.out.println("Editar livro");
		System.out.println();
		
		System.out.print("Entre o id do livro: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		Livro clienteAModifcar = dao.getByKey(id);
		
		Livro novoCliente = obtemDadosCliente(clienteAModifcar);
		
		novoCliente.setId(id);
		
		dao.update(novoCliente);
		
	}

	@Override
	public void excluir() {
		listarTodos();
		
		System.out.println("Apagar livro");
		System.out.println();
		
		System.out.print("Entre o id do livro: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		dao.delete(id);
		
	}

}