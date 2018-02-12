import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;

import org.junit.Test;

import com.mhts.bean.Ticketer;
import com.mhts.utils.JDBCUtils;

public class JtableTest extends JFrame {
	
	
	@Test
	public void resetPassword() throws SQLException {
		Connection con = JDBCUtils.getConnect();
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		
		for(int i=0;i<100;i++) {
			String sql = "insert into ticketer(name,id_card,phone,account,password,window,status) values('"+i+"','410000000000000000','15444411111','1','1','1','0')";
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeUpdate();
		}
		
	}
}
