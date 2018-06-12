package br.com.calendario.util;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Teste extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel painel = null;
	
	public Teste() {
		super();
		initialize();
	}


	private void initialize() {
		this.setSize(613, 598);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Agenda");
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(null);
	}

	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getPainel(), null);
		}
		return jContentPane;
	}

	private JPanel getPainel() {
		if (painel == null) {
			painel = new PainelCalendario(85,70);
			painel.setBounds(new Rectangle(0, 0, 596, 563));
		}
		return painel;
	}


	public static void main(String[] args) {
		new Teste().setVisible(true);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"