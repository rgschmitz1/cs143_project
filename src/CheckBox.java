import javax.swing.JDialog;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class CheckBox {

	private JDialog frame;
	private JLabel jlabel;
	private ArrayList<JCheckBox> chkbxAnswers = new ArrayList<>();
	private ArrayList<GridBagConstraints> gbc_chkbxAnswers = new ArrayList<>();
	private JButton btnConfirm;
	private JButton btnHint;

	/**
	 * Launch the application.
	 */
	public String CheckBoxWindow() {
		try {
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getSelectedBoxes().toString();
	}

	/**
	 * Create the application.
	 */
	public CheckBox(ReadYaml quiz) {
		initialize(quiz);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ReadYaml quiz) {
		frame = new JDialog(null, "", Dialog.ModalityType.APPLICATION_MODAL);
		String title = "Score: " + TestGui.SCORE + " - " + quiz.getTitle();
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);

		jlabel = new JLabel("<html>"+quiz.getQuestion().replaceAll("(\r\n|\n)", "<br />")+"</html>");
		GridBagConstraints gbc_jlabel = new GridBagConstraints();
		gbc_jlabel.gridx = 0;
		gbc_jlabel.gridy = 0;
		frame.getContentPane().add(jlabel, gbc_jlabel);

		// These are the answers
		int i=0;
		for (String s : quiz.getAnswers()) {
			chkbxAnswers.add(new JCheckBox(s));
			chkbxAnswers.get(i).setActionCommand(String.valueOf(i));
			gbc_chkbxAnswers.add(new GridBagConstraints());
			gbc_chkbxAnswers.get(i).anchor = GridBagConstraints.WEST;
			gbc_chkbxAnswers.get(i).gridx = 0;
			gbc_chkbxAnswers.get(i).gridy = i+1;
			frame.getContentPane().add(chkbxAnswers.get(i), gbc_chkbxAnswers.get(i));
			i++;
		}
		i++;

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getSelectedBoxes().size() == 0) {
					JOptionPane.showMessageDialog(null, "You must select an answer before continuing, try again!");
				}
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = i;
		frame.getContentPane().add(btnConfirm, gbc_btnConfirm);

		btnHint = new JButton("Hint");
		btnHint.addActionListener(new Hint(quiz));
		GridBagConstraints gbc_btnHint = new GridBagConstraints();
		gbc_btnHint.gridx = 1;
		gbc_btnHint.gridy = i;
		frame.getContentPane().add(btnHint, gbc_btnHint);
	}

	// Get selected button
	private ArrayList<Integer> getSelectedBoxes() {
		ArrayList<Integer> selectedBoxes = new ArrayList<Integer>();
		for (int i = 0; i < chkbxAnswers.size(); i++) {
			if (chkbxAnswers.get(i).isSelected()) {
				selectedBoxes.add(Integer.parseInt(chkbxAnswers.get(i).getActionCommand()));
			}
		}
		return selectedBoxes;
	}
	// Display a hint
	private class Hint implements ActionListener {
		ReadYaml quiz;
		public Hint(ReadYaml quiz) {
			this.quiz = quiz;
		}

		public void actionPerformed(ActionEvent arg0) {
			JLabel label = new JLabel("<html>" + quiz.getHintText().replaceAll("(\r\n|\n)", "<br />") + "<html/>");
			if (quiz.getHintImage() != null) {
				label.setIcon(new ImageIcon(this.getClass().getResource(quiz.getHintImage())));
			}
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			JOptionPane.showMessageDialog(null, label, "Hint", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
