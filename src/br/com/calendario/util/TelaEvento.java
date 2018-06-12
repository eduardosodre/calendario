package br.com.calendario.util;

/*  Codigo Gerado Automaticamente Pelo Framework JAVA Facil
 * 
 *  Framework Desenvolvido por Eduardo Nicolini Sodre da Silva
 *  www.studiowebmaster.com.br
 *  classe gerada pelo framework em:05122011122747 
 */


import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.calendario.beans.Evento;


public class TelaEvento extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lbDescricao = null;
	private JTextField txtDescricao = null;
	private JLabel lbObservacao = null;
	private JTextArea txtObservacao = null;
	private JLabel lbRepeteAno = null;
	private JCheckBox checkRepeteAno = null;
	private JLabel lbRepeteMes = null;
	private JCheckBox checkRepeteMes = null;
	private JButton btnFechar = null;
	private JButton btnSalvar = null;
	private boolean editar = false;
	private JLabel lbStatusObjeto = null;
	private JScrollPane jScrollPane1 = null;
	private Evento evento = null;
	private Boolean sucesso = false;  //  @jve:decl-index=0:
	
	public TelaEvento() {// Construtor da classe
		super();// Construtor da classe que estamos extendendo... no caso
				// JDialog
		initialize(); // chama o metodo que inicializa os componentes
	}

	private void initialize() {// metodo que inicializa os componentes
		this.setSize(730, 354);// tamanho da tela
		this.setTitle("Cadastro de Evento");
		this.setContentPane(getJContentPane());// setando o painel do nosso
												// formulario - o
												// GetJContentPane() é um metodo
												// logo abaixo
		this.setModal(true);// dizendo que não poderá mecher na tela de tras
		this.setResizable(false);// não irá redimensionar a tela
		this.setLocationRelativeTo(null);// iniciará no centro da tela
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if(isEditar()){
					mostra(getEvento());
				}
			}
		});
	}


	private JPanel getJContentPane() {// metodo que constroi os componentes no
										// painel principal
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			lbStatusObjeto = new JLabel();
			lbStatusObjeto.setBounds(new Rectangle(10, 5, 369, 16));
			lbStatusObjeto.setText("");
			lbStatusObjeto.setHorizontalAlignment(SwingConstants.LEFT);
			jContentPane.add(lbStatusObjeto, null);
													// texto no painel
			lbDescricao = new JLabel();
			lbDescricao.setBounds(new Rectangle(10, 32, 100, 30));
			lbDescricao.setText("Descricao:");
			jContentPane.add(lbDescricao, null);// adicionando a label no painel
			jContentPane.add(getTxtDescricao(), null);// adicionando o campo de
														// texto no painel
			lbObservacao = new JLabel();
			lbObservacao.setBounds(new Rectangle(10, 67, 100, 30));
			lbObservacao.setText("Observacao:");
			jContentPane.add(lbObservacao, null);// adicionando a label no
													// painel
														// texto no painel
												// no painel
												// no painel
												// no painel
			lbRepeteAno = new JLabel();
			lbRepeteAno.setBounds(new Rectangle(11, 207, 100, 30));
			lbRepeteAno.setText("RepeteAno:");
			jContentPane.add(lbRepeteAno, null);// adicionando a label no painel
			jContentPane.add(getCheckRepeteAno(), null);// adicionando o
														// checkBox no painel
			lbRepeteMes = new JLabel();
			lbRepeteMes.setBounds(new Rectangle(11, 242, 100, 30));
			lbRepeteMes.setText("RepeteMes:");
			jContentPane.add(lbRepeteMes, null);// adicionando a label no painel
			jContentPane.add(getCheckRepeteMes(), null);// adicionando o
														// checkBox no painel
													// painel
			jContentPane.add(getBtnFechar(), null);
			jContentPane.add(getBtnSalvar(), null);
			jContentPane.add(getJScrollPane1(), null);
		}
		return jContentPane;
	}

	// metodo que constroi o componente
	private JTextField getTxtDescricao() {
		if (txtDescricao == null) {
			txtDescricao = new JTextField();
			txtDescricao.setBounds(new Rectangle(155, 32, 386, 30));
		}
		return txtDescricao;
	}

	// metodo que constroi o componente
	private JTextArea getTxtObservacao() {
		if (txtObservacao == null) {
			txtObservacao = new JTextArea();
		}
		return txtObservacao;
	}

	// metodo que constroi o componente
	//
	private JCheckBox getCheckRepeteAno() {
		if (checkRepeteAno == null) {
			checkRepeteAno = new JCheckBox();
			checkRepeteAno.setBounds(new Rectangle(156, 207, 60, 30));
		}
		return checkRepeteAno;
	}

	// metodo que constroi o componente
	//
	private JCheckBox getCheckRepeteMes() {
		if (checkRepeteMes == null) {
			checkRepeteMes = new JCheckBox();
			checkRepeteMes.setBounds(new Rectangle(156, 242, 60, 30));
		}
		return checkRepeteMes;
	}

	private void mostra(Evento objeto) {// metodo recebe a instancia de um
										// objeto e é exibido no formulario
		lbStatusObjeto.setText("Exibindo Registro");
		// é inteiro.. tem que por o toString no final
		txtDescricao.setText(objeto.getDescricao());
		txtObservacao.setText(objeto.getObservacao());
		checkRepeteAno.setSelected(objeto.getRepeteAno());
		checkRepeteMes.setSelected(objeto.getRepeteMes());
	}

	private JButton getBtnFechar() {
		if (btnFechar == null) {
			btnFechar = new JButton();
			btnFechar.setBounds(new Rectangle(602, 283, 110, 30));
			btnFechar.setText("Fechar");
			btnFechar.setMnemonic('F');
			btnFechar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnFechar;
	}

	private JButton getBtnSalvar() {
		if (btnSalvar == null) {
			btnSalvar = new JButton();
			btnSalvar.setBounds(new Rectangle(11, 283, 110, 30));
			btnSalvar.setText("Salvar");
			btnSalvar.setMnemonic('S');
			btnSalvar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(isEditar()){
						Evento evento = getEvento();
						evento = monta(evento);
						setEvento(evento);
						setSucesso(true);
						dispose();
					}else{
						Evento evento = new Evento();
						evento = monta(evento);
						setEvento(evento);
						setSucesso(true);
						dispose();
					}
				}
			});
		}
		return btnSalvar;
	}

	private Evento monta(Evento c) { // metodo que monta o objeto para salvar ou
										// alterar
	// passamos os dados do formulario para o objeto
		c.setDescricao(txtDescricao.getText());
		c.setObservacao(txtObservacao.getText());
		c.setRepeteAno(checkRepeteAno.isSelected());
		c.setRepeteMes(checkRepeteMes.isSelected());
		return c;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(155, 63, 553, 140));
			jScrollPane1.setViewportView(getTxtObservacao());
		}
		return jScrollPane1;
	}

	public static void main(String args[]) {
		new TelaEvento().setVisible(true);
	}
	

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public boolean isEditar() {
		return editar;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
