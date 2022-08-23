package labs.lab9;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class TextEditor extends JFrame{
	private JPanel mainPanel;
	private JPanel editPanel;
	private JPanel fontPanel;
	private JPanel stylePanel;
	private JPanel sizePanel;
	private JPanel clearPanel;
	private JTextArea textArea;
	private JButton clearButton;
	
	private JComboBox<String> fontChoice;
	
	private JCheckBox italicCheckBox;
	private JCheckBox boldCheckBox;
	
	private JRadioButton eight_pt;
	private JRadioButton sixteen_pt;
	private JRadioButton twenty_four_pt;
	private JRadioButton thirty_two_pt;
	private JRadioButton fourty_pt;
	private ButtonGroup group;
	
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu edit;
	
	private ArrayList<Pair> history = new ArrayList<Pair>();
	private ArrayList<Pair> history_for_redo= new ArrayList<Pair>();
	
	private Font previous_font = new Font("Arial", Font.PLAIN, 24);
	//private String previous_text;
	
	ActionListener clearListener =  new AddClearListener();
	ActionListener modifyTextListener = new AddTextModifierListener();
	
	ActionListener exitListener = new AddExitListener();
	ActionListener undoListener = new AddUndoListener();
	ActionListener redoListener = new AddRedoListener();
	
	boolean first_undo = false;
	
	public TextEditor() {
		createMainPanel();
		createEditPanel();
		createTextArea();
		createClearButton();
		
		createMenu();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,500);
		setVisible(true);
	}
	
	public void createMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
	}
	
	public void createEditPanel() {
		editPanel = new JPanel();
		editPanel.setLayout(new GridLayout(3, 1));
		createFontPanel();
		createStylePanel();
		createSizePanel();
		mainPanel.add(editPanel, BorderLayout.NORTH);
		add(mainPanel);
	}
	
	public void createFontPanel() {
		fontPanel = new JPanel();
		fontPanel.setLayout(new FlowLayout());
		fontPanel.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
		
		fontChoice = new JComboBox<String>();
		fontChoice.addItem("Arial");
		fontChoice.addItem("Georgia");
		fontChoice.addItem("SansSerif");
		fontChoice.setSelectedIndex(0);
		fontChoice.addActionListener(modifyTextListener);
		
		fontPanel.add(fontChoice);
		editPanel.add(fontPanel);
	}
	
	public void createStylePanel() {
		stylePanel = new JPanel();
		stylePanel.setLayout(new FlowLayout());
		stylePanel.setBorder(new TitledBorder(new EtchedBorder(), "Style"));
		
		italicCheckBox = new JCheckBox("Italic");
		boldCheckBox = new JCheckBox("Bold");
		
		italicCheckBox.addActionListener(modifyTextListener);
		boldCheckBox.addActionListener(modifyTextListener);
		
		stylePanel.add(italicCheckBox);
		stylePanel.add(boldCheckBox);
		
		editPanel.add(stylePanel);
	}
	
	public void createSizePanel() {
		sizePanel = new JPanel();
		sizePanel.setLayout(new FlowLayout());
		sizePanel.setBorder(new TitledBorder(new EtchedBorder(), "Size"));
		
		eight_pt =  new JRadioButton("8 pt.");
		sixteen_pt =  new JRadioButton("16 pt.");
		twenty_four_pt =  new JRadioButton("24 pt.");
		thirty_two_pt =  new JRadioButton("32 pt.");
		fourty_pt =  new JRadioButton("40 pt.");
		eight_pt.addActionListener(modifyTextListener);
		sixteen_pt.addActionListener(modifyTextListener);
		twenty_four_pt.addActionListener(modifyTextListener);
		thirty_two_pt.addActionListener(modifyTextListener);
		fourty_pt.addActionListener(modifyTextListener);
	
		group = new ButtonGroup();
		group.add(eight_pt);
		group.add(sixteen_pt);
		group.add(twenty_four_pt);
		group.add(thirty_two_pt);
		group.add(fourty_pt);
		
		twenty_four_pt.setSelected(true);
		
		sizePanel.add(eight_pt);
		sizePanel.add(sixteen_pt);
		sizePanel.add(twenty_four_pt);
		sizePanel.add(thirty_two_pt);
		sizePanel.add(fourty_pt);
		
		editPanel.add(sizePanel);
	}
	
	public void createTextArea() {
		textArea = new JTextArea(8, 6);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 24));
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void createClearButton() {
		clearPanel = new JPanel();
		clearPanel.setLayout(new FlowLayout());
		clearButton = new JButton("Clear");
		clearButton.addActionListener(clearListener);
		clearPanel.add(clearButton);
		
		mainPanel.add(clearPanel, BorderLayout.SOUTH);
	}
	
	public void createMenu() {
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(exitListener);
		file.add(exitItem);	
		
		edit = new JMenu("Edit");
		JMenuItem undoItem = new JMenuItem("Undo");
		undoItem.addActionListener(undoListener);
		edit.add(undoItem);	
		
		JMenuItem redoItem = new JMenuItem("Redo");
		redoItem.addActionListener(redoListener);
		edit.add(redoItem);	
		
		menuBar.add(file);
		menuBar.add(edit);
		
		setJMenuBar(menuBar);
	}
	
	class AddClearListener implements ActionListener {
		public void actionPerformed(ActionEvent Event) {
			history.add(new Pair(textArea.getText(), previous_font));
			textArea.setText("");
		}
	}
	
	class AddTextModifierListener implements ActionListener {
		private boolean running = true;
		
		public void setRunning(boolean running) {
			this.running = running;
		}
		
		public void actionPerformed(ActionEvent Event) {
			String selectedString = (String) fontChoice.getSelectedItem();
			
			int size;
			
			if(eight_pt.isSelected()) {
				size = 8;
			}
			else if(sixteen_pt.isSelected()) {
				size = 16;
			}
			else if(twenty_four_pt.isSelected()) {
				size = 24;
			}
			else if(thirty_two_pt.isSelected()) {
				size = 32;
			}
			else {
				size = 40;
			}
			
			if(italicCheckBox.isSelected()) {
				if(boldCheckBox.isSelected()) {
					if(!previous_font.equals(new Font(selectedString, Font.BOLD + Font.ITALIC, size)) && running) {
						history.add(new Pair(previous_font));
						history_for_redo.clear();
						textArea.setFont(new Font(selectedString, Font.BOLD + Font.ITALIC, size));
						previous_font = new Font(selectedString, Font.BOLD + Font.ITALIC, size);
					}
				}
				else {
					if(!previous_font.equals(new Font(selectedString, Font.ITALIC, size))) {
						if(running) {
							history.add(new Pair(previous_font));
							history_for_redo.clear();
						}
						textArea.setFont(new Font(selectedString, Font.ITALIC, size));
						previous_font = new Font(selectedString, Font.ITALIC, size);
					}
				}
			}
			else if (boldCheckBox.isSelected()) {
				if(!previous_font.equals(new Font(selectedString, Font.BOLD, size))) {
					if(running) {
						history.add(new Pair(previous_font));
						history_for_redo.clear();
					}
					textArea.setFont(new Font(selectedString, Font.BOLD, size));
					previous_font = new Font(selectedString, Font.BOLD, size);
				}
			}
			else {
				if(!previous_font.equals(new Font(selectedString, Font.PLAIN, size))) {
					if(running) {
						history.add(new Pair(previous_font));
						history_for_redo.clear();
					}
					textArea.setFont(new Font(selectedString, Font.PLAIN, size));
					previous_font = new Font(selectedString, Font.PLAIN, size);
				}
			}
		}
	}
	
	class AddExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	
	class AddUndoListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(history.size() > 0) {
				
				((AddTextModifierListener)modifyTextListener).setRunning(false);

				Pair pair = history.get(history.size() - 1);
				Font font = pair.getFont();

				if(!font.equals(previous_font)) {
					history_for_redo.add(new Pair(previous_font));
				}
				
				String name = font.getName();
				
				if(font.getName() != previous_font.getName()) {
					if(name == "Arial") {
						fontChoice.setSelectedIndex(0);
					}
					else if(name == "Georgia") {
						fontChoice.setSelectedIndex(1);
					}
					else{
						fontChoice.setSelectedIndex(2);
					}
				}
				
				if(font.getStyle() != previous_font.getStyle()) {
					if(font.getStyle() == Font.PLAIN) {
						italicCheckBox.setSelected(false);
						boldCheckBox.setSelected(false);
					}
					else if(font.getStyle() == Font.ITALIC) {
						italicCheckBox.setSelected(true);
						boldCheckBox.setSelected(false);
					}
					else if(font.getStyle() == Font.BOLD) {
						italicCheckBox.setSelected(false);
						boldCheckBox.setSelected(true);
					}
					else {
						italicCheckBox.setSelected(true);
						boldCheckBox.setSelected(true);
					}
				}
					
				int size = font.getSize();
				if(font.getSize() != previous_font.getSize()) {
					group.clearSelection();
					if(size == 8) {
						eight_pt.setSelected(true);
					}
					else if(size == 16) {
						sixteen_pt.setSelected(true);
					}
					else if(size == 24) {
						twenty_four_pt.setSelected(true);
					}
					else if(size == 32) {
						thirty_two_pt.setSelected(true);
					}
					else {
						fourty_pt.setSelected(true);
					}
				}
			
				textArea.setFont(font);
				if(pair.getClear()) {
					if(!first_undo) {
						if(!textArea.getText().equals("")) {
							history_for_redo.add(new Pair(textArea.getText(), previous_font));
							textArea.setText("");
							if(first_undo == false) {
								first_undo = true;
							}
						}
						else {
							history_for_redo.add(new Pair("", previous_font));
							textArea.setText(pair.getString());
							if(history.size() > 1) {
								history.remove(history.size() - 1);
							}
						}
					}
					else {
						history_for_redo.add(new Pair(textArea.getText(), previous_font));
						textArea.setText(pair.getString());
						if(history.size() > 1) {
							history.remove(history.size() - 1);
						}
					}
				}
				else {
					if(history.size() > 1) {
						history.remove(history.size() - 1);
					}
				}
				
				previous_font = font;
				
				((AddTextModifierListener)modifyTextListener).setRunning(true);
			}
		}
	}
	
	class AddRedoListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(history_for_redo.size() > 0) {
				Pair pair = history_for_redo.get(history_for_redo.size() - 1);
				Font font = pair.getFont();
				
				((AddTextModifierListener)modifyTextListener).setRunning(false);
				
				if(pair.getClear()) {
					if(pair.getString().equals("")) {
						history.add(new Pair(textArea.getText(), font));
					}
					else {
						history.add(new Pair("", font));
					}
				}
				else {
					history.add(new Pair(previous_font));
				}
				
				String name = font.getName();
				if(font.getName() != previous_font.getName()) {
					if(name == "Arial") {;
						fontChoice.setSelectedIndex(0);
					}
					else if(name == "Georgia") {
						fontChoice.setSelectedIndex(1);
					}
					else{
						fontChoice.setSelectedIndex(2);
					}
				}
				
				if(font.getStyle() != previous_font.getStyle()) {
					if(font.getStyle() == Font.PLAIN) {
						italicCheckBox.setSelected(false);
						boldCheckBox.setSelected(false);
					}
					else if(font.getStyle() == Font.ITALIC) {
						italicCheckBox.setSelected(true);
						boldCheckBox.setSelected(false);
					}
					else if(font.getStyle() == Font.BOLD) {
						italicCheckBox.setSelected(false);
						boldCheckBox.setSelected(true);
					}
					else {
						italicCheckBox.setSelected(true);
						boldCheckBox.setSelected(true);
					}
				}
					
				int size = font.getSize();
				if(font.getSize() != previous_font.getSize()) {
					group.clearSelection();
					if(size == 8) {
						eight_pt.setSelected(true);
					}
					else if(size == 16) {
						sixteen_pt.setSelected(true);
					}
					else if(size == 24) {
						twenty_four_pt.setSelected(true);
					}
					else if(size == 32) {
						thirty_two_pt.setSelected(true);
					}
					else {
						fourty_pt.setSelected(true);
					}
				}
				
				textArea.setFont(font);
				if(pair.getClear()) {
					textArea.setText(pair.getString());
				}
				
				previous_font = font;

				history_for_redo.remove(history_for_redo.size() - 1);
				
				((AddTextModifierListener)modifyTextListener).setRunning(true);
			}
		}
	}
	
	
	public static void main(String[] args) {
		TextEditor text = new TextEditor();
	}
}