package ctrl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.TMView;
import data.TuringIO;

/**
 * Implements all the listeners of GUI components
 */
public class TMListener implements ActionListener, KeyListener, ItemListener{
	
	private TMView view;
	private TMCtrl ctrl;
	
	public TMListener(TMView v, TMCtrl c){
		this.view = v;
		this.ctrl = c;
	}

	/* --- Listeners of view --- */

		/* --- KeyListener --- */	
	@Override
	public void keyPressed(KeyEvent e) {
		//If enter on the input field, init the tape
		if(e.getSource() == view.getInputField() && e.getKeyCode() == KeyEvent.VK_ENTER){
			ctrl.init();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		Character c = e.getKeyChar();
		//Consume the key event if the character is not allowed in the machine alphabet
		if(e.getSource() == view.getInputField() && !ctrl.getMachine().getMachineAlpha().contains(c)){
			e.consume();
		}
	}
	/* --- Unused --- */
	@Override
	public void keyReleased(KeyEvent e){;}
	/* -------------- */
	
		/* ------------------- */	

		/* --- ActionListener --- */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		//Start
		if(src == view.getButStart()){
			ctrl.startButton(false);
		}
		//Transition by Transition
		if(src == view.getButStep()){
			ctrl.stepButton();
		}
		//Step by Step
		if(src == view.getButStep2()){
			ctrl.step2Button();
		}
		//Stop
		if(src == view.getButStop()){
			ctrl.stopButton();
		}
		//Reset
		if(src == view.getButReset()){
			ctrl.resetButton();
		}
		
		//MENU
		if(src == view.getMenu_charger()){
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Ouvrir le fichier de configuration");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(view);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				TuringIO.setLOAD_PATH(chooser.getSelectedFile().getAbsolutePath());
				view.loadData();
			}
		}
		if(src == view.getMenu_sauver()){
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choisir de r�pertoire de sauvegarde");
			chooser.setApproveButtonText("Valider");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(view);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				TuringIO.setSAVE_PATH(chooser.getSelectedFile().getAbsolutePath());
				System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
			}
		}
		if(src == view.getMenu_fermer()){
			view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
		}
		if(src == view.getMenu_ex1()){
			TuringIO.setLOAD_PATH("./Fonc_Cleaner.txt");
			view.loadData();
			
		}
		if(src == view.getMenu_ex2()){
			TuringIO.setLOAD_PATH("./Fonc_Copy_Word.txt");
			view.loadData();
		}
		if(src == view.getMenu_vitesse()){
			SpinnerNumberModel sModel = new SpinnerNumberModel(ctrl.getSpeed(), 0, 5000, 50);
			JSpinner spinner = new JSpinner(sModel);
			int option = JOptionPane.showOptionDialog(view, spinner, "Entrez le d�lai d'ex�cution", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(option == JOptionPane.OK_OPTION){
				ctrl.setSpeed((int) spinner.getValue());
			}
		}
	}
		/* --------------------- */

	
		/* --- ItemListener --- */

	@Override
	public void itemStateChanged(ItemEvent e) {
		//RadioButtons
		if(e.getSource() == view.getMenu_radio_50() || e.getSource() == view.getMenu_radio_100() || e.getSource() == view.getMenu_radio_200()){
			if(view.getMenu_radio_50().isSelected()){
				view.getTapePanel().setNbCases(50);
			}
			else if(view.getMenu_radio_100().isSelected()){
				view.getTapePanel().setNbCases(100);
			}
			else if(view.getMenu_radio_200().isSelected()){
				view.getTapePanel().setNbCases(200);
			}
		}
		else{
			if(view.getMenu_radio_jaune().isSelected()){
				view.getTapePanel().setHeadColor(Color.YELLOW, ctrl.getLect());
			}
			else if(view.getMenu_radio_bleu().isSelected()){
				view.getTapePanel().setHeadColor(Color.CYAN, ctrl.getLect());
			}
			else if(view.getMenu_radio_rouge().isSelected()){
				view.getTapePanel().setHeadColor(Color.RED, ctrl.getLect());
			}
			else if(view.getMenu_radio_vert().isSelected()){
				view.getTapePanel().setHeadColor(Color.GREEN, ctrl.getLect());
			}
		}
	}
	
		/* -------------------- */
	
	/**
	 * Setup the new controller when loading a new configuration file
	 * @param c The new controller
	 */
	public void setCtrl(TMCtrl c){
		ctrl = c;
	}
}
