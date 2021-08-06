package net.frostq.filestealer;

import java.io.OutputStream;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.common.collect.Lists;

import net.frostq.filestealer.stealer.Stealer;
import net.frostq.filestealer.windows.MainFrame;

public class FileStealer {
	public static final List<Stealer> instances = Lists.newArrayList();
	public static final OutputStream sysOut = System.out,
			sysErr = System.err;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
}