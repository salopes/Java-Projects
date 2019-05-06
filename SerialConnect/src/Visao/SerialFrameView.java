package Visao;

//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Enumeration;
//import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

//import com.sap.conn.jco.JCoException;
//import com.sun.java.swing.plaf.windows.resources.windows;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import serial.SerialComRead;

public class SerialFrameView {

	protected Shell shlSerialConnect;
	private Text textStatus;
	private Text textMOTONCO;
	
	public String sMT="", pesoMT="", pesoSAP="";
	public SerialComRead leitura = new SerialComRead("COM7", 9600, 0);
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SerialFrameView window = new SerialFrameView();
			window.open();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSerialConnect.open();
		shlSerialConnect.layout();
		while (!shlSerialConnect.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void setPesoMT(String pesoMT){
        this.pesoMT = pesoMT;
	}

	public String getPesoMT(){
        return pesoMT;
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSerialConnect = new Shell();
		shlSerialConnect.setImage(SWTResourceManager.getImage("C:\\Users\\SALOMAO\\Documents\\desenv_losango\\java_essencial\\SerialConnect\\src\\Imagens\\FS Sem fundo.png"));
		shlSerialConnect.setSize(370, 386);
		shlSerialConnect.setText("Comunica\u00E7\u00E3o SAP");
		
		Composite composite = new Composite(shlSerialConnect, SWT.BORDER);
		composite.setBounds(10, 10, 339, 334);
		
		Label lblNewLabel = new Label(composite, SWT.BORDER | SWT.CENTER);
		lblNewLabel.setBounds(10, 10, 315, 15);
		lblNewLabel.setText("Comunica\u00E7\u00E3o SERIAL MOTONGO");
		
		Label lblComunicao = new Label(composite, SWT.BORDER | SWT.CENTER);
		lblComunicao.setText("Status:");
		lblComunicao.setAlignment(SWT.LEFT);
		lblComunicao.setBounds(10, 63, 122, 19);
		
		textStatus = new Text(composite, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		textStatus.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		textStatus.setEditable(false);
		textStatus.setText("Desconectado!");
		textStatus.setBounds(149, 61, 102, 19);
		
		Label lblLedOn = new Label(composite, SWT.CENTER);
		lblLedOn.setEnabled(false);
		lblLedOn.setImage(SWTResourceManager.getImage(SerialFrameView.class, "/Imagens/Gree1.PNG"));
		lblLedOn.setBounds(269, 64, 55, 37);
		
		textMOTONCO = new Text(composite, SWT.BORDER | SWT.MULTI);
		textMOTONCO.setEditable(false);
		textMOTONCO.setBounds(10, 113, 315, 210);
		
		Label lblConteudo = new Label(composite, SWT.BORDER | SWT.CENTER);
		lblConteudo.setText("Conte\u00FAdo");
		lblConteudo.setAlignment(SWT.LEFT);
		lblConteudo.setBounds(10, 88, 122, 19);

		Button btnLerMOTONGO = new Button(composite, SWT.NONE);
		btnLerMOTONGO.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		
		btnLerMOTONGO.setText("LER MOTONGO");
		btnLerMOTONGO.setBounds(10, 32, 90, 25);
		
		Button btnDesconectar = new Button(composite, SWT.NONE);
		btnDesconectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				lblLedOn.setEnabled(false);
		        leitura.FecharCom();
	
				textStatus.setText("Desconectado!");
				
				btnDesconectar.setEnabled(false);
		        textMOTONCO.setText("");
		        
			}
		});
		btnDesconectar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnDesconectar.setText("DESCONECTAR");
		btnDesconectar.setEnabled(false);
		btnDesconectar.setBounds(124, 31, 90, 25);
		
		Button btnSair = new Button(composite, SWT.NONE);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				SerialFrameView window2 = new SerialFrameView();
				leitura.FecharCom();
				
				try {
					window2.finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnSair.setText("SAIR");
		btnSair.setBounds(235, 31, 90, 25);
		
		
		btnLerMOTONGO.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				lblLedOn.setEnabled(true);
 		        textMOTONCO.setEnabled(true);
	            textMOTONCO.setEditable(true);

	            leitura.HabilitarLeitura();
		        leitura.ObterIdDaPorta();
		        leitura.AbrirPorta();
		        leitura.LerDados();
		        	
				textStatus.setText("Conectado!");
				
				btnDesconectar.setEnabled(true);
              
				textMOTONCO.append("======= Comunicacao SERIAL =======\n");
				textMOTONCO.append("FBS BIoenergia: Proj S4HANA R.01\n");
				textMOTONCO.append("Conectado no MTONCO\n");
				textMOTONCO.append("Porta   : COM7\n");
				textMOTONCO.append("Baudrate: 9600\n");
				textMOTONCO.append("Timeout : 0\n");
				textMOTONCO.append("Conectado no SAP dev 200\n");
				textMOTONCO.append("dados são enviados a cada processo\n");
				textMOTONCO.append("==================================");
				
			}
		});
		
	}
}
