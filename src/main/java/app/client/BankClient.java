package app.client;


import bank.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class BankClient extends WebServiceGatewaySupport {

    public List<Account> getAccountsRequest(String id) {
        GetAccountsRequest request = new GetAccountsRequest();
        request.setClientId(id);

        GetAccountsResponse response = (GetAccountsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/ws"));
        return response.getAccounts();
    }

    public List<History> getHistory(String nrb){
        GetTransactionsRequest request = new GetTransactionsRequest();
        request.setNrb(nrb);
        GetTransactionsResponse response = (GetTransactionsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback("http://localhost:8080/ws"));
        return response.getTransactions();
    }
}
