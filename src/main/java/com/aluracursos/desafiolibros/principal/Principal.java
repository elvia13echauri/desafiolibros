package com.aluracursos.desafiolibros.principal;

import com.aluracursos.desafiolibros.models.Datos;
import com.aluracursos.desafiolibros.models.DatosLibros;
import com.aluracursos.desafiolibros.service.ConsumoAPI;
import com.aluracursos.desafiolibros.service.ConvierteDatos;

import javax.script.ScriptContext;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "http://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //top 10 libros mas descargados
        System.out.println("Top 10 de Libros mas descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda por nombre (titulo)
        System.out.println("Ingresa el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado: ");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }

        //trabajando con estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("Media de Descargas: " + est.getAverage());
        System.out.println("Maximo de Descargas: " + est.getMax());
        System.out.println("Minimo de Descargas: " + est.getMin());
        System.out.println("Registris evaluados para las estadisticas: " + est.getCount());

    }

}
