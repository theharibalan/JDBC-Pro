import java.sql.*;
import java.util.*;

class Main {
    String jdbcURL="jdbc:derby:FirstDB;create=true";
    Connection con = DriverManager.getConnection(jdbcURL);
    String tablename;
    Main(String a) throws SQLException {
        tablename = a;
    }
    Main() throws SQLException {}
}

// METHODS
// create table
class Create extends Main{

    Create() throws SQLException {

    }

    public void createTable(String tablename) throws SQLException {
            String sql = "create table "+tablename+"(id int,name varchar(20))";
//            System.out.print("DATABASE...CREATED..");
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            System.out.println("Table created...");
        }
}

// Display Databse
class DisplayDB extends Main{
    DisplayDB() throws Exception{}

    public void showdb(String tablename) throws Exception{
        Statement st = con.createStatement();
        DatabaseMetaData meta = con.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, null,  new String[]{"TABLE"})) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);
            }
        }
    }
}



//Insert element to the table
class Insert extends Main{
    Insert() throws SQLException {
    }

    public void InsertRow(String tablename) throws SQLException{
        Scanner s=new Scanner(System.in);
        System.out.print("enter id: ");
        int id =s.nextInt();
        System.out.print("name: ");
        String name =s.next();
        PreparedStatement stmt=con.prepareStatement("insert into "+tablename+" values(?,?)");
        stmt.setInt(1, id);
        stmt.setString(2,name);
        int row=stmt.executeUpdate();
        System.out.println(row+"row(s) added");

//        String sql="insert into stud values(100,'Haribalan')";
//        Statement st=con.createStatement();
//        int row =st.executeUpdate(sql);
//        System.out.println(row+"row(s) has been inserted");

    }
}

// delete the elements form the table
class Delete extends Main{
    Delete() throws SQLException{

    }
    public void deleteRow(String tablename, int rowno, int id) throws SQLException {
        PreparedStatement stmt2 = con.prepareStatement("delete from "+tablename+" where id=?");
        stmt2.setInt(rowno,id);
        int row = stmt2.executeUpdate();
        System.out.println(row+" row(S) is deleted");

    }
}

class Struct extends Main{
    Struct() throws Exception{}
    public void Droptable(String tablename) throws SQLException {
        Statement ps = con.createStatement();
        String query = "Drop table "+tablename;
        ps.executeUpdate(query);
        System.out.println("Table "+tablename+" dropped successfully ");
    }
}


//class for update the values in the table
class Update extends Main{
    Update() throws Exception{
    }

    public void UpdateRow(String tablename, int id, String name) throws SQLException {
        PreparedStatement st =  con.prepareStatement("update "+tablename+" set name=? where id=?");
        st.setString(1,name);
        st.setInt(2,id);
        int row=st.executeUpdate();
        System.out.println(row+"row(s) updated");
    }
}

//Class For Display the table
class Display extends Main{
    Display() throws SQLException{

    }
    public void display(String tablename) throws SQLException{
        String sql="select * from "+tablename;
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(sql);
        while(rs.next())
        {
            System.out.println(rs.getInt(1)+"----->"+rs.getString(2));
        }
    }
}


public class Example {

    public static void main(String[] args) throws Exception {

        System.out.print("Enter your tablename: ");
        Scanner inp = new Scanner(System.in);
        String tablename = inp.next();
        Main m = new Main(tablename);

        while(true){
            System.out.println("---------------------Enter the option to start the SQL Operations (1/2/3/4/5)------------");
            System.out.println("1. Create new Table");
            System.out.println("2. Insert value into Table");
            System.out.println("3. Display the values of Table");
            System.out.println("4. Delete the value of Table");
            System.out.println("5. Update the value of Table");
            System.out.println("6. Drop the table");
            System.out.println("7. View all the tables in the database");
            System.out.println("8. Exit");
            System.out.print("Choose one option to perform: ");

            int n = inp.nextInt();

            if(n==1){
//                String tablename = inp.next();
                Create c = new Create();
                c.createTable(tablename);
            }
            else if (n==2){
                Insert i = new Insert();
                i.InsertRow(tablename);
            }
            else if(n==3){
                    Display d = new Display();
                    d.display(tablename);

            }
            else if(n==4){
                System.out.print("Enter the row no ");
                int row = inp.nextInt();
                System.out.print("Enter the id ");
                int id = inp.nextInt();
                Delete d1 = new Delete();
                d1.deleteRow(tablename, row, id);
            }
            else if(n == 5){
                System.out.print("Enter the id ");
                int id = inp.nextInt();
                System.out.print("Enter the name ");
                String name = inp.next();
                Update up = new Update();
                up.UpdateRow(tablename,id,name);
            }
            else if(n == 6){
                Struct st = new Struct();
                st.Droptable(tablename);
            }
            else if(n == 7){
                DisplayDB ddb = new DisplayDB();
                ddb.showdb(tablename);
            }
            else if(n==8){
                break;
            }
            else{
                System.out.println("Invalid Input");
            }

        }
    }

}
