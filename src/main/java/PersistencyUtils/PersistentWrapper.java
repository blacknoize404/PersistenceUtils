package persistencyutils;

import java.io.*;

/**
 * Guarda y carga datos persistentes para un tipo de objeto
 * específico y actúa de envoltorio para el mismo.
 *
 * @param <E> El tipo de dato genérico que debe extender Serializable
 * @author Barnés Inside
 * @version 0.7
 */
public final class PersistentWrapper<E extends Serializable> {

    /**
     * La referencia al tipo de dato de la clase a serializar.
     */
    private final Class<E> type;

    /**
     * El objeto en sí donde guardar o cargar datos desde y en el archivo.
     */
    private E content;

    /**
     * La ubicación del archivo donde guardar o cargar datos.
     */
    private final String location;

    /**
     * Constructor para PersistentWrapper.
     *
     * @param type     Clase del dato a almacenar.
     * @param location Dirección del archivo a guardar y/o cargar.
     */
    public PersistentWrapper(Class<E> type, String location) {
        this.type = type;
        this.location = location;
    }

    /**
     * Constructor para PersistentWrapper.
     *
     * @param content  Instancia del dato a almacenar.
     * @param type     Clase del dato a almacenar.
     * @param location Dirección del archivo a guardar y/o cargar.
     */
    public PersistentWrapper(E data, Class<E> type, String location) {
        this.type = type;
        this.content = data;
        this.location = location;
    }

    /**
     * Crea una instancia de PersistentWrapper.
     *
     * @param type     Clase del dato a almacenar.
     * @param location Dirección del archivo a guardar y/o cargar.
     * @param <E>      Tipo de dato.
     * @return Instancia de PersistentWrapper.
     */
    public static <E extends Serializable> PersistentWrapper<E> of(Class<E> type, String location) {
        return new PersistentWrapper<>(type, location);
    }

    /**
     * Crea una instancia de PersistentWrapper con contenido inicial.
     *
     * @param content  Instancia del dato a almacenar.
     * @param type     Clase del dato a almacenar.
     * @param location Dirección del archivo a guardar y/o cargar.
     * @param <E>      Tipo de dato.
     * @return Instancia de PersistentWrapper.
     */
    public static <E extends Serializable> PersistentWrapper<E> of(E data, Class<E> type, String location) {
        return new PersistentWrapper<>(data, type, location);
    }

    /**
     * Obtiene el contenido del objeto envuelto.
     *
     * @return El objeto envuelto.
     */
    public E getContent() {
        return content;
    }

    /**
     * Asigna el contenido del objeto envuelto.
     *
     * @param content Objeto a asignar.
     */
    public void setContent(E content) {
        this.content =  content;
    }

    /**
     * Deserializa el objeto guardado en bytes desde el archivo definido.
     *
     * @throws IOException            Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si la clase a deserializar no es compatible con los datos del archivo.
     */
    private void deserialize() throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(location);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Object object = in.readObject();
            content = type.cast(object);
        }

    }


    /**
     * Carga el contenido desde el archivo.
     *
     * @return Esta instancia de PersistentWrapper.
     * @throws IOException            Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si la clase a deserializar no es compatible con los datos del archivo.
     */
    public PersistentWrapper<E> load() throws IOException, ClassNotFoundException {
        deserialize();
        return this;
    }

    /**
     * Serializa y guarda el objeto en un archivo.
     * 
     * @throws IOException Si ocurre un error al escribir en el archivo o si el contenido es nulo.
     */
    private void serialize() throws IOException {
        if (content == null) {
            throw new IOException("El contenido no puede ser nulo");
        }
        try (FileOutputStream fileOut = new FileOutputStream(location);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(content);
        }

    }

    /**
     * Alias de {@link #serialize()}.
     *
     * @throws IOException Si ocurre un error al guardar el archivo.
     * @see #serialize()
     */
    public void save() throws IOException {
        serialize();
    }

    /**
     * Obtiene el identificador único de la clase que se desea guardar o cargar.
     *
     * @return serialVersionUID de la clase.
     */
    public long getContentSerialID() {
        return ObjectStreamClass.lookup(type).getSerialVersionUID();
    }

}
