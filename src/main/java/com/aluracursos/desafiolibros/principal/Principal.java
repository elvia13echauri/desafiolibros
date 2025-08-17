package com.aluracursos.desafiolibros.principal;

import com.aluracursos.desafiolibros.service.ConsumoAPI;
import com.aluracursos.desafiolibros.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "http://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
    }
}
