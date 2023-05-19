
public class DbTest {
    static String userName,receiverName,msg;
    public static void main(String[] args) {
        userName = "21x033";receiverName = "21x027";msg = "hi";
        System.out.println("insert into chatDetails values('"+userName+"','"+receiverName+"','"+msg+"',"+"sysdate())");
    }
}
