package com.trustedshops.androidsdk.trustbadge;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class TrustbadgeOrder {

    /**
     * @required
     */
    protected String tsCheckoutOrderNr;
    /**
     * @required
     */
    protected String tsCheckoutBuyerEmail;
    /**
     * @required
     */
    protected String tsCheckoutOrderAmount;
    /**
     * @required
     */
    protected String tsCheckoutOrderCurrency;
    /**
     * @required
     */
    protected String tsCheckoutOrderPaymentType;
    protected String tsCheckoutOrderEstDeliveryDate;
    protected ArrayList<Product> tsCheckoutProductItems;


    public String getTrustbadgeCheckoutDataHtml() {
        DocumentBuilderFactory domFact = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = domFact.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = (Element) document.createElement("rootElement");
            document.appendChild(root);
            root.appendChild(document.createTextNode("Some"));
            root.appendChild(document.createTextNode(" "));
            root.appendChild(document.createTextNode("text"));

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource domSource = new DOMSource(document);

            transformer.transform(domSource, result);

            return writer.toString();
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            Log.d("TSDEBUG", "Parser exception " + pce.getMessage());
        }  catch(TransformerException ex) {

        }

        return "empty";

    }
}
