package models;

import java.util.ArrayList;
import java.util.List;

public class Instrumento extends Caracteristica {

	private int id_inst;
	private Tipo tipo;
	private Modelo modelo;
	private Fabricante fabricante;
	private ArrayList<Inst_Caract> items;
	
	public Instrumento(){
		
	}
	
	public Instrumento(int id_inst,int id,String nome,Tipo tipo,Modelo modelo,Fabricante fabricante,double valor){
		this.id_inst=id_inst;
		this.tipo=tipo;
		this.modelo=modelo;
		this.fabricante=fabricante;
		super.setId(id);
		super.setNome(nome);
		super.setValor(valor);
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public ArrayList<Inst_Caract> getItems() {
		return items;
	}

	public void setItems(ArrayList<Inst_Caract> items) {
		this.items = items;
	}

	public int getId_inst() {
		return id_inst;
	}

	public void setId_inst(int id_inst) {
		this.id_inst = id_inst;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
}
