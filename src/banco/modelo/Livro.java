package banco.modelo;


public class Livro extends Livros implements Imprimivel {

	public Livro() { super(); }
	
	public Livro(int id, String nome, String endereco, long cpf, long rg,
			long telefone) {
		super(id, nome, endereco, cpf, rg, telefone);

	}


	@Override
	public String imprimeEmLista() {
		return String.format("%d\t%s\t%s\t%d\t%d\t%d%.2f", getId(), getNome(), getEndereco(), getCpf(), 
				getRg(), getTelefone());
	}

	@Override
	public String[] getColunas() {
		String[] colunas = {"id", "Nome", "Endereço", "CPF", "RG", "Telefone"};
		return colunas;
	}
	
	
	
}
