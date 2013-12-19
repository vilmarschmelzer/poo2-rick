package models;

public class Fabricante {
	
	private String cnpj;
	private String nome;
	private String nome_fantasia;
	
	public Fabricante(){
		
	}
	
	public Fabricante(String cnpj,String nome,String nome_fantasia){
		this.setCnpj(cnpj);
		this.setNome(nome);
		this.setNome_fantasia(nome_fantasia);
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}
	
	public String getTextoCombo(){
		return this.cnpj+" - "+this.nome;
	}
}
