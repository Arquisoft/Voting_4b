package es.uniovi.asw.Calculate;

import es.uniovi.asw.Calculate.info.ObjectInfo;
import es.uniovi.asw.Calculate.voters.ReferendumObject;
import es.uniovi.asw.Calculate.voters.VotersType;
import es.uniovi.asw.WebController.WebObserver;
import es.uniovi.asw.service.GetVotes;

public class Calculate {

	private GetVotes vots;
	private VotersType type;
	private ObjectInfo info;

	private WebObserver obserber;

	public Calculate(GetVotes votes, VotersType type, ObjectInfo info) {
		this.vots = votes;
		this.type = type;
		this.info = info;

		obserber = new WebObserver();
		sendInfo();
	}

	public void getVs() {
		type.actualize(vots);// Recalculamos los datos
	}

	private void sendInfo() {
		obserber.actualizar(info.getResultado());// pasar info como parametro a
													// la web
	}

	public void actualiceC() {
		ReferendumObject object = new ReferendumObject(type.getResult().get("si"), type.getResult().get("no"));
		info.actualiza(object);
	}
}
