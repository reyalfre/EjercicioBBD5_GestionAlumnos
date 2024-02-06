1. Ejecutar libros.sql. Posteriormente, crear una clase llamada Libros que tenga los siguientes métodos de acceso a datos para la tabla libros de la BD librería:


- anadirLibro recibe todos los datos necesarios.

- borraLibro recibe el ISBN.

- verCatalogo muestra todos los libros ordenador por título ascendente.

- actualizarCopias recibe el ISBN y el nuevo número de copias Todas estas operaciones se realizaran mediante sentencias preparadas.

- Añade un nuevo campo precio a la tabla Libros. Añade un nuevo método a la clase Libros que reciba un double indicando el precio por página de cada libro. Este método debe consultar las páginas de cada libro, multiplicar por el precio por página y rellenar la columna precio de cada libro.


- Añade un nuevo método a la clase Libros que reciba dos isbn y un double que indica el precio por página, y que realizará lo siguiente:

- Consultará las páginas de los dos libros y calculará el precio para los dos.

- Actualizará para ambos libros el precio con el precio máximo obtenido del cálculo anterior.