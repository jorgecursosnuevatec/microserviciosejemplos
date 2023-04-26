package com.jgr.spring.rest.controller;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.BackToTheFuture;
import com.github.javafaker.DragonBall;
import com.github.javafaker.Faker;

/**
 * The Class Controlador.
 */
@RequestMapping("/caracteres")
@RestController
public class Controlador {
	
	private Faker faker;
	
	
	
	@RequestMapping(value="/chuck", method=RequestMethod.GET)	
	public ResponseEntity<?> lista(){
		
		List<String> listaFaker= new ArrayList<String>();
		//faker = new Faker();
		
		listaFaker=Stream.generate(()-> new Faker())
				.peek(p->System.out.println(p))				
				.map(p->p.chuckNorris().fact())
				.distinct()
				.limit(5)
				.collect(Collectors.toList());
						
		return new ResponseEntity<>(listaFaker, HttpStatus.OK);
	}
	

}
