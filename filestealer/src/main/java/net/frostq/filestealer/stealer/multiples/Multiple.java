package net.frostq.filestealer.stealer.multiples;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.common.collect.Lists;

import net.frostq.filestealer.stealer.Listener;
import net.frostq.filestealer.stealer.Logger;
import net.frostq.filestealer.stealer.ThiefThread;
import net.frostq.filestealer.windows.ProgressConsole;

import java.awt.FlowLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JScrollPane;

public class Multiple extends JFrame implements Listener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5259628921903003973L;
	private JPanel contentPane;
	
	private List<Path> targetParents = Lists.newArrayList();
	private Listener lis;

	public Multiple() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setTitle("Multiple Stealers?");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
	}
	
	private ProgressConsole console;
	
	public void setProgressConsole(ProgressConsole console) {
		this.console = console;
	}
	
	@Override
	public Logger getLogger() {
		return console;
	}
	
	private Listener l;
	
	
	@Override
	public void update(ThiefThread t, int cursor) {
		lis.update(t, cursor); //@super
		/*
		 * lblForProgress.setText(getCurrentStatisticsInStr());
		 * progressBar.setValue(((int) (getCurrentStatistics() * 100.0)));
		 * 
		 * if(btnStealActivate.isEnabled()) btnStealActivate.setEnabled(false);
		 * if(getCurrentStatistics() == 1.0 && oneShot) System.exit(0);
		 */
	}
	
	@Override
	public int getCurrentCursor() {
		return lis.getCurrentCursor();
	}

	@Override
	public float getCurrentStatistics() {
		return lis.getCurrentStatistics();
	}

	@Override
	public String getCurrentPercentage() {
		return lis.getCurrentPercentage();
	}

	@Override
	public String getCurrentStatisticsInStr() {
		return lis.getCurrentStatisticsInStr();
	}
	
	public void addParentTarget(Path dir) {
		if(Files.isRegularFile(dir)) dir = dir.getParent();
		
		
	}
}