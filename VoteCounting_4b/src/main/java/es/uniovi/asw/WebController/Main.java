package es.uniovi.asw.WebController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Main {
	public static int[] resultados;

	@RequestMapping("/")
	public String landing(Model model) {

		int[] aux = new int[2];
		aux[0] = 70;
		aux[1] = 30;

		model.addAttribute("resultados", aux);
		return "index";
	}

}