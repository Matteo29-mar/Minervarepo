package com.minerva.borrowing.entities;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;








@Document("borrowings")
public class Borrowing {


	@Id
	private String id;


	private String[] id_libri;


	private LocalDate data_inizio;


	private LocalDate data_scadenza;


	private LocalDate data_riconsegna;




	private String id_cliente;








	public Borrowing(String id, String[] id_libri, LocalDate data_inizio, LocalDate data_scadenza, LocalDate data_riconsegna, String id_cliente) {


		this.id= id;
		this.id_libri= id_libri;
		this.data_inizio= data_inizio;
		this.data_scadenza= data_scadenza;
		this.data_riconsegna= data_riconsegna;
		this.id_cliente= id_cliente;
	}




	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getId_libri() {
		return id_libri;
	}
	public void setId_libri(String[] id_libri) {
		this.id_libri = id_libri;
	}
	public LocalDate getData_inizio() {
		return data_inizio;
	}
	public void setData_inizio(LocalDate data_inizio) {
		this.data_inizio = data_inizio;
	}
	public LocalDate getData_scadenza() {
		return data_scadenza;
	}
	public void setData_scadenza(LocalDate data_scadenza) {
		this.data_scadenza = data_scadenza;
	}
	public LocalDate getData_riconsegna() {
		return data_riconsegna;
	}
	public void setData_riconsegna(LocalDate data_riconsegna) {
		this.data_riconsegna = data_riconsegna;
	}
	public String getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id_libri);
		result = prime * result + Objects.hash(data_inizio, data_riconsegna, data_scadenza, id, id_cliente);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrowing other = (Borrowing) obj;
		return Objects.equals(data_inizio, other.data_inizio)
				&& Objects.equals(data_riconsegna, other.data_riconsegna)
				&& Objects.equals(data_scadenza, other.data_scadenza) && Objects.equals(id, other.id)
				&& Objects.equals(id_cliente, other.id_cliente) && Arrays.equals(id_libri, other.id_libri);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Borrowing [id=");
		builder.append(id);
		builder.append(", id_libri=");
		builder.append(Arrays.toString(id_libri));
		builder.append(", data_inizio=");
		builder.append(data_inizio);
		builder.append(", data_scadenza=");
		builder.append(data_scadenza);
		builder.append(", data_riconsegna=");
		builder.append(data_riconsegna);
		builder.append(", id_cliente=");
		builder.append(id_cliente);
		builder.append("]");
		return builder.toString();
	}


}
