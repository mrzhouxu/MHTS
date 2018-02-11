import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JtableTest extends JFrame {
	public JtableTest() {
		setTitle("表格测试");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		// -----创建标题--------------
		Vector bt = new Vector();
		bt.add("序号");
		bt.add("工号");
		bt.add("名字");
		bt.add("性别");
		// -----创建内容--------------
		Vector data1 = new Vector();
		data1.add("1");
		data1.add("10001");
		data1.add("小明");
		data1.add("男");
		Vector data2 = new Vector();
		data2.add("2");
		data2.add("10002");
		data2.add("小王");
		data2.add("女");
		Vector datas = new Vector();
		datas.add(data1);
		datas.add(data2);
		// -----表格模式--------------
		DefaultTableModel dd = new DefaultTableModel(datas, bt);
		// -----创建表格--------------
		JTable tab = new JTable();
		// -----设置表格模式--------------
		tab.setModel(dd);
//		JScrollPane js = new JScrollPane(tab);
//		setLayout(new BorderLayout());
		add(new JScrollPane(tab), BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		JtableTest test = new JtableTest();
	}
}
