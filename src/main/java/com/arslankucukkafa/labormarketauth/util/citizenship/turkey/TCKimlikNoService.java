package com.arslankucukkafa.labormarketauth.util.citizenship.turkey;

import com.arslankucukkafa.labormarketauth.util.citizenship.VerifyCtizenship;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
public class TCKimlikNoService implements VerifyCtizenship {

    private final WebClient webClient;

    public TCKimlikNoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://tckimlik.nvi.gov.tr/service/kpspublic.asmx").build();
    }

    @Override
    public boolean verifyCtizenship(String tcKimlikNo, String firstName, String lastName, int birthYear) {
        String soapRequestBody = createSoapRequestBody(tcKimlikNo, firstName, lastName, birthYear);

        String response = webClient.post()
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula")
                .bodyValue(soapRequestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseSoapResponse(response);
    }

    private String createSoapRequestBody(String tcKimlikNo, String firstName, String lastName, int birthYear) {
        return """
                <?xml version="1.0" encoding="utf-8"?>
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                    <soap:Body>
                        <TCKimlikNoDogrula xmlns="http://tckimlik.nvi.gov.tr/WS">
                            <TCKimlikNo>%s</TCKimlikNo>
                            <Ad>%s</Ad>
                            <Soyad>%s</Soyad>
                            <DogumYili>%d</DogumYili>
                        </TCKimlikNoDogrula>
                    </soap:Body>
                </soap:Envelope>
                """.formatted(tcKimlikNo, firstName, lastName, birthYear);
    }

    private boolean parseSoapResponse(String response) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(response)));

            Node resultNode = document.getElementsByTagName("TCKimlikNoDogrulaResult").item(0);
            return Boolean.parseBoolean(resultNode.getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
