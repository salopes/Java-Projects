/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package readdixml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Salomao Lopes
 */
public class ReadDIXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //objetos para construir e fazer a leitura do documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            //abre e faz o parser de um documento xml de acordo com o nome passado no parametro
            Document doc = builder.parse("C:\\Users\\SALOMAO\\Documents\\SEIDOR\\OutrosProjetos\\FSBIOENERGIA_S4HANA\\DI\\1821135751.xml");
            
            //cria uma lista de adicao. Busca no documento todas as tag adicao
            NodeList listaDeAdicao = doc.getElementsByTagName("adicao");
            
            //pego o tamanho da lista de adicao
            int tamanhoLista = listaDeAdicao.getLength();
            System.out.println("Qtd de ADICOES = " + tamanhoLista);
            
            //varredura na lista de adicao
            for (int i = 0; i < tamanhoLista; i++) {
                
                //pego cada item (listaDeAdicao) como um  (node)
                Node noAdicao = listaDeAdicao.item(i);
                
                System.out.println("Grupo Adiçao = " + i);
                
                //verifica se o noAdicao  do tipo element (e nao do tipo texto etc)
                if(noAdicao.getNodeType() == Node.ELEMENT_NODE){
                    
                    //caso seja um element, converto o no Adicao em Element adicao
                    Element elementoAdicao = (Element) noAdicao;
                    
                    //ja posso pegar o atributo do element
                    //String id = elementoAdicao.getAttribute("id");
                    
                    //imprimindo o id
                    //System.out.println("ID = " + i);      
                    
                    //recupero os nos filhos do elemento pessoa (nome, idade e peso)
                    NodeList listaDeFilhosDeAdicao = elementoAdicao.getChildNodes();
                    
                    //pego o tamanho da lista de filhos do elemento pessoa
                    int tamanhoListaFilhos = listaDeFilhosDeAdicao.getLength();
                            
                    //varredura na lista de filhos do elemento pessoa
                    for (int j = 0; j < tamanhoListaFilhos; j++) {
                        
                        //crio um no com o cada tag filho dentro do no adicao
                        Node noFilho = listaDeFilhosDeAdicao.item(j);
                        
                        //verifico se sÃ£o tipo element
                        System.out.println("noFilho.getNodeType" + noFilho.getNodeType());
                        
                        if(noFilho.getNodeType() == Node.ELEMENT_NODE){
                            
                            //converto o no filho em element filho
                            Element elementoFilho = (Element) noFilho;
                            
                            //verifico em qual filho estamos pela tag
                            switch(elementoFilho.getTagName()){
// ----
                            case "codigoAcrescimo":
                            	//imprimo o codigoAcrescimo
                            	System.out.println("codigoAcrescimo=" + elementoFilho.getTextContent());
                            	break;

                            	case "denominacao":
                            	//imprimo o denominacao
                            	System.out.println("denominacao=" + elementoFilho.getTextContent());
                            	break;

                            	case "moedaNegociadaCodigo":
                            	//imprimo o moedaNegociadaCodigo
                            	System.out.println("moedaNegociadaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "moedaNegociadaNome":
                            	//imprimo o moedaNegociadaNome
                            	System.out.println("moedaNegociadaNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorMoedaNegociada":
                            	//imprimo o valorMoedaNegociada
                            	System.out.println("valorMoedaNegociada=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorReais":
                            	//imprimo o valorReais
                            	System.out.println("valorReais=" + elementoFilho.getTextContent());
                            	break;

                            	case "cideValorAliquotaEspecifica":
                            	//imprimo o cideValorAliquotaEspecifica
                            	System.out.println("cideValorAliquotaEspecifica=" + elementoFilho.getTextContent());
                            	break;

                            	case "cideValorDevido":
                            	//imprimo o cideValorDevido
                            	System.out.println("cideValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "cideValorRecolher":
                            	//imprimo o cideValorRecolher
                            	System.out.println("cideValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "codigoRelacaoCompradorVendedor":
                            	//imprimo o codigoRelacaoCompradorVendedor
                            	System.out.println("codigoRelacaoCompradorVendedor=" + elementoFilho.getTextContent());
                            	break;

                            	case "codigoVinculoCompradorVendedor":
                            	//imprimo o codigoVinculoCompradorVendedor
                            	System.out.println("codigoVinculoCompradorVendedor=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaAdValorem":
                            	//imprimo o cofinsAliquotaAdValorem
                            	System.out.println("cofinsAliquotaAdValorem=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaEspecificaQuantidadeUnidade":
                            	//imprimo o cofinsAliquotaEspecificaQuantidadeUnidade
                            	System.out.println("cofinsAliquotaEspecificaQuantidadeUnidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaEspecificaValor":
                            	//imprimo o cofinsAliquotaEspecificaValor
                            	System.out.println("cofinsAliquotaEspecificaValor=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaReduzida":
                            	//imprimo o cofinsAliquotaReduzida
                            	System.out.println("cofinsAliquotaReduzida=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaValorDevido":
                            	//imprimo o cofinsAliquotaValorDevido
                            	System.out.println("cofinsAliquotaValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "cofinsAliquotaValorRecolher":
                            	//imprimo o cofinsAliquotaValorRecolher
                            	System.out.println("cofinsAliquotaValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaIncoterm":
                            	//imprimo o condicaoVendaIncoterm
                            	System.out.println("condicaoVendaIncoterm=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaLocal":
                            	//imprimo o condicaoVendaLocal
                            	System.out.println("condicaoVendaLocal=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaMetodoValoracaoCodigo":
                            	//imprimo o condicaoVendaMetodoValoracaoCodigo
                            	System.out.println("condicaoVendaMetodoValoracaoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaMetodoValoracaoNome":
                            	//imprimo o condicaoVendaMetodoValoracaoNome
                            	System.out.println("condicaoVendaMetodoValoracaoNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaMoedaCodigo":
                            	//imprimo o condicaoVendaMoedaCodigo
                            	System.out.println("condicaoVendaMoedaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaMoedaNome":
                            	//imprimo o condicaoVendaMoedaNome
                            	System.out.println("condicaoVendaMoedaNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaValorMoeda":
                            	//imprimo o condicaoVendaValorMoeda
                            	System.out.println("condicaoVendaValorMoeda=" + elementoFilho.getTextContent());
                            	break;

                            	case "condicaoVendaValorReais":
                            	//imprimo o condicaoVendaValorReais
                            	System.out.println("condicaoVendaValorReais=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisCoberturaCambialCodigo":
                            	//imprimo o dadosCambiaisCoberturaCambialCodigo
                            	System.out.println("dadosCambiaisCoberturaCambialCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisCoberturaCambialNome":
                            	//imprimo o dadosCambiaisCoberturaCambialNome
                            	System.out.println("dadosCambiaisCoberturaCambialNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisInstituicaoFinanciadoraCodigo":
                            	//imprimo o dadosCambiaisInstituicaoFinanciadoraCodigo
                            	System.out.println("dadosCambiaisInstituicaoFinanciadoraCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisInstituicaoFinanciadoraNome":
                            	//imprimo o dadosCambiaisInstituicaoFinanciadoraNome
                            	System.out.println("dadosCambiaisInstituicaoFinanciadoraNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisMotivoSemCoberturaCodigo":
                            	//imprimo o dadosCambiaisMotivoSemCoberturaCodigo
                            	System.out.println("dadosCambiaisMotivoSemCoberturaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisValorRealCambio":
                            	//imprimo o dadosCambiaisValorRealCambio
                            	System.out.println("dadosCambiaisValorRealCambio=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCambiaisMotivoSemCoberturaNome":
                            	//imprimo o dadosCambiaisMotivoSemCoberturaNome
                            	System.out.println("dadosCambiaisMotivoSemCoberturaNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCargaPaisProcedenciaCodigo":
                            	//imprimo o dadosCargaPaisProcedenciaCodigo
                            	System.out.println("dadosCargaPaisProcedenciaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCargaUrfEntradaCodigo":
                            	//imprimo o dadosCargaUrfEntradaCodigo
                            	System.out.println("dadosCargaUrfEntradaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosCargaViaTransporteCodigo":
                            	//imprimo o dadosCargaViaTransporteCodigo
                            	System.out.println("dadosCargaViaTransporteCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaAplicacao":
                            	//imprimo o dadosMercadoriaAplicacao
                            	System.out.println("dadosMercadoriaAplicacao=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaCodigoNaladiNCCA":
                            	//imprimo o dadosMercadoriaCodigoNaladiNCCA
                            	System.out.println("dadosMercadoriaCodigoNaladiNCCA=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaCodigoNaladiSH":
                            	//imprimo o dadosMercadoriaCodigoNaladiSH
                            	System.out.println("dadosMercadoriaCodigoNaladiSH=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaCodigoNcm":
                            	//imprimo o dadosMercadoriaCodigoNcm
                            	System.out.println("dadosMercadoriaCodigoNcm=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaCondicao":
                            	//imprimo o dadosMercadoriaCondicao
                            	System.out.println("dadosMercadoriaCondicao=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaMedidaEstatisticaQuantidade":
                            	//imprimo o dadosMercadoriaMedidaEstatisticaQuantidade
                            	System.out.println("dadosMercadoriaMedidaEstatisticaQuantidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaMedidaEstatisticaUnidade":
                            	//imprimo o dadosMercadoriaMedidaEstatisticaUnidade
                            	System.out.println("dadosMercadoriaMedidaEstatisticaUnidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaNomeNcm":
                            	//imprimo o dadosMercadoriaNomeNcm
                            	System.out.println("dadosMercadoriaNomeNcm=" + elementoFilho.getTextContent());
                            	break;

                            	case "dadosMercadoriaPesoLiquido":
                            	//imprimo o dadosMercadoriaPesoLiquido
                            	System.out.println("dadosMercadoriaPesoLiquido=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrCoeficienteReducao":
                            	//imprimo o dcrCoeficienteReducao
                            	System.out.println("dcrCoeficienteReducao=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrIdentificacao":
                            	//imprimo o dcrIdentificacao
                            	System.out.println("dcrIdentificacao=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrValorDevido":
                            	//imprimo o dcrValorDevido
                            	System.out.println("dcrValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrValorDolar":
                            	//imprimo o dcrValorDolar
                            	System.out.println("dcrValorDolar=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrValorReal":
                            	//imprimo o dcrValorReal
                            	System.out.println("dcrValorReal=" + elementoFilho.getTextContent());
                            	break;

                            	case "dcrValorRecolher":
                            	//imprimo o dcrValorRecolher
                            	System.out.println("dcrValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "numeroDestaque":
                            	//imprimo o numeroDestaque
                            	System.out.println("numeroDestaque=" + elementoFilho.getTextContent());
                            	break;

                            	case "fabricanteCidade":
                            	//imprimo o fabricanteCidade
                            	System.out.println("fabricanteCidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "fabricanteEstado":
                            	//imprimo o fabricanteEstado
                            	System.out.println("fabricanteEstado=" + elementoFilho.getTextContent());
                            	break;

                            	case "fabricanteLogradouro":
                            	//imprimo o fabricanteLogradouro
                            	System.out.println("fabricanteLogradouro=" + elementoFilho.getTextContent());
                            	break;

                            	case "fabricanteNome":
                            	//imprimo o fabricanteNome
                            	System.out.println("fabricanteNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "fabricanteNumero":
                            	//imprimo o fabricanteNumero
                            	System.out.println("fabricanteNumero=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorCidade":
                            	//imprimo o fornecedorCidade
                            	System.out.println("fornecedorCidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorComplemento":
                            	//imprimo o fornecedorComplemento
                            	System.out.println("fornecedorComplemento=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorEstado":
                            	//imprimo o fornecedorEstado
                            	System.out.println("fornecedorEstado=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorLogradouro":
                            	//imprimo o fornecedorLogradouro
                            	System.out.println("fornecedorLogradouro=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorNome":
                            	//imprimo o fornecedorNome
                            	System.out.println("fornecedorNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "fornecedorNumero":
                            	//imprimo o fornecedorNumero
                            	System.out.println("fornecedorNumero=" + elementoFilho.getTextContent());
                            	break;

                            	case "freteMoedaNegociadaCodigo":
                            	//imprimo o freteMoedaNegociadaCodigo
                            	System.out.println("freteMoedaNegociadaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "freteValorMoedaNegociada":
                            	//imprimo o freteValorMoedaNegociada
                            	System.out.println("freteValorMoedaNegociada=" + elementoFilho.getTextContent());
                            	break;

                            	case "freteValorReais":
                            	//imprimo o freteValorReais
                            	System.out.println("freteValorReais=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAcordoTarifarioTipoCodigo":
                            	//imprimo o iiAcordoTarifarioTipoCodigo
                            	System.out.println("iiAcordoTarifarioTipoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaAcordo":
                            	//imprimo o iiAliquotaAcordo
                            	System.out.println("iiAliquotaAcordo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaAdValorem":
                            	//imprimo o iiAliquotaAdValorem
                            	System.out.println("iiAliquotaAdValorem=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaPercentualReducao":
                            	//imprimo o iiAliquotaPercentualReducao
                            	System.out.println("iiAliquotaPercentualReducao=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaReduzida":
                            	//imprimo o iiAliquotaReduzida
                            	System.out.println("iiAliquotaReduzida=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaValorCalculado":
                            	//imprimo o iiAliquotaValorCalculado
                            	System.out.println("iiAliquotaValorCalculado=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaValorDevido":
                            	//imprimo o iiAliquotaValorDevido
                            	System.out.println("iiAliquotaValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaValorRecolher":
                            	//imprimo o iiAliquotaValorRecolher
                            	System.out.println("iiAliquotaValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiAliquotaValorReduzido":
                            	//imprimo o iiAliquotaValorReduzido
                            	System.out.println("iiAliquotaValorReduzido=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiBaseCalculo":
                            	//imprimo o iiBaseCalculo
                            	System.out.println("iiBaseCalculo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiFundamentoLegalCodigo":
                            	//imprimo o iiFundamentoLegalCodigo
                            	System.out.println("iiFundamentoLegalCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiMotivoAdmissaoTemporariaCodigo":
                            	//imprimo o iiMotivoAdmissaoTemporariaCodigo
                            	System.out.println("iiMotivoAdmissaoTemporariaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiRegimeTributacaoCodigo":
                            	//imprimo o iiRegimeTributacaoCodigo
                            	System.out.println("iiRegimeTributacaoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "iiRegimeTributacaoNome":
                            	//imprimo o iiRegimeTributacaoNome
                            	System.out.println("iiRegimeTributacaoNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaAdValorem":
                            	//imprimo o ipiAliquotaAdValorem
                            	System.out.println("ipiAliquotaAdValorem=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaEspecificaCapacidadeRecipciente":
                            	//imprimo o ipiAliquotaEspecificaCapacidadeRecipciente
                            	System.out.println("ipiAliquotaEspecificaCapacidadeRecipciente=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaEspecificaQuantidadeUnidadeMedida":
                            	//imprimo o ipiAliquotaEspecificaQuantidadeUnidadeMedida
                            	System.out.println("ipiAliquotaEspecificaQuantidadeUnidadeMedida=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaEspecificaTipoRecipienteCodigo":
                            	//imprimo o ipiAliquotaEspecificaTipoRecipienteCodigo
                            	System.out.println("ipiAliquotaEspecificaTipoRecipienteCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaEspecificaValorUnidadeMedida":
                            	//imprimo o ipiAliquotaEspecificaValorUnidadeMedida
                            	System.out.println("ipiAliquotaEspecificaValorUnidadeMedida=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaNotaComplementarTIPI":
                            	//imprimo o ipiAliquotaNotaComplementarTIPI
                            	System.out.println("ipiAliquotaNotaComplementarTIPI=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaReduzida":
                            	//imprimo o ipiAliquotaReduzida
                            	System.out.println("ipiAliquotaReduzida=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaValorDevido":
                            	//imprimo o ipiAliquotaValorDevido
                            	System.out.println("ipiAliquotaValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiAliquotaValorRecolher":
                            	//imprimo o ipiAliquotaValorRecolher
                            	System.out.println("ipiAliquotaValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiRegimeTributacaoCodigo":
                            	//imprimo o ipiRegimeTributacaoCodigo
                            	System.out.println("ipiRegimeTributacaoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "ipiRegimeTributacaoNome":
                            	//imprimo o ipiRegimeTributacaoNome
                            	System.out.println("ipiRegimeTributacaoNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "descricaoMercadoria":
                            	//imprimo o descricaoMercadoria
                            	System.out.println("descricaoMercadoria=" + elementoFilho.getTextContent());
                            	break;

                            	case "numeroSequencialItem":
                            	//imprimo o numeroSequencialItem
                            	System.out.println("numeroSequencialItem=" + elementoFilho.getTextContent());
                            	break;

                            	case "quantidade":
                            	//imprimo o quantidade
                            	System.out.println("quantidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "unidadeMedida":
                            	//imprimo o unidadeMedida
                            	System.out.println("unidadeMedida=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorUnitario":
                            	//imprimo o valorUnitario
                            	System.out.println("valorUnitario=" + elementoFilho.getTextContent());
                            	break;

                            	case "numeroAdicao":
                            	//imprimo o numeroAdicao
                            	System.out.println("numeroAdicao=" + elementoFilho.getTextContent());
                            	break;

                            	case "numeroDI":
                            	//imprimo o numeroDI
                            	System.out.println("numeroDI=" + elementoFilho.getTextContent());
                            	break;

                            	case "numeroLI":
                            	//imprimo o numeroLI
                            	System.out.println("numeroLI=" + elementoFilho.getTextContent());
                            	break;

                            	case "paisAquisicaoMercadoriaCodigo":
                            	//imprimo o paisAquisicaoMercadoriaCodigo
                            	System.out.println("paisAquisicaoMercadoriaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "paisAquisicaoMercadoriaNome":
                            	//imprimo o paisAquisicaoMercadoriaNome
                            	System.out.println("paisAquisicaoMercadoriaNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "paisOrigemMercadoriaCodigo":
                            	//imprimo o paisOrigemMercadoriaCodigo
                            	System.out.println("paisOrigemMercadoriaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "paisOrigemMercadoriaNome":
                            	//imprimo o paisOrigemMercadoriaNome
                            	System.out.println("paisOrigemMercadoriaNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsBaseCalculoAliquotaICMS":
                            	//imprimo o pisCofinsBaseCalculoAliquotaICMS
                            	System.out.println("pisCofinsBaseCalculoAliquotaICMS=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsBaseCalculoFundamentoLegalCodigo":
                            	//imprimo o pisCofinsBaseCalculoFundamentoLegalCodigo
                            	System.out.println("pisCofinsBaseCalculoFundamentoLegalCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsBaseCalculoPercentualReducao":
                            	//imprimo o pisCofinsBaseCalculoPercentualReducao
                            	System.out.println("pisCofinsBaseCalculoPercentualReducao=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsBaseCalculoValor":
                            	//imprimo o pisCofinsBaseCalculoValor
                            	System.out.println("pisCofinsBaseCalculoValor=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsFundamentoLegalReducaoCodigo":
                            	//imprimo o pisCofinsFundamentoLegalReducaoCodigo
                            	System.out.println("pisCofinsFundamentoLegalReducaoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsRegimeTributacaoCodigo":
                            	//imprimo o pisCofinsRegimeTributacaoCodigo
                            	System.out.println("pisCofinsRegimeTributacaoCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisCofinsRegimeTributacaoNome":
                            	//imprimo o pisCofinsRegimeTributacaoNome
                            	System.out.println("pisCofinsRegimeTributacaoNome=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaAdValorem":
                            	//imprimo o pisPasepAliquotaAdValorem
                            	System.out.println("pisPasepAliquotaAdValorem=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaEspecificaQuantidadeUnidade":
                            	//imprimo o pisPasepAliquotaEspecificaQuantidadeUnidade
                            	System.out.println("pisPasepAliquotaEspecificaQuantidadeUnidade=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaEspecificaValor":
                            	//imprimo o pisPasepAliquotaEspecificaValor
                            	System.out.println("pisPasepAliquotaEspecificaValor=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaReduzida":
                            	//imprimo o pisPasepAliquotaReduzida
                            	System.out.println("pisPasepAliquotaReduzida=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaValorDevido":
                            	//imprimo o pisPasepAliquotaValorDevido
                            	System.out.println("pisPasepAliquotaValorDevido=" + elementoFilho.getTextContent());
                            	break;

                            	case "pisPasepAliquotaValorRecolher":
                            	//imprimo o pisPasepAliquotaValorRecolher
                            	System.out.println("pisPasepAliquotaValorRecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "relacaoCompradorVendedor":
                            	//imprimo o relacaoCompradorVendedor
                            	System.out.println("relacaoCompradorVendedor=" + elementoFilho.getTextContent());
                            	break;

                            	case "seguroMoedaNegociadaCodigo":
                            	//imprimo o seguroMoedaNegociadaCodigo
                            	System.out.println("seguroMoedaNegociadaCodigo=" + elementoFilho.getTextContent());
                            	break;

                            	case "seguroValorMoedaNegociada":
                            	//imprimo o seguroValorMoedaNegociada
                            	System.out.println("seguroValorMoedaNegociada=" + elementoFilho.getTextContent());
                            	break;

                            	case "seguroValorReais":
                            	//imprimo o seguroValorReais
                            	System.out.println("seguroValorReais=" + elementoFilho.getTextContent());
                            	break;

                            	case "sequencialRetificacao":
                            	//imprimo o sequencialRetificacao
                            	System.out.println("sequencialRetificacao=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorMultaARecolher":
                            	//imprimo o valorMultaARecolher
                            	System.out.println("valorMultaARecolher=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorMultaARecolherAjustado":
                            	//imprimo o valorMultaARecolherAjustado
                            	System.out.println("valorMultaARecolherAjustado=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorReaisFreteInternacional":
                            	//imprimo o valorReaisFreteInternacional
                            	System.out.println("valorReaisFreteInternacional=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorReaisSeguroInternacional":
                            	//imprimo o valorReaisSeguroInternacional
                            	System.out.println("valorReaisSeguroInternacional=" + elementoFilho.getTextContent());
                            	break;

                            	case "valorTotalCondicaoVenda":
                            	//imprimo o valorTotalCondicaoVenda
                            	System.out.println("valorTotalCondicaoVenda=" + elementoFilho.getTextContent());
                            	break;

                            	case "vinculoCompradorVendedor":
                            	//imprimo o vinculoCompradorVendedor
                            	System.out.println("vinculoCompradorVendedor=" + elementoFilho.getTextContent());
                            	break;
// ----
                            
                            }
                        }
                    }
                }
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ReadDIXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ReadDIXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadDIXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

