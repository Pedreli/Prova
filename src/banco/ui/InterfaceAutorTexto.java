package banco.ui;

import java.util.List;

import banco.dao.LivroDao;
import banco.dao.AutorDao;
import banco.modelo.Livro;
import banco.modelo.Autor;

public class InterfaceAutorTexto extends InterfaceModeloTexto {

	private AutorDao dao;
	private LivroDao clienteDao;
	
	public InterfaceAutorTexto() {
		super();
		dao = new AutorDao();
		clienteDao = new LivroDao();
	}
	
	@Override
	public void adicionar() {
		System.out.println("Adicionar autor");
		System.out.println();
		
		Autor novaConta = obtemDadosConta(null);	
		dao.insert(novaConta);
	}

	private Autor obtemDadosConta(Autor conta) {
		System.out.print("Insira o nome: ");
		int numero = entrada.nextInt();
		
		System.out.print("Insira o cpf do autor: ");
		int agencia = entrada.nextInt();
		
		System.out.print("Insira o saldo: ");
		double saldo = entrada.nextDouble();
		
		System.out.print("Insira o ID do autor: ");
		int idCliente = entrada.nextInt();
		
		Livro cliente = clienteDao.getByKey(idCliente);
		
		return new Autor(0, agencia, numero, cliente, saldo);
	}

	@Override
	public void listarTodos() {
		List<Autor> contas = dao.getAll();
		
		System.out.println("Lista de autores");
		System.out.println();
		
		System.out.println("id\tNome\tCPF");
		
		for (Autor conta : contas) {
			imprimeItem(conta);
		}
	}

	@Override
	public void editar() {
		listarTodos();
		
		System.out.println("Editar autor");
		System.out.println();
		
		System.out.print("Entre o id do autor: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		Autor contaAModificar = dao.getByKey(id);
		
		Autor novaConta = obtemDadosConta(contaAModificar);
		
		novaConta.setId(id);
		
		dao.update(novaConta);
	}

	@Override
	public void excluir() {
		listarTodos();
		
		System.out.println("Apagar autor");
		System.out.println();
		
		System.out.print("Entre o id do autor: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		
		dao.delete(id);
	}

}
