/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Libro;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public class ControladorLibros {

    private ConexionDB4O conexion;

    public ControladorLibros(ConexionDB4O conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Libro> obtenerLibro() {
        ArrayList<Libro> l = new ArrayList<>();
        Query all = conexion.getBd().query();
        all.constrain(Libro.class);
        all.descend("publicacion").orderDescending();
        ObjectSet<Libro> resultado = conexion.consultaInterfaz(all);
        while (resultado.hasNext()) {
            Libro l1 = resultado.next();
            l.add(l1);
        }
        return l;
    }
    
    public ArrayList<Libro> obtenerLibroFiltro() {
        ArrayList<Libro> l = new ArrayList<>();
        Query all = conexion.getBd().query();
        all.constrain(Libro.class);
        all.descend("precio").constrain(30).greater();
        all.descend("publicacion").orderDescending();
        ObjectSet resultado = conexion.consultaInterfaz(all);
        while (resultado.hasNext()) {
            Libro l1 = (Libro)resultado.next();
            l.add(l1);
        }
        return l;
    }
}
