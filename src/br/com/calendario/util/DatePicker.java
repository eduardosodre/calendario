package br.com.calendario.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * DatePicker is a Component with displays a grid of days to be selected.
 */
@SuppressWarnings("serial")
public class DatePicker extends JPanel {
	private TextDataChooser calendar;

	private JLabel previousMonth;
	private JLabel nextMonth;
	private JLabel monthLabel;

	private JLabel weekDaysLabel[] = new JLabel[7];
	private JLabel dayLabels[] = new JLabel[42];
	private JButton today = new JButton();

	private Calendar actualSelection;
	private String selectedDay; // @jve:decl-index=0:
	private int selectedDayIndex;
	private MouseListener listener;

	public static String[] monthNames;
	public static String[] weekDays;

	static {
		monthNames = new String[12];

		monthNames[0] = "Janeiro/";
		monthNames[1] = "Fevereiro/";
		monthNames[2] = "Mar�o/";
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
	public DatePicker(TextDataChooser cal, int day, int month, int year) {
		calendar = cal;
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
		this.setBounds(0, 0, 340, 345);
		// this.setBackground(Color.black);
	}

	private void init() {
		setLayout(null);

		Font fontHeader = new Font("SansSerif", Font.BOLD, 11);
		Font fontCells = new Font("SansSerif", Font.PLAIN, 11);

		previousMonth.setFont(fontHeader);
		previousMonth.setBounds(0, 0, 15, 20);
		previousMonth.setHorizontalAlignment(SwingConstants.CENTER);
		// previousMonth.setBackground(new Color(188, 227, 227));
		previousMonth.setBackground(new Color(0, 120, 241));
		previousMonth.setOpaque(true);
		previousMonth.addMouseListener(listener);
		add(previousMonth);

		nextMonth.setFont(fontHeader);
		nextMonth.setBounds(160, 0, 15, 20);
		nextMonth.setHorizontalAlignment(SwingConstants.CENTER);
		nextMonth.setBackground(new Color(0, 120, 241));
		nextMonth.setOpaque(true);
		nextMonth.addMouseListener(listener);
		add(nextMonth);

		monthLabel.setFont(fontHeader);
		monthLabel.setBounds(15, 0, 150, 20);
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setBackground(new Color(0, 120, 241));
		monthLabel.setOpaque(true);
		monthLabel.addMouseListener(listener);
		add(monthLabel);

		int px = 0;
		for (int i = 0; i < 7; i++) {
			weekDaysLabel[i] = new JLabel(weekDays[i].toString());
			weekDaysLabel[i] = createLabelWithBorder("");
			weekDaysLabel[i].setFont(fontHeader);
			weekDaysLabel[i].setText(weekDays[i].toString());
			weekDaysLabel[i].setBounds(px, 20, 25, 20);
			weekDaysLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			weekDaysLabel[i].setBackground(Color.white);
			weekDaysLabel[i].setOpaque(true);
			weekDaysLabel[i].addMouseListener(listener);
			add(weekDaysLabel[i]);
			px += 25;
		}
		// StringBuilder weekDays = new StringBuilder();
		// for (String s : DatePicker.weekDays)
		// weekDays.append(s).append("   ");
		// weekDays.delete(weekDays.length() - 3, weekDays.length());

		// weekDaysLabel = new JLabel(weekDays.toString());
		// weekDaysLabel.setFont(fontHeader);
		// weekDaysLabel.setBounds(0, 20, 139, 15);
		// weekDaysLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// weekDaysLabel.setBackground(Color.white);
		// weekDaysLabel.setOpaque(true);
		// weekDaysLabel.addMouseListener(listener);
		// add(weekDaysLabel);

		int altura = 20, largura = 25;
		int x = 0;
		int y = 40;
		int col = 0;
		for (int i = 0; i < 42; i++) {
			dayLabels[i] = createLabelWithBorder("");
			dayLabels[i].setBounds(x, y, largura, altura);
			dayLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			dayLabels[i].setFont(fontCells);
			dayLabels[i].setOpaque(true);
			dayLabels[i].setBackground(Color.white);
			dayLabels[i].addMouseListener(listener);
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
		today.setBounds(0, 160, 175, 20);
		today.setMnemonic('H');
		today.setForeground(Color.black);
		today.setText("Hoje");
		today.addMouseListener(listener);
		add(today);
	}

	private int getToday() {
		return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
	}

	private int getCurrentMonth() {
		return Integer.parseInt(new SimpleDateFormat("MM").format(new Date())) - 1;
	}

	private int getCurrentYear() {
		return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
	}

	private int populateCells() {
		Calendar now = Calendar.getInstance();
		now.clear(Calendar.DATE);
		now.set(actualSelection.get(Calendar.YEAR), actualSelection.get(Calendar.MONTH), 1);
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		int monthDay = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		int i = 0;

		int day = 1;

		for (int j = 0; j < 42; j++) {
			if ((j < weekDay - 1) || (j > (monthDay + weekDay - 2)))
				dayLabels[j].setText("");
			else {
				dayLabels[j].setText(String.valueOf(day));
				if (actualSelection.get(Calendar.DAY_OF_MONTH) == day)
					i = j;
				day++;
			}
		}
		return i;
	}

	private String getMonthName(int month) {
		return monthNames[month] + actualSelection.get(Calendar.YEAR);
	}

	/**
	 * Retorna a data Selecionada
	 * 
	 * @return String - Data
	 */
	public String getDate() {

		StringBuilder result = new StringBuilder();

		int day = Integer.parseInt(selectedDay);

		if (day == 0) {
			selectedDay = String.valueOf(actualSelection.get(Calendar.DAY_OF_MONTH));
			day = Integer.parseInt(selectedDay);
		}

		if (day < 10)
			result.append("0");
		result.append(selectedDay).append("/");

		// System.out.println(actualSelection.get(Calendar.MONTH));
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
		dayLabels[x].setForeground(Color.white);
		selectedDayIndex = x;
	}

	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			for (int i = 1; i < 42; i++) {
				if (e.getSource() == dayLabels[i]) {
					if (dayLabels[i].getText() != "") {
						setSelectedDay(i);
						selectedDay = String.valueOf(dayLabels[i].getText());
						calendar.dateSelected(getDate());
					}
				}
			}

			if (e.getSource() == today) {
				selectedDay = String.valueOf(getToday());
				actualSelection.set(Calendar.MONTH, getCurrentMonth());
				actualSelection.set(Calendar.YEAR, getCurrentYear());

				setSelectedDay(actualSelection.get(Calendar.DAY_OF_MONTH) + 1);
				calendar.dateSelected(getDate());
				return;
			}

			// If it's not 'today' pressed, then it is month 'next' or
			// 'previous'

			dayLabels[selectedDayIndex].setBackground(Color.lightGray);
			dayLabels[selectedDayIndex].setForeground(Color.black);

			if (e.getSource() == nextMonth)
				actualSelection.add(Calendar.MONTH, 1);

			if (e.getSource() == previousMonth)
				actualSelection.add(Calendar.MONTH, -1);

			String monthName = getMonthName(actualSelection.get(Calendar.MONTH));
			monthLabel.setText(monthName);
			populateCells();
		}
	}

	private JLabel createLabelWithBorder(String text) {
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createEtchedBorder());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
