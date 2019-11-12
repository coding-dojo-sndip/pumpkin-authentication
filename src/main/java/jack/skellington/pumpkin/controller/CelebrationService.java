package jack.skellington.pumpkin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jack.skellington.pumpkin.controller.ErrorService.WebException;
import jack.skellington.pumpkin.dao.CelebrationDao;
import jack.skellington.pumpkin.model.Celebration;

@Controller
@ControllerAdvice
public class CelebrationService {

	@Autowired
	CelebrationDao celebrationDao;

	@GetMapping("/celebrations")
	@ResponseBody
	public List<Celebration> getCelebrations(HttpServletRequest request) {
		return celebrationDao.getCelebrations();
	}

	@GetMapping(value = "/celebration/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Celebration getCelebration(@PathVariable("name") String name) throws WebException {
		Celebration celebrationPotentiel = celebrationDao.getCelebration(name);
		if (celebrationPotentiel == null) {
			throw new WebException(404, "Celebration " + name + " not found");
		}
		return celebrationPotentiel;
	}

	@PatchMapping(value = "/celebration/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void setCelebration(HttpServletRequest request, @RequestBody Celebration celebration, HttpServletResponse response,
			@PathVariable("name") String name) throws WebException {
		if (request.isUserInRole("admin")) {
			Celebration celebrationPotentiel = celebrationDao.getCelebration(name);
			if (celebrationPotentiel == null) {
				throw new WebException(404, "Celebration " + name + " not found");
			} else {
				celebrationPotentiel.setProgramme(celebration.getProgramme());
				response.setStatus(204);
			}
		}
		else {
			throw new WebException(403, "vous n'avez pas le droit");
		}
	}

}
