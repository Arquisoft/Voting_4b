package es.uniovi.asw.DBManagement.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "VOTE")
public class Vote implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private PollingStation colegio;

	private Candidate candidatura;
	private boolean contabilizado;

	public Vote() {

	}

	public Vote(PollingStation colegio, Candidate candidatura) {
		super();
		this.colegio = colegio;
		this.candidatura = candidatura;
		this.contabilizado = false;
	}

	public Long getId() {
		return id;
	}

	public PollingStation getColegio() {
		return colegio;
	}

	public void setColegio(PollingStation colegio) {
		this.colegio = colegio;
	}

	public Candidate getCandidatura() {
		return candidatura;
	}

	public void setCandidatura(Candidate candidatura) {
		this.candidatura = candidatura;
	}

	public boolean isContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(boolean contabilizado) {
		this.contabilizado = contabilizado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidatura == null) ? 0 : candidatura.hashCode());
		result = prime * result + ((colegio == null) ? 0 : colegio.hashCode());
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
		Vote other = (Vote) obj;
		if (candidatura == null) {
			if (other.candidatura != null)
				return false;
		} else if (!candidatura.equals(other.candidatura))
			return false;
		if (colegio == null) {
			if (other.colegio != null)
				return false;
		} else if (!colegio.equals(other.colegio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vote [colegio=" + colegio + ", candidatura=" + candidatura + ", contabilizado=" + contabilizado + "]";
	}

}
