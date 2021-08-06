package net.frostq.filestealer.windows;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.frostq.filestealer.stealer.Logger;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProgressConsole extends JFrame implements Logger {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1881200516173752590L;
	private JPanel contentPane;
	
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JLabel threadLbl;
	
	public ProgressConsole() {
		setTitle("Progress Console");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(btnClose, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		
		threadLbl = new JLabel("");
		threadLbl.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel_1.add(threadLbl);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JCheckBox chckbxBottomFix = new JCheckBox("Bottom fix");
		chckbxBottomFix.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel_2.add(chckbxBottomFix, BorderLayout.WEST);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setSelectionColor(Color.LIGHT_GRAY);
		textArea.setFont(new Font("Source Code Pro", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);
		
		chckbxBottomFix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bottomFix = chckbxBottomFix.isSelected();
			}
		});
	}
	
	protected boolean bottomFix = false;
	
	public void setThreadsOut(int threads) {
		threadLbl.setText(threads + " threads");
	}
	
	public void update(final String text) {
		textArea.append(text + "\n");
		
		if(bottomFix) scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		
		System.out.println(text);
	}
}