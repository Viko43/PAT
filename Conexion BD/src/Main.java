import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/escuela";
        String user = "root";
        String pass = "Nomelase+2";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            EstudianteDAO dao = new EstudianteDAOImpl(conn);

            // Insertar
            dao.insertar(new Estudiante(0, "Oscar", "oscar@mail.com"));

            // Listar
            for (Estudiante e : dao.listar()) {
                System.out.println(e.getId() + " - " + e.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}