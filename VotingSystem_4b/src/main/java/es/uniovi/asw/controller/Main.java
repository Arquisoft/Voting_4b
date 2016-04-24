package es.uniovi.asw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.uniovi.asw.dbupdate.ColegioRepository;
import es.uniovi.asw.dbupdate.EleccionesRepository;
import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.dbupdate.VotoRepository;
import es.uniovi.asw.modelo.ColegioElectoral;
import es.uniovi.asw.modelo.Elecciones;
import es.uniovi.asw.modelo.PartidoPolitico;
import es.uniovi.asw.modelo.Voto;

@Controller
public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	@Autowired
	VotoRepository votoRepository = null;
	@Autowired
	VoterRepository voterRepository = null;

	@Autowired
	ColegioRepository colegioRepository = null;

	@Autowired
	EleccionesRepository eleccionesRepository = null;

	@RequestMapping("/")
	public ModelAndView landing(Model model) {
		LOG.info("Index page access");

		return new ModelAndView("index");
	}
	

	@RequestMapping(value = "/votar")
	public String votar(Voto voto,
			@ModelAttribute("partidoPolitico") String partidoPolitico,
			Model model) {

		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		for (PartidoPolitico p : PartidoPolitico.values()) {
			partidos.add(p);
		}
		model.addAttribute("partidos", partidos);
		return "/votar";
	}

	@RequestMapping(value = "/votar", method = RequestMethod.POST)
	public String saveVote(Voto voto,
			@ModelAttribute("partidoPolitico") String partidoPolitico,
			Model model) {

		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		for (PartidoPolitico p : PartidoPolitico.values()) {
			partidos.add(p);
		}
		model.addAttribute("partidos", partidos);

		if (partidoPolitico == null) {
			Voto v = new Voto(null, null, false, false, true);
			votoRepository.save(v);
			model.addAttribute("mensaje",
					"Ha votado correctamente: voto en blanco");
			LOG.info("Se ha añadido un nuevo voto en blanco");
			// voterRepository.setEjercioDerechoAlVotoFor(true,
			// voter.getNif());
			return "/votar";
		}
		boolean encontrado = false;

		for (PartidoPolitico p : PartidoPolitico.values()) {
			if (p.toString().equals(partidoPolitico)) {
				Voto v = new Voto(null, p.toString(), false, false, false);
				votoRepository.save(v);
				model.addAttribute("mensaje", "Ha votado correctamente");
				LOG.info("Se ha añadido un nuevo voto");
				// voterRepository.setEjercioDerechoAlVotoFor(true,
				// voter.getNif());
				encontrado = true;
				return "/votar";
			}
		}
		if (partidoPolitico.equals("")) {
			Voto v = new Voto(null, null, false, false, true);
			votoRepository.save(v);
			model.addAttribute("mensaje",
					"Ha votado correctamente: voto en blanco");
			LOG.info("Se ha añadido un nuevo voto en blanco");
			// voterRepository.setEjercioDerechoAlVotoFor(true,
			// voter.getNif());
			return "/votar";

		} else {
			if (encontrado == false) {
				Voto v = new Voto(null, null, false, true, false);
				votoRepository.save(v);
				model.addAttribute("mensaje",
						"Ha votado correctamente: voto nulo");
				LOG.info("Se ha añadido un nuevo voto nulo");
				// voterRepository.setEjercioDerechoAlVotoFor(true,
				// voter.getNif());
				return "/votar";
			}
			return "/votar";
		}

	}

	@RequestMapping(value = "/modificar_elecciones")
	public String modificar(Elecciones elecciones, Model model) {
		LOG.info("Modificar elecciones page access");
		return "/modificar_elecciones";
	}

	@RequestMapping(value = "/modificar_elecciones", method = RequestMethod.POST)
	public String addElecciones(Elecciones elecciones, Model model) {
		LOG.info("Modificar elecciones page access");
		try{
		if (elecciones.getNombre() != null && elecciones.getOpciones() != null
				&& elecciones.getFechaInicio() instanceof Date && elecciones.getFechaFin() instanceof Date
				&& elecciones.getFechaFin().after(elecciones.getFechaInicio())) {
			
			
			List<Elecciones> listaElecciones = eleccionesRepository.findAll();
			if (listaElecciones != null && !listaElecciones.isEmpty()) {
				for (Elecciones e : listaElecciones) {
					if (!e.getNombre().equals(elecciones.getNombre())) {
						Elecciones eleccion = new Elecciones(
								elecciones.getNombre(),
								elecciones.getFechaInicio(),
								elecciones.getFechaFin(),
								elecciones.getOpciones());
						eleccionesRepository.save(eleccion);
						model.addAttribute("mensaje",
								"Se han convocado las nuevas elecciones con nombre: "
										+ elecciones.getNombre());
						LOG.info("Se han convocado las nuevas elecciones con nombre: "
								+ elecciones.getNombre());
						return "/modificar_elecciones";
					}
				}
			}else{
				Elecciones eleccion = new Elecciones(elecciones.getNombre(),
						elecciones.getFechaInicio(), elecciones.getFechaFin(),
						elecciones.getOpciones());
				eleccionesRepository.save(eleccion);
				model.addAttribute("mensaje",
						"Se han convocado las nuevas elecciones con nombre: "
								+ elecciones.getNombre());
				LOG.info("Se han convocado las nuevas elecciones con nombre: "
						+ elecciones.getNombre());
				return "/modificar_elecciones";
			}
		}else{
			
			model.addAttribute("mensaje", "Por favor, rellene todos los campos");
			LOG.info("No se han podido convocar nuevas elecciones");
			return "/modificar_elecciones";

		} 
		}catch(Exception e){
			model.addAttribute("mensaje", "Por favor, rellene todos los campos");
			LOG.info("No se han podido convocar nuevas elecciones");
			return "/modificar_elecciones";
		}
		return "/modificar_elecciones";
	}

	@RequestMapping(value = "/add_colegio")
	public String colegio(ColegioElectoral colegioElectoral, Model model) {
		LOG.info("Add school page access");
		return "/add_colegio";
	}

	@RequestMapping(value = "/add_colegio", method = RequestMethod.POST)
	public String addColegio(ColegioElectoral colegioElectoral, Model model) {

		if (colegioElectoral.getCircunscripcion() == null
				|| colegioElectoral.getComunidadAutonoma() == null) {
			model.addAttribute("mensaje", "Por favor, rellene todos los campos");
			LOG.info("No se ha añadido ningún colegio electoral");
			return "/add_colegio";
		} else {

			List<ColegioElectoral> colegios = colegioRepository.findAll();
			if (colegios.isEmpty()) {
				ColegioElectoral c = new ColegioElectoral(
						colegioElectoral.getCodigoColegio(),
						colegioElectoral.getCircunscripcion(),
						colegioElectoral.getComunidadAutonoma());
				colegioRepository.save(c);
				model.addAttribute("mensaje",
						"Se ha añadido el nuevo colegio electoral con código: "
								+ c.getCodigoColegio());
				LOG.info("Se ha añadido un nuevo colegio electoral");
			} else {
				for (ColegioElectoral colegioElectoral2 : colegios) {
					if (colegioElectoral.getCodigoColegio() != colegioElectoral2
							.getCodigoColegio()) {
						ColegioElectoral c = new ColegioElectoral(
								colegioElectoral.getCodigoColegio(),
								colegioElectoral.getCircunscripcion(),
								colegioElectoral.getComunidadAutonoma());
						colegioRepository.save(c);
						model.addAttribute("mensaje",
								"Se ha añadido el nuevo colegio electoral con código: "
										+ c.getCodigoColegio());
						LOG.info("Se ha añadido un nuevo colegio electoral");
						return "/add_colegio";

					} else {
						model.addAttribute("mensaje",
								"No se ha podido añadir el colegio electoral");
						LOG.info("Error, no se ha podido añadir el colegio electoral");
						return "/add_colegio";

					}
				}
			}
			return "/add_colegio";
		}
	}

}