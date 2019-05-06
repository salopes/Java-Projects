package Visao;

//import java.awt.*;
import javax.swing.*;

public class SerialFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Declaração de JLabel
    JLabel lb_listaPorta
         , lb_tipoPorta
         , lb_nomePorta;  
    
  //Declaração de JTextField
    JTextField tf_listaPorta
             , tf_tipoPorta
             , tf_nomePorta;
    
  //Declaração de JComboBox
    //JComboBox cb_cor, cb_marca;
    
  //Declaração de JButton
    JButton bt_salvar
          , bt_limpar
          , bt_fechar;   
	
  //Método construtor da classe
	public SerialFrame(){   
		    setTitle("Monitor de Portas Seriais"); //Definição do título da janela
	        setSize(550, 450); //Definição do tamanho da janela   
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Define se a janela fechará o sistema ou apenas ela mesma (neste caso apenas ela mesma
	        
	        //Nesta parte estou inicializando os meus componentes
	        lb_listaPorta = new JLabel("Lista Portas");   
	        lb_tipoPorta  = new JLabel("Tipo Porta");   
	        lb_nomePorta  = new JLabel("Nome Portas");   

	        tf_listaPorta = new JTextField("");           
	        tf_tipoPorta  = new JTextField("");   
	        tf_nomePorta  = new JTextField("");   

	        //cb_cor        = new JComboBox();   
	        //cb_marca      = new JComboBox();
	        
	        bt_salvar      = new JButton("Salvar");   
	        bt_limpar      = new JButton("Limpar");   
	        bt_fechar      = new JButton("Fechar");   

	        //Aqui estou definindo os ítens do JComboBox cb_cor 
	        //cb_cor.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione a Cor","Amarelo", "Azul", "Branco", "Cinza","Grafite", "Prata", "Preto", "Verde", "Vermelho"}));   

	        //Aqui estou definindo os ítens do JComboBox cb_marca 
	        //cb_marca.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Selecione a Marca","Ford", "Chevrolet", "Toyota", "Wolkswagem","Fiat", "Honda"}));
	        
	        getContentPane().setLayout(null);//Define o gerenciador de layout como null, ou seja, você pode colocar os componentes em qualquer lugar do formulário   
	        //Adiciona os componentes ao formulário
	        getContentPane().add(lb_listaPorta);   
	        getContentPane().add(lb_tipoPorta);   
	        getContentPane().add(lb_nomePorta);

	        //getContentPane().add(cb_cor);   
	        //getContentPane().add(cb_marca);
	        
	        getContentPane().add(tf_listaPorta);   
	        getContentPane().add(tf_tipoPorta);   
	        getContentPane().add(tf_nomePorta);
	        
	        getContentPane().add(bt_salvar);   
	        getContentPane().add(bt_limpar);   
	        getContentPane().add(bt_fechar);   

	        //Define o posicionamento dos componentes na tela (posição da coluna, posição da linha, comprimento da linha, altura da linha)
	        lb_listaPorta  .setBounds(20, 20, 100, 15);   
	        tf_listaPorta  .setBounds(20, 40, 80, 25);   
	        
	        lb_tipoPorta   .setBounds(115,20, 100, 15);   
	        tf_tipoPorta   .setBounds(115,40, 150, 25);   
	        
	        lb_nomePorta   .setBounds(20, 70, 100, 15);   
	        tf_nomePorta   .setBounds(20, 90, 150, 25);
	        
	        //lb_marca        .setBounds(20, 120,300, 15);   
	        //cb_marca        .setBounds(20, 140,250, 25);
	        //lb_cor          .setBounds(20,170,200, 15);   
	        //cb_cor          .setBounds(20,190,250, 25);

	        bt_salvar       .setBounds(20,340,80, 30);   
	        bt_limpar       .setBounds(105,340,80, 30);   
	        bt_fechar       .setBounds(190,340,80, 30);   
	}

}
