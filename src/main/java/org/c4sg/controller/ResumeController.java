package org.c4sg.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.c4sg.exception.ResumeStorageException;
import org.c4sg.service.ResumeStorageService;
import org.c4sg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.annotations.Api;



@RestController
@RequestMapping(value = "/api/users")
@Api(description = "Resume Operations ", tags = "resume")
public class ResumeController {

	@Autowired
	private final ResumeStorageService resumeService;
	
	@Autowired
    private UserService userService;

	@Autowired
	public ResumeController(ResumeStorageService storageService) {
		this.resumeService = storageService;
	}

//	@GetMapping("/{id}/resume") 
//	public String listUploadedFiles(@PathVariable int id, Model model) throws IOException {
//
//		model.addAttribute("files",
//				resumeService.loadAll()
//						.map(path -> MvcUriComponentsBuilder
//								.fromMethodName(ResumeController.class, "serveFile", path.getFileName().toString())
//								.build().toString())
//						.collect(Collectors.toList()));
//
//		return "uploadForm";
//	}

	@GetMapping("/{id}/resume/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable int id, @PathVariable String filename) {
		
		Resource file = resumeService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/{id}/resume")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		resumeService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@ExceptionHandler(ResumeStorageException.class)
	public ResponseEntity<?> handleStorageFileNotFound(ResumeStorageException exc) {
		return ResponseEntity.notFound().build();
	}

}