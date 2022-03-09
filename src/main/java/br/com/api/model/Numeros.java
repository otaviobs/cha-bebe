package br.com.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "numeros")
public class Numeros {
	
	@Transient
	public static final int SEQUENCE_ID = 1;
	
	@Id
	private int id;
	
	@NotNull(message = "Precisa declarar um valor para nome")
	@Size(min = 0, max = 150, message = "Máximo 150 caracteres")
	private String nome;
	
	@NotNull(message = "Precisa declarar um valor para número")
	@Indexed(unique = true)
	private Integer numero;
	
	private String fralda;
	private Integer status;
	
	public Numeros() {
	}
	
	public Numeros(int id, String nome, Integer numero, String fralda, Integer status) {
		super();
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.fralda = fralda;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getFralda() {
		return fralda;
	}

	public void setFralda(String fralda) {
		this.fralda = fralda;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
