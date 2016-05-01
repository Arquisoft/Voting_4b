package es.uniovi.asw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import es.uniovi.asw.Calculate.Calculate;
import es.uniovi.asw.Calculate.voters.VotersTypeImpl;
import es.uniovi.asw.modelo.Gizmo;
import es.uniovi.asw.modelo.ServerResponse;
import es.uniovi.asw.service.DBVotes;
import es.uniovi.asw.service.VoterAcces;
import es.uniovi.asw.dbupdate.ColegioRepository;
import es.uniovi.asw.dbupdate.EleccionesRepository;
import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.dbupdate.VotoRepository;
import es.uniovi.asw.modelo.ColegioElectoral;
import es.uniovi.asw.modelo.Elecciones;
import es.uniovi.asw.modelo.Referendum;
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
	public static int[] resultados;
	public static String mensajeResultado;
	public static String fechaRefresco;
	public static int numeroVotosContabilizados;

	private boolean primerAccesoWeb = true;

	private Calculate calculate;

	@Autowired
	private VoterAcces voterAccess;

	private static final long TIEMPO_MS = 3000;
	public static final int MAX_VOTOS_PERMITIDOS = 5000;

	@RequestMapping("/")
	public ModelAndView landing(Model model) {
		LOG.info("Index page access");

		return new ModelAndView("index");
	}

	@RequestMapping(value = "/votar")
	public String votar(Voto voto, @ModelAttribute("opcion") String opcionVoto, Model model) {// TODO
																								// CAMBIO

		List<Elecciones> elecciones = eleccionesRepository.findAll();
		if (!elecciones.isEmpty()) {
			Elecciones e = elecciones.get(0);
			Date hoy = new Date();
			if (e.getFechaInicio().after(hoy)) {
				model.addAttribute("mensaje", "Por favor, espere a que se inicien las elecciones");
				return "esperarElecciones";
			} else if (e.getFechaFin().before(hoy)) {
				model.addAttribute("mensaje", "Las elecciones ya han finalizado, espere a unas nuevas elecciones");
				return "esperarElecciones";
			}
		} else {
			model.addAttribute("mensaje",
					"Aún no se han añadido elecciones." + "Por favor, espere a unas nuevas elecciones.");
			return "esperarElecciones";
		}

		List<Referendum> opciones = new ArrayList<Referendum>();
		for (Referendum opcion : Referendum.values()) {
			opciones.add(opcion);
		}
		model.addAttribute("opciones", opciones);

		return "/votar";
	}

	@RequestMapping(value = "/votar", method = RequestMethod.POST)
	public String saveVote(Voto voto, @ModelAttribute("opcion") String opcionVoto, Model model, HttpSession session) {// TODO
																														// CAMBIO

		List<Referendum> opciones = new ArrayList<Referendum>();
		for (Referendum opcion : Referendum.values()) {
			opciones.add(opcion);
		}
		model.addAttribute("opciones", opciones);

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

		if (opcionVoto == null) {
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

		// for (PartidoPolitico p : PartidoPolitico.values()) {
		// if (p.toString().equals(partidoPolitico)) {
		if (opcionVoto.equals(Referendum.SI.toString()) || opcionVoto.equals(Referendum.NO.toString())) {
			Voto v = new Voto(null, opcionVoto, false, false, false);
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
		if (opcionVoto.equals(""))

		{
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

		} else

		{
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

	@RequestMapping("/showResults")
	public String showResults(Model model) {
		
		List<Elecciones> elecciones = eleccionesRepository.findAll();
		if (!elecciones.isEmpty()) {
			Elecciones e = elecciones.get(0);
			Date hoy = new Date();
			if (!e.getFechaFin().before(hoy)) {
				model.addAttribute("mensaje", "Por favor, espere para ver los resultados, las elecciones aún no han finalizado");
				return "esperarElecciones";
			}
		} else {
			model.addAttribute("mensaje",
					"Aún no se han añadido elecciones." + "Por favor, espere a unas nuevas elecciones.");
			return "esperarElecciones";
		}
		
	
		if (primerAccesoWeb) {
			primerAccesoWeb = false;
			cargarPrimerosDatosVotos();
			calculate = new Calculate(new DBVotes(votoRepository), new VotersTypeImpl());
			numeroVotosContabilizados = calculate.getType().getTipo().nVoto();
		}

		preparaModelo(model);
		
		Timer timer = new Timer();
		timer.schedule(new TimerWebData(model), 0, TIEMPO_MS);
		return "/showResults";
	}
	
	private void preparaModelo(Model model) {
		model.addAttribute("resultadosString", mensajeResultado);
		model.addAttribute("resultados", resultados);
		model.addAttribute("ultimaActualizacion", "Ultima actualizacion: " + fechaRefresco);
		model.addAttribute("votosContabilizados",
				"Votos contabilizados hasta el momento: " + numeroVotosContabilizados);
		
		boolean refrescar = true;
		
		// Se da por hecho que se llegan a introducir 5000 votos como máximo, es un limite
		// artificl porque sino se sabriamos cuando para de meter datos simulados de los
		// puntos de voto fisicos
		if (numeroVotosContabilizados >= MAX_VOTOS_PERMITIDOS) {
			refrescar = false;
		}
		
		model.addAttribute("refrescar", refrescar);
	}

	// Clase timer que actualiza los datos de la web si han llegado nuevos datos
	class TimerWebData extends TimerTask {

		private Model model;

		public TimerWebData(Model model) {
			super();
			this.model = model;
		}

		public void run() {
			// Se da por hecho que se llegan a introducir 5000 votos como máximo, es un limite
			// artificl porque sino se sabriamos cuando para de meter datos simulados de los
			// puntos de voto fisicos
			if (calculate.getType().getTipo().nVoto() >= MAX_VOTOS_PERMITIDOS) {
				cancel();
				return;
			}
			agregarVotosAleatorios();
			actualizar(model);
		}

		private void actualizar(Model model) {
			// Se actualiza si en la base de datos hay mas votos de los que se
			// han contado
			// hasta el momento
			if (calculate.getType().getTipo().nVoto() < votoRepository.count()) {
				calculate.recalcularYActualizarObjetosWeb();
				numeroVotosContabilizados = calculate.getType().getTipo().nVoto();
				preparaModelo(model);
			}
		}

		private void agregarVotosAleatorios() {
			int randomIndex = (int) (Math.random() * 40);
			for (int i = randomIndex; i < 20; i++) {
				int random = (int) (Math.random() * 4);
				switch (random) {
				case 0:
					votoRepository.save(new Voto(null, "Si", false, false, false));
					votoRepository.save(new Voto(null, "Si", false, false, false));
					votoRepository.save(new Voto(null, "si", false, false, false));
					break;
				case 1:
					votoRepository.save(new Voto(null, "No", false, true, false));
					break;
				case 2:
					votoRepository.save(new Voto(null, null, false, false, true));
					break;
				case 3:
					votoRepository.save(new Voto(null, null, false, true, false));
					break;

				}
			}
		}
	}

	private void cargarPrimerosDatosVotos() {
		// VOTOS DE PRUEBA QUE TIENEN QUE ESTAR DEL CONTEO DIGITAL
		Voto votoSi = new Voto(null, Referendum.SI.toString(), false, false, false);
		Voto votoSi2 = new Voto(null, Referendum.SI.toString(), false, false, false);
		Voto votoNo = new Voto(null, Referendum.NO.toString(), false, false, false);
		Voto votoNulo = new Voto(null, null, false, true, false);

		Voto votoBlanco1 = new Voto(null, null, false, false, true);
		Voto votoBlanco2 = new Voto(null, null, false, true, true);

		votoRepository.save(votoSi);
		votoRepository.save(votoSi2);
		votoRepository.save(votoNo);
		votoRepository.save(votoNulo);
		votoRepository.save(votoBlanco1);
		votoRepository.save(votoBlanco2);
	}

}