package es.uniovi.asw.Calculate;

import es.uniovi.asw.Calculate.voters.VotersType;
import es.uniovi.asw.WebController.WebObserver;
import es.uniovi.asw.service.GetVotes;

public class Calculate {

	private GetVotes vots;
	private VotersType type;

	private WebObserver obserber;

	public Calculate(GetVotes votes, VotersType type) {
		this.vots = votes;
		this.type = type;

		obserber = new WebObserver();
		recalcularYActualizarObjetosWeb();
	}

	public void recalcularYActualizarObjetosWeb() {
		type.actualize(vots);// Recalculamos los datos
		obserber.actualizar(type.getResult());
	}

}
