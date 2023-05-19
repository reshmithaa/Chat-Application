import javax.swing.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
class DataBase {
    public String url="jdbc:mysql://sql12.freesqldatabase.com:3306/sql12602550";
    public String user="sql12602550";
    public String password="ttdFiRSqEU";
    Connection connection;
    Statement statement;
    ResultSet resultSet = null;
    public DataBase() throws RuntimeException{
        try {
            connection=DriverManager.getConnection(url,user,password);
            statement=connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void  insertSignupDetails(String name,String userName,String password)throws  Exception{
        try {
            statement.executeUpdate("insert into loginDetails values('"+name+"','"+userName+"','"+password+"')");
//            connection.commit();
        }catch (SQLIntegrityConstraintViolationException e){
            if (Objects.equals(e.getMessage(), "Duplicate entry '" + userName + "' for key 'PRIMARY'")){
                throw new SQLIntegrityConstraintViolationException("User Name already exist Try to Sign in...");
            }
            throw e;
        }
    }
    public void checkLoginDetails(String userName, String password) throws Exception{
        statement = connection.createStatement();
        String sql="select * from loginDetails where UserName='"+userName+"' and password_='"+password+"'";
        resultSet = statement.executeQuery(sql);
        if (!resultSet.next())
            throw new SQLException();
    }
    public ResultSet allFromUser(){
        String sql="select * from loginDetails";
        ResultSet rs=null;
        try {
            rs=connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getHistory(String senderName,String receiverName){
        String sql="select senderUserName,receiverUserName,message from `chatDetails` where (`senderUserName`='"+senderName+"' and `receiverUserName`='"+receiverName+"') or (`senderUserName`='"+receiverName+"' and `receiverUserName`='"+senderName+"') order by `datetime`";
        ResultSet resultSet=null;
        try {
            resultSet=connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    void insertChatDetails(String userName,String receiverName,String msg){
        try {
            statement.executeUpdate("insert into chatDetails values('"+userName+"','"+receiverName+"','"+msg+"',"+"sysdate())");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void flush(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close() throws SQLException {
        connection.close();
    }
}