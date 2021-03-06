import java.sql.*;
import java.util.List;

public class BookService {

    static String DB_URL = "jdbc:sqlite:C:\\task20\\newDatabase";
    static String INSERT_TITLE_AND_PAGES = "INSERT INTO Books VALUES (?, ?, ?, ?);";
    static String INSERT_FIRST_AND_LAST_NAME = "INSERT INTO Authors VALUES (?, ?, ?)";
    static String SELECT_TITLE_FROM_JOINED_TABLES = "SELECT Books.id, title, authorId, pagesCount " +
            "FROM Books JOIN Authors ON Books.authorId=Authors.id WHERE firstName=? AND lastName=?";

    void addBook(Book book) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_TITLE_AND_PAGES)
        ) {
            preparedStatement.setString(1, book.id);
            preparedStatement.setString(2, book.title);
            preparedStatement.setString(3, book.authorId);
            preparedStatement.setInt(4, book.pagesCount);
            preparedStatement.executeUpdate();
        }
    }

    void addAuthor(Author author) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_FIRST_AND_LAST_NAME)
        ) {
            preparedStatement.setString(1, author.id);
            preparedStatement.setString(2, author.firstName);
            preparedStatement.setString(3, author.lastName);
            preparedStatement.executeUpdate();
        }
    }

    List<Book> getBooksByAuthor(List<Book> list, String name1, String name2) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(DB_URL);
                PreparedStatement statement =
                        connection.prepareStatement(SELECT_TITLE_FROM_JOINED_TABLES)
        ) {
            statement.setString(1, name1);
            statement.setString(2, name2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String title = resultSet.getString(2);
                String authorId = resultSet.getString(3);
                int pages = resultSet.getInt(4);
                list.add(new Book(id, title, authorId, pages));
            }
        }
        return list;
    }
}
