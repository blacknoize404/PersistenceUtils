package PersistencyUtils;

import java.io.*;

/**
 * Guarda y carga datos persistentes para un tipo de objeto
 * específico y actúa de envoltorio para el mismo.
 *
 * @param <E> El tipo de dato genérico que debe extender de serializable
 * @author Barnés Inside
 * @version 0.6
 */
public final class PersistentWrapper<E extends Serializable> {

    /**
     * La referencia al tipo de dato de la clase a serializar.
     */
    private final Class<?> type;

    /**
     * El objeto en sí donde guardar o cargar datos desde y en el archivo.
     */
    private E content;

    /**
     * La ubicación del archivo donde guardar o cargar datos.
     */
    private final String location;

    /**
     * <p>Constructor for PersistentWrapper.</p>
     *
     * @param type La clase del dato a almacenar.
     * @param location La dirección del archivo a guardar y/o cargar.
     */
    public PersistentWrapper(Class<?> type, String location) {
        this.type = type;
        this.location = location;
    }

    /**
     * <p>Constructor for PersistentWrapper.</p>
     *
     * @param data Una instancia del dato a almacenar.
     * @param type La clase del dato a almacenar.
     * @param location La dirección del archivo a guardar y/o cargar.
     */
    public PersistentWrapper(E data, Class<?> type, String location) {
        this.type = type;
        this.content = data;
        this.location = location;
    }


    /**
     * <p>of.</p>
     *
     * @param type La clase del dato a almacenar.
     * @param location La dirección del archivo a guardar y/o cargar.
     * @param <E> a E class
     * @return a {@link PersistencyUtils.PersistentWrapper} object
     */
    public static <E extends Serializable> PersistentWrapper<E> of(Class<?> type, String location) {
        return new PersistentWrapper<>(type, location);
    }

    /**
     * <p>of.</p>
     *
     * @param data Una instancia del dato a almacenar.
     * @param type La clase del dato a almacenar.
     * @param location La dirección del archivo a guardar y/o cargar.
     * @param <E> a E class
     * @return a {@link PersistencyUtils.PersistentWrapper} object
     */
    public static <E extends Serializable> PersistentWrapper<E> of(E data, Class<?> type, String location) {
        return new PersistentWrapper<>(data, type, location);
    }

    /**
     * Obtiene el contenido del objeto envuelto.
     *
     * @return el objeto envuelto.
     */
    public E getContent() {
        return content;
    }

    /**
     * Asigna el contenido del objeto envuelto.
     *
     * @param content a E object
     */
    public void setContent(E content) {
        this.content =  content;
    }

    /**
     * Deserializa el objeto guardado en bytes desde el archivo definido.
     * @throws IOException si ha ocurrido un error al leer el archivo
     * @throws ClassNotFoundException si la clase a deserializar no es compatible con los datos del archivo
     * @see #load()
     */

    @SuppressWarnings("unchecked")
    private void deserialize() throws IOException, ClassNotFoundException {

        FileInputStream fileIn = new FileInputStream(location);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object object = in.readObject();
        in.close();
        fileIn.close();

        content = (E) type.cast(object);

    }


    /**
     * Alias de {@link #deserialize()}.
     *
     * @throws java.io.IOException si ha ocurrido un error al leer el archivo
     * @throws java.lang.ClassNotFoundException si la clase a deserializar no es compatible con los datos del archivo
     * @see #deserialize()
     * @return la instancia del {@code PersistentWrapper} que cargó los datos.
     */
    public PersistentWrapper<E> load() throws IOException, ClassNotFoundException {
        deserialize();
        return this;
    }

    /**
     * Guarda el objeto en un archivo de forma serializada
     * @throws IOException si ha ocurrido un error al escribir en el archivo
     */
    private void serialize() throws IOException {

        FileOutputStream fileOut = new FileOutputStream(location);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        if (content == null) throw new IOException("El contenido no puede ser nulo");

        out.writeObject(content);
        out.close();
        fileOut.close();

    }

    /**
     * Alias de {@link #serialize()}.
     *
     * @throws java.io.IOException si ha ocurrido un error al guardar el archivo
     * @see #serialize()
     */
    public void save() throws IOException {
        serialize();
    }

    /**
     * Obtiene un identificador único de la clase que se desea guardar o cargar.
     *
     * @return a long
     */
    public long getContentSerialID() {
        return ObjectStreamClass.lookup(type).getSerialVersionUID();
    }

}
