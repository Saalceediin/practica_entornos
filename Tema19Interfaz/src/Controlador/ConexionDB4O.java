package Controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import Modelo.DB4OException;
import Modelo.Libro;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.IncompatibleFileFormatException;
import com.db4o.ext.OldFormatException;
import com.db4o.query.Query;


/**
 *
 * @author usuario
 */
public class ConexionDB4O {
     // Objeto para almacenar la base de datos orientada a objetos
    private ObjectContainer bd;
    private String rutabd;

    /**
     * Constructor por defecto
     *
     * @param rutabd Ruta del fichero .db4o para la base de datos orientada a
     * objetos
     */
    public ConexionDB4O(String rutabd) {
        bd = null;
        this.rutabd = rutabd;
    }

    /**
     * Conecta con la base de datos orientada a objetos
     *
     * @throws DB4OException Se lanzará la excepción cuando ocurra un error con
     * la base de datos
     */
    public void conectar() throws DB4OException {
        try {
            bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), rutabd);
        } catch (Db4oIOException | DatabaseFileLockedException | IncompatibleFileFormatException | OldFormatException | DatabaseReadOnlyException error) {
            throw new DB4OException(error.toString());
        }
    }

    /**
     * Desconecta de la base de datos orientada objetos
     *
     * @throws DB4OException Se lanzará la excepción cuando ocurra un error con
     * la base de datos
     */
    public void desconectar() throws DB4OException {
        try {
            bd.close();
        } catch (Db4oIOException error) {
            throw new DB4OException(error.toString());
        }
    }
    
    public void insertar(Libro a){
        bd.store(a);
    }

    
    public void consulta(Query q){
        ObjectSet res=q.execute();
        while(res.hasNext()){
            System.out.println(res.next());
        }      
    }
    
    public ObjectSet consultaInterfaz(Query q){
        ObjectSet res=q.execute();
        return res;   
    }
    
    public int eliminar (Query q){
        int contador=0;
        ObjectSet res=q.execute();
        while(res.hasNext()){
                this.bd.delete(res.next());
                contador++;
            }
        return contador;
    }

    public String getRutabd() {
        return rutabd;
    }

    public ObjectContainer getBd() {
        return bd;
    }
}
