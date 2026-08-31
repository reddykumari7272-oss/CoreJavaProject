package movie;

import java.sql.*;
import java.util.Scanner;

public class MovieRecommendationSystem {

    static final String URL =
            "jdbc:mysql://localhost:3306/movies";
    static final String USER = "root";
    static final String PASSWORD = "";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            while(true) {

                System.out.println("\n===== MOVIE RECOMMENDATION SYSTEM =====");
                System.out.println("1. Admin Login");
                System.out.println("2. Search Movie");
                System.out.println("3. Exit");
                System.out.print("Choose Option: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch(choice) {

                    case 1:
                        adminLogin();
                        break;

                    case 2:
                        searchMovie();
                        break;

                    case 3:
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice");
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }

    static void adminLogin() {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if(username.equals("admin") &&
                password.equals("admin123")) {
        	    System.out.println("\nLogin Successful!");
        	    System.out.println("Welcome Admin");

            adminPanel();

        } else {

            System.out.println("Invalid Login");
        }
    }

    static void adminPanel() {

        while(true) {

            System.out.println("\n===== ADMIN PANEL =====");
            System.out.println("1. Add Movie");
            System.out.println("2. Update Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. View Movies");
            System.out.println("5. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    addMovie();
                    break;

                case 2:
                    updateMovie();
                    break;

                case 3:
                    deleteMovie();
                    break;

                case 4:
                    viewMovies();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void addMovie() {

        try {

            Connection con = getConnection();

            System.out.print("Movie Title: ");
            String title = sc.nextLine();

            System.out.print("Genre: ");
            String genre = sc.nextLine();

            String sql =
                    "INSERT INTO movies_1(title,genres) VALUES(?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, genre);

            int result = ps.executeUpdate();

            if(result > 0)
                System.out.println("Movie Added");

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void updateMovie() {

        try {

            Connection con = getConnection();

            System.out.print("Movie ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New Title: ");
            String title = sc.nextLine();

            System.out.print("New Genre: ");
            String genre = sc.nextLine();

            String sql =
                    "UPDATE movies_1 SET title=?, genres=? WHERE movieId=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setInt(3, id);

            ps.executeUpdate();

            System.out.println("Movie Updated");

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteMovie() {

        try {

            Connection con = getConnection();

            System.out.print("Movie ID: ");
            int id = sc.nextInt();

            String sql =
                    "DELETE FROM movies_1 WHERE movieId=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Movie Deleted");

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void viewMovies() {

        try {

            Connection con = getConnection();

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery(
                            "SELECT * FROM movies_1 LIMIT 50");

            while(rs.next()) {

                System.out.println(
                        rs.getInt("movieId")
                                + " | "
                                + rs.getString("title")
                                + " | "
                                + rs.getString("genres"));
            }

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void searchMovie() {

        try {

            Connection con = getConnection();

            System.out.print("Enter Movie Name: ");
            String movie = sc.nextLine();

            String sql =
                    "SELECT * FROM movies_1 WHERE title LIKE ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "%" + movie + "%");

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while(rs.next()) {

                System.out.println(
                        rs.getInt("movieId")
                                + " | "
                                + rs.getString("title")
                                + " | "
                                + rs.getString("genres"));
            }
            if(!found) {
            	System.out.println("Movie Not Found!");
            }

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


