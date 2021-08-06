package net.frostq.filestealer.windows;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.Dimension;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.frostq.filestealer.stealer.Listener;
import net.frostq.filestealer.stealer.Logger;
import net.frostq.filestealer.stealer.Stealer;
import net.frostq.filestealer.stealer.ThiefThread;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JProgressBar;

public class MainFrame extends JFrame implements Listener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -459563569141876941L;
	private JPanel contentPane;
	private JTextField sourceField;
	private JTextField targetField;
	private JLabel lblErrorSource = new JLabel("");
	private JLabel lblErrorTarget = new JLabel("");
	private JProgressBar progressBar = new JProgressBar(0, 100);
	private JLabel lblForProgress = new JLabel("");
	
	private JButton btnStealActivate = new JButton("Steal It!");
	
	private Path beforeOne;
	private Listener lis;
	
	private boolean oneShot = false;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("FileStealer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.1),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.05),
				750, 300);
		this.setMinimumSize(new Dimension(600, 300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "name_313767928390000");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton langKO = new JButton("KO");
		langKO.setFont(new Font("AppleSDGothicNeoSB00", Font.PLAIN, 12));
		panel.add(langKO, "2, 2");
		
		JButton langEN = new JButton("EN");
		langEN.setFont(new Font("AppleSDGothicNeoSB00", Font.PLAIN, 12));
		panel.add(langEN, "4, 2");
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFrame();
			}
		});
		
		JButton btnMultiFiles = new JButton("Multiple Files");
		btnMultiFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnMultiFiles.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(btnMultiFiles, "12, 2, 10, 1");
		btnReset.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(btnReset, "36, 2");
		
		JLabel lblSource = new JLabel("Source");
		lblSource.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(lblSource, "6, 6");
		
		sourceField = new JTextField();
		sourceField.setName("SOURCEFIELD");
		sourceField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validation(sourceField);
			}
		});
		sourceField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(sourceField, "10, 6, 22, 1, fill, default");
		sourceField.setColumns(10);
		
		JButton sourceDirectorySelect = new JButton("Select");
		sourceDirectorySelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
				if(validation(sourceField)) chooser.setCurrentDirectory(new File(sourceField.getText().trim()));
		        chooser.setAcceptAllFileFilterUsed(true);
		        chooser.setDialogTitle("Selecting Source Directory");
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
				int returnVal = chooser.showOpenDialog(sourceDirectorySelect);
				if(returnVal == 0) {
					File f = chooser.getSelectedFile();
					if(f.isDirectory()) {
						sourceField.setText(f.getAbsolutePath());
					} else JOptionPane.showMessageDialog(sourceDirectorySelect, "You should select a folder!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(sourceDirectorySelect, "You've just cancelled", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		sourceDirectorySelect.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(sourceDirectorySelect, "36, 6");
		

		lblErrorSource.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		panel.add(lblErrorSource, "10, 8");
		
		JLabel lblTarget = new JLabel("Target");
		lblTarget.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(lblTarget, "6, 10");
		
		targetField = new JTextField();
		targetField.setName("TARGETFIELD");
		targetField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(!validation(targetField)) {
					try {
						Path p = Paths.get(targetField.getText().trim());
						
						if(beforeOne != p) {
							Files.createDirectories(p);
							
							if(beforeOne != null) Files.deleteIfExists(beforeOne);
						}
						
						beforeOne = p;
						validation(targetField);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		targetField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(targetField, "10, 10, 22, 1, fill, default");
		targetField.setColumns(10);
		
		JButton targetDirectorySelect = new JButton("Select");
		targetDirectorySelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
				if(validation(targetField)) chooser.setCurrentDirectory(new File(targetField.getText().trim()));
		        chooser.setAcceptAllFileFilterUsed(true);
		        chooser.setDialogTitle("Selecting Target Directory");
		        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        
				int returnVal = chooser.showOpenDialog(targetDirectorySelect);
				if(returnVal == 0) {
					File f = chooser.getSelectedFile();
					if(f.isDirectory()) {
						targetField.setText(f.getAbsolutePath());
					} else JOptionPane.showMessageDialog(targetDirectorySelect, "You should select a folder!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(targetDirectorySelect, "You've just cancelled", "Cancellation", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		targetDirectorySelect.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(targetDirectorySelect, "36, 10");
		
		MainFrame f = this;
		
		btnStealActivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!validation(sourceField) || !validation(targetField)) return;
				
				if(!validateDirectory(sourceField.getText()) || !validateDirectory(targetField.getText()))
					return;
				
				if(sourceField.getText().trim().contentEquals(targetField.getText().trim())) {
					JOptionPane.showMessageDialog(btnStealActivate, "Cannot be the same with the directory of source and target.", "Are you sure??", JOptionPane.QUESTION_MESSAGE);
					return;
				}
				
				sourceField.setEditable(false);
				targetField.setEditable(false);
				
				ProgressConsole pc = new ProgressConsole();
				pc.setVisible(true);
				
				btnStealActivate.setEnabled(false);
				
				Stealer s = new Stealer(Paths.get(sourceField.getText()), -1, null, null);
				s.setOutputDirectory(Paths.get(targetField.getText()));
				
				setProgressConsole(pc);
				s.startConsole(f);
				
				lis = s.getListener();
				s.setListener(f);
				
				s.start();
			}
		});
		
		JButton btnHide = new JButton("Hide");
		btnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lis == null) {
					JOptionPane.showMessageDialog(null, "저기... 아무것도 실행된 게 없어요 이 사람아. 그냥 끈다?");
					System.exit(0);
				}
				
				JOptionPane.showMessageDialog(null, "다 이동되면 끌게~");
				f.setVisible(false);
				oneShot = true;
			}
		});
		btnHide.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		panel.add(btnHide, "2, 12");
		
		lblErrorTarget.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		panel.add(lblErrorTarget, "10, 12");
		
		lblForProgress.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		panel.add(lblForProgress, "30, 12");
		
		panel.add(progressBar, "8, 14, 24, 1");
		progressBar.setValue(0);
		btnStealActivate.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		panel.add(btnStealActivate, "36, 14, fill, fill");
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel, "28, 18");
		
		langKO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSource.setText("대상 경로");
				lblTarget.setText("이동 경로");
				sourceDirectorySelect.setText("선택");
				targetDirectorySelect.setText("선택");
				btnStealActivate.setText("빨리 쌔벼!");
			}
		});
		
		langEN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSource.setText("Source");
				lblTarget.setText("Target");
				sourceDirectorySelect.setText("Select");
				targetDirectorySelect.setText("Select");
				btnStealActivate.setText("Steal It!");
			}
		});
	}
	
	public boolean validateDirectory(String field) {
		return Files.isDirectory(Paths.get(field)) && field.trim().length() > 0;
	}
	
	public boolean validation(JTextField field) {
		if(!validateDirectory(field.getText())) {
			if(field.getName() == "SOURCEFIELD") {
				lblErrorSource.setText("Error! Check if there is a valid path.");
				return false;
			} else if(field.getName() == "TARGETFIELD") {
				field.getText();
				
				lblErrorTarget.setText("Error! Check if there is a valid path.");
				return false;
			}
		} else {
			if(field.getName() == "SOURCEFIELD") {
				lblErrorSource.setText("");
			} else if(field.getName() == "TARGETFIELD") {
				lblErrorTarget.setText("");
			}
			
			return true;
		}
		
		return false;
	}
	
	public void resetFrame() {
		beforeOne = null;
		lis = null;
		sourceField.setText("");
		targetField.setText("");
		lblErrorSource.setText("");
		lblErrorSource.setText("");
		progressBar.setValue(0);
		lblForProgress.setText("");
		
		btnStealActivate.setEnabled(true);
		
		sourceField.setEditable(true);
		targetField.setEditable(true);
		
		setProgressConsole(null);
	}
	
	private ProgressConsole console;
	
	public void setProgressConsole(ProgressConsole console) {
		this.console = console;
	}
	
	@Override
	public Logger getLogger() {
		return console;
	}

	@Override
	public void update(ThiefThread t, int cursor) {
		lis.update(t, cursor); //@super
		lblForProgress.setText(getCurrentStatisticsInStr());
		progressBar.setValue(((int) (getCurrentStatistics() * 100.0)));
		
		if(btnStealActivate.isEnabled()) btnStealActivate.setEnabled(false);
		if(getCurrentStatistics() == 1.0 && oneShot) System.exit(0);
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
}