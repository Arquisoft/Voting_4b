package es.uniovi.asw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import es.uniovi.asw.UserInfo;
import es.uniovi.asw.Calculate.voters.ReferendumObject;
import es.uniovi.asw.modelo.Gizmo;
import es.uniovi.asw.modelo.ServerResponse;
import es.uniovi.asw.service.VoterAcces;
import es.uniovi.asw.dbupdate.ColegioRepository;
import es.uniovi.asw.dbupdate.EleccionesRepository;
import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.dbupdate.VotoRepository;
import es.uniovi.asw.modelo.ColegioElectoral;
import es.uniovi.asw.modelo.Elecciones;
import es.uniovi.asw.modelo.PartidoPolitico;
import es.uniovi.asw.modelo.Voter;
import es.uniovi.asw.modelo.Voto;
import groovy.lang.Grab;

@Grab("thymeleaf-spring4")
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

	private UserInfo userInfo;
	private ServerResponse serverResponse;

	// Guarda un array con los resultados de las votaciones para mostrar
	public static ReferendumObject resultados;

	@Autowired
	private VoterAcces voterAccess;

	@RequestMapping("/")
	public ModelAndView landing(Model model) {
		LOG.info("Index page access");

		return new ModelAndView("index");
	}

	@RequestMapping(value = "/votar")
	public String votar(Voto voto, @ModelAttribute("partidoPolitico") String partidoPolitico, Model model) {

		List<Elecciones> elecciones = eleccionesRepository.findAll();
		if (!elecciones.isEmpty()) {
			Elecciones e = elecciones.get(0);
			Date hoy = new Date();
			if (e.getFechaInicio().after(hoy)) {
				model.addAttribute("mensaje", "Por favor, espere a que se inicien las elecciones");
				return "esperarElecciones";
			} else if (e.getFechaFin().before(hoy)) {
				LOG.info(e.getFechaFin().toString());
				LOG.info(hoy.toString());
				model.addAttribute("mensaje", "Las elecciones ya han finalizado, espere a unas nuevas elecciones");
				return "esperarElecciones";
			}
		} else {
			model.addAttribute("mensaje",
					"Aún no se han añadido elecciones." + "Por favor, espere a unas nuevas elecciones.");
			return "esperarElecciones";
		}

		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		for (PartidoPolitico p : PartidoPolitico.values()) {
			partidos.add(p);
		}
		model.addAttribute("partidos", partidos);
		return "/votar";
	}

	@RequestMapping(value = "/votar", method = RequestMethod.POST)
	public String saveVote(Voto voto, @ModelAttribute("partidoPolitico") String partidoPolitico, Model model,
			HttpSession session) {

		List<PartidoPolitico> partidos = new ArrayList<PartidoPolitico>();
		for (PartidoPolitico p : PartidoPolitico.values()) {
			partidos.add(p);
		}
		model.addAttribute("partidos", partidos);

		if (session.getAttribute("usuario") != null) {
			Voter voter = voterRepository.findByUsuario((String) session.getAttribute("usuario"));
			if (voter != null) {
				if (voter.isEjercioDerechoAlVoto()) {
					LOG.info("El usuario " + voter.getUsuario() + " ya ha votado");
					model.addAttribute("mensaje", "No puede votar, porque ya ha votado anteriormente");
					return "/votar";
				}

			}
		}

		if (partidoPolitico == null) {
			Voto v = new Voto(null, null, false, false, true);
			votoRepository.save(v);
			model.addAttribute("mensaje", "Ha votado correctamente: voto en blanco");
			LOG.info("Se ha añadido un nuevo voto en blanco");
			if (session.getAttribute("usuario") != null) {
				Voter voter = voterRepository.findByUsuario((String) session.getAttribute("usuario"));
				if (voter != null) {
					voterAccess.updateEjercioDerechoAlVoto(true, voter.getNif());
					LOG.info(voter.toString());
				}
			}

			return "/votar";
		}
		boolean encontrado = false;

		for (PartidoPolitico p : PartidoPolitico.values()) {
			if (p.toString().equals(partidoPolitico)) {
				Voto v = new Voto(null, p.toString(), false, false, false);
				votoRepository.save(v);
				model.addAttribute("mensaje", "Ha votado correctamente");
				LOG.info("Se ha añadido un nuevo voto");
				if (session.getAttribute("usuario") != null) {
					Voter voter = voterRepository.findByUsuario((String) session.getAttribute("usuario"));
					if (voter != null) {
						voterAccess.updateEjercioDerechoAlVoto(true, voter.getUsuario());
						LOG.info(voter.toString());
					}
				}
				encontrado = true;

				return "/votar";
			}
		}
		if (partidoPolitico.equals("")) {
			Voto v = new Voto(null, null, false, false, true);
			votoRepository.save(v);
			model.addAttribute("mensaje", "Ha votado correctamente: voto en blanco");
			LOG.info("Se ha añadido un nuevo voto en blanco");
			if (session.getAttribute("usuario") != null) {
				Voter voter = voterRepository.findByUsuario((String) session.getAttribute("usuario"));
				if (voter != null) {
					voterAccess.updateEjercioDerechoAlVoto(true, voter.getUsuario());
					LOG.info(voter.toString());
				}
			}
			return "/votar";

		} else {
			if (encontrado == false) {
				Voto v = new Voto(null, null, false, true, false);
				votoRepository.save(v);
				model.addAttribute("mensaje", "Ha votado correctamente: voto nulo");
				LOG.info("Se ha añadido un nuevo voto nulo");
				if (session.getAttribute("usuario") != null) {
					Voter voter = voterRepository.findByUsuario((String) session.getAttribute("usuario"));
					if (voter != null) {
						voterAccess.updateEjercioDerechoAlVoto(true, voter.getUsuario());
						LOG.info(voter.toString());
					}
				}
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
		try {
			if (elecciones.getNombre() != null && elecciones.getOpciones() != null
					&& elecciones.getFechaInicio() instanceof Date && elecciones.getFechaFin() instanceof Date
					&& elecciones.getFechaFin().after(elecciones.getFechaInicio())) {

				List<Elecciones> listaElecciones = eleccionesRepository.findAll();
				if (listaElecciones != null && !listaElecciones.isEmpty()) {
					for (Elecciones e : listaElecciones) {
						if (!e.getNombre().equals(elecciones.getNombre())) {
							Elecciones eleccion = new Elecciones(elecciones.getNombre(), elecciones.getFechaInicio(),
									elecciones.getFechaFin(), elecciones.getOpciones());
							eleccionesRepository.save(eleccion);
							model.addAttribute("mensaje",
									"Se han convocado las nuevas elecciones con nombre: " + elecciones.getNombre());
							LOG.info("Se han convocado las nuevas elecciones con nombre: " + elecciones.getNombre());
							return "/modificar_elecciones";
						}
					}
				} else {
					Elecciones eleccion = new Elecciones(elecciones.getNombre(), elecciones.getFechaInicio(),
							elecciones.getFechaFin(), elecciones.getOpciones());
					eleccionesRepository.save(eleccion);
					model.addAttribute("mensaje",
							"Se han convocado las nuevas elecciones con nombre: " + elecciones.getNombre());
					LOG.info("Se han convocado las nuevas elecciones con nombre: " + elecciones.getNombre());
					return "/modificar_elecciones";
				}
			} else {

				model.addAttribute("mensaje", "Por favor, rellene todos los campos");
				LOG.info("No se han podido convocar nuevas elecciones");
				return "/modificar_elecciones";

			}
		} catch (Exception e) {
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

		if (colegioElectoral.getCircunscripcion() == null || colegioElectoral.getComunidadAutonoma() == null) {
			model.addAttribute("mensaje", "Por favor, rellene todos los campos");
			LOG.info("No se ha añadido ningún colegio electoral");
			return "/add_colegio";
		} else {

			List<ColegioElectoral> colegios = colegioRepository.findAll();
			if (colegios.isEmpty()) {
				ColegioElectoral c = new ColegioElectoral(colegioElectoral.getCodigoColegio(),
						colegioElectoral.getCircunscripcion(), colegioElectoral.getComunidadAutonoma());
				colegioRepository.save(c);
				model.addAttribute("mensaje",
						"Se ha añadido el nuevo colegio electoral con código: " + c.getCodigoColegio());
				LOG.info("Se ha añadido un nuevo colegio electoral");
			} else {
				for (ColegioElectoral colegioElectoral2 : colegios) {
					if (colegioElectoral.getCodigoColegio() != colegioElectoral2.getCodigoColegio()) {
						ColegioElectoral c = new ColegioElectoral(colegioElectoral.getCodigoColegio(),
								colegioElectoral.getCircunscripcion(), colegioElectoral.getComunidadAutonoma());
						colegioRepository.save(c);
						model.addAttribute("mensaje",
								"Se ha añadido el nuevo colegio electoral con código: " + c.getCodigoColegio());
						LOG.info("Se ha añadido un nuevo colegio electoral");
						return "/add_colegio";

					} else {
						model.addAttribute("mensaje", "No se ha podido añadir el colegio electoral");
						LOG.info("Error, no se ha podido añadir el colegio electoral");
						return "/add_colegio";

					}
				}
			}
			return "/add_colegio";
		}
	}

	// Parte Voters

	@RequestMapping("/WelcomePage")
	public String voterInicioSesion(Model model) {
		LOG.info("Voter log in page access");
		model.addAttribute("gizmo", new Gizmo());
		return "WelcomePage";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public Object getVI(@RequestBody UserInfo userInfo) {

		this.userInfo = userInfo;

		this.serverResponse = this.voterAccess.getVoter(userInfo.getEmail(), userInfo.getClave());

		return serverResponse;
	}

	@RequestMapping(value = "/showUserInfo", method = RequestMethod.POST)
	public String getVR(Gizmo gizmo, Model model, Elecciones elecciones, HttpSession session) {
		LOG.info("Show User info in page access");

		try {
			ServerResponse response = this.voterAccess.getVoter(gizmo.getField1(), gizmo.getField2());
			this.serverResponse = response;
			userInfo = new UserInfo(gizmo.getField1(), gizmo.getField2());
		} catch (ResourceNotFoundException e) {
			model.addAttribute("mensaje", "Por favor, introduzca un email y password correctos");
			LOG.info("No se ha podido iniciar sesión porque no es correcto el usuario");
			return "WelcomePage";
		}

		Voter usuarioConectado = voterRepository.findByEmailAndClave(userInfo.getEmail(), userInfo.getClave());

		if (usuarioConectado != null) {
			session.setAttribute("usuario", usuarioConectado.getUsuario());
			if (usuarioConectado.getUsuario().equals("junta")) {
				LOG.info("Se ha iniciado sesión como Junta Electoral");
				return "modificar_elecciones";
			} else {
				ArrayList<Object> atributos = new ArrayList<>();
				atributos.add(this.serverResponse);

				model.addAttribute("atributes", atributos);
				session.setAttribute("atributes", atributos);
				LOG.info("Se ha iniciado sesión como votante");
				return "voterpage";
			}
		}

		return "WelcomePage";

	}

	@RequestMapping("/voterpage")
	public String voterPage(Gizmo gizmo, Model model, Elecciones elecciones, HttpSession session) {
		LOG.info("Voter page in page access");
		return "voterpage";
	}

	@RequestMapping("/InfoPage")
	public String voterInfo(Gizmo gizmo, Model model, Elecciones elecciones, HttpSession session) {
		LOG.info("Voter info in page access");

		return "InfoPage";
	}

	@RequestMapping(value = "/cerrarSesion", method = RequestMethod.POST)
	public String cerrarSesion(Model model, HttpSession session) {
		LOG.info("Close session in page access");
		session.invalidate();
		return "index";

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePass(Model model) {
		model.addAttribute(new Gizmo());
		return "ChangePassword";
	}

	@RequestMapping(value = "/changingPassword", method = RequestMethod.POST)
	public String changingPassword(Gizmo gizmo, Model model) {

		this.voterAccess.updatePassword(userInfo.getEmail(), userInfo.getClave(), gizmo.getField1());

		ArrayList<Object> atributos = new ArrayList<>();
		atributos.add(serverResponse);

		model.addAttribute("atributes", atributos);

		return "InfoPage";
	}

	@RequestMapping("/showingResults")
	public String showResults(Model model) {

		ColegioElectoral colegio = new ColegioElectoral(50, "Asturias", "Principado de Asturias");
		colegioRepository.save(colegio);

		Voto voto = new Voto(colegio, PartidoPolitico.UPD.toString(), false, false, false);
		votoRepository.save(voto);

		// TODO: Meter save de varios votos

		int[] aux = new int[2];
		aux[0] = 70;
		aux[1] = 30;

		model.addAttribute("resultados", aux);
		return "index";
	}

}