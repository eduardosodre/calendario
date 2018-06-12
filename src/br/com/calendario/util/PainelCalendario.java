package br.com.calendario.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import br.com.calendario.beans.Evento;
import br.com.calendario.beans.ListaEvento;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DatePicker is a Component with displays a grid of days to be selected.
 */
@SuppressWarnings("serial")
public class PainelCalendario extends JPanel {

	private JLabel previousMonth;
	private JLabel nextMonth;
	private JLabel monthLabel;

	private JLabel weekDaysLabel[] = new JLabel[7];
	private JLabel dayLabels[] = new JLabel[42];

	private Calendar actualSelection;
	private String selectedDay;  //  @jve:decl-index=0:
	private int selectedDayIndex;
	private MouseListener listener;

	public static String[] monthNames;
	public static String[] weekDays;
	
	private ListaEvento lista = null;  //  @jve:decl-index=0:
	
	private Integer tamanhoX = 40;
	private Integer tamanhoY = 40;

	static {
		monthNames = new String[12];

		monthNames[0] = "Janeiro/";
		monthNames[1] = "Fevereiro/";
		monthNames[2] = "Março/";
		monthNames[3] = "Abril/";
		monthNames[4] = "Maio/";
		monthNames[5] = "Junho/";
		monthNames[6] = "Julho/";
		monthNames[7] = "Agosto/";
		monthNames[8] = "Setembro/";
		monthNames[9] = "Outubro/";
		monthNames[10] = "Novembro/";
		monthNames[11] = "Dezembro/";

		weekDays = new String[7];
		weekDays[0] = "D";
		weekDays[1] = "S";
		weekDays[2] = "T";
		weekDays[3] = "Q";
		weekDays[4] = "Q";
		weekDays[5] = "S";
		weekDays[6] = "S";
	}

	/**
	 * Construtor
	 * 
	 * @param cal
	 *            - CalendarView from where this is opening from
	 * @param day
	 *            Current day, use zero to show current day
	 * @param month
	 *            Current month (Can be zero if day is zero two)
	 * @param year
	 *            Current year (Can be zero if day is zero two)
	 */
	public PainelCalendario() {
		int day = 0, month = 0, year = 0;
		listener = new MouseListener();

		previousMonth = createLabelWithBorder("<");
		previousMonth.setForeground(Color.white);
		nextMonth = createLabelWithBorder(">");
		nextMonth.setForeground(Color.white);
		monthLabel = createLabelWithBorder("");
		monthLabel.setForeground(Color.white);

		selectedDay = "0";

		actualSelection = Calendar.getInstance();
		if (day == 0) {
			actualSelection.set(Calendar.DAY_OF_MONTH, getToday());
			actualSelection.set(Calendar.MONTH, getCurrentMonth());
			actualSelection.set(Calendar.YEAR, getCurrentYear());
		} else {
			actualSelection.set(Calendar.DAY_OF_MONTH, day);
			actualSelection.set(Calendar.MONTH, month);
			actualSelection.set(Calendar.YEAR, year);
		}

		init();

		String monthName = getMonthName(actualSelection.get(Calendar.MONTH));
		monthLabel.setText(monthName);

		setSelectedDay(populateCells());
	}
	
	public PainelCalendario(int tamanhoX, int tamanhoY) {
		this.tamanhoX = tamanhoX;
		this.tamanhoY = tamanhoY;
		
		int day = 0, month = 0, year = 0;
		listener = new MouseListener();

		previousMonth = createLabelWithBorder("<");
		nextMonth = createLabelWithBorder(">");
		monthLabel = createLabelWithBorder("");

		selectedDay = "0";

		actualSelection = Calendar.getInstance();
		if (day == 0) {
			actualSelection.set(Calendar.DAY_OF_MONTH, getToday());
			actualSelection.set(Calendar.MONTH, getCurrentMonth());
			actualSelection.set(Calendar.YEAR, getCurrentYear());
		} else {
			actualSelection.set(Calendar.DAY_OF_MONTH, day);
			actualSelection.set(Calendar.MONTH, month);
			actualSelection.set(Calendar.YEAR, year);
		}

		init();

		String monthName = getMonthName(actualSelection.get(Calendar.MONTH));
		monthLabel.setText(monthName);

		setSelectedDay(populateCells());
	}

	private void init() {
		setLayout(null);

		Font fontHeader = new Font("SansSerif", Font.BOLD, 11);
		Font fontCells = new Font("SansSerif", Font.PLAIN, 11);
		
		int larguraTotal = 7*tamanhoX;
		

		previousMonth.setFont(fontHeader);
		previousMonth.setBounds(0, 0, tamanhoX, tamanhoY);
		previousMonth.setHorizontalAlignment(SwingConstants.CENTER);
//		previousMonth.setBackground(new Color(188, 227, 227));
		previousMonth.setBackground(new Color(0, 120, 241));
		previousMonth.setOpaque(true);
		previousMonth.addMouseListener(listener);
		add(previousMonth);

		nextMonth.setFont(fontHeader);
		nextMonth.setBounds(tamanhoX+(larguraTotal-(tamanhoX*2)), 0, tamanhoX, tamanhoY);
		nextMonth.setHorizontalAlignment(SwingConstants.CENTER);
//		nextMonth.setBackground(new Color(188, 227, 227));
		nextMonth.setBackground(new Color(0, 120, 241));
		nextMonth.setOpaque(true);
		nextMonth.addMouseListener(listener);
		add(nextMonth);

		monthLabel.setFont(fontHeader);
		monthLabel.setBounds(tamanhoX, 0, larguraTotal-(tamanhoX*2), tamanhoY);
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		monthLabel.setBackground(new Color(188, 227, 227));
		monthLabel.setBackground(new Color(0, 120, 241));
		monthLabel.setOpaque(true);
		monthLabel.addMouseListener(listener);
		add(monthLabel);

		int px = 0;
		int alt = tamanhoY;
		for(int i=0;i<7;i++){
			weekDaysLabel[i] = new JLabel(weekDays[i].toString());
			weekDaysLabel[i] = createLabelWithBorder("");
			weekDaysLabel[i].setFont(fontHeader);
			weekDaysLabel[i].setText(weekDays[i].toString());
			weekDaysLabel[i].setBounds(px, alt, tamanhoX, tamanhoY);
			weekDaysLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			weekDaysLabel[i].setBackground(Color.white);
			weekDaysLabel[i].setOpaque(true);
			weekDaysLabel[i].addMouseListener(listener);
			add(weekDaysLabel[i]);
			px += tamanhoX;
		}
//		StringBuilder weekDays = new StringBuilder();
//		for (String s : DatePicker.weekDays)
//			weekDays.append(s).append("   ");
//		weekDays.delete(weekDays.length() - 3, weekDays.length());

//		weekDaysLabel = new JLabel(weekDays.toString());
//		weekDaysLabel.setFont(fontHeader);
//		weekDaysLabel.setBounds(0, 20, 139, 15);
//		weekDaysLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		weekDaysLabel.setBackground(Color.white);
//		weekDaysLabel.setOpaque(true);
//		weekDaysLabel.addMouseListener(listener);
//		add(weekDaysLabel);

		int altura = tamanhoY, largura = tamanhoX;
		int x = 0;
		int y = 2*tamanhoY;
		int col = 0;
		for (int i = 0; i < 42; i++) {
			dayLabels[i] = createLabelWithBorder("");
			dayLabels[i].setBounds(x, y, largura, altura);
			dayLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			dayLabels[i].setFont(fontCells);
			dayLabels[i].setOpaque(true);
			dayLabels[i].setBackground(Color.white);
			dayLabels[i].addMouseListener(listener);
			dayLabels[i].setToolTipText("");
			add(dayLabels[i]);
			col++;
			if (col == 7) {
				col = 0;
				y += altura;
				x = 0;
			} else {
				x += largura;
			}
		}
		
	}

	private int getToday() {
		return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
	}

	private int getCurrentMonth() {
		return Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) - 1;
	}

	private int getCurrentYear() {
		return Integer
				.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	}
	
	
	private void salvar(){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance("br.com.calendario.beans");
			Marshaller unmarshaller = context.createMarshaller();
			unmarshaller.marshal(lista, new File("calendario/cal.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
			File file = new File("calendario");
			if(!file.exists()){
				file.mkdir();
			}
			try {
				context = JAXBContext.newInstance("br.com.calendario.beans");
				Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(new ListaEvento(), new File("calendario/cal.xml"));
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			
		}
	}

	private int populateCells() {
		File diretorio = new File("calendario");
		if(!diretorio.exists()){
			diretorio.mkdir();
		}
		JAXBContext context;
		try {
			context = JAXBContext.newInstance("br.com.calendario.beans");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			lista = unmarshaller.unmarshal(new StreamSource(new File("calendario/cal.xml")), ListaEvento.class).getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
			File file = new File("calendario");
			if(!file.exists()){
				file.mkdir();
			}
			try {
				context = JAXBContext.newInstance("br.com.calendario.beans");
				Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(new ListaEvento(), new File("calendario/cal.xml"));
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
			
		}
		
		
		Calendar now = Calendar.getInstance();
		now.clear(Calendar.DATE);
		now.set(actualSelection.get(Calendar.YEAR), actualSelection.get(Calendar.MONTH), 1);
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		int monthDay = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int mes = now.get(Calendar.MONTH);
		int ano = now.get(Calendar.YEAR);
		
		int i = 0;

		int day = 1;
		mes++;
//		System.out.println(mes);
		for (int j = 0; j < 42; j++) {
			if ((j < weekDay - 1) || (j > (monthDay + weekDay - 2)))
				dayLabels[j].setText("");
			else {
				dayLabels[j].setText(String.valueOf(day));
				if (actualSelection.get(Calendar.DAY_OF_MONTH) == day){
					i = j;
				}
				
				
				if(lista != null){
					for (int k = 0; k < lista.getListaEventos().size(); k++) {
						Evento ev = lista.getListaEventos().get(k);
//						System.out.println("Ano Calendario = "+ano+" - "+ev.getAno()+" - Mes cal: "+mes+" - "+ev.getMes()+" - Dia cal: "+day+" - "+ev.getDia());
//						System.out.println(j);
						if(ev.getDia() == day){
							if(ev.getRepeteMes()){
								dayLabels[j].setToolTipText(dayLabels[j].getToolTipText()+lista.getListaEventos().get(k).getDescricao()+" / ");
								dayLabels[j].setBackground(Color.red);
//								System.out.println("dia "+day);
							}else if(ev.getMes() != null && ev.getMes() == mes){
								if(ev.getRepeteAno()){
									dayLabels[j].setToolTipText(dayLabels[j].getToolTipText()+lista.getListaEventos().get(k).getDescricao()+" / ");
									dayLabels[j].setBackground(Color.red);
								}else if(ev.getAno() != null && ev.getAno() == ano){
									dayLabels[j].setToolTipText(dayLabels[j].getToolTipText()+lista.getListaEventos().get(k).getDescricao()+" / ");
									dayLabels[j].setBackground(Color.red);
								}
//								System.out.println(ev.getDescricao()+" - "+ev.getRepeteAno());
							}
						}
					}
				}
				day++;
			}
			if(dayLabels[j].getToolTipText() != null  && dayLabels[j].getToolTipText().length() > 0){
				dayLabels[j].setToolTipText(dayLabels[j].getToolTipText().substring(0, dayLabels[j].getToolTipText().length()-2));
			}
		}
		return i;
	}

	private String getMonthName(int month) {
		return monthNames[month] + actualSelection.get(Calendar.YEAR);
	}


	public String getDate() {

		StringBuilder result = new StringBuilder();

		int day = Integer.parseInt(selectedDay);

		if (day == 0) {
			selectedDay = String.valueOf(actualSelection
					.get(Calendar.DAY_OF_MONTH));
			day = Integer.parseInt(selectedDay);
		}

		if (day < 10)
			result.append("0");
		result.append(selectedDay).append("/");

//		System.out.println(actualSelection.get(Calendar.MONTH));
		if (actualSelection.get(Calendar.MONTH) < 9)
			result.append("0");

		result.append(actualSelection.get(Calendar.MONTH) + 1).append("/").append(actualSelection.get(Calendar.YEAR));
		return result.toString();
	}

	/**
	 * Method wich select the day (Highlight it)
	 */
	public void setSelectedDay(int x) {
		dayLabels[x].setBackground(new Color(72, 164, 255));
		dayLabels[x].setForeground(Color.red);
		selectedDayIndex = x;
	}

	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			for (int i = 1; i < 42; i++) {
				if (e.getSource() == dayLabels[i]) {
					if (dayLabels[i].getText() != "") {
						setSelectedDay(i);
						selectedDay = String.valueOf(dayLabels[i].getText());
					}
					
					if(e.getClickCount() == 2){
						ListaEvento listaEvento = new ListaEvento();
						List<Evento> lista1 = new ArrayList<Evento>();
						for (int j = 0; j < lista.getListaEventos().size(); j++) {
							Evento ev = lista.getListaEventos().get(j);
							if(ev.getDia() == Integer.parseInt(dayLabels[i].getText())){
								if(ev.getRepeteMes()){
									listaEvento.getListaEventos().add(ev);
									lista1.add(ev);
								}else if(ev.getMes() != null && ev.getMes() == actualSelection.get(Calendar.MONTH)+1){
									listaEvento.getListaEventos().add(ev);
									lista1.add(ev);
								}
							}
						}
						for (int j = 0; j < lista1.size(); j++) {
							lista.getListaEventos().remove(lista1.get(j));
						}
						String dia = Integer.parseInt(dayLabels[i].getText())+"";
						if(Integer.parseInt(dayLabels[i].getText()) < 10){
							dia = "0"+Integer.parseInt(dayLabels[i].getText());
						}
						String mes = (actualSelection.get(Calendar.MONTH)+1)+"";
						if((actualSelection.get(Calendar.MONTH)+1) < 10){
							mes = "0"+(actualSelection.get(Calendar.MONTH)+1);
						}
						VisualizaEventos tela = new VisualizaEventos();
						tela.setListaEvento(listaEvento);
						tela.setData(dia+"/"+mes+"/"+actualSelection.get(Calendar.YEAR));
						tela.setVisible(true);
						lista.getListaEventos().addAll(tela.getLista());
						salvar();
					}
				}
			}

			dayLabels[selectedDayIndex].setBackground(Color.lightGray);
			dayLabels[selectedDayIndex].setForeground(Color.black);

			if (e.getSource() == nextMonth){
				actualSelection.add(Calendar.MONTH, 1);
				pintarBranco();
			}

			if (e.getSource() == previousMonth){
				actualSelection.add(Calendar.MONTH, -1);
				pintarBranco();
			}

			String monthName = getMonthName(actualSelection.get(Calendar.MONTH));
			monthLabel.setText(monthName);
			populateCells();
		}
	}
	
	private void pintarBranco(){
		for (int i = 0; i < 42; i++) {
			dayLabels[i].setBackground(Color.white);
			dayLabels[i].setToolTipText(null);
		}
	}
	

	private JLabel createLabelWithBorder(String text) {
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}