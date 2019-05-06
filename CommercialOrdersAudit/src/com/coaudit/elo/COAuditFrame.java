package com.coaudit.elo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

	
public class COAuditFrame extends JFrame{
	//Ctrl Shift O - Cria a importacao automatica (adiciona alinha import)
	public JTextField semanaField;
	public JTextArea  resultField;
	
	GregorianCalendar gc = new GregorianCalendar();
	Date dtINIAtual = gc.getTime();
	
	//Criand o construtor da Classe
	public COAuditFrame() {
		//Invocar o construor da super classe passando para ele a string que ser� utilziaa na construcao do titulo da janela 
		super("Auditoria de Pedidos Comerciais");
		
		criarMenu();
		criarFormulario();
	}
	
	private void criarMenu(){

		JMenu menuAuditaPedidos = new JMenu("Audita Pedidos");
		JMenu menuAjuda = new JMenu("Ajuda");
		
		JMenuItem menuItemProcessar = new JMenuItem("Processar");
		JMenuItem menuItemNovo      = new JMenuItem("Novo Processo");
		JMenuItem menuItemFechar    = new JMenuItem("Fechar");
		JMenuItem menuItemSobre     = new JMenuItem("Sobre");
		
		menuAuditaPedidos.add(menuItemProcessar);
		menuAuditaPedidos.add(menuItemNovo);
		menuAuditaPedidos.add(menuItemFechar);
		menuAjuda.add(menuItemSobre);

		JMenuBar barra = new JMenuBar();
		setJMenuBar(barra);

		barra.add(menuAuditaPedidos);
		barra.add(menuAjuda);
		
		NovoAction novoAction = new NovoAction();
		FecharAction fecharAction = new FecharAction();
		ProcessarAction processarAction = new ProcessarAction();
		SobreAction sobreAction = new SobreAction();
		
		menuItemNovo.addActionListener(novoAction);
		menuItemFechar.addActionListener(fecharAction);
		menuItemProcessar.addActionListener(processarAction);
		menuItemSobre.addActionListener(sobreAction);
		
	}
	
	private void criarFormulario(){
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel painelTitutlo = new JPanel();
		painelTitutlo.setLayout(new FlowLayout());

		JPanel painelProcessa = new JPanel();
		painelProcessa.setLayout(new FlowLayout());
		painelProcessa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		
		JLabel semanaLabel = new JLabel("Semana de Processamento");
		semanaField = new JTextField(10);
				
		painelProcessa.add(semanaLabel);
		painelProcessa.add(semanaField);

		JPanel painelResult = new JPanel();
		painelResult.setLayout(new FlowLayout());
		painelResult.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		
		resultField = new JTextArea();
		
		painelResult.add(resultField);
		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());
		
		ProcessarAction processarAction = new ProcessarAction();
		JButton botaoProcessa = new JButton("Auditar Pedidos");
		botaoProcessa.addActionListener(processarAction);
		
		LimparAction limparAction = new LimparAction();
		JButton botaoLimpar = new JButton("Limpar Resultado");
		botaoLimpar.addActionListener(limparAction);
		
		FecharAction fecharAction = new FecharAction();
		JButton botaoSair = new JButton("Fechar");
		botaoSair.addActionListener(fecharAction);
		
		panelBotoes.add(botaoProcessa);
		panelBotoes.add(botaoSair);
		panelBotoes.add(botaoLimpar);
		
		getContentPane().add(painelTitutlo, BorderLayout.NORTH);
		getContentPane().add(painelProcessa, BorderLayout.WEST);
		getContentPane().add(painelResult, BorderLayout.CENTER);
		getContentPane().add(panelBotoes, BorderLayout.SOUTH);
	}

	
	private class NovoAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			semanaField.setText("");
		}
	}

	private class FecharAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	private class ProcessarAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			resultField.setText("");
			//resultField.setSize(0, 250);
			//resultField.isShowing();
		}
	}
	
	private class SobreAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(null, "Auditor de Recebdimento de Pedidos Comerciais"
					                          + "Criado pela Equipe ICT Comercial\n"
					                          + "Verifica se os Pedidos Comerciais do Link.e.Entry\n"
					                          + "Foram recepcionados no IMS e, se existem Pedidos\n"
					                          + "no IMS que n�o foram registrados nos Relat�rios do\n"
					                          + "Link.e.Entr\n\n"
					                          + "ver: 1.0.1\n"
					                          + dtINIAtual
					                          + "\n Copyright 2016 AARS Inc. Todos os direitos reservados."
					                          , "Sobre"
					                          , JOptionPane.PLAIN_MESSAGE);
		}
	}

	private class LimparAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			resultField.setText("");
		}
	}
	
}
