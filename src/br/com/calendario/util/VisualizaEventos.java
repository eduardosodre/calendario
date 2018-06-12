package br.com.calendario.util;

/*  Codigo Gerado Automaticamente Pelo Framework JAVA Facil
 * 
 *  Framework Desenvolvido por Eduardo Nicolini Sodre da Silva
 *  www.studiowebmaster.com.br
 *  classe gerada pelo framework em:05122011122747 
 */


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import br.com.calendario.beans.Evento;
import br.com.calendario.beans.ListaEvento;

import com.towel.el.annotation.AnnotationResolver;
import com.towel.swing.table.ObjectTableModel;

public class VisualizaEventos extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton btnFechar = null;
	private JScrollPane jScrollPane = null;
	private JTable table = null;
	private ObjectTableModel<Evento> model = null;
	private ListaEvento listaEvento = null; // @jve:decl-index=0:
	private JLabel lbStatusObjeto = null;
	private JButton btnEditar = null;
	private String data = null; // @jve:decl-index=0:
	private JButton btnCadastrar = null;
	private JButton btnExcluir = null;
	private List<Evento> lista = new ArrayList<Evento>();  //  @jve:decl-index=0:

	public VisualizaEventos() {// Construtor da classe
		super();// Construtor da classe que estamos extendendo... no caso
		// JDialog
		initialize(); // chama o metodo que inicializa os componentes
	}

	private void initialize() {// metodo que inicializa os componentes
		this.setSize(800, 400);// tamanho da tela
		this.setTitle("Visualizar Eventos");
		this.setContentPane(getJContentPane());
		this.setModal(true);// dizendo que não poderá mecher na tela de tras
		this.setResizable(false);// não irá redimensionar a tela
		this.setLocationRelativeTo(null);// iniciará no centro da tela
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				setListaEvento(getListaEvento());
				carregaLista();// chama o metodo carregaLista
			}
		});
	}
	

	private JPanel getJContentPane() {// metodo que constroi os componentes no
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			lbStatusObjeto = new JLabel();
			lbStatusObjeto.setBounds(new Rectangle(4, 6, 369, 16));
			lbStatusObjeto.setText("");
			lbStatusObjeto.setHorizontalAlignment(SwingConstants.CENTER);
			jContentPane.add(lbStatusObjeto, null);
			// texto no painel
			// texto no painel
			// painel
			// texto no painel
			// no painel
			// no painel
			// no painel
			// checkBox no painel
			// checkBox no painel

			jContentPane.add(getBtnFechar(), null);
			jContentPane.add(getJScrollPane(), null);

			jContentPane.add(getBtnEditar(), null);
			jContentPane.add(getBtnCadastrar(), null);
			jContentPane.add(getBtnExcluir(), null);
		}
		return jContentPane;
	}

	private JButton getBtnFechar() {
		if (btnFechar == null) {
			btnFechar = new JButton();
			btnFechar.setBounds(new Rectangle(677, 331, 110, 30));
			btnFechar.setText("Fechar");
			btnFechar.setMnemonic('F');
			btnFechar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					System.out.println(lista.size());
					dispose();
				}
			});
		}
		return btnFechar;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(5, 25, 783, 302));
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane.setViewportView(getTable());
		}
		return jScrollPane;
	}

	private void carregaLista() {
		carregaTable(listaEvento.getListaEventos());
		lista.addAll(listaEvento.getListaEventos());
	}

	private void carregaTable(List<Evento> lista) {// metodo que carrega os dados na tabela
		// necessario para o funcionamento da tabela
		AnnotationResolver resolver = new AnnotationResolver(Evento.class);
		// instancia o Modelo da tabela.. passamos entre aspas as colunas que
		// queremos que é exibida na tabela separado por virgula sem espaços
		model = new ObjectTableModel<Evento>(resolver, "descricao,observacao,repeteMes,repeteAno");
		model.setData(lista);// passamos a lista para o modelo
		
		table.setModel(model);// passamos o filtro para a tabela
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// maneira de
		// redimensionar a
		// tabela
		table.getColumnModel().getColumn(0).setPreferredWidth(290);// tamanho das
		table.getColumnModel().getColumn(1).setPreferredWidth(290);// tamanho das
		table.getColumnModel().getColumn(2).setPreferredWidth(80);// tamanho das
		table.getColumnModel().getColumn(3).setPreferredWidth(80);// tamanho das
		// colunas
		if (table.getRowCount() > 0) {// caso o tanto de linhas da tabela for
			// maior que zero.. mandamos o foco para
			// a tabela
			table.requestFocus();
			table.changeSelection(0, 0, false, false);
		}
	}

	/**
	 * This method initializes btnEditar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditar() {
		if (btnEditar == null) {
			btnEditar = new JButton();
			btnEditar.setBounds(new Rectangle(120, 331, 110, 30));
			btnEditar.setText("Alterar");
			btnEditar.setMnemonic('A');
			btnEditar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (table.getSelectedRow() > -1) {
						TelaEvento tela = new TelaEvento();
						tela.setEditar(true);
						tela.setEvento(model.getValue(table.getSelectedRow()));
						tela.setVisible(true);
						if (tela.getSucesso()) {
							model.getData().set(table.getSelectedRow(), tela.getEvento());
							lista.set(table.getSelectedRow(), tela.getEvento());
							table.updateUI();
						}
					}
				}
			});
		}
		return btnEditar;
	}

	/**
	 * This method initializes btnCadastrar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCadastrar() {
		if (btnCadastrar == null) {
			btnCadastrar = new JButton();
			btnCadastrar.setBounds(new Rectangle(7, 331, 110, 30));
			btnCadastrar.setText("Novo");
			btnCadastrar.setMnemonic('N');
			btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TelaEvento tela = new TelaEvento();
					tela.setVisible(true);
					if (tela.getSucesso()) {
						Evento evento = tela.getEvento();
						evento = setDatas(evento);
						model.add(evento);
						lista.add(evento);
						table.updateUI();
					}
				}
			});
		}
		return btnCadastrar;
	}
	
	private Evento setDatas(Evento eve){
		String data = getData();
		eve.setDia(Integer.parseInt(data.substring(0, 2)));
		eve.setMes(Integer.parseInt(data.substring(3, 5)));
		eve.setAno(Integer.parseInt(data.substring(6, 10)));
		return eve;
	}

	/**
	 * This method initializes btnExcluir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExcluir() {
		if (btnExcluir == null) {
			btnExcluir = new JButton();
			btnExcluir.setBounds(new Rectangle(233, 331, 110, 30));
			btnExcluir.setText("Excluir");
			btnExcluir.setMnemonic('E');
			btnExcluir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(table.getSelectedRow() > -1){
						lista.remove(table.getSelectedRow());
						model.remove(table.getSelectedRow());
						table.updateUI();
					}
				}
			});
		}
		return btnExcluir;
	}

	public static void main(String args[]) {
		new VisualizaEventos().setVisible(true);
	}

	public ListaEvento getListaEvento() {
		return listaEvento;
	}

	public void setListaEvento(ListaEvento listaEvento) {
		this.listaEvento = listaEvento;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setLista(List<Evento> lista) {
		this.lista = lista;
	}

	public List<Evento> getLista() {
		return lista;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
