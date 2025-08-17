package com.aluracursos.desafiolibros;

import com.aluracursos.desafiolibros.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafiolibrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafiolibrosApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraElMenu();
    }
}
