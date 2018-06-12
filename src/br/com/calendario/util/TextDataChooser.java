package br.com.calendario.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.towel.awt.ann.Action;
import com.towel.awt.ann.ActionManager;
import com.towel.time.DateUtils;

@SuppressWarnings("serial")
public class TextDataChooser extends JFormattedTextField {
	

	private DatePicker cal;
	@Action(method = "openPopup")
    public JButton btnFolder;
	
	private int cont = 0;
	private JDialog glassPane;
    
    public TextDataChooser() {
        super(); 
        this.setLayout(new BorderLayout());
        glassPane = new JDialog();
        glassPane.setUndecorated(true);
		glassPane.setSize(175, 180);
		glassPane.setModal(true);
		glassPane.setTitle("Data");
        btnFolder = new JButton();
        btnFolder.setIcon(new ImageIcon(getClass().getResource("/icones/calendar.png")));
        btnFolder.setPreferredSize(new Dimension(15,10));
        btnFolder.setBackground(null);
        btnFolder.setOpaque(false);
        btnFolder.setBackground(new Color(204, 204, 255));
        btnFolder.setBorderPainted(false);
        this.add(btnFolder, BorderLayout.EAST);
        this.setSelectionEnd(10);
        this.setText("__/__/____");
		this.setColumns(10);

		this.addKeyListener(keyAdapter);
		this.addFocusListener(focusAdapter);
		this.addMouseListener(mouseAdapter);
		new ActionManager(this);
    }
    
    public TextDataChooser(int x, int y) {
        super();
        this.setLayout(new BorderLayout());
        glassPane = new JDialog();
        glassPane.setUndecorated(true);
		glassPane.setSize(175, 180);
		glassPane.setModal(true);
		glassPane.setTitle("Data");
        btnFolder = new JButton();
        btnFolder.setIcon(new ImageIcon(getClass().getResource("/icones/calendar.png")));
        btnFolder.setBackground(null);
        btnFolder.setOpaque(false);
        btnFolder.setBackground(new Color(204, 204, 255));
        btnFolder.setBorderPainted(false);
        btnFolder.setPreferredSize(new Dimension(15,10));
        this.add(btnFolder, BorderLayout.EAST);
        this.setSelectionEnd(10);
        this.setText("__/__/____");
		this.setColumns(10);

		this.addKeyListener(keyAdapter);
		this.addFocusListener(focusAdapter);
		this.addMouseListener(mouseAdapter);
		new ActionManager(this);
    }
    
//    public void setEnabled(boolean b){
//    	btnFolder.setEnabled(b);
//    	this.setEnabled(b);
//    }
    
    public Calendar getSelectedDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.parseDate(getText()));
		return cal;
	}
    
    public void dateSelected(String s) {
		this.setText(s);
		glassPane.remove(cal);
		cal = null;
		glassPane.setVisible(false);
	}
    
    @SuppressWarnings("unused")
	private void openPopup() {
		String strDia = this.getText();
		if (DateUtils.isValidDate(strDia)) {
			int dia = Integer.parseInt(strDia.substring(0, 2));
			int mes = Integer.parseInt(strDia.substring(3, 5));
			int ano = Integer.parseInt(strDia.substring(6, 10));

			cal = new DatePicker(this, dia, mes - 1, ano);
		} else {
			cal = new DatePicker(this, 0, 0, 0);
		}

		if(isEnabled()){
			JPanel content = new JPanel();
			content.setLayout(null);
			content.add(cal);
			
			content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
			content.getActionMap().put("ESCAPE", new AbstractAction("ESCAPE") {
				public void actionPerformed(ActionEvent evt) {
					glassPane.dispose();
				}
			});

			glassPane.setLocation(btnFolder.getLocationOnScreen());
			glassPane.setContentPane(content);
			glassPane.setVisible(true);
		}
	}
    
    
 // ==================KEYLISTENER=================================================
	private KeyAdapter keyAdapter = new KeyAdapter() {
		public void keyTyped(KeyEvent k) {
			char c = k.getKeyChar();
			if ((getText().length() > 9) & (!getText().equals("__/__/____"))){
//			if ((getText().length() > 9)){
				if(getSelectionStart() == 0 && getSelectionEnd() == 10){
					if ((c < '0') | (c > '9')){
						k.consume();
					}else{
						setText("");
						cont = 1;
					}
				}else if(getSelectionStart() == getSelectionEnd() && getSelectionEnd() < 10){
					if ((c < '0') | (c > '9')){
						k.consume();
					}else{
						int i = getSelectionStart();
						int proximo = i+1;
						setText(getText().substring(0, i)+c+getText().substring(proximo, 10));
						if(proximo == 2 || proximo == 5){
							proximo++;
						}
						setSelectionStart(proximo);
						setSelectionEnd(proximo);
//						System.out.println(getSelectionStart());
					}
					k.consume();
				}else{
					k.consume();
				}
			}else {
				if ((c < '0') | (c > '9'))// & (c != '/'))
					k.consume();
				else {
					if (cont == 0) {
						setText("");
						cont = 1;
					}
					switch (getText().length()) {
					case 2:
						setText(getText() + "/");
						break;
					case 5:
						setText(getText() + "/");
						break;
					}
				}
			}
		}
		
		@SuppressWarnings("static-access")
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == e.VK_A){
				if(isDate(getText())){
					Date data = convertStringToDate(getText());
					setText(convertDatatoString(addDias(data, 1)));
				}else{
					setText(convertDatatoString(new Date()));
				}
			}else if(e.getKeyCode() == e.VK_O){
				if(isDate(getText())){
					Date data = convertStringToDate(getText());
					setText(convertDatatoString(removeDias(data, 1)));
				}else{
					Date data = new Date();
					setText(convertDatatoString(removeDias(data, 1)));
				}
			}else if(e.getKeyCode() == e.VK_H){
				Date data = new Date();
				setText(convertDatatoString(data));
			}
		}
	};
	

	private FocusAdapter focusAdapter = new FocusAdapter() {
		public void focusLost(FocusEvent fe) {
			if (!getText().equals("__/__/____") & !getText().equals("")) {
				if(getText().length() == 5){
					setText(getText()+"/"+getDataYYYY());
				}else if(getText().length() > 7){
					if(getText().length() < 10){
						setText(
								getText().substring(0, 6)+
								getDataYY()+
								getText().substring(6, 8));
					}
				}else if(getText().length() == 2){
					setText(getText()+"/"+getDataMM()+"/"+getDataYYYY());
				}
				if (!DateUtils.isValidDate(getText())) {
//					JOptionPane.showMessageDialog(null, "Data Inválida");
				}
			}
		}
	};
	
	private MouseAdapter mouseAdapter = new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				setText(getDataAtual());
			}
		}
	};
	
	public String getDataYY() {
        String data = "yyyy";
        String data1;

        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);

        return data1.substring(0,2);
    }
	
	public String getDataYYYY() {
        String data = "yyyy";
        String data1;

        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);

        return data1;
    }
	
	public String getDataMM() {
        String data = "MM";
        String data1;

        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);

        return data1;
    }
	
	public Date getData() {
		return convertStringToDate(getText());
	}
	
	private String getDataAtual() {
        String data = "dd/MM/yyyy";
        String data1;

        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);

        return data1;
    }
	
 	public Date addDias(Date date, int dias) {   
 	     
 	   Calendar calendar = Calendar.getInstance();   
 	   calendar.setTime(date);   
 	   calendar.add(Calendar.DATE, dias); 
 	  
 	   return calendar.getTime();   
 	      
 	   }
  	
  	public Date removeDias(Date date, Integer dias) {   
         GregorianCalendar gc = new GregorianCalendar();   
         gc.setTime(date);   
         gc.set(Calendar.DATE, gc.get(Calendar.DATE) - dias);   
         return gc.getTime();   
  	} 
  	
  	public Date convertStringToDate(String data){
		 DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 Date date = null;
		try {
			date = (Date)formatter.parse(data);
		} catch (ParseException e) {
		} 
		 return date;
	}
  	
  	public String convertDatatoString(Date data) {
		String strdata;
		java.text.SimpleDateFormat formata = new java.text.SimpleDateFormat("dd/MM/yyyy");  
		strdata = formata.format(data);
	
		return strdata;
	}
  	
  	
  	public static boolean isDate(String dateStr){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = new GregorianCalendar();
        
        String dia;
        String mes;
        String ano;
        try{
        	 String[] data = dateStr.split("/");
             dia = data[0];
             mes = data[1];
             ano = data[2];
        	Integer.parseInt(dia);
            Integer.parseInt(mes);
            Integer.parseInt(ano);
        }catch(NumberFormatException e){
        	return false;
        }catch(Exception e){
        	return false;
        }

        // gerando o calendar  
        try {
			cal.setTime(df.parse(dateStr));
			if ((new Integer(dia)).intValue() != (new Integer(cal.get(Calendar.DAY_OF_MONTH))).intValue()) {
	            return (false);
	        } else if ((new Integer(mes)).intValue() != (new Integer(cal.get(Calendar.MONTH) + 1)).intValue()) {
	            return (false);
	        } else if ((new Integer(ano)).intValue() != (new Integer(cal.get(Calendar.YEAR))).intValue()) {
	            return (false);
	        }
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
        return (true);
    }
}